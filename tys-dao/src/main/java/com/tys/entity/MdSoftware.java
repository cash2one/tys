package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONType;
import com.tys.base.BaseEntity;

/**
 * The persistent class for the md_software database table.
 * 
 */
@JSONType(ignores={"createBy", "isDeleted", "updateTime", "updatedBy"})
@Entity
@Table(name = "md_software")
@NamedQuery(name = "MdSoftware.findAll", query = "SELECT m FROM MdSoftware m")
public class MdSoftware extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, length = 50)
	private String alias;

	@Column(name = "company_id", nullable = false, length = 20)
	private String companyId;

	@Column(nullable = false, length = 200)
	private String description;

	@Column(nullable = true)
	private Integer fileId;

	@Column(nullable = false)
	private Integer type;

	@Column(nullable=false, length=256)
	private String url;

	@Column(nullable=false)
	private Integer version;

	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "fileId", insertable = false, updatable = false)
	private MdFile file;
	public MdSoftware() {
	}


	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}


	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}



	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public MdFile getFile() {
		return file;
	}

	public void setFile(MdFile file) {
		this.file = file;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
}