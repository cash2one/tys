package com.tys.entity.slim;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tys.base.BaseEntity;
import com.tys.entity.InfoQuestionAnswer;


/**
 * 
 * 问题列表中，内容仅显示30个字
 * 注：问题详情页，不能使用本实体
 */
@Entity
@Table(name="info_question")
public class Question extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(nullable=false, length=30)
	private String author;

	@Lob
	@Column(nullable=false)
	private String content;

	@Column(name="course_id", nullable=false)
	private Integer courseId;


	@Column(name="grade_id", nullable=false)
	private Integer gradeId;

	@Column(nullable=false)
	private Integer isResolved;

	@Column(name="points", nullable=false)
	private Integer points;

	@Column(nullable=false, length=30)
	private String title;

	@Column(name = "city_code", nullable = false)
	private Integer cityCode;
	
	//TODO 待优化
	@OneToMany(fetch=FetchType.EAGER)
	@JoinColumn(name="question_id")
	private Set<InfoQuestionAnswer> answerCount;
	
	
	public Question() {
	}


	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		if(this.content != null && this.content.length() > 30)
			return this.content.substring(0, 30) + "...";
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getGradeId() {
		return this.gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getIsResolved() {
		return this.isResolved;
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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}


	public Integer getAnswerCount() {
		if(answerCount != null)
			return answerCount.size();
		return 0;
	}


	public void setAnswerCount(Set<InfoQuestionAnswer> answerCount) {
		this.answerCount = answerCount;
	}


}