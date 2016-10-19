package com.tys.studentcard.detector.res;

import io.netty.buffer.ByteBuf;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class QueryStatusRes extends MessageRes {

    public QueryStatusRes() {
        setType("82");
    }

    private Date curTime;

    @Override
    protected int bodyLength() {
        return 14;
    }

    @Override
    protected void write0(ByteBuf byteBuf) {
        DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        byteBuf.writeBytes(dateFormat.format(curTime).getBytes());
    }

    public Date getCurTime() {
        return curTime;
    }

    public void setCurTime(Date curTime) {
        this.curTime = curTime;
    }
}
