package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONType;


/**
 * The persistent class for the rel_question_answer database table.
 * 
 */
@JSONType(ignores={"isDeleted", "createBy", "updateTime", "updatedBy"})
@Entity
@Table(name="info_question_answer")
@NamedQuery(name="InfoQuestionAnswer.findAll", query="SELECT r FROM InfoQuestionAnswer r")
public class InfoQuestionAnswer extends com.tys.base.BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Lob
	@Column(nullable=false)
	private String answer;


	@Column(name="question_id", nullable=false)
	private int questionId;

	
	@Column(name="user_id", nullable=false)
	private int userId;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", insertable = false, updatable = false)
	private MdUser author;
	
	
	public InfoQuestionAnswer() {
	}


	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}


	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}


	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getAuthor() {
		return author.getName();
	}

	public void setAuthor(MdUser author) {
		this.author = author;
	}


}