/**
 * 
 */
package com.tys.dto.spi.req;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqFeedbackDTO {

	@NotBlank
	@Length(min=5, max=500)
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}


}
