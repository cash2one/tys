/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqBindDTO {


	@NotBlank
	private String imei;

	@NotNull
	private Integer childId;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

}
