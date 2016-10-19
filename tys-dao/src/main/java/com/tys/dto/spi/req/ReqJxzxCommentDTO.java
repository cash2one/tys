/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqJxzxCommentDTO {
	
	@NotNull
	private Integer newsId;
	
	private Integer commentId;
	
	@NotBlank
	@Length(min=5)
	private String comment;

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}

	public Integer getCommentId() {
		return commentId;
	}

	public void setCommentId(Integer commentId) {
		this.commentId = commentId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	

	
	
	
}
