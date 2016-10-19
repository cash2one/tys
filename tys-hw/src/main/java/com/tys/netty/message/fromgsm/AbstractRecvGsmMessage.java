package com.tys.netty.message.fromgsm;

import com.tys.util.MUtil;


/**
 *	接收GSM端发来的数据包
 */
public abstract class AbstractRecvGsmMessage {

	//IProcess 索引KEY
	private String processType;
	
	// 数据头
	private String bigType;

	private String terminalNo;

	private String command;

	private String[] parameters;
	
	
	public String getProcessType() {
		return processType;
	}

	public void setProcessType(String processType) {
		this.processType = processType;
	}

	public String getBigType() {
		return bigType;
	}

	public void setBigType(String bigType) {
		this.bigType = bigType;
	}

	public String getTerminalNo() {
		return terminalNo;
	}

	public void setTerminalNo(String terminalNo) {
		this.terminalNo = terminalNo;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * 解析参数
	 * @param parameters
	 * @return	解析成功与否
	 */
	protected abstract boolean parseParameters(String[] parameters);

	
	
	public static AbstractRecvGsmMessage revMessage(String msgStr) {
		AbstractRecvGsmMessage baseMessage = null;
		if(msgStr.endsWith(";")){
			msgStr = msgStr.substring(0, msgStr.length()-1);//去掉最后的分号
			String[] msgStrs = msgStr.split(":");

			if (msgStrs.length == 2) {
				if("ii,limei".contains(msgStrs[0])){
					String[] paras = msgStrs[1].split(",");

					if (paras.length > 1) {
						
						if (msgStrs[0].equals("limei")) {
							// 学生卡上传数据
							baseMessage = new UploadReqMessage();
							baseMessage.setBigType(msgStrs[0]);//数据头
							baseMessage.setProcessType("upload");//IProcess 索引值
							baseMessage.setCommand(paras[1]);//CMD
							baseMessage.setTerminalNo(paras[0]);// IMEI
							baseMessage.setParameters(paras);//参数数组
							if(!baseMessage.parseParameters(paras)){
								//数据解析失败
								MUtil.log("parse error!");
								return null;
							}
						} else {
							// 学生卡发送心跳包与指令回复
							baseMessage = new SimpleRecvMessage();
							baseMessage.setBigType(msgStrs[0]);//数据头
							baseMessage.setProcessType(paras[1]);//IProcess 索引值
							baseMessage.setCommand(paras[1]);// CMD
							baseMessage.setTerminalNo(paras[0]);// IMEI
							baseMessage.setParameters(paras);//参数数组
						}
						
					}
				}
				
			}
		
		}

		return baseMessage;
	}

	public static String sendMessage(AbstractRecvGsmMessage baseMessage) {
		StringBuffer sb = new StringBuffer();
		sb.append(baseMessage.getBigType()).append(':');
		
		for(String para : baseMessage.getParameters()){
			sb.append(para).append(',');
		}
		sb.deleteCharAt(sb.length()-1);//去掉最后一个逗号

		return sb.toString();
	}
}
