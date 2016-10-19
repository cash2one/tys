package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_question database table.
 * 
 */
@Entity
@Table(name="info_question")
@NamedQuery(name="InfoQuestion.findAll", query="SELECT m FROM InfoQuestion m")
public class InfoQuestion extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="answer_id", nullable=false)
	private Integer answerId;

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
	
	
	
	public InfoQuestion() {
	}

	public Integer getAnswerId() {
		return this.answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
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

	

}