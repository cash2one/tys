package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tys.base.BaseEntity;

/**
 * The persistent class for the md_student database table.
 * 
 */
@Entity
@Table(name = "md_student")
@NamedQuery(name = "MdStudent.findAll", query = "SELECT m FROM MdStudent m")
public class MdStudent extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	// @JSONField (format="yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthday;

	@Column(name = "class_id", nullable = false)
	private Integer classId;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false)
	private Integer sex = 0;

	@Column(nullable = false)
	private Integer type = 0;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "class_id", insertable = false, updatable = false)
	private MdClass mdClass;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by", insertable = false, updatable = false)
	private SysUser sysUser;

	public MdStudent() {
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public MdClass getMdClass() {
		return mdClass;
	}

	public void setMdClass(MdClass mdClass) {
		this.mdClass = mdClass;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}