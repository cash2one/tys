package com.tys.sec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tys.entity.RelSysuserPermission;
import com.tys.service.RelSysuserPermissionService;
import com.tys.service.SysUserService;
import com.tys.util.MUtil;
import com.tys.util.custom.CustomGrantedAuthorityImpl;

public class SysSecService implements UserDetailsService {

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private RelSysuserPermissionService relSysuserPermissionService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MUtil.log("System user=" + username);
		UserDetails user = null;
		try {
			// 搜索数据库以匹配用户登录名.
			// 我们可以通过dao使用Hibernate来访问数据库
			String pw = sysUserService.findPwByAcct(username);
			if(pw == null){
				throw new UsernameNotFoundException("用户不存在");
			} else {
				// 用户名、密码、是否启用、是否被锁定、是否过期、权限
				user = new User(username, pw, true, true, true, true, getAuthorities(username));
			}

		} catch (Exception e) {
			throw new UsernameNotFoundException("异常处理：检索用户信息未通过！");
		}
		return user;
	}

	/**
	 * 获得访问角色权限列表
	 * 
	 * @param username
	 * @param access
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities(String username) {

		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();

		authList.add(new CustomGrantedAuthorityImpl("ROLE_ADMIM"));

		//TODO 查询权限，并添加到authList
//		List<RelSysuserPermission> list = relSysuserPermissionService.findBySysUser(username);
//		if(list != null && list.size() > 0){
//			for(RelSysuserPermission tmp : list){
//				authList.add(new CustomGrantedAuthorityImpl(tmp.getPermission().getName()));
//			}
//		}
		
		return authList;
	}

	public List<RelSysuserPermission> getPermission(String username){
		return relSysuserPermissionService.findBySysUser(username);
	}
	
}
