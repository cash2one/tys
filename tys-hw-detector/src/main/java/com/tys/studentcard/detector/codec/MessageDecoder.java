package com.tys.studentcard.detector.codec;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tys.studentcard.detector.req.MessageFactory;
import com.tys.studentcard.detector.req.MessageReq;
import com.tys.util.MUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class MessageDecoder extends ByteToMessageDecoder {

	private static final String STR_FORMAT = "0000";
	protected final static Logger logger = LoggerFactory.getLogger(MessageDecoder.class);

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list)
			throws Exception {
		if(byteBuf.readableBytes() == 0)
			return;
		String readData = byteBuf.toString(Charset.defaultCharset());
		
		MUtil.log(channelHandlerContext.channel().remoteAddress() + "\t\tD <-- " + readData);

		// 判断数据头
		int headIndex = -6;
		while ((headIndex = readData.indexOf("DIRM01", headIndex + 6)) != -1) {
			if (headIndex > byteBuf.readerIndex()) {
				// 索引移到数据头
				byteBuf.readerIndex(headIndex);
			}

			byteBuf.markReaderIndex();
			int markIndex = byteBuf.readerIndex();
			
			String mark = new String(byteBuf.readBytes(6).array());
			final DecimalFormat df = new DecimalFormat(STR_FORMAT);

			int length = df.parse(new String(byteBuf.readBytes(4).array())).intValue();
			if (length > 0 && byteBuf.readableBytes() < length - 4) {
				// 数据不全,丢弃
				continue;
			}

			String type = new String(byteBuf.readBytes(2).array());
			MessageReq messageReq = MessageFactory.buildMessage(type);
			messageReq.setMask(mark);
			messageReq.setLength(length);
			messageReq.setType(type);
			if (!messageReq.read(byteBuf)) {// 包内容读取有误
				logger.debug("parse data error");
				// 跳过本段数据
				byteBuf.readerIndex(markIndex + 6 + length);
				continue;
			}

			// 判断校验
			byteBuf.resetReaderIndex();
			byteBuf.readerIndex(byteBuf.readerIndex() + 6);
			if (checkSum(byteBuf.readBytes(length))) {
				list.add(messageReq);
			} else {
				//校验失败,丢弃
			}
		}
		
	}

	private boolean checkSum(ByteBuf checkBytes) {
		int checkSum = 0;
		int len = checkBytes.readableBytes() - 2;// 除去最后的校验值
		for (int i = 0; i < len; i++) {
			checkSum += checkBytes.readByte() & 0xff;
		}
		checkSum = checkSum & 0xff;
		ByteBuf checkCode = checkBytes.readBytes(2);// 读校验值
		String cc = checkCode.toString(Charset.defaultCharset());
		
		if(cc.equals(String.format("%02X", checkSum))){
			return true;
		}
		
		logger.info(String.format("checkSum err %02X", checkSum));
		return false;
	}

}
