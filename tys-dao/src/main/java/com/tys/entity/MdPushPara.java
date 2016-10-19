package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the md_push_para database table.
 * 
 */
@Entity
@Table(name="md_push_para")
@NamedQuery(name="MdPushPara.findAll", query="SELECT m FROM MdPushPara m")
public class MdPushPara implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	@Column(name="user_id", nullable=false)
	private Integer userId;
	
	@Column(name="bd_channel_id", nullable=false, length=32)
	private String bdChannelId;

	@Column(name="bd_user_id", nullable=false, length=32)
	private String bdUserId;

	@Column(name="company_id", nullable=false, length=20)
	private String companyId;

	private Integer deployStatus;

	@Column(nullable=false)
	private Integer platform;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time", nullable=false)
	private Date updateTime;

	public MdPushPara() {
	}
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBdChannelId() {
		return this.bdChannelId;
	}

	public void setBdChannelId(String bdChannelId) {
		this.bdChannelId = bdChannelId;
	}

	public String getBdUserId() {
		return this.bdUserId;
	}

	public void setBdUserId(String bdUserId) {
		this.bdUserId = bdUserId;
	}

	public String getCompanyId() {
		return this.companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public Integer getDeployStatus() {
		return this.deployStatus;
	}

	public void setDeployStatus(Integer deployStatus) {
		this.deployStatus = deployStatus;
	}


	public Integer getPlatform() {
		return this.platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}



	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	

}