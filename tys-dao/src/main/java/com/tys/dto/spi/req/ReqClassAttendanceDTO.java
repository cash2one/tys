/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Min;

/**
 * @author Administrator
 *
 */
public class ReqClassAttendanceDTO {

	@Min(value=1451577600000L)//不小于2016-01-01
	private Long date;

	
	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}
