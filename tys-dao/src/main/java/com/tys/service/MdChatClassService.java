package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.MdChatClass;
import com.tys.util.custom.BaseService;

@Service
@Repository
public class MdChatClassService extends BaseService<MdChatClass> {

	@Transactional
	public void modifyNotice(Integer chatClassId,String content){
		String hql = "update MdChatClass set notice ='"+content+"' where id = " + chatClassId;
		this.executeUpdate(hql);
	}
}
