/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.DCity;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdCityService extends BaseService<DCity> {
	
	public DCity findByName(String name){
		String sql = " from DCity where name='"+name+"'";
		return this.findUnique(sql, DCity.class);
	}
	
}
