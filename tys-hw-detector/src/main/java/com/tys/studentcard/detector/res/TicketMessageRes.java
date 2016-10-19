package com.tys.studentcard.detector.res;

import io.netty.buffer.ByteBuf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TicketMessageRes extends MessageRes {

    private char validFlag;

    private Date nowTime;

    @Override
    protected int bodyLength() {
        return 15;
    }

    @Override
    protected void write0(ByteBuf byteBuf) {
        byteBuf.writeByte((byte) validFlag);
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        byteBuf.writeBytes(dateFormat.format(nowTime).getBytes());
    }

    public char getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(char validFlag) {
        this.validFlag = validFlag;
    }

    public Date getNowTime() {
        return nowTime;
    }

    public void setNowTime(Date nowTime) {
        this.nowTime = nowTime;
    }


}
