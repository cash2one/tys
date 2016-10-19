package com.tys.studentcard.detector.client;

import com.tys.studentcard.detector.req.MessageReq;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class SecureChatClientHandler extends SimpleChannelInboundHandler<MessageReq> {

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageReq msg) throws Exception {
        System.err.println(msg);
    }
}
