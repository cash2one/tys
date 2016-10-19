package com.tys.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelSysuserLevel;
import com.tys.util.MCollectionUtils;
import com.tys.util.custom.BaseService;

@Service
@Repository
public class RelSysuserLevelService extends BaseService<RelSysuserLevel> {

	public List<Integer> findUserIdByParentId(Integer parentId) {
		List<Integer> result = new ArrayList<Integer>();
		String hql = " from RelSysuserLevel where parentId = " + parentId;
		List<RelSysuserLevel> list = this.findList(hql);

		if (MCollectionUtils.isEmpty(list)) {
			return result;
		}
		for (RelSysuserLevel l : list) {
			result.add(l.getSysUserId());
		}
		return result;
	}

	public List<RelSysuserLevel> getUserLevelByUserId(int userId) {
		String hql = " from RelSysuserLevel where sysUserId = " + userId;
		return findList(hql);
	}
	
	public RelSysuserLevel getParentByUserId(Integer userId){
		List<RelSysuserLevel> list = this.getUserLevelByUserId(userId);
		if(MCollectionUtils.isEmpty(list)){
			return null;
		}
		
		if(list.size() == 1){
			return list.get(0);
		}
		
		Collections.sort(list, new Comparator<RelSysuserLevel>(){
			public int compare(RelSysuserLevel arg0, RelSysuserLevel arg1) {  
                return arg1.getParentLevel().compareTo(arg0.getParentLevel());  
            }  
		});
		
		return list.get(0);
	}

	/**
	 * 创建用户关联关系 *注意：此方法只适用于当前用户创建自己下一级用户，！！不支持跨等级创建新用户！！*
	 * 
	 * @param newId
	 * @param currentId
	 */
	@Transactional
	public void addRelUserLevel(int newId, int currentId) {
		List<RelSysuserLevel> list = this.getUserLevelByUserId(currentId);
		// 添加当前用户的父级与新创建的（自己下一级）用户的关系
		for (RelSysuserLevel relSysuserLevel : list) {
			RelSysuserLevel userLevel = new RelSysuserLevel();
			userLevel.setParentId(relSysuserLevel.getParentId());
			userLevel.setParentLevel(relSysuserLevel.getParentLevel() + 1);
			userLevel.setSysUserId(newId);
			this.save(userLevel);
		}

		// 添加当前用户与新创建的用户的关系
		RelSysuserLevel userLevel = new RelSysuserLevel();
		userLevel.setParentId(currentId);
		userLevel.setParentLevel(list.size() + 1);
		userLevel.setSysUserId(newId);
		this.save(userLevel);
	}
}
