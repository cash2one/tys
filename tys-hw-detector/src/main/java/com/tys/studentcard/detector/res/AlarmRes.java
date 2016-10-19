package com.tys.studentcard.detector.res;

import io.netty.buffer.ByteBuf;

/**
 * Created by zhutao on 15/1/24.
 */
public class AlarmRes extends MessageRes {

    private char validFlag;

    @Override
    protected int bodyLength() {
        return 1;
    }

    @Override
    protected void write0(ByteBuf byteBuf) {
        byteBuf.writeByte((byte) validFlag);
    }

    public char getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(char validFlag) {
        this.validFlag = validFlag;
    }
}
