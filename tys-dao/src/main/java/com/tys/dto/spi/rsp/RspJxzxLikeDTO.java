package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;

public class RspJxzxLikeDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private Integer likeCount;
	
	

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}



}
