/**
 * 
 */
package com.tys.dto.spi.req;

/**
 * @author Administrator
 *
 */
public class ReqAddChildrenDTO {

	private String name;
	private Integer sex;
	private Integer classId;
	private Long birthday;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

}
