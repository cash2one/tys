package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.MClass;

public class RspClassDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<MClass> list;

	public List<MClass> getList() {
		return list;
	}

	public void setList(List<MClass> list) {
		this.list = list;
	}

}
