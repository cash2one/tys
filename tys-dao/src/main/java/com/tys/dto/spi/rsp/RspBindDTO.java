/**
 * 
 */
package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;

/**
 * @author Administrator
 *
 */
public class RspBindDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 8553155744655775250L;

	private String imei;

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

}
