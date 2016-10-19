package com.tys.studentcard.detector.processor;

import com.tys.studentcard.detector.req.MessageReq;

import io.netty.channel.Channel;

public interface IProcessor {

    public String getType();

    public void dealWith(Channel channel, MessageReq messageReq);
}
