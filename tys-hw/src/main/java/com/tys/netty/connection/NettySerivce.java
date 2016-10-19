package com.tys.netty.connection;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tys.netty.decode.GisDelimiters;
import com.tys.netty.decode.StringEncoderEx;
import com.tys.netty.handler.GisHandler;
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
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateHandler;

@Service
public class NettySerivce {

	@Value("${tcp.port}")
	private int port;

	@Value("${tcp.ot}")
	private int heartbeatTimeout;

	@Autowired
	private GisHandler gisHandler;

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
						b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
								.childHandler(new ChannelInitializer<SocketChannel>() {
							@Override
							public void initChannel(SocketChannel ch) throws Exception {
								ChannelPipeline pipeline = ch.pipeline();
								pipeline.addLast(
										new DelimiterBasedFrameDecoder(8192, false, GisDelimiters.lineDelimiter()));
								pipeline.addLast(new IdleStateHandler(heartbeatTimeout, 0, 0));
								pipeline.addLast(new StringDecoder());
								pipeline.addLast(new StringEncoderEx());
								pipeline.addLast(gisHandler);
							}
						}).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
						ChannelFuture f = b.bind(port).sync();
						MUtil.log("Tcp port " + port + " listening...");

						f.channel().closeFuture().sync();
						MUtil.log("Tcp port " + port + " close");
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
