package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.InfoAppNews;

public class RspJxzxInfoDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	
	private Integer leftCount;

	private InfoAppNews info; 
	
	private Integer isLike;
	private Integer likeCount;
	private Integer commentCount;
	
	
	
	
	
	public Integer getLeftCount() {
		return leftCount;
	}

	public void setLeftCount(Integer leftCount) {
		this.leftCount = leftCount;
	}


	public InfoAppNews getInfo() {
		return info;
	}

	public void setInfo(InfoAppNews info) {
		this.info = info;
	}

	public Integer getIsLike() {
		return isLike;
	}

	public void setIsLike(Integer isLike) {
		this.isLike = isLike;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	


}
