package com.tys.netty.handler;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tys.netty.message.fromgsm.AbstractRecvGsmMessage;
import com.tys.netty.process.IProcess;
import com.tys.netty.utils.ChannelHolder;
import com.tys.util.MUtil;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Service
@ChannelHandler.Sharable
public class GisHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

	private ApplicationContext applicationContext;

	private Map<String, IProcess> processes = new HashMap<String, IProcess>();
	
	@Autowired
	private ChannelHolder channelHolder; 
	
	
	@PostConstruct
	public void init() {
		Map<java.lang.String, IProcess> processMap = applicationContext.getBeansOfType(IProcess.class);
		for (IProcess process : processMap.values()) {
			processes.put(process.getType(), process);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
		
		MUtil.log(ctx.channel().remoteAddress() + "\t\t<-- " + msg);
		// 处理多段数据
		AbstractRecvGsmMessage baseMessage = AbstractRecvGsmMessage.revMessage(msg);

		if (baseMessage != null) {
			IProcess process = processes.get(baseMessage.getProcessType());
			if (process != null) {
				if ("A,B".contains(baseMessage.getCommand())) {// 心跳或者时间校正，更新在线状态
					channelHolder.put(baseMessage.getTerminalNo(), ctx.channel());
				}

				try {
					process.dealWith(ctx.channel(), baseMessage, msg);
				} catch (Exception e) {
					e.printStackTrace();
					ChannelHolder.notify(baseMessage.getTerminalNo(), baseMessage.getCommand(), null, null, "error");
				}
			} else {
				// 下发指令，接收到终端回复,参考ChannelHolder.sendMesage
				ChannelHolder.notify(baseMessage.getTerminalNo(), baseMessage.getCommand(),
						baseMessage.getParameters(), null, null);
			}
		}
	}
	
	
	@Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
            } else if (e.state() == IdleState.WRITER_IDLE) {
            }
        }
    }


	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		super.exceptionCaught(ctx, cause);
		MUtil.log(cause.toString());
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		super.handlerRemoved(ctx);
		Channel channel = ctx.channel();
		String imeiAtt = (String) channel.attr(ChannelHolder.ATTR_IMEI).get();
		if (imeiAtt != null) {
			MUtil.log(channel.remoteAddress() + " imei=" + imeiAtt + " disconnect!");
			channelHolder.remove(imeiAtt);
		}
	}
	
	
	
	
	

}
