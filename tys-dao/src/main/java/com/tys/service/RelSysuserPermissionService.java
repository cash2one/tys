package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelSysuserPermission;
import com.tys.util.custom.BaseService;

@Service
@Repository
public class RelSysuserPermissionService extends BaseService<RelSysuserPermission> {

	public List<RelSysuserPermission> findBySysUser(String username) {
		String sql = "select c from RelSysuserPermission c where c.sysUser.loginName='" + username + "'";
		return findList(sql);
	}

	public List<RelSysuserPermission> findBySysUser(int id) {
		String sql = "select c from RelSysuserPermission c where c.sysUser.id=" + id;
		return findList(sql);
	}

	/**
	 * 批量插入权限
	 * @param permissions eg:  (1,2,3,4,5,6)
	 * @param userid
	 */
	@Transactional
	public void savePermissions(String permissions, int userid) {
		// 初始化权限ID数组
		String[] permission = permissions.split(",");
		// 赋予注册用户权限
		for (String string : permission) {
			RelSysuserPermission userPermission = new RelSysuserPermission();
			userPermission.setSysuserId(userid);
			userPermission.setPermissionId(Integer.parseInt(string));
			this.save(userPermission);
		}
	}

	/**
	 * 根据用户ID批量移除权限
	 * @param userid
	 */
	@Transactional
	public void removePermissionsByUserId(int userid) {
		List<RelSysuserPermission> permissionList = this.findBySysUser(userid);
		for (RelSysuserPermission relSysuserPermission : permissionList) {
			this.delete(relSysuserPermission);
		}
	}
}
