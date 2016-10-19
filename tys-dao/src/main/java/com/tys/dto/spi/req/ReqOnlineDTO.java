package com.tys.dto.spi.req;

import org.hibernate.validator.constraints.NotBlank;

public class ReqOnlineDTO {

	@NotBlank
	private String imei;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}
