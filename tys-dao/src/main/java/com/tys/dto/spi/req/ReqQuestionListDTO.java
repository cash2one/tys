/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Min;

/**
 * @author Administrator
 *
 */
public class ReqQuestionListDTO {
	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long timeStart;

	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long timeEnd;
	
	@Min(value = 1451577600000L) // 不小于2016-01-01
	private Long lastUpdate;
	
	private Integer cityCode;
	
	private Integer userId;
	
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

	public Long getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	
	
}
