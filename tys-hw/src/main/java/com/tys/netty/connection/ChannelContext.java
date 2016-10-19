package com.tys.netty.connection;

import io.netty.channel.Channel;

public class ChannelContext {

    private String id;

    private Channel channel;

    private String message;
    
    //回复的参数列表
  	private String[] parameters;
  	
  	//自定义参数
  	private Object obj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
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

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	
    
}
