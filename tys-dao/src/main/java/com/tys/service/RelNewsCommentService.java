/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.RelNewsComment;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class RelNewsCommentService extends BaseService<RelNewsComment> {

	
	
	public Long findCountByNewsId(Integer newsId) {
		String sql = "select count(t.id) from RelNewsComment t where t.newsId="+newsId;
		Long count = findCount(sql);
		return count;
	}


}
