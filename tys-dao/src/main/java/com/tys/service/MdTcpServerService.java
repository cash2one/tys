/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.MdTcpServer;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdTcpServerService extends BaseService<MdTcpServer> {
	

	
	public MdTcpServer findMinOnline() {
		String sql = "select t from MdTcpServer t where t.onLineCount=(select min(s.onLineCount) from MdTcpServer s)";
		return findUnique(sql);
	}
	
	
}
