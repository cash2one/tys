package com.tys.netty.process;

import com.tys.netty.message.fromgsm.AbstractRecvGsmMessage;

import io.netty.channel.Channel;

public interface IProcess {

    public String getType();

    public void dealWith(Channel channel, AbstractRecvGsmMessage message,String msg);

}
