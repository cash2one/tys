/**
 * 
 */
package com.tys.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.entity.SysUser;
import com.tys.service.RelSysuserLevelService;
import com.tys.service.SysUserService;
import com.tys.util.MCollectionUtils;
import com.tys.util.MStrUtil;
import com.tys.web.utils.ResultInfo;
import com.tys.web.utils.SysUserHelper;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sys")
public class SysUserController {
	
	@Autowired
	private SysUserService sysUserService;
	
	@Autowired
	private RelSysuserLevelService  relSysuserLevelService;
	
	
	@RequestMapping(value = "/dealerList",method = RequestMethod.GET)
	public @ResponseBody List<SysUser> dealerList(@RequestParam(value = "areaCode", required = false) Integer areaCode) {
		return this.sysUserService.findDealerListByAreaCode(areaCode);
	
	}
	
	@RequestMapping(value = "/checkAgent",method = RequestMethod.GET)
	public @ResponseBody String checkAgent(@RequestParam(value = "agentPhone", required = false) String agentPhone,HttpSession session) {
		String result = "success";
		if(MStrUtil.isNullOrEmpty(agentPhone)){
			result = "经销商电话不能为空!";
			return result;
		}
		
		try {
			SysUser user = this.sysUserService.findByPhone(agentPhone);
			if(null == user){
				result = "经销商不存在!";
				return result;
			}
			
			SysUser curUser = SysUserHelper.getSysUser(session);
			if(null == curUser){
				result = "当前会话已经失效!";
				return result;
			}
			
			if(Integer.valueOf(2).equals(curUser.getUserType())){
				List<Integer> dealerIdList = this.relSysuserLevelService.findUserIdByParentId(curUser.getId());
				if(MCollectionUtils.isEmpty(dealerIdList)){
					result = "没有可分配的经销商!";
					return result;
				}
				
				if(!dealerIdList.contains(user.getId())){
					result = "无权分配该经销商!";
					return result;
				}
				
			}
		} catch (Exception e) {
			result = "系统异常!";
		}
		return result;
	}

	@RequestMapping("/checkLoginName")
	public @ResponseBody ResultInfo findSysuserByLoginName(@RequestParam("loginName") String loginName){
		ResultInfo result = new ResultInfo(true);
		SysUser user = sysUserService.findByAcct(loginName);
		if(user!=null){
			result.setSuccess(true);
		}else{
			result.setSuccess(false);
		}
		return result;
	}
}
