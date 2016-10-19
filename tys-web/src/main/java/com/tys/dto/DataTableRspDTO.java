package com.tys.dto;

import java.util.List;


/**
 *	DataTable返回数据对象
 */
public class DataTableRspDTO<T> {
	
	/**
	 * 请求次数计数器，每次发送给服务器后又原封返回，因为请求是异步的为了确保每次请求能对应到服务器返回的数据，
	 * 假设你请求的是第一页的数据收到的却是第二页的，这样就乱套了 （这是我对draw的理解，如果有不同理解的可以告知）
	 */
	private Integer draw;

	/**
	 * 数据总数
	 */
	private Integer recordsTotal;

	/**
	 * 过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数）
	 */
	private Integer recordsFiltered;

	/**
	 * 数据内容
	 */
	private List<T> data;

	/**
	 * 后台出错提示文字
	 */
	private String error;

//	/**
//	 * 自动绑定到 tr节点上
//	 */
//	private String DT_RowId;
//	
//	/**
//	 * 自动把这个类名添加到 tr
//	 */
//	private String DT_RowClass;
//	
//	/**
//	 * 使用 jQuery.data() 方法把数据绑定到row中，方便之后用来检索（比如加入一个点击事件）
//	 */
//	private String DT_RowData;
//
//	/**
//	 * 自动绑定数据到 tr上，使用 jQuery.attr() 方法，对象的键用作属性，值用作属性的值。注意这个 需要 Datatables
//	 * 1.10.5+的版本才支持
//	 */
//	private String DT_RowAttr;

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Integer recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Integer getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Integer recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> list) {
		this.data = list;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

//	public String getDT_RowId() {
//		return DT_RowId;
//	}
//
//	public void setDT_RowId(String dT_RowId) {
//		DT_RowId = dT_RowId;
//	}
//
//	public String getDT_RowClass() {
//		return DT_RowClass;
//	}
//
//	public void setDT_RowClass(String dT_RowClass) {
//		DT_RowClass = dT_RowClass;
//	}
//
//	public String getDT_RowData() {
//		return DT_RowData;
//	}
//
//	public void setDT_RowData(String dT_RowData) {
//		DT_RowData = dT_RowData;
//	}
//
//	public String getDT_RowAttr() {
//		return DT_RowAttr;
//	}
//
//	public void setDT_RowAttr(String dT_RowAttr) {
//		DT_RowAttr = dT_RowAttr;
//	}

	
	
}
