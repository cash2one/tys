/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqSettingHomeworkDTO {
	
	@NotNull
	private Integer courseId;
	
	@NotNull
	private Integer classId;
	
	@NotNull
	@Min(value=1451577600000L)//不小于2016-01-01
	private Long commitTime;
	
	@NotBlank
	private String content;
	
	private String picIds;


	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Long getCommitTime() {
		return commitTime;
	}

	public void setCommitTime(Long commitTime) {
		this.commitTime = commitTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPicIds() {
		return picIds;
	}

	public void setPicIds(String picIds) {
		this.picIds = picIds;
	}

	
	

}
