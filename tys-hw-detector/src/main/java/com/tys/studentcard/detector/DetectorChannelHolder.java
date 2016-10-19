package com.tys.studentcard.detector;

import com.tys.studentcard.detector.req.MessageReq;
import com.tys.studentcard.detector.res.MessageRes;

import io.netty.channel.Channel;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


@Service
public class DetectorChannelHolder implements ApplicationContextAware {


    private static final Map<String, Channel> channels = new ConcurrentHashMap<String, Channel>();

    private static final Map<String, DetectorChannelContext> channelContexts = new ConcurrentHashMap<String, DetectorChannelContext>();

    private static final ScheduledExecutorService scheduExec = Executors.newScheduledThreadPool(2);


    public static void put(String terminalNo, Channel channel) {
        channels.put(terminalNo, channel);
    }


    public static MessageReq sendMesage(String terminalNo, MessageRes baseMessage) {
        Channel channel = channels.get(terminalNo);
        if (!channels.get(terminalNo).isActive()) {
            return null;
        }
        try {
            DetectorChannelContext channelContext = new DetectorChannelContext();
            channelContext.setChannel(channel);
            channelContext.setId(baseMessage.getType() + "_" + baseMessage.getSequence());
            channels.get(terminalNo).writeAndFlush(baseMessage);
            channelContexts.put(baseMessage.getType() + "_" + baseMessage.getSequence(), channelContext);
            synchronized (channelContext) {
                channelContext.wait(300000);
            }
            channelContexts.remove(baseMessage.getType() + "_" + baseMessage.getSequence());
            return channelContext.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void notify(String type, int sequence, MessageReq message) {
        DetectorChannelContext channelContext = channelContexts.get(type + "_" + sequence);
        if (channelContext != null) {
            channelContext.setMessage(message);
            synchronized (channelContext) {
                channelContext.notify();
            }
        }

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        scheduExec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for (Map.Entry<String, Channel> entry : channels.entrySet()) {
                    if (!entry.getValue().isActive()) {
                        channels.remove(entry.getKey());
                    }
                }
            }
        }, 360000, 360000, TimeUnit.MILLISECONDS);
    }
}
