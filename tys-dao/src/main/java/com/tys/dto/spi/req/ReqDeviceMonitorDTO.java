/**
 * 
 */
package com.tys.dto.spi.req;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqDeviceMonitorDTO {
	
	@NotBlank
	private String imei;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}



}
