/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqQueryAdDTO {

	@NotNull
	private Integer cityCode;
	
	private Integer adPosition;

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getAdPosition() {
		return adPosition;
	}

	public void setAdPosition(Integer adPosition) {
		this.adPosition = adPosition;
	}






}
