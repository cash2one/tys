package com.tys.netty.dto;

import java.io.Serializable;

public class BaseDTO extends BaseEntity implements Serializable {

	private static final long serialVersionUID = -8676342044679100787L;

	private Integer onePageCount;

	private Integer currentPageIndex;

	private String orderbyField;

	private String orderbyType = "ASC";

	private boolean isCalcCount;

	private Long paginationTotalCount;

	public Long getPaginationTotalCount() {
		return paginationTotalCount;
	}

	public void setPaginationTotalCount(Long paginationTotalCount) {
		this.paginationTotalCount = paginationTotalCount;
	}

	public boolean isCalcCount() {
		return isCalcCount;
	}

	public void setCalcCount(boolean isCalcCount) {
		this.isCalcCount = isCalcCount;
	}

	public boolean isPagination() {
		return currentPageIndex != null && onePageCount != null;
	}

	public String getOrderbyField() {
		return orderbyField;
	}

	public void setOrderbyField(String orderbyField) {
		this.orderbyField = orderbyField;
	}

	public String getOrderbyType() {
		return orderbyType;
	}

	public void setOrderbyType(String orderbyType) {
		this.orderbyType = orderbyType;
	}

	public Integer getCurrentPageIndex() {
		return currentPageIndex;
	}

	public void setCurrentPageIndex(Integer currentPageIndex) {
		this.currentPageIndex = (currentPageIndex == null || currentPageIndex < 1) ? 1
				: currentPageIndex;
	}

	public Integer getOnePageCount() {
		return onePageCount;
	}

	public void setOnePageCount(Integer onePageCount) {
		this.onePageCount = onePageCount;
	}
}
