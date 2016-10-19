/**
 * 
 */
package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;

/**
 * @author Administrator
 *
 */
public class RspLocationDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 8553155744655775250L;

	private Integer mapType;
	private Double longitude;
	private Double latitude;
	private Long upload;

	public Integer getMapType() {
		return mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Long getUpload() {
		return upload;
	}

	public void setUpload(Long upload) {
		this.upload = upload;
	}

}
