package com.tys.dto.query;

public class ReqAgentDTO {
	private int id;
	private String loginName;
	private String password;
	private String name;
	private String phone;
	private String email;
	private String address;
	private Integer agentType;
	private String provinceCodes;
	private String cityCodes;
	private String areaCodes;
	private String permissions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAgentType() {
		return agentType;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public String getProvinceCodes() {
		return provinceCodes;
	}

	public void setProvinceCodes(String provinceCodes) {
		this.provinceCodes = provinceCodes;
	}

	public String getCityCodes() {
		return cityCodes;
	}

	public void setCityCodes(String cityCodes) {
		this.cityCodes = cityCodes;
	}

	public String getAreaCodes() {
		return areaCodes;
	}

	public void setAreaCodes(String areaCodes) {
		this.areaCodes = areaCodes;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
}
