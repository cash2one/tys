/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqUserInfoDTO {

	@NotNull
	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}
