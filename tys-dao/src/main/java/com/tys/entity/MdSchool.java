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
 * The persistent class for the md_school database table.
 * 
 */
@Entity
@Table(name = "md_school")
@NamedQuery(name = "MdSchool.findAll", query = "SELECT m FROM MdSchool m")
public class MdSchool extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 200)
	private String address;

	@Column(name = "admin_id", nullable = false)
	private Integer adminId;

	@Column(name = "admin_name", nullable = false)
	private String adminName;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "admin_id", referencedColumnName = "id", insertable = false, updatable = false)
	private SysUser sysUser;

	@Column(name = "province_id", nullable = false)
	private Integer provinceId;

	@Column(name = "area_id", nullable = false)
	private Integer areaId;

	@Column(name = "city_id", nullable = false)
	private Integer cityId;

	private Double latitude;

	private Double longitude;

	@Column(name = "message_flag", nullable = false)
	private Integer messageFlag;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 50)
	private String tel;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "created_by", referencedColumnName = "id", insertable = false, updatable = false)
	private SysUser sysUserCreate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "province_id", referencedColumnName = "code", insertable = false, updatable = false)
	private DProvince dProvince;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "city_id", referencedColumnName = "code", insertable = false, updatable = false)
	private DCity dCity;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "area_id", referencedColumnName = "code", insertable = false, updatable = false)
	private DArea dArea;

	public MdSchool() {
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getAdminId() {
		return this.adminId;
	}

	public void setAdminId(Integer adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getMessageFlag() {
		return this.messageFlag;
	}

	public void setMessageFlag(Integer messageFlag) {
		this.messageFlag = messageFlag;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
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

	public SysUser getSysUserCreate() {
		return sysUserCreate;
	}

	public void setSysUserCreate(SysUser sysUserCreate) {
		this.sysUserCreate = sysUserCreate;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

}