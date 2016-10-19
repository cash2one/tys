package com.tys.studentcard.detector.codec;

import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tys.studentcard.detector.res.MessageRes;
import com.tys.util.MUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class MessageEncoder extends MessageToByteEncoder<MessageRes> {
	protected final static Logger logger = LoggerFactory.getLogger(MessageEncoder.class);
	
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, MessageRes messageRes, ByteBuf byteBuf) throws Exception {
        messageRes.write(byteBuf);
        MUtil.log(channelHandlerContext.channel().remoteAddress() + "\t\tD --> " + byteBuf.toString(Charset.defaultCharset()));
    }
}
