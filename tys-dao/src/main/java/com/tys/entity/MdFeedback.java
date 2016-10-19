package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_feedback database table.
 * 
 */
@Entity
@Table(name="md_feedback")
@NamedQuery(name="MdFeedback.findAll", query="SELECT m FROM MdFeedback m")
public class MdFeedback extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Lob
	@Column(nullable=false)
	private String content;


	public MdFeedback() {
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}