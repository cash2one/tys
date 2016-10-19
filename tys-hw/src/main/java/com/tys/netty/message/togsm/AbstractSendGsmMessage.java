package com.tys.netty.message.togsm;

import io.netty.channel.Channel;

/**
 *	发送/回复给GSM的数据包
 */
public abstract class AbstractSendGsmMessage {

	protected final static String BIG_TYPE = "rr,imei";//回复数据头
	protected final static String DATA_HEAD = "rr,limei";//发送指令数据头

	protected final static String DATE_FROMAT = "yyyyMMddHHmmss";

	private String terminalNo;

	private String command;

	private String message;
	
	private String[] parameters;

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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	public abstract void sendMessage(Channel channel);
}
