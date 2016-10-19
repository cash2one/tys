package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.BatchSize;

import com.tys.base.BaseEntity;

/**
 * The persistent class for the md_equipment database table.
 * 
 */
@Entity
@Table(name = "md_equipment")
@NamedQuery(name = "MdEquipment.findAll", query = "SELECT m FROM MdEquipment m")
@BatchSize(size=100)
public class MdEquipment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "dealer_id", nullable = false)
	private Integer dealerId;

	@Column(nullable = false, length = 30)
	private String imei;

	@Column(nullable = false, length = 200)
	private String remark;

	@Column(name = "school_id", nullable = false)
	private Integer schoolId;

	@Column(nullable = false)
	private Integer state;

	@Column(nullable = false)
	private Integer type;

	@Column(nullable = false, length = 64)
	private String host;

	@Column(nullable = false)
	private Integer port;

	@Transient
	private String dealerName;
	@Transient
	private String  schoolName;

	public MdEquipment() {
	}

	public Integer getDealerId() {
		return this.dealerId;
	}

	public void setDealerId(Integer dealerId) {
		this.dealerId = dealerId;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	

}