/**
 * 
 */
package com.tys.web.utils;

import javax.servlet.http.HttpSession;

import com.tys.entity.SysUser;

/**
 * @author Administrator
 *
 */
public class SysUserHelper {
	
	public static SysUser getSysUser(HttpSession session){
		return (SysUser)session.getAttribute("sysUser");
	}

}
