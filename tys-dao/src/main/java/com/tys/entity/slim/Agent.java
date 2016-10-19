package com.tys.entity.slim;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tys.base.BaseEntity;
import com.tys.entity.RelSysuserArea;

/**
 * The persistent class for the sys_user database table.
 * 
 */
@Entity
@Table(name = "sys_user")
@NamedQuery(name = "Agent.findAll", query = "SELECT s FROM Agent s")
public class Agent extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(length = 200)
	private String address;

	@Column(length = 256)
	private String email;

	@Column(name = "login_name", length = 30)
	private String loginName;

	@Column(length = 50)
	private String name;

	@Column(name = "old_password", length = 64)
	private String oldPassword;

	@Column(nullable = false, length = 64)
	private String password;

	@Column(length = 30)
	private String phone;

	@Column(name = "user_type", nullable = false)
	private Integer userType;

	@Column(name = "agent_type", nullable = false)
	private Integer agentType;

	@Column(name = "province_code", nullable = false)
	private Integer provinceCode;

	@Column(name = "city_code", nullable = false)
	private Integer cityCode;

	@Column(name = "area_code", nullable = false)
	private Integer areaCode;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "agent_id", referencedColumnName = "id", insertable = false, updatable = false)
	private List<RelSysuserArea> userArea;

	public Agent() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOldPassword() {
		return this.oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(Integer provinceCode) {
		this.provinceCode = provinceCode;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(Integer areaCode) {
		this.areaCode = areaCode;
	}

	public Integer getAgentType() {
		return agentType;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public List<RelSysuserArea> getUserArea() {
		return userArea;
	}

	public void setUserArea(List<RelSysuserArea> userArea) {
		this.userArea = userArea;
	}
}