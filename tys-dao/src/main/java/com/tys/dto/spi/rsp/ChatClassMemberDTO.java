/**
 * 
 */
package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.entity.MdCourse;

/**
 * @author Administrator
 *
 */
public class ChatClassMemberDTO {

	private Integer userId;

	private Integer type;

	private List<MdCourse> courseList;

	private String name;

	private String headUrl;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<MdCourse> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<MdCourse> courseList) {
		this.courseList = courseList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

}
