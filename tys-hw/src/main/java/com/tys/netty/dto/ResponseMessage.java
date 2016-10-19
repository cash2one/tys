package com.tys.netty.dto;

public class ResponseMessage {

	private boolean isSuccess;

	//保存额外信息
	private String message;

	private String imeiNo;

	private String commond;
	
	//回复的参数列表
	private String[] parameters;
	
	private Object obj;
	

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}
		
	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getCommond() {
		return commond;
	}

	public void setCommond(String commond) {
		this.commond = commond;
	}

	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}
	
	

}
