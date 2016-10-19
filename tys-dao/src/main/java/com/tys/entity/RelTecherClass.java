package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;

/**
 * The persistent class for the rel_techer_class database table.
 * 
 */
@Entity
@Table(name = "rel_techer_class")
@NamedQuery(name = "RelTecherClass.findAll", query = "SELECT r FROM RelTecherClass r")
public class RelTecherClass extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "class_id", nullable = false)
	private Integer classId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "class_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdClass mdClass;

	@Column(name = "course_id", nullable = true)
	private Integer courseId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdCourse mdCourse;

	@Column(name = "techer_id", nullable = false)
	private Integer techerId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "techer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdUser mdUser;

	public RelTecherClass() {
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getTecherId() {
		return this.techerId;
	}

	public void setTecherId(Integer techerId) {
		this.techerId = techerId;
	}

	public MdClass getMdClass() {
		return mdClass;
	}

	public void setMdClass(MdClass mdClass) {
		this.mdClass = mdClass;
	}

	public MdCourse getMdCourse() {
		return mdCourse;
	}

	public void setMdCourse(MdCourse mdCourse) {
		this.mdCourse = mdCourse;
	}

	public MdUser getMdUser() {
		return mdUser;
	}

	public void setMdUser(MdUser mdUser) {
		this.mdUser = mdUser;
	}

}