package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;

public class RspOnlineDTO extends BaseSpiRsp {

	private static final long serialVersionUID = -4831209104247468995L;

	private Integer online;

	private String ip;

	private Integer httpPort;

	public Integer getOnline() {
		return online;
	}

	public void setOnline(Integer online) {
		this.online = online;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return httpPort;
	}

	public void setPort(Integer port) {
		this.httpPort = port;
	}

}
