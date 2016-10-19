package com.tys.studentcard.detector.processor;

import io.netty.channel.Channel;

import org.springframework.stereotype.Service;

import com.tys.studentcard.detector.req.MessageReq;
import com.tys.studentcard.detector.res.TicketMessageRes;

import java.util.Date;

@Service
public class TicketProcessor implements IProcessor {
    @Override
    public String getType() {
        return "05";
    }

    @Override
    public void dealWith(Channel channel, MessageReq messageReq) {
        TicketMessageRes ticketMessageRes = new TicketMessageRes();
        ticketMessageRes.setSequence(messageReq.getSequence());
        ticketMessageRes.setType(getType());
        ticketMessageRes.setNowTime(new Date());
        ticketMessageRes.setValidFlag(messageReq.getValidFlag());
        channel.writeAndFlush(ticketMessageRes);
    }
}
