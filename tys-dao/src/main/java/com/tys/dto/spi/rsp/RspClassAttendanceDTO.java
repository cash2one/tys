package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;

public class RspClassAttendanceDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<RspClassAttendanceInnerDTO> list;

	public List<RspClassAttendanceInnerDTO> getList() {
		return list;
	}

	public void setList(List<RspClassAttendanceInnerDTO> list) {
		this.list = list;
	}

	
}
