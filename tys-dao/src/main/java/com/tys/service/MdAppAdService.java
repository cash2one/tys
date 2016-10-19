/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.DCity;
import com.tys.entity.MdAppAd;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdAppAdService extends BaseService<MdAppAd> {

	
	@Autowired
	private AddressService addressService;
	
	
	public List<MdAppAd> findByCityAndPosition(Integer cityCode, Integer adPosition) {
		
		StringBuilder sb = new StringBuilder();
		sb.append("select t from MdAppAd t where t.isDeleted=0 and t.status=1");
		if(adPosition != null){
			sb.append(" and t.adPosition=").append(adPosition);
		}
		
		//先查到市
		DCity city = addressService.findCityById(cityCode);
		
		if(city != null){
			Integer provinceCode = city.getProvincecode();
			sb.append(" and t.provinceCode is NULL");//全国的广告
			sb.append(" or (t.cityCode is NULL and t.provinceCode=").append(provinceCode).append(")");//省的广告
			sb.append(" or (t.areaCode is NULL and t.cityCode=").append(cityCode).append(")");//市的广告
//			sb.append(" or t.area_code=").append(areaCode);//区县的广告
		}
		
		return findList(sb.toString());
	}




}
