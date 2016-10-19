package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.JSONObject;


/**
 * The persistent class for the info_push_msg database table.
 * 
 */
@Entity
@Table(name="info_push_msg")
@NamedQuery(name="InfoPushMsg.findAll", query="SELECT i FROM InfoPushMsg i")
public class InfoPushMsg implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Lob
	private String extraData;

	private Integer handleRet;

	@Temporal(TemporalType.TIMESTAMP)
	private Date handleTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date pushTime;

	@Column(nullable=false)
	private Integer type;

	@Column(nullable=false)
	private Integer userId;

	public InfoPushMsg() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public JSONObject getExtraData() {
		if(this.extraData != null){
			return JSONObject.parseObject(this.extraData);
		}
		return null;
	}

	public void setExtraData(String extraData) {
		this.extraData = extraData;
	}

	public Integer getHandleRet() {
		return this.handleRet;
	}

	public void setHandleRet(Integer handleRet) {
		this.handleRet = handleRet;
	}

	public Date getHandleTime() {
		return this.handleTime;
	}

	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}

	public Date getPushTime() {
		if(this.pushTime == null)
			this.pushTime = new Date();
		return this.pushTime;
	}

	public void setPushTime(Date pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}