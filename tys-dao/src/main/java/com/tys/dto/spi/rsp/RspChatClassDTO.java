package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.ChatClass;

public class RspChatClassDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<ChatClass> list;

	public List<ChatClass> getList() {
		return list;
	}

	public void setList(List<ChatClass> list2) {
		this.list = list2;
	}

	

}
