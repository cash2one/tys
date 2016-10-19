package com.tys.netty.message.fromgsm;

public class SimpleRecvMessage extends AbstractRecvGsmMessage {

	@Override
	protected boolean parseParameters(String[] parameters) {
		//不作处理
		return true;
	}

}
