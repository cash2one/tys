package com.tys.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.helper.SysUserHelperService;
import com.tys.service.AuthCustomPermissionService;

/**
 * 自定义权限控制列表
 * 
 * @author wjc
 */

@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class PermissionController {
	@Resource
	private AuthCustomPermissionService permissionService;
	
	@Resource
	private SysUserHelperService helperService;

	@RequestMapping(value = "/gettreeview", method = { RequestMethod.GET })
	@ResponseBody
	public List initTreeView(HttpServletRequest request) {
		int id = helperService.getAcctId(request.getSession());
		int userType = helperService.getSysuser(request.getSession()).getUserType();
		return permissionService.getTreeViewData(userType,id);
	}
}