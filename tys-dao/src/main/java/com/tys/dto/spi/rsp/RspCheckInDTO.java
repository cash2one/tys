/**
 * 
 */
package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;

/**
 * @author Administrator
 *
 */
public class RspCheckInDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 5636493409358390742L;

	private Integer points;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

}
