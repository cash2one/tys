package com.tys.entity.slim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;

import com.tys.base.BaseEntity;
import com.tys.entity.SysUser;

/**
 * The persistent class for the sys_user database table.
 * 
 */
@Entity
@Table(name = "sys_user")
public class Employee extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 50)
	private String department;

	@Column(length = 256)
	private String email;

	@Column(name = "login_name", length = 30)
	private String loginName;

	@Column(length = 50)
	private String name;

	@Column(length = 30)
	private String phone;

	@Column(name = "user_type", nullable = false)
	private Integer userType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
	private SysUser company;

	public Employee() {
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public SysUser getCompany() {
		return company;
	}

	public void setCompany(SysUser company) {
		this.company = company;
	}
}