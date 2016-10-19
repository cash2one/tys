package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONType;
import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_app_news database table.
 * 
 */
@JSONType(ignores={"createBy", "isDeleted", "updateTime", "updatedBy"})
@Entity
@Table(name="info_app_news")
@NamedQuery(name="InfoAppNews.findAll", query="SELECT m FROM InfoAppNews m")
public class InfoAppNews extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="pic_id", nullable=false)
	private Integer picId;

	@Column(name="school_id", nullable=false)
	private Integer schoolId;

	@Column(nullable=false, length=50)
	private String source;

	@Column(nullable=false, length=50)
	private String title;

	@Column(nullable=false, length=256)
	private String url;

	public InfoAppNews() {
	}


	public String getPicUrl() {
		//TODO 待修改
		if(picId != null)
			return "http://112.126.65.114/res/dl?picId=" + this.picId;
		return null;
	}

	public void setPicId(Integer picId) {
		this.picId = picId;
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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

}