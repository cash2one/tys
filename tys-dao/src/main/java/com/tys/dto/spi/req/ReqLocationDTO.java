/**
 * 
 */
package com.tys.dto.spi.req;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 *
 */
public class ReqLocationDTO {

	@NotBlank
	private String imei;
	
	@Range(min=0, max=3)//目前仅有0~3
	private Integer mapType = 0;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}


}
