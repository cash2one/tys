package com.tys.studentcard.detector.req;

import io.netty.buffer.ByteBuf;

import java.text.DecimalFormat;
import java.text.ParseException;

public abstract class MessageReq implements Cloneable {

	protected static final String STR_FORMAT = "0000";

	protected static final String TWO_STR_FORMAT = "00";

	protected static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

	private String mask;

	private int length;

	private String type;

	private int sequence;

	private char validFlag = '0';

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

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

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public char getValidFlag() {
		return validFlag;
	}

	public void setValidFlag(char validFlag) {
		this.validFlag = validFlag;
	}

	public boolean read(ByteBuf byteBuf) throws ParseException {
		DecimalFormat df = new DecimalFormat(STR_FORMAT);
		sequence = df.parse(new String(byteBuf.readBytes(4).array())).intValue();
		boolean ret = read0(byteBuf, length - 12);// 有可能出错,要将结果返回
		if (ret)
			validFlag = '1';
		return ret;
	}

	/**
	 * @param byteBuf
	 *            包内容起始
	 * @param length
	 *            包内容长度
	 * @return 包内容是否读取成功
	 */
	protected abstract boolean read0(ByteBuf byteBuf, int length);

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
