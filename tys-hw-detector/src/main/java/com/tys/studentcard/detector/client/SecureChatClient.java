package com.tys.studentcard.detector.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;


public final class SecureChatClient {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new SecureChatClientInitializer());
            Channel channel = b.connect("127.0.0.1", 8089).sync().channel();
            TicketClientRes ticketMessageRes = new TicketClientRes();
            ticketMessageRes.setSequence(1);
            ticketMessageRes.setType("0a");
            channel.writeAndFlush(ticketMessageRes);
            Thread.sleep(100000);
        } finally {
            group.shutdownGracefully();
        }
    }
}
