package com.tys.studentcard.detector;

import com.tys.studentcard.detector.req.MessageReq;

import io.netty.channel.Channel;

public class DetectorChannelContext {

    private String id;

    private Channel channel;

    private MessageReq message;

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

    public MessageReq getMessage() {
        return message;
    }

    public void setMessage(MessageReq message) {
        this.message = message;
    }
}