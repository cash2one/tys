package com.tys.netty.decode;

import java.util.List;

import com.tys.util.MUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.string.StringEncoder;

public class StringEncoderEx extends StringEncoder {

	@Override
	protected void encode(ChannelHandlerContext ctx, CharSequence msg, List<Object> out) throws Exception {
		MUtil.log(ctx.channel().remoteAddress() + "\t\t--> " + msg);
		super.encode(ctx, msg, out);
	}

	
}
