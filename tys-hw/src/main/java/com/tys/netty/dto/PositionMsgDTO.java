package com.tys.netty.dto;

import java.util.Date;

public class PositionMsgDTO extends BaseDTO {

	private static final long serialVersionUID = 7943038187996266800L;

	private String imeiNo;

	private String gpsStatus;

	private Integer battery;

	private Double latitude;

	private Double longitude;

	private Double parsedLatitude;

	private Double parsedLongitude;

	private String terminalStatus;

	private String address;

	private String gsmSignal;

	private String alarmType;

	private Date alarmDate;

	private String positionTime;

	private Integer positionType;

	private String strPositionType;

	public String getImeiNo() {
		return imeiNo;
	}

	public void setImeiNo(String imeiNo) {
		this.imeiNo = imeiNo;
	}

	public String getGpsStatus() {
		return gpsStatus;
	}

	public void setGpsStatus(String gpsStatus) {
		this.gpsStatus = gpsStatus;
	}

	public Integer getBattery() {
		return battery;
	}

	public void setBattery(Integer battery) {
		this.battery = battery;
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

	public String getTerminalStatus() {
		return terminalStatus;
	}

	public void setTerminalStatus(String terminalStatus) {
		this.terminalStatus = terminalStatus;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getGsmSignal() {
		return gsmSignal;
	}

	public void setGsmSignal(String gsmSignal) {
		this.gsmSignal = gsmSignal;
	}

	public String getAlarmType() {
		return alarmType;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public Date getAlarmDate() {
		return alarmDate;
	}

	public void setAlarmDate(Date alarmDate) {
		this.alarmDate = alarmDate;
	}

	public Double getParsedLatitude() {
		return parsedLatitude;
	}

	public void setParsedLatitude(Double parsedLatitude) {
		this.parsedLatitude = parsedLatitude;
	}

	public Double getParsedLongitude() {
		return parsedLongitude;
	}

	public void setParseLongitude(Double parsedLongitude) {
		this.parsedLongitude = parsedLongitude;
	}

	public String getPositionTime() {
		return positionTime;
	}

	public void setPositionTime(String positionTime) {
		this.positionTime = positionTime;
	}

	public Integer getPositionType() {
		return positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	public void setParsedLongitude(Double parsedLongitude) {
		this.parsedLongitude = parsedLongitude;
	}

	public String getStrPositionType() {
		return strPositionType;
	}

	public void setStrPositionType(String strPositionType) {
		this.strPositionType = strPositionType;
	}

}
