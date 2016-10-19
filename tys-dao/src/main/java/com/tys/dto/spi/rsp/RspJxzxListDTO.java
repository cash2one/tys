package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.InfoAppNews;

public class RspJxzxListDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<InfoAppNews> list;
	
	private Integer leftCount;

	public List<InfoAppNews> getList() {
		return list;
	}

	public void setList(List<InfoAppNews> list) {
		this.list = list;
	}

	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}


}
