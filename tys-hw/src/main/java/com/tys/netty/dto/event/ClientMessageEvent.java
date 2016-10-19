package com.tys.netty.dto.event;

import org.springframework.context.ApplicationEvent;

import com.tys.netty.dto.BaseDTO;
import com.tys.netty.dto.ResponseMessage;

public class ClientMessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = 5216388643331647395L;

	private ResponseMessage responseMessage;

	private BaseDTO reqDto;

	public ClientMessageEvent() {
		super("");
	}

	public ClientMessageEvent(Object source) {
		super(source);
	}

	public ClientMessageEvent(ResponseMessage responseMessage, BaseDTO reqDto,Object source) {
		super(source);
		this.responseMessage = responseMessage;
	}

	public ClientMessageEvent(ResponseMessage responseMessage, BaseDTO reqDto) {
		super("");
		this.responseMessage = responseMessage;
		this.reqDto = reqDto;
	}

	public ResponseMessage getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(ResponseMessage responseMessage) {
		this.responseMessage = responseMessage;
	}

	public BaseDTO getReqDto() {
		return reqDto;
	}

	public void setReqDto(BaseDTO reqDto) {
		this.reqDto = reqDto;
	}
	
	

}
