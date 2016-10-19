package com.tys.dto;

/**
 * DataTable请求参数对象
 */
public class DataTableReqDTO {

	private Integer draw;

	private Integer start;

	private Integer length;

	private Integer page;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public Integer getPage() {
		if(start == null || length == null)
			return 1;
		return page == null ? ((start / length) + 1) : page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
