/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.MdLocation;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdLocationService extends BaseService<MdLocation> {

	public MdLocation findNewbyImei(String imei) {
		String sql = "from MdLocation where imei='"+imei+"' order by createTime desc";
		return findUnique(sql);
	}
	
}
