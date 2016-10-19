package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the rel_news_comment database table.
 * 
 */
@Entity
@Table(name="rel_news_comment")
@NamedQuery(name="RelNewsComment.findAll", query="SELECT r FROM RelNewsComment r")
public class RelNewsComment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(nullable=false, length=200)
	private String comment;

	@Column(name="comment_id", nullable=false)
	private Integer commentId;

	@Column(name="news_id", nullable=false)
	private Integer newsId;

	@Column(name="user_id", nullable=false)
	private Integer userId;

	public RelNewsComment() {
	}


	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getCommentId() {
		return this.commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public Integer getNewsId() {
		return this.newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

}