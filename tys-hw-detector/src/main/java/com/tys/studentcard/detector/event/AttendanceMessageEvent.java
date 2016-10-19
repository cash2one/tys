package com.tys.studentcard.detector.event;

import org.springframework.context.ApplicationEvent;

import com.tys.base.BaseDTO;

public class AttendanceMessageEvent extends ApplicationEvent {

	private static final long serialVersionUID = 7081577597696236871L;

	private BaseDTO reqDto;

	public AttendanceMessageEvent() {
		super("");
	}

	public AttendanceMessageEvent(Object source) {
		super(source);
	}

	public AttendanceMessageEvent(BaseDTO reqDto) {
		super("");
		this.reqDto = reqDto;
	}

	public BaseDTO getReqDto() {
		return reqDto;
	}

	public void setReqDto(BaseDTO reqDto) {
		this.reqDto = reqDto;
	}

}
