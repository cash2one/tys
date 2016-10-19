package com.tys.netty.process;

import io.netty.channel.Channel;

import org.springframework.stereotype.Service;

import com.tys.netty.message.fromgsm.AbstractRecvGsmMessage;

@Service
public class TicketProcess implements IProcess {

    @Override
    public String getType() {
        return "A";
    }

    @Override
    public void dealWith(Channel channel, AbstractRecvGsmMessage message, String msg) {
        channel.writeAndFlush("Ibaby OK;");
    }
}
