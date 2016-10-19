package com.tys.dto.spi.rsp;

import java.util.Date;

import com.tys.util.MDateUtil;

public class RspChildListInnerDTO {

	private Integer id;

	private String imei;

	private String detector;

	private String name;

	private Integer sex;

	private Integer classId;

	private String className;

	private Integer schoolId;

	private String schoolName;

	private Date birthday;

	public RspChildListInnerDTO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getDetector() {
		return detector;
	}

	public void setDetector(String detector) {
		this.detector = detector;
	}

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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getBirthday() {
		if(birthday != null)
			return MDateUtil.formatDate(MDateUtil.ISO_EXPANDED_DATE_FORMAT, birthday);
		return null;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	

}
