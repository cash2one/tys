/**
 * 
 */
package com.tys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelChatClassUser;
import com.tys.entity.slim.ChatClass;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
public class RelChatClassUserService extends BaseService<RelChatClassUser> {
	
	@Transactional
	public List<ChatClass> findByUserId(Integer userId){
		String sql = " from RelChatClassUser a where a.userId = " + userId;
		List<RelChatClassUser> list = this.findList(sql);
		
		List<ChatClass> result = new ArrayList<ChatClass>();
		for (RelChatClassUser relChatClassUser : list) {
			ChatClass mdChatClass = relChatClassUser.getChatClass();
			if(null == mdChatClass){
				continue;
			}
			//惰性加载情况下，要get一下内容才能加载数据，否则过后，该持久层session将回收
			mdChatClass.getChatName();
			result.add(mdChatClass);
		}
		return result;
	}
	
	
	public RelChatClassUser findByUserIdAndChatClassId(Integer chatClassId , Integer userId){
		String hql = "from RelChatClassUser where chatClassId =" + chatClassId + " and userId = " + userId;
		return this.findUnique(hql);
	}

}
