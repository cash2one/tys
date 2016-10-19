package com.tys.studentcard.detector.client;


import com.tys.studentcard.detector.res.MessageRes;

import io.netty.buffer.ByteBuf;

public class TicketClientRes extends MessageRes {
    @Override
    public int bodyLength() {
        return 18;
    }

    @Override
    public void write0(ByteBuf byteBuf) {
//        byteBuf.writeBytes("111111111111111111".getBytes());
//        byteBuf.writeBytes("11111111".getBytes());
    }
}
