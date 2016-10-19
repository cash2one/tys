package com.tys.entity.slim;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tys.entity.InfoQuestionAnswer;

/**
 * The persistent class for the md_question database table.
 * 
 */
@Entity
@Table(name = "info_question")
public class QuestionUpdate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "is_deleted", nullable = false)
	private Integer isDeleted = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false)
	private Date updateTime;

	@Column(nullable = false)
	private Integer isResolved;

	@Column(name = "points", nullable = false)
	private Integer points;

	//TODO 待优化
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="question_id")
	private Set<InfoQuestionAnswer> answerCount;

	public QuestionUpdate() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getIsResolved() {
		return isResolved;
	}

	public void setIsResolved(Integer isResolved) {
		this.isResolved = isResolved;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getAnswerCount() {
		if (answerCount != null)
			return answerCount.size();
		return 0;
	}

	public void setAnswerCount(Set<InfoQuestionAnswer> answerCount) {
		this.answerCount = answerCount;
	}

}