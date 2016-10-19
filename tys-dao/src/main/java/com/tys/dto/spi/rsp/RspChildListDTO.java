package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;

public class RspChildListDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private List<RspChildListInnerDTO> childList;

	public List<RspChildListInnerDTO> getChildList() {
		return childList;
	}

	public void setChildList(List<RspChildListInnerDTO> childList) {
		this.childList = childList;
	}

}
