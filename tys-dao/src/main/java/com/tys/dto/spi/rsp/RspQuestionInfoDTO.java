package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.QuestionFile;

public class RspQuestionInfoDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private String title;
	private String content;

	private List<QuestionFile> files;

	private Integer points;
	private Integer gradeId;
	private Integer courseId;
	
	private Integer answerId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<QuestionFile> getFiles() {
		return files;
	}

	public void setFiles(List<QuestionFile> files) {
		this.files = files;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getGradeId() {
		return gradeId;
	}

	public void setGradeId(Integer gradeId) {
		this.gradeId = gradeId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

}
