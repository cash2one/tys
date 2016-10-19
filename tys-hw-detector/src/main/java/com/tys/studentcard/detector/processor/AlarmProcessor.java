package com.tys.studentcard.detector.processor;

import com.tys.studentcard.detector.req.MessageReq;
import com.tys.studentcard.detector.res.AlarmRes;

import io.netty.channel.Channel;
import org.springframework.stereotype.Service;

@Service
public class AlarmProcessor implements IProcessor {

    @Override
    public String getType() {
        return "11";
    }

    @Override
    public void dealWith(Channel channel, MessageReq messageReq) {
        AlarmRes alarmRes = new AlarmRes();
        alarmRes.setType(messageReq.getType());
        alarmRes.setSequence(messageReq.getSequence());
        alarmRes.setValidFlag('1');
        channel.writeAndFlush(alarmRes);

    }
}
