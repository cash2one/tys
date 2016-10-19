package com.tys.sec;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.tys.entity.AuthCustomPermission;
import com.tys.entity.RelSysuserPermission;
import com.tys.entity.SysUser;
import com.tys.entity.SysUserLoginHis;
import com.tys.service.AuthCustomPermissionService;
import com.tys.service.RelSysuserPermissionService;
import com.tys.service.SysUserLoginHisService;
import com.tys.service.SysUserService;

public class SysLoginHandler implements AuthenticationSuccessHandler {

	@Autowired
	private SysUserLoginHisService sysUserLoginHisService;
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private RelSysuserPermissionService relSysuserPermissionService;
	@Autowired
	private AuthCustomPermissionService authCustomPermissionService;
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		
		String userName = authentication.getName();
		
		SysUserLoginHis his = new SysUserLoginHis();
		his.setLoginName(userName);
		his.setLoginIp(request.getRemoteAddr());
		his.setCreateTime(new Date());
		
		sysUserLoginHisService.save(his);
		
		//把账号id保存到session中，方便后面更新数据
		SysUser sys = sysUserService.findByAcct(userName);
		HttpSession session = request.getSession();
		session.setAttribute("accountId", sys.getId());
		session.setAttribute("sysUser", sys);
		try {
			if(sys.getUserType()==0 || sys.getUserType()==1){//超级管理、总公司默认有所有权限
				List<AuthCustomPermission> list = authCustomPermissionService.findAll();
				for (AuthCustomPermission permission : list) {
					session.setAttribute("AUTH_"+permission.getId(), permission.getName());
				}
			} else {
				List<RelSysuserPermission> list = relSysuserPermissionService.findBySysUser(userName);
				for (RelSysuserPermission permission : list) {
					session.setAttribute("AUTH_"+permission.getPermissionId(), permission.getPermission().getName());
				}
			}
			
			response.sendRedirect("index");
//			response.sendRedirect("getteachers");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}