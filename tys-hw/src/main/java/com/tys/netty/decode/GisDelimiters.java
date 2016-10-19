package com.tys.netty.decode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class GisDelimiters {

	public static ByteBuf[] nulDelimiter() {
		return new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] { 0 }) };
	}

	public static ByteBuf[] lineDelimiter() {
		return new ByteBuf[] { Unpooled.wrappedBuffer(new byte[] { ';' }), };
	}

	private GisDelimiters() {
		// Unused
	}
}
