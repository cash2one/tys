/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelPushUser;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
public class RelPushUserService extends BaseService<RelPushUser> {

	public List<Integer> findUserIdByTagName(String tagName) {
		String sql = "select t.userId from RelPushUser t where t.tagName='"+tagName+"'";
		
		return findList(sql, Integer.class);
	}

	@Transactional
	public int deleteByTagName(String tagName) {
		String sql = "delete from RelPushUser where tagName='"+tagName+"'";
		return executeUpdate(sql);
	}
	
}
