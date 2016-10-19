/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqJxzxListDTO {
	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long timeStart;

	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long timeEnd;
	
	@NotNull
	private Integer type;
	
	public Long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(Long timeStart) {
		this.timeStart = timeStart;
	}

	public Long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Long timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	
}
