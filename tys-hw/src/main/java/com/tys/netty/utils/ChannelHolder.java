package com.tys.netty.utils;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tys.entity.MdRtData;
import com.tys.netty.connection.ChannelContext;
import com.tys.netty.dto.ResponseMessage;
import com.tys.netty.message.togsm.AbstractSendGsmMessage;
import com.tys.service.MdRtDataService;
import com.tys.util.MUtil;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

@Service
public class ChannelHolder {

	public static int serverStatus = 0;
	
	public static final AttributeKey<String> ATTR_IMEI = AttributeKey.valueOf("imei");

	//在线设备
	private static final Map<String, Channel> channels = new ConcurrentHashMap<String, Channel>();

	private static final Map<String, ChannelContext> channelContexts = new ConcurrentHashMap<String, ChannelContext>();

	@Autowired
	private MdRtDataService mdRtDataService;


	public void put(String terminalNo, Channel channel) {
		boolean update = false;
		if (channels.containsKey(terminalNo)) {
			Channel oldChannel = channels.get(terminalNo);

			if (oldChannel != channel) {// 新连接
				// 移除imei属性，在连接关闭时，不再更新数据库状态
				oldChannel.attr(ATTR_IMEI).remove();
				oldChannel.close();

				update = true;
			}
		} else {
			update = true;
		}

		if (update) {
			MUtil.log("New connect "+terminalNo);
			// 更新连接
			InetSocketAddress addr = (InetSocketAddress) channel.localAddress();
			
			MdRtData data = mdRtDataService.findByImei(terminalNo);
			if(data == null){
				data = new MdRtData();
				data.setImei(terminalNo);
				data.setIsOnline(1);
				data.setConnectTime(new Date());
				data.setCurIp(addr.getAddress().getHostAddress());
				data.setCurPort(addr.getPort());
				data.setCurHttpPort(0);
				mdRtDataService.save(data);
			} else {
				mdRtDataService.updateOnline(terminalNo, addr.getAddress().getHostAddress(), addr.getPort(), 1);
			}
			
			
			Attribute<String> imeiAtt = channel.attr(ATTR_IMEI);
			imeiAtt.set(terminalNo);
			channels.put(terminalNo, channel);
		}
	}

	public void remove(String terminalNo) {
		// 更新连接
		channels.remove(terminalNo);
		mdRtDataService.updateOnline(terminalNo, null, null, 0);
	}

	/**
	 * 发送数据到GSM，并取得回复数据,阻塞式
	 * 
	 * @param terminalNo
	 *            imei号
	 * @param toGsmMessage
	 *            发送的数据包
	 * @return 回复数据包
	 */
	public static ResponseMessage sendMesage(String terminalNo, AbstractSendGsmMessage toGsmMessage) {
		ResponseMessage response = new ResponseMessage();
		response.setImeiNo(terminalNo);
		response.setCommond(toGsmMessage.getCommand());

		Channel channel = channels.get(terminalNo);
		if (!isOnline(terminalNo)) {
			response.setSuccess(false);
			return response;
		}
		try {
			ChannelContext channelContext = new ChannelContext();
			channelContext.setChannel(channel);
			if ("D".equals(toGsmMessage.getCommand())) {
				channelContexts.put(terminalNo + "_" + "upload_tracker", channelContext);
			} else if ("Q".equals(toGsmMessage.getCommand())) {
				channelContexts.put(terminalNo + "_" + "Q01_tracker", channelContext);
			} else {
				channelContexts.put(terminalNo + "_" + toGsmMessage.getCommand(), channelContext);
			}

			toGsmMessage.sendMessage(channel);
			channelContext.setMessage("message timeout");
			// 等待GSM回复
			synchronized (channelContext) {
				channelContext.wait(200000);
			}

			channelContexts.remove(terminalNo + "_" + toGsmMessage.getCommand());
			String contextMsg = channelContext.getMessage();
			if ("error".equals(contextMsg) || "message timeout".equals(contextMsg)) {// 出错或者超时
				response.setSuccess(false);
			} else {
				response.setSuccess(true);
				response.setParameters(channelContext.getParameters());
				response.setObj(channelContext.getObj());
			}
			
			response.setMessage(contextMsg);
			

			return response;
		} catch (Exception e) {
			response.setSuccess(false);
			return response;
		}

	}

	/**
	 * 调用sendMesage后线程将阻塞，待接收到GSM回复后，唤醒线程,并传递相关参数
	 * 
	 * @param terminalNo
	 * @param command
	 * @param paras			解析出的参数数组
	 * @param obj			自定义参数
	 * @param message		额外信息
	 */
	public static void notify(String terminalNo, String command, String[] paras, Object obj, String message) {
		ChannelContext channelContext = channelContexts.get(terminalNo + "_" + command);
		if (channelContext != null) {
			channelContext.setMessage(message);
			channelContext.setParameters(paras);
			channelContext.setObj(obj);
			synchronized (channelContext) {
				channelContext.notify();
			}
		}

	}

	public static boolean isOnline(String imei) {
		Channel c = channels.get(imei);
		if (c != null) {
			return c.isActive();
		}
		return false;
	}
	
	
	public static int getOnlineCount(){
		return channels.size();
	}


}
