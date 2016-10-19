/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqAnswerListDTO {

	@NotNull
	private Integer questionId;
	
	private Long lastTime;


	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}




}
