package com.tys.util.custom;

import java.util.ArrayList;
import java.util.List;

public class MPage<T> {
	private final static int DEFAULT_PAGE_SIZE = 10;
	private int pageNo;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private int totalCount;
	private List<T> result;

	public MPage() {
		this(1, DEFAULT_PAGE_SIZE, 0, new ArrayList<T>());
	}

	public MPage(int pageNo, int pageSize, int totalCount, List<T> result) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.result = result;
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return this.pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		if (this.totalCount % this.pageSize == 0) {
			return this.totalCount / this.pageSize;
		}
		return this.totalCount / this.pageSize + 1;
	}

	public List<T> getResult() {
		return this.result;
	}

	public void setResult(List<T> result) {
		this.result = result;
	}

	protected static int getStartOfPage(int pageNo) {
		return getStartOfPage(pageNo, DEFAULT_PAGE_SIZE);
	}

	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}
}
