package com.tys.netty.message.togsm;

import io.netty.channel.Channel;

public class SetServerMessage extends AbstractSendGsmMessage {


    public void sendMessage(Channel channel) {
        String message = DATA_HEAD + ":" + getTerminalNo() + "," + getCommand() + "," + getMessage() + ",;";
        channel.writeAndFlush(message);
    }

}
