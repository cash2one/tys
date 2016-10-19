/**
 * 
 */
package com.tys.dto.spi.rsp;

import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.MdPoint;

/**
 * @author Administrator
 *
 */
public class RspPointListDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 5636493409358390742L;

	private Integer points;

	private List<MdPoint> pointList;

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public List<MdPoint> getPointList() {
		return pointList;	
	}

	public void setPointList(List<MdPoint> pointList) {
		this.pointList = pointList;
	}


}
