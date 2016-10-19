package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_chat_class database table.
 * 
 */
@Entity
@Table(name="md_chat_class")
@NamedQuery(name="MdChatClass.findAll", query="SELECT m FROM MdChatClass m")
public class MdChatClass extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="chat_name", nullable=false, length=30)
	private String chatName;

	@Column(name="class_id", nullable=false)
	private Integer classId;


	@Column(nullable=false, length=200)
	private String notice;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="notice_time")
	private Date noticeTime;


	public MdChatClass() {
	}


	public String getChatName() {
		return this.chatName;
	}

	public void setChatName(String chatName) {
		this.chatName = chatName;
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}


	public String getNotice() {
		return this.notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

	public Date getNoticeTime() {
		return this.noticeTime;
	}

	public void setNoticeTime(Date noticeTime) {
		this.noticeTime = noticeTime;
	}


}