package com.tys.spi.app.sec;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.tys.service.MdUserService;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.custom.CustomGrantedAuthorityImpl;
import com.tys.util.custom.CustomUser;

public class UserSecService implements UserDetailsService {

	@Autowired
	private MdUserService mdUserService;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		MUtil.log("App login : " + username);
		CustomUser user = null;
		String pw = mdUserService.findPwByAcct(username);
		if(!MStrUtil.isNull(pw)){
			// 用户名、密码、是否启用、是否被锁定、是否过期、权限
			user = new CustomUser(username, pw, true, true, true, true, getAuthorities());
		} else {
			MUtil.log("User not found");
			throw new UsernameNotFoundException("用户未注册");
		}
		
		return user;
	}

	/**
	 * 获得访问角色权限列表
	 * 
	 * @param access
	 * @return
	 */
	public Collection<GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
		authList.add(new CustomGrantedAuthorityImpl("ROLE_PARENT"));
		return authList;
	}
}
