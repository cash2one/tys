/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqCommitQuestionDTO {

	private Integer questionId;

	@NotBlank
	@Length(min = 1, max = 30)
	private String title;

	@NotBlank
	@Length(min = 5)
	private String content;

	@Pattern(regexp = "\\d+(,\\d+){0,9}") // 格式XXX,XXX,XXX,XXX，最多9个
	private String fileIds;

	private Integer points;
	@NotNull
	private Integer gradeId;
	@NotNull
	private Integer courseId;
	@NotBlank
	@Length(min = 1, max = 30)
	private String author;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

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


	public String getFileIds() {
		return fileIds;
	}

	public void setFileIds(String fileIds) {
		this.fileIds = fileIds;
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


}
