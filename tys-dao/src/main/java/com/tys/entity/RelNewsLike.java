package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rel_news_like database table.
 * 
 */
@Entity
@Table(name="rel_news_like")
@NamedQuery(name="RelNewsLike.findAll", query="SELECT r FROM RelNewsLike r")
public class RelNewsLike implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="news_id", nullable=false)
	private Integer newsId;

	@Column(name="user_id", nullable=false)
	private Integer userId;

	public RelNewsLike() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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