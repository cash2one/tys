package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.tys.entity.slim.QuestionFile;


/**
 * The persistent class for the rel_question_file database table.
 * 
 */
@Entity
@Table(name="rel_question_file")
@NamedQuery(name="RelQuestionFile.findAll", query="SELECT r FROM RelQuestionFile r")
public class RelQuestionFile implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;


	@Column(name="question_id", nullable=false)
	private Integer questionId;
	
	@Column(name="file_id", nullable=false)
	private Integer fileId;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "file_id", insertable = false, updatable = false)
	private QuestionFile questionFile;
	
	public RelQuestionFile() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public QuestionFile getQuestionFile() {
		return questionFile;
	}

	public void setQuestionFile(QuestionFile questionFile) {
		this.questionFile = questionFile;
	}
	
	

}