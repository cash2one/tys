package com.tys.netty.message.togsm;

import com.tys.netty.message.togsm.AbstractSendGsmMessage;

import io.netty.channel.Channel;

/**
 *	下发指令到GSM的数据包
 */
public class ReqMessage extends AbstractSendGsmMessage {

    public void sendMessage(Channel channel) {
        String message = DATA_HEAD + ":" + getTerminalNo() + "," + getCommand() + "," + getMessage() + ";";
        channel.writeAndFlush(message);
    }

}
