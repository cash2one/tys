/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.DArea;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdAreaService extends BaseService<DArea> {
	
	public DArea findByName(String name){
		String sql = " from DArea where name='"+name+"'";
		return this.findUnique(sql, DArea.class);
	}
	
}
