package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.HomeWork;

public class RspMySettingHomeWorkDTO extends BaseSpiRsp {

	private static final long serialVersionUID = -5433269517890859400L;
	
	private List<HomeWork> list;

	public List<HomeWork> getList() {
		return list;
	}

	public void setList(List<HomeWork> list2) {
		this.list = list2;
	}
	
	
}
