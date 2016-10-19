/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.DProvince;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdProvinceService extends BaseService<DProvince> {
	
	public DProvince findByName(String name){
		String sql = " from DProvince where name='"+name+"'";
		return this.findUnique(sql, DProvince.class);
	}
	
}
