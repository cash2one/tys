package com.tys.helper;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tys.entity.SysUser;
import com.tys.service.SysUserService;

@Service
public class SysUserHelperService {

	
	@Autowired
	private SysUserService sysUserService;
	
	public UserDetails getUserDetails() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (UserDetails) principal;
		}
		return null;
	}

	/**
	 * 获取当前登录用户名
	 * @return
	 */
	public String getAcct() {
		UserDetails user = getUserDetails();
		if (user != null)
			return user.getUsername();
		else
			return "";
	}
	
	/**
	 * 获取当前用户的账号id
	 * @param session
	 * @return
	 */
	public Integer getAcctId(HttpSession session) {
		if(session != null){
			//此参数在登录时设置
			return (Integer) session.getAttribute("accountId");
		} else {
			return null;
		}
	}
	
	
	/**
	 * 获取当前用户的实体
	 * @param session
	 * @return
	 */
	public SysUser getSysuser(HttpSession session) {
		Integer accid = getAcctId(session);
		if(accid != null){
			return this.sysUserService.findById(accid);
		}
		return null;
	}
	
	

}
