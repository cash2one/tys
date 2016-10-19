package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.MdAppAd;

public class RspQueryAppAdDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<MdAppAd> list;

	public List<MdAppAd> getList() {
		return list;
	}

	public void setList(List<MdAppAd> list) {
		this.list = list;
	}

}
