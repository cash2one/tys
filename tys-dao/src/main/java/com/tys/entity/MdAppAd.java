package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONType;
import com.tys.base.BaseEntity;

/**
 * The persistent class for the md_app_ad database table.
 * 
 */
@JSONType(ignores={"createBy", "isDeleted", "updatedBy", "picId"})
@Entity
@Table(name = "md_app_ad")
@NamedQuery(name = "MdAppAd.findAll", query = "SELECT m FROM MdAppAd m")
public class MdAppAd extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "ad_position", nullable = false)
	private Integer adPosition;

	@Temporal(TemporalType.DATE)
	@Column(name = "end_date", nullable = false)
	private Date endDate;

	@Column(nullable = false)
	private Integer picId;

	@Column(nullable = false, length = 256)
	private String remarker;

	@Temporal(TemporalType.DATE)
	@Column(name = "start_date", nullable = false)
	private Date startDate;

	@Column(nullable = false)
	private Integer status;

	@Column(nullable = false, length = 50)
	private String title;

	@Column(nullable = false, length = 256)
	private String url;

	@Column(name = "province_code", nullable = false)
	private Integer provinceCode;

	@Column(name = "city_code", nullable = false)
	private Integer cityCode;

	@Column(name = "area_code", nullable = false)
	private Integer areaCode;
	
	@Transient
	private String picUrl;

	public MdAppAd() {
	}

	public Integer getAdPosition() {
		return this.adPosition;
	}

	public void setAdPosition(Integer adPosition) {
		this.adPosition = adPosition;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getPicId() {
		return this.picId;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public String getRemarker() {
		return this.remarker;
	}

	public void setRemarker(String remarker) {
		this.remarker = remarker;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	public String getPicUrl() {
		//TODO 待修改
		if(picId != null)
			return "http://localhost:8080?picId="+picId;
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	
	

}