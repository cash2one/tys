package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the `rel_student_ guardian` database table.
 * 
 */
@Entity
@Table(name="`rel_student_guardian`")
@NamedQuery(name="RelStudentGuardian.findAll", query="SELECT r FROM RelStudentGuardian r")
public class RelStudentGuardian implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(name="phone_index", nullable=false)
	private Integer phoneIndex;

	@Column(nullable=false, length=30)
	private String phone;

	@Column(name="student_id", nullable=false)
	private Integer studentId;

	public RelStudentGuardian() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getStudentId() {
		return this.studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public Integer getPhoneIndex() {
		return phoneIndex;
	}

	public void setPhoneIndex(Integer phoneIndex) {
		this.phoneIndex = phoneIndex;
	}
	
	

}