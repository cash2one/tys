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
public class ReqClassNoticeDTO  {

	@NotNull
	private Integer chatClassId;

	@NotBlank
	@Length(min=1, max=200)
	private String content;

	public Integer getChatClassId() {
		return chatClassId;
	}

	public void setChatClassId(Integer chatClassId) {
		this.chatClassId = chatClassId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
