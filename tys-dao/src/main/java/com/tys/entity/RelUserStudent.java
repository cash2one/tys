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
 * The persistent class for the rel_user_student database table.
 * 
 */
@Entity
@Table(name = "rel_user_student")
@NamedQuery(name = "RelUserStudent.findAll", query = "SELECT r FROM RelUserStudent r")
public class RelUserStudent extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8236294650070707162L;

	@Column(name = "student_id")
	private Integer studentId;

	@Column(name = "user_id")
	private Integer userId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", insertable = false, updatable = false)
	private MdStudent mdStudent;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private MdUser mdUser;

	public RelUserStudent() {
	}

	public MdStudent getMdStudent() {
		return mdStudent;
	}

	public void setMdStudent(MdStudent mdStudent) {
		this.mdStudent = mdStudent;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public MdUser getMdUser() {
		return mdUser;
	}

	public void setMdUser(MdUser mdUser) {
		this.mdUser = mdUser;
	}

}