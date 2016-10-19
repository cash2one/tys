/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 *
 */
public class ReqPushParamDTO {

	@NotBlank
	private String userId;

	@NotBlank
	private String channelId;

	@NotNull
	@Range(min=3, max=4)
	private Integer deviceType;

	
	private String companyId = "tys_1.0";

	

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
