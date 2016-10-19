/**
 * 
 */
package com.tys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.AuthCustomPermission;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class AuthCustomPermissionService extends BaseService<AuthCustomPermission> {
	public List<AuthCustomPermission> getAllType() {
		String sql = "from AuthCustomPermission a where a.type is null";
		return findList(sql);
	};

	public List<AuthCustomPermission> getPermissionByType(int userType,int userId) {
		String sql = "from AuthCustomPermission a where 1=1";
		// 超级管理员和总公司始终都有所有权限
		if (userType != 0 && userType!=1) {
			sql += " and a.id in (select b.permissionId from RelSysuserPermission b where b.sysuserId="+userId+")";
		}
		return findList(sql);
	};

	public List getTreeViewData(int userType,int id) {
		List permissionList = new ArrayList();
		List<AuthCustomPermission> list = getPermissionByType(userType,id);
		for (AuthCustomPermission permission : list) {
			Map map = new HashMap();
			map.put("id", permission.getId());
			map.put("text", permission.getName());
			map.put("parentid", permission.getType());
			map.put("value", permission.getId());
			permissionList.add(map);
		}
		return permissionList;
	}
}
