/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tys.entity.InfoPushMsg;
import com.tys.util.MDateUtil;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
public class InfoPushMsgService extends BaseService<InfoPushMsg> {

	
	public List<InfoPushMsg> findByUserId(Integer userId, Long lastTime) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from InfoPushMsg t where t.userId=").append(userId);
		if(lastTime != null){
			sb.append(" and t.pushTime>'").append(MDateUtil.format(MDateUtil.ISO_EXPANDED_DATE_FORMAT, lastTime));
		}
		sb.append(" limit 20");
		
		return findList(sb.toString());
	}
	
}
