package com.tys.studentcard.detector.client;

import com.tys.studentcard.detector.codec.MessageDecoder;
import com.tys.studentcard.detector.codec.MessageEncoder;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class SecureChatClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("encoder", new MessageEncoder());//这里
        pipeline.addLast("decoder", new MessageDecoder());//这里
        pipeline.addLast(new SecureChatClientHandler());
    }
}
