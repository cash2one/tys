/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.InfoQuestionAnswer;
import com.tys.util.MDateUtil;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class InfoQuestionAnswerService extends BaseService<InfoQuestionAnswer> {

	
	
	public List<InfoQuestionAnswer> findByQuestionId(Integer questionId, Long lastTime) {
		StringBuilder sql = new StringBuilder();
		sql.append("select t from InfoQuestionAnswer t where t.isDeleted=0");
		
		if(lastTime != null){
			sql.append(" and t.createTime>'").append(MDateUtil.format(MDateUtil.DATETIME_PATTERN, lastTime)).append("'");
		}
		
		return findList(sql.toString());
	}
	
	
	
}
