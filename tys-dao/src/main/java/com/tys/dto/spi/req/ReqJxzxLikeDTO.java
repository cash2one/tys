/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqJxzxLikeDTO {
	
	@NotNull
	private Integer newsId;
	@NotNull
	private Integer like;

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	

	
	
}
