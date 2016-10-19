/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Min;

/**
 * @author Administrator
 *
 */
public class ReqMySettingHomeworkDTO {

	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long timeStart;
	
	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long timeEnd;

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

}
