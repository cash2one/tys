package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tys.base.BaseEntity;

/**
 * The persistent class for the md_class database table.
 * 
 */
@Entity
@Table(name = "md_class")
@NamedQuery(name = "MdClass.findAll", query = "SELECT m FROM MdClass m")
public class MdClass extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "admin_id", nullable = false)
	private Integer adminId;

	@Column(name = "class_name", nullable = false, length = 50)
	private String className;

	@Column(name = "grade_id", nullable = false)
	private Integer gradeId;

	@Column(name = "school_id", nullable = false)
	private Integer schoolId;

	@Column(nullable = false)
	private Integer year;
	
	@Column(name = "push_tag", nullable = false, length = 20)
	private String pushTag;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdUser mdUser;

	@ManyToOne
	// (fetch = FetchType.LAZY)
	@JoinColumn(name = "school_id", insertable = false, updatable = false)
	private MdSchool mdSchool;

	@ManyToOne
	// (fetch = FetchType.LAZY)
	@JoinColumn(name = "grade_id", insertable = false, updatable = false)
	private MdGrade mdGrade;

	public MdClass() {
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

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
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

	public MdSchool getMdSchool() {
		return mdSchool;
	}

	public void setMdSchool(MdSchool mdSchool) {
		this.mdSchool = mdSchool;
	}

	public MdGrade getMdGrade() {
		return mdGrade;
	}

	public void setMdGrade(MdGrade mdGrade) {
		this.mdGrade = mdGrade;
	}

	public MdUser getMdUser() {
		return mdUser;
	}

	public void setMdUser(MdUser mdUser) {
		this.mdUser = mdUser;
	}

	public String getPushTag() {
		return pushTag;
	}

	public void setPushTag(String pushTag) {
		this.pushTag = pushTag;
	}
	
}