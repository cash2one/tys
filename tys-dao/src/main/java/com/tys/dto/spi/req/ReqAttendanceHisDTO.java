/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 *
 */
public class ReqAttendanceHisDTO {

	@NotNull
	private Integer childId;
	
	@Range(min=0, max=30)
	private Integer days = 7;
	
	@Min(value=1451577600000L)//不小于2016-01-01
	private Long date;

	
	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

}
