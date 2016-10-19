package com.tys.studentcard.detector.res;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.netty.buffer.ByteBuf;

/**
 * Created by zhutao on 15/1/20.
 */
public class AuthenticateRes extends MessageRes {

    private char validFlag;

    private Date nowTime;

    @Override
    protected int bodyLength() {
        return 15;
    }

    @Override
    protected void write0(ByteBuf byteBuf) {
        byteBuf.writeByte((byte) validFlag);
        final DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
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


    public static void main(String[] args) {
        for (byte b : "DIRS01".getBytes()) {
            System.out.println(b);
        }

    }

}
