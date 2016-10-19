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

import com.tys.service.MdUserService;
import com.tys.util.MUtil;
import com.tys.util.custom.CustomGrantedAuthorityImpl;

public class SysTeacherSecService implements UserDetailsService {

	@Autowired
	private MdUserService mdUserService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MUtil.log("Teacher user=" + username);
		UserDetails user = null;
		try {
			// 搜索数据库以匹配用户登录名.
			String pw = mdUserService.findPwByAcct(username);
			if(pw == null){
				throw new UsernameNotFoundException("用户不存在");
			} else {
				// 用户名、密码、是否启用、是否被锁定、是否过期、权限
				user = new User(username, pw, true, true, true, false, getAuthorities(username));
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
		//TODO 添加老师固定权限
		
		return authList;
	}
}
