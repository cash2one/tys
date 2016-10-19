package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.Attendance;

public class RspAttendanceDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<Attendance> list;

	public List<Attendance> getList() {
		return list;
	}

	public void setList(List<Attendance> list2) {
		this.list = list2;
	}

}
