/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.RelNewsLike;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class RelNewsLikeService extends BaseService<RelNewsLike> {

	
	public boolean isUserLike(Integer userId) {
		String sql = "select count(t.id) from RelNewsLike t where t.userId="+userId;
		Long count = findCount(sql);
		if(count == null || count == 0){
			return false;
		}
		return true;
	}
	
	public Long findLikeCountByNewsId(Integer newsId) {
		String sql = "select count(t.id) from RelNewsLike t where t.newsId="+newsId;
		Long count = findCount(sql);
		return count;
	}


}
