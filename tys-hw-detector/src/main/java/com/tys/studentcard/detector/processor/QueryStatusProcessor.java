package com.tys.studentcard.detector.processor;

import com.tys.studentcard.detector.DetectorChannelHolder;
import com.tys.studentcard.detector.req.MessageReq;
import com.tys.studentcard.detector.req.QueryStatusReq;

import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class QueryStatusProcessor implements IProcessor {
    @Override
    public String getType() {
        return "82";
    }

    @Override
    public void dealWith(Channel channel, MessageReq messageReq) {
        QueryStatusReq queryStatusReq = (QueryStatusReq) messageReq;
        DetectorChannelHolder.notify(messageReq.getType(), messageReq.getSequence(), queryStatusReq);
    }
}
