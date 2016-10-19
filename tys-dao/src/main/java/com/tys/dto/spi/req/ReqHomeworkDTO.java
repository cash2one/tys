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
public class ReqHomeworkDTO {

	@Min(value=1451577600000L)//不小于2016-01-01
	private Long lastTime;
	
	@NotNull
	private Integer classId;

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	
	

}
