package com.tys.netty.dto;

import java.io.Serializable;

public class ResponsePositionDTO implements Serializable {

	private static final long serialVersionUID = 2078588219157150635L;

	/***
	 * 中心纬度
	 */
	private Double latitude;
	/***
	 * 中心经度
	 */
	private Double longitude;
	/**
	 * 状态
	 */
	private String state;

	private String errMsg;

	private String address;

	private String gpsDate;

	private String positionType;

	public ResponsePositionDTO() {

	}

	public ResponsePositionDTO(String state, String errMsg) {
		this.state = state;
		this.errMsg = errMsg;
	}

	public ResponsePositionDTO(Double latitude, Double longitude, String state,
			String address, String gpsDate,String positionType) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.state = state;
		this.address = address;
		this.gpsDate = gpsDate;
		this.positionType = positionType;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGpsDate() {
		return gpsDate;
	}

	public void setGpsDate(String gpsDate) {
		this.gpsDate = gpsDate;
	}

	public String getPositionType() {
		return positionType;
	}

	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}

}
