package com.tys.netty.process;

import java.net.InetAddress;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tys.entity.MdEquipment;
import com.tys.entity.MdTcpServer;
import com.tys.netty.message.fromgsm.AbstractRecvGsmMessage;
import com.tys.netty.message.togsm.SetServerMessage;
import com.tys.netty.message.togsm.TimeCheckResMessage;
import com.tys.netty.util.NetUtil;
import com.tys.netty.utils.ChannelHolder;
import com.tys.service.MdEquipmentService;
import com.tys.service.MdTcpServerService;
import com.tys.util.MStrUtil;

import io.netty.channel.Channel;

@Service
public class TimeCheckProcess implements IProcess,ApplicationContextAware {
	
	@Value("${tcp.port}")
	private int port;
	
	@Autowired
	private MdTcpServerService mdTcpServerService;
	@Autowired
	private MdEquipmentService mdEquipmentService;
	
	
	
	
	@Override
	public String getType() {
		return "B";
	}
	
	
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//		mdTcpServerService = applicationContext.getBean(MdServerService.class);
//		relRegionEquipmentService = applicationContext.getBean(RelRegionEquipmentService.class);
	}

	@Override
	public void dealWith(Channel channel, AbstractRecvGsmMessage message, String msg) {
		TimeCheckResMessage timeCheckResMessage = new TimeCheckResMessage();
		timeCheckResMessage.setCommand(message.getCommand());
		timeCheckResMessage.setTerminalNo(message.getTerminalNo());
		timeCheckResMessage.sendMessage(channel);
		
		//检测分配IP
		if(ChannelHolder.serverStatus == 1){
			checkIpPort(message.getTerminalNo(), channel);
		}
		
	}
	
	
	private void checkIpPort(String imei, Channel channel){
		//判断ip,端口
		try {
			String localIp = NetUtil.getLocalIP();
			
			MdEquipment entity = mdEquipmentService.findByImei(imei);
			if(entity != null){
				String host = entity.getHost();
				Integer tmpPort = entity.getPort();
				
				if(host == null || tmpPort == null){
					//分配IP端口，先获取挂载量少的服务器
					MdTcpServer server = mdTcpServerService.findMinOnline();
					
					if(MStrUtil.isNull(server.getDomain()))
						entity.setHost(server.getIp());
					else
						entity.setHost(server.getDomain());
					entity.setPort(server.getPort());
					mdEquipmentService.update(entity);
					//下发修改IP端口指令
					setServer(imei, entity.getHost(), entity.getPort());
					
				} else {
					String ip;
					if(NetUtil.isValidIP(host)){
						ip = host;
					} else {
						//域名解析
						ip = InetAddress.getByName(host).getHostAddress();
					}
					
					if(!ip.equals(localIp) || port != tmpPort){
						//下发修改IP端口指令
						setServer(imei, entity.getHost(), entity.getPort());
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	private void setServer(final String imei, final String ipdomain, final Integer port){
		new Thread(){
			@Override
			public void run() {
				SetServerMessage msg = new SetServerMessage();
				msg.setTerminalNo(imei);
				msg.setCommand("U");
				msg.setMessage(ipdomain + "," + port);
				ChannelHolder.sendMesage(imei, msg);
			}
		}.start();
	}
	
	
	
}
