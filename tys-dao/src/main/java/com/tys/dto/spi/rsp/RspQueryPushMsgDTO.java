package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.InfoPushMsg;

public class RspQueryPushMsgDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	
	private List<InfoPushMsg> list;


	public List<InfoPushMsg> getList() {
		return list;
	}

	public void setList(List<InfoPushMsg> list) {
		this.list = list;
	}
	
	

}
