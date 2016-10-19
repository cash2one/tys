/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.MdRtData;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdRtDataService extends BaseService<MdRtData> {
	

	
	public MdRtData findByImei(String imei) {
		String sql = "select c from MdRtData c where c.imei='" + imei + "'";
		return findUnique(sql);
	}

	@Transactional
	public void updateOnline(String imei, String host, Integer port, int online) {
		String sql = "update MdRtData set isOnline="+online;
		if(host != null){
			sql += ",curIp='"+host+"'";
		}
		if(port != null){
			sql += ",curPort="+port;
		}
		if(online == 1){
			sql += ",connectTime=NOW()";
		} else {
			sql += ",lastBreakTime=NOW()";
		}
		sql += " where imei='" + imei + "'";
		executeUpdate(sql);
	}
	
	
}
