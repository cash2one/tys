package com.tys.netty.message.togsm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import io.netty.channel.Channel;

public class TimeCheckResMessage extends AbstractSendGsmMessage {


    public void sendMessage(Channel channel) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FROMAT);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        String message = BIG_TYPE + ":" + getTerminalNo() + "," + getCommand() + "," + simpleDateFormat.format(new Date()) + ";";
        channel.writeAndFlush(message);
    }

}
