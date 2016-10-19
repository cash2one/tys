/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;

/**
 * @author Administrator
 *
 */
public class ReqChatClassMemberDTO {

	@NotNull
	private Integer chatClassId;

	public Integer getChatClassId() {
		return chatClassId;
	}

	public void setChatClassId(Integer chatClassId) {
		this.chatClassId = chatClassId;
	}

}
