/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqJxzxInfoDTO {
	
	@NotNull
	private Integer newsId;

	public Integer getNewsId() {
		return newsId;
	}

	public void setNewsId(Integer newsId) {
		this.newsId = newsId;
	}
	

	
	
}
