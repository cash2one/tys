package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the rel_push_user database table.
 * 
 */
@Entity
@Table(name="rel_push_user")
@NamedQuery(name="RelPushUser.findAll", query="SELECT r FROM RelPushUser r")
public class RelPushUser extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="tag_name", nullable=false)
	private String tagName;

	@Column(name="user_id", nullable=false)
	private Integer userId;

	public RelPushUser() {
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}