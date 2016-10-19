package com.tys.dto.spi.rsp;

public class RspClassAttendanceInnerDTO {

	private Integer classId;
	private String className;
	private Integer gradeId;
	private String gradeName;
	
	private Integer total;
	private Integer externalStudent;
	private Integer bindCount;
	private Integer attendance;
	private Long dateTime;

	
	
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

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getExternalStudent() {
		return externalStudent;
	}

	public void setExternalStudent(Integer externalStudent) {
		this.externalStudent = externalStudent;
	}

	public Integer getAttendance() {
		return attendance;
	}

	public void setAttendance(Integer attendance) {
		this.attendance = attendance;
	}

	public Long getDateTime() {
		return dateTime;
	}

	public void setDateTime(Long dateTime) {
		this.dateTime = dateTime;
	}

	public Integer getBindCount() {
		return bindCount;
	}

	public void setBindCount(Integer bindCount) {
		this.bindCount = bindCount;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}

	
	
}
