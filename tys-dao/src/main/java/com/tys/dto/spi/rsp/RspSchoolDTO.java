package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.School;

public class RspSchoolDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<School> list;

	public List<School> getList() {
		return list;
	}

	public void setList(List<School> list) {
		this.list = list;
	}

}
