package com.tys.entity.slim;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * The persistent class for the md_class database table.
 * 
 */
@Entity
@Table(name = "md_class")
public class MClass implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",unique = true, nullable = false)
	private Integer classId;
	
	@Column(name = "admin_id", nullable = false)
	private Integer adminId;

	@Column(name = "class_name", nullable = false, length = 50)
	private String className;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "grade_id", insertable = false, updatable = false)
	private Grade grade;

	@Column(name = "school_id", nullable = false)
	private Integer schoolId;

	@Column(nullable = false)
	private Integer year;
	
	@Column(name = "is_deleted", nullable = false)
	private Integer isDeleted = 0;


	public MClass() {
	}

	public Integer getAdminId() {
		return this.adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getYear() {
		return this.year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Grade getGrade() {
		return grade;
	}

	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	
	


}