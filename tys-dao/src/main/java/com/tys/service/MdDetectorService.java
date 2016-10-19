/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.MdDetector;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdDetectorService extends BaseService<MdDetector> {

	public boolean isExistsByDeviceId(String deviceId) {
		String sql = "select t.id from MdDetector t where t.detectorNo='"+deviceId+"'";
		return findUnique(sql, Integer.class) != null;
	}

	public MdDetector findByDeviceNo(String deviceId) {
		String sql = "select t from MdDetector t where t.detectorNo='"+deviceId+"'";
		return findUnique(sql);
	}

}
