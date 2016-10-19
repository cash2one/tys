package com.tys.base;

import java.io.Serializable;

import com.tys.util.constants.ErrorCodeConstants;

/**
 * 接口返回对象
 * 
 * @author Administrator
 *
 */
public class BaseSpiRsp implements Serializable {

	private static final long serialVersionUID = 4402566827804068909L;

	/** 返回状态，默认未知错误码 */
	private Integer status = ErrorCodeConstants.ERROR;

	public BaseSpiRsp() {
		
	}
	

	public BaseSpiRsp(Integer status) {
		super();
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String temp = "ReturnParameters [";
		temp = temp + ("status=" + String.valueOf(status));
		temp = temp + "]";
		return temp;

	}

}
