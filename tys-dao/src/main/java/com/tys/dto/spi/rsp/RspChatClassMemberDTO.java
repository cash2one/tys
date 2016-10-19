package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;

public class RspChatClassMemberDTO extends BaseSpiRsp {

	private static final long serialVersionUID = -3595568516697968077L;
	private List<ChatClassMemberDTO> list;

	public List<ChatClassMemberDTO> getList() {
		return list;
	}

	public void setList(List<ChatClassMemberDTO> list) {
		this.list = list;
	}

}
