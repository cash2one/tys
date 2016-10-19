package com.tys.studentcard.detector.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.tys.studentcard.detector.processor.IProcessor;
import com.tys.studentcard.detector.req.MessageReq;

@Service
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<MessageReq> implements ApplicationContextAware {

    private Map<String, IProcessor> processors = new HashMap<String, IProcessor>();

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, IProcessor> processorMap = applicationContext.getBeansOfType(IProcessor.class);
        for (Map.Entry<String, IProcessor> processorEntry : processorMap.entrySet()) {
            processors.put(processorEntry.getValue().getType(), processorEntry.getValue());
        }
    }


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageReq req) throws Exception {
        IProcessor processor = processors.get(req.getType());
        processor.dealWith(channelHandlerContext.channel(), req);
    }


}
