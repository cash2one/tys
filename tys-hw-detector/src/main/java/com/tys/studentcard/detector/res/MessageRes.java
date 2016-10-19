package com.tys.studentcard.detector.res;

import io.netty.buffer.ByteBuf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class MessageRes {

    protected static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    protected final static Logger logger = LoggerFactory.getLogger(MessageRes.class);
    
    private String type;

    private int sequence;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }


    public void write(ByteBuf byteBuf) {
    	int markIndex = byteBuf.writerIndex();
        byteBuf.writeBytes("DIRS01".getBytes());
        String length = String.format("%04d", bodyLength() + 12);
        String sequenceString = String.format("%04d", sequence);
        byteBuf.writeBytes(length.getBytes());
        byteBuf.writeBytes(type.getBytes());
        byteBuf.writeBytes(sequenceString.getBytes());
        write0(byteBuf);
        
        //计算校验
        byteBuf.markReaderIndex();
        int checkCode = calCheckSum(byteBuf.readerIndex(markIndex + 6));//除去头
        byteBuf.resetReaderIndex();
        byteBuf.writeBytes(Integer.toHexString(checkCode).toUpperCase().getBytes());
        
    }
    
    private int calCheckSum(ByteBuf checkBytes) {
		int checkSum = 0;
		
		int len = checkBytes.readableBytes();
		for (int i = 0; i < len; i++) {
			checkSum += checkBytes.readByte() & 0xff;
		}
		checkSum = checkSum & 0xff;
		logger.debug(String.format("calCheckSum=%x", checkSum));
		return checkSum;
	}


    protected abstract int bodyLength();

    protected abstract void write0(ByteBuf byteBuf);



}
