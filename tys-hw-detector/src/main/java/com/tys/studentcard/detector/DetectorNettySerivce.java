package com.tys.studentcard.detector;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tys.studentcard.detector.codec.MessageDecoder;
import com.tys.studentcard.detector.codec.MessageEncoder;
import com.tys.studentcard.detector.handler.MessageHandler;
import com.tys.util.MUtil;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

@Service
public class DetectorNettySerivce {

    @Value("${tcp.detector.port}")
    private int port = 8000;

    @Autowired
    private MessageHandler messageHandler;

    private static AtomicBoolean started = new AtomicBoolean(false);
    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    
    
    @PostConstruct
    public void run() {
        if (started.compareAndSet(false, true)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerBootstrap b = new ServerBootstrap();
                        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            public void initChannel(SocketChannel ch) throws Exception {
                                ChannelPipeline pipeline = ch.pipeline();
                                pipeline.addLast("encoder", new MessageEncoder());
                                pipeline.addLast("decoder", new MessageDecoder());
                                pipeline.addLast(messageHandler);
                            }
                        }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
                        ChannelFuture f = b.bind(port).sync();
                        MUtil.log("Detector port "+port+ " listening...");
                        f.channel().closeFuture().sync();
                        MUtil.log("Detector port "+port+ " close");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        workerGroup.shutdownGracefully();
                        bossGroup.shutdownGracefully();
                    }
                }
            }).start();
        }
    }

    @PreDestroy
	public void destory(){
		bossGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS);
		workerGroup.shutdownGracefully(0, 0, TimeUnit.MILLISECONDS);
	}

}
