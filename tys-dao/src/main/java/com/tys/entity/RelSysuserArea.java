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

@Entity
@Table(name = "rel_sysuser_area")
@NamedQuery(name = "RelSysuserArea.findAll", query = "SELECT s FROM RelSysuserArea s")
public class RelSysuserArea extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "agent_id")
	private Integer agentId;
	
	@Column(name = "province_code")
	private Integer provinceCode;
	
	@Column(name = "city_code")
	private Integer cityCode;
	
	@Column(name = "area_code")
	private Integer areaCode;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "province_code", referencedColumnName = "code", insertable = false, updatable = false)
	private DProvince dProvince;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_code", referencedColumnName = "code", insertable = false, updatable = false)
	private DCity dCity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_code", referencedColumnName = "code", insertable = false, updatable = false)
	private DArea dArea;

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
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

	public DProvince getdProvince() {
		return dProvince;
	}

	public void setdProvince(DProvince dProvince) {
		this.dProvince = dProvince;
	}

	public DCity getdCity() {
		return dCity;
	}

	public void setdCity(DCity dCity) {
		this.dCity = dCity;
	}

	public DArea getdArea() {
		return dArea;
	}

	public void setdArea(DArea dArea) {
		this.dArea = dArea;
	}
}
