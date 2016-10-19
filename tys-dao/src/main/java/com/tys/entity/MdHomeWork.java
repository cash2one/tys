package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tys.base.BaseEntity;

/**
 * The persistent class for the md_home_work database table.
 * 
 */
@Entity
@Table(name = "md_home_work")
@NamedQuery(name = "MdHomeWork.findAll", query = "SELECT m FROM MdHomeWork m")
public class MdHomeWork extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "class_id", nullable = false)
	private Integer classId;

	@Column(name = "course_id", nullable = false)
	private Integer courseId;

	@Column(nullable = false)
	private Integer state;

	@Column(name = "teacher_id", nullable = false)
	private Integer teacherId;

	@Lob
	@Column(name = "work_content", nullable = false)
	private String workContent;

	@Column(name = "work_name", nullable = false, length = 50)
	private String workName;

	private String author;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "complete_time", nullable = false, length = 50)
	private Date completeTime;

	@ManyToOne
	// (fetch = FetchType.LAZY)
	@JoinColumn(name = "class_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdClass mdClass;

	@ManyToOne
	// (fetch = FetchType.LAZY)
	@JoinColumn(name = "course_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdCourse mdCourse;

	@ManyToOne
	// (fetch = FetchType.LAZY)
	@JoinColumn(name = "teacher_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MdUser mdUser;

	public MdHomeWork() {
	}

	public Integer getClassId() {
		return this.classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
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

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Integer getTeacherId() {
		return this.teacherId;
	}

	public void setTeacherId(Integer teacherId) {
		this.teacherId = teacherId;
	}

	public String getWorkContent() {
		return this.workContent;
	}

	public void setWorkContent(String workContent) {
		this.workContent = workContent;
	}

	public String getWorkName() {
		return this.workName;
	}

	public void setWorkName(String workName) {
		this.workName = workName;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public MdClass getMdClass() {
		return mdClass;
	}

	public void setMdClass(MdClass mdClass) {
		this.mdClass = mdClass;
	}

	public MdCourse getMdCourse() {
		return mdCourse;
	}

	public void setMdCourse(MdCourse mdCourse) {
		this.mdCourse = mdCourse;
	}

	public MdUser getMdUser() {
		return mdUser;
	}

	public void setMdUser(MdUser mdUser) {
		this.mdUser = mdUser;
	}

}