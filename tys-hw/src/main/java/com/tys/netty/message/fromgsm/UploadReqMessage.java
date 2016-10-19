package com.tys.netty.message.fromgsm;

import java.util.Date;

import com.tys.netty.dto.AddressInfo;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.NumberUtil;


public class UploadReqMessage extends AbstractRecvGsmMessage {

	private String gpsStatus;

	private Integer battery;

	private Integer gsmSignal;

	private Integer gpsStarSearchNumber;

	private Double gpsAngle;

	private String gpsDate;

	private Double latitude;

	private String latitudeDirection;

	private Double longitude;

	private String longitudeDirection;

	private Double speed;

	private String mnc;

	private String mcc;

	private String lac1;

	private String cellId1;

	private String sign1;

	private String lac2;

	private String cellId2;

	private String sign2;

	private String lac3;

	private String cellId3;

	private String sign3;

	private String timeZone;

	private String terminalStatus;
	
	private AddressInfo addressInfo;
	
	@Override
	protected boolean parseParameters(String[] paras) {
		
		//注意与协议的长度不同，基站数据有一点不同
		if (paras.length >= 30) {
			this.setGpsStatus(paras[2]);
			this.setBattery(MStrUtil.isNull(paras[3]) ? null : Double.valueOf(paras[3]).intValue());
			this.setGsmSignal(MStrUtil.isNull(paras[4]) ? null : Double.valueOf(paras[4]).intValue());
			this.setGpsStarSearchNumber(MStrUtil.isNull(paras[5]) ? null: Double.valueOf(paras[5]).intValue());
			this.setGpsAngle(MStrUtil.isNull(paras[6]) ? null : Double.valueOf(paras[6]));
			this.setGpsDate(parseGpsDate(paras[7]));
			
			this.setLatitude(parsePosition(paras[8]));
			this.setLatitudeDirection(paras[9]);
			this.setLongitude(parsePosition(paras[10]));
			this.setLongitudeDirection(paras[11]);
			this.setSpeed(MStrUtil.isNull(paras[12]) ? null : Double.valueOf(paras[12]));
			this.setMcc(paras[13]);
			this.setMnc(paras[14]);
			this.setLac1(paras[15]);
			this.setCellId1(paras[16]);
			this.setSign1(paras[17]);
			this.setLac2(paras[20]);
			this.setCellId2(paras[21]);
			this.setSign2(paras[22]);
			this.setLac3(paras[25]);
			this.setCellId3(paras[26]);
			this.setSign3(paras[27]);
			this.setTimeZone(paras[28]);
			this.setTerminalStatus(paras[29]);
			return true;
		}
		return false;
	}
	
	
	private String parseGpsDate(String date){
		String pDate = null;
		try {
			pDate = MDateUtil.formatDate(MDateUtil.DATETIME_PATTERN, new Date(Long.valueOf(date)));
		} catch (Exception e) {
		}
		if (MStrUtil.isNull(pDate)) {
			pDate = MDateUtil.formatDate();
		}
		return pDate;
	}
	
	private Double parsePosition(String value) {
		if (MStrUtil.isNull(value)) {
			return null;
		}

		int poi = value.indexOf(".");
		if (poi == -1) {
			return null;
		}

		try {
			String s1 = value.substring(0, poi - 2);
			String s2 = value.substring(poi - 2, value.length());
			return NumberUtil.round(Double.valueOf(s1) + Double.valueOf(s2) / 60, 8);
		} catch (Exception e) {
			return null;
		}
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

	public Integer getGsmSignal() {
		return gsmSignal;
	}

	public void setGsmSignal(Integer gsmSignal) {
		this.gsmSignal = gsmSignal;
	}

	public Integer getGpsStarSearchNumber() {
		return gpsStarSearchNumber;
	}

	public void setGpsStarSearchNumber(Integer gpsStarSearchNumber) {
		this.gpsStarSearchNumber = gpsStarSearchNumber;
	}

	public Double getGpsAngle() {
		return gpsAngle;
	}

	public void setGpsAngle(Double gpsAngle) {
		this.gpsAngle = gpsAngle;
	}

	public String getGpsDate() {
		return gpsDate;
	}

	public void setGpsDate(String gpsDate) {
		this.gpsDate = gpsDate;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLatitudeDirection() {
		return latitudeDirection;
	}

	public void setLatitudeDirection(String latitudeDirection) {
		this.latitudeDirection = latitudeDirection;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public String getLongitudeDirection() {
		return longitudeDirection;
	}

	public void setLongitudeDirection(String longitudeDirection) {
		this.longitudeDirection = longitudeDirection;
	}

	public Double getSpeed() {
		return speed;
	}

	public void setSpeed(Double speed) {
		this.speed = speed;
	}

	public String getMnc() {
		return mnc;
	}

	public void setMnc(String mnc) {
		this.mnc = mnc;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getLac1() {
		return lac1;
	}

	public void setLac1(String lac1) {
		this.lac1 = lac1;
	}

	public String getCellId1() {
		return cellId1;
	}

	public void setCellId1(String cellId1) {
		this.cellId1 = cellId1;
	}

	public String getSign1() {
		return sign1;
	}

	public void setSign1(String sign1) {
		this.sign1 = sign1;
	}

	public String getLac2() {
		return lac2;
	}

	public void setLac2(String lac2) {
		this.lac2 = lac2;
	}

	public String getCellId2() {
		return cellId2;
	}

	public void setCellId2(String cellId2) {
		this.cellId2 = cellId2;
	}

	public String getSign2() {
		return sign2;
	}

	public void setSign2(String sign2) {
		this.sign2 = sign2;
	}

	public String getLac3() {
		return lac3;
	}

	public void setLac3(String lac3) {
		this.lac3 = lac3;
	}

	public String getCellId3() {
		return cellId3;
	}

	public void setCellId3(String cellId3) {
		this.cellId3 = cellId3;
	}

	public String getSign3() {
		return sign3;
	}

	public void setSign3(String sign3) {
		this.sign3 = sign3;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTerminalStatus() {
		return terminalStatus;
	}

	public void setTerminalStatus(String terminalStatus) {
		this.terminalStatus = terminalStatus;
	}


	public AddressInfo getAddressInfo() {
		return addressInfo;
	}

	public void setAddressInfo(AddressInfo addressInfo) {
		this.addressInfo = addressInfo;
	}

	

}
