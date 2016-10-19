package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.MdCourse;

public class RspQueryCourseDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<MdCourse> list;

	public List<MdCourse> getList() {
		return list;
	}

	public void setList(List<MdCourse> list) {
		this.list = list;
	}


	
}
