package com.tys.dto.spi.req;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

public class ReqSchoolDTO {

	private Integer cityCode;

	private String name;

	private Integer classId;

	@DecimalMin(value="-90")
	@DecimalMax(value="90")
	private Double latitude;

	@DecimalMin(value="-180")
	@DecimalMax(value="180")
	private Double longitude;

	private Integer schoolId;

	private Integer areaCode;

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

}
