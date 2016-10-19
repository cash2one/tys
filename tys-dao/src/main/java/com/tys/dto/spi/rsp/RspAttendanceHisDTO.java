package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.slim.Attendance;

public class RspAttendanceHisDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<Attendance> list;
	private String imei;

	public List<Attendance> getList() {
		return list;
	}

	public void setList(List<Attendance> list2) {
		this.list = list2;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	
	
}
