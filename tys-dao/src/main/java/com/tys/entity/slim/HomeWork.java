package com.tys.entity.slim;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * The persistent class for the md_home_work database table.
 * 
 */
@Entity
@Table(name = "md_home_work")
public class HomeWork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",unique = true, nullable = false)
	private Integer homeworkId;

	@Column(name = "course_id", nullable = false)
	private Integer courseId;

	@Column(name = "class_id", nullable = false)
	private Integer classId;
	
	@Column(nullable = false)
	private Integer state;

	@Lob
	@Column(name = "work_content", nullable = false)
	private String content;

	@Column(name = "work_name", nullable = false, length = 50)
	private String title;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "complete_time", nullable = false, length = 50)
	private Date completeTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "class_id", referencedColumnName="id",insertable=false, updatable=false)
	private MClass className;
	
	private String author;
	
	@Column(name = "teacher_id", nullable = false)
	private Integer teacherId;
	
	public HomeWork() {
	}
	
	public String getClassName() {
		return className.getClassName();
	}

	public void setClassName(MClass className) {
		this.className = className;
	}



	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}


	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}


	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public Integer getHomeworkId() {
		return homeworkId;
	}

	public void setHomeworkId(Integer homeworkId) {
		this.homeworkId = homeworkId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getTeacherId() {
		return teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	
	
	
}