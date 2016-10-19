package com.tys.spi.app.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.tys.entity.MdUser;
import com.tys.service.MdUserService;
import com.tys.util.MStrUtil;

@Service
public class UserHelpService {
	
	@Autowired
	private MdUserService mdUserService;
	
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
	public MdUser getMduser(HttpSession session) {
		Integer accid = getAcctId(session);
		if(accid != null){
			return this.mdUserService.findById(accid);
		}
		return null;
	}
	
	
	
	/**
	 * 注意性能损失
	 * @return
	 */
	@Deprecated
	public Integer getAcctId() {
		UserDetails user = getUserDetails();
		if (null == user || MStrUtil.isNull(user.getUsername())) {
			return null;
		}

		MdUser mdUser = this.mdUserService.findByAcct(user.getUsername());
		if (null == mdUser) {
			return null;
		}
		return mdUser.getId();
	}
	
	/**
	 * 注意性能损失
	 * @return
	 */
	@Deprecated
	public MdUser getMduser() {
		UserDetails user = getUserDetails();
		if (null == user || MStrUtil.isNull(user.getUsername())) {
			return null;
		}
		return this.mdUserService.findByAcct(user.getUsername());
	}

}
