package com.tys.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.DataTableReqDTO;
import com.tys.dto.DataTableRspDTO;
import com.tys.dto.query.ReqAgentDTO;
import com.tys.entity.RelSysuserLevel;
import com.tys.entity.RelSysuserPermission;
import com.tys.entity.SysUser;
import com.tys.entity.slim.Agent;
import com.tys.helper.SysUserHelperService;
import com.tys.service.RelSysuserAreaService;
import com.tys.service.RelSysuserLevelService;
import com.tys.service.RelSysuserPermissionService;
import com.tys.service.SysUserService;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;

/**
 * 代理商管理
 * 
 * @author wjc
 * @since 2016-3-21 10:04:18
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class AgentController {

	@Resource
	private SysUserService sysUserService;

	@Resource
	private RelSysuserPermissionService userPermissionService;

	@Resource
	private RelSysuserLevelService userLevelService;

	@Resource
	private RelSysuserAreaService userAreaService;

	@Resource
	private SysUserHelperService helperService;

	@RequestMapping(value = "/getagent", method = { RequestMethod.GET })
	public ModelAndView getAgents(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("company/agent");
		return mv;
	}

	@RequestMapping(value = "/getagentlist", method = { RequestMethod.GET })
	public void getAgentsList(HttpServletRequest request, HttpServletResponse response,
			@ModelAttribute DataTableReqDTO req) {

		SysUser currentAgent = helperService.getSysuser(request.getSession());

		MPage<Agent> page = sysUserService.findAgentByType(currentAgent, (req.getStart() / req.getLength()) + 1,
				req.getLength());
		DataTableRspDTO<Agent> rsp = new DataTableRspDTO<Agent>();

		rsp.setDraw(req.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/searchagent", method = { RequestMethod.GET })
	public void searchAgent(HttpServletResponse response, @ModelAttribute DataTableReqDTO req,
			@RequestParam(value = "name", required = false, defaultValue = "") String name,
			@RequestParam(value = "provinceCode", required = false, defaultValue = "0") Integer provinceCode,
			@RequestParam(value = "cityCode", required = false, defaultValue = "0") Integer cityCode,
			@RequestParam(value = "areaCode", required = false, defaultValue = "0") Integer areaCode) {

		MPage<Agent> page = sysUserService.searchAgent((req.getStart() / req.getLength()) + 1, req.getLength(), name,
				provinceCode, cityCode, areaCode);
		DataTableRspDTO<Agent> rsp = new DataTableRspDTO<Agent>();

		rsp.setDraw(req.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/mergeagent", method = { RequestMethod.POST })
	public ModelAndView mergeAgent(HttpServletRequest request, @ModelAttribute ReqAgentDTO agent) {
		ModelAndView mv = new ModelAndView();
		SysUser newAgent = new SysUser();
		// 判断是 insert or update
		if (agent.getId() == 0) {
			newAgent.setPassword(MStrUtil.getMD5(agent.getPassword(), agent.getLoginName()));
			// 用户类型 代理商
			newAgent.setUserType(2);
			newAgent.setAgentType(agent.getAgentType());
			newAgent.setLoginName(agent.getLoginName());
			newAgent.setName(agent.getName());
			newAgent.setEmail(agent.getEmail());
			newAgent.setPhone(agent.getPhone());
			newAgent.setAddress(agent.getAddress());
			sysUserService.save(newAgent);

			int newAgentId = sysUserService.findByAcct(agent.getLoginName()).getId();
			int currentId = helperService.getAcctId(request.getSession());
			userPermissionService.savePermissions(agent.getPermissions(), newAgentId);

			// 添加用户层级关系
			userLevelService.addRelUserLevel(newAgentId, currentId);

			// 添加代理商地理关系
			userAreaService.newRelSysuserArea(agent.getAgentType(), currentId, newAgentId, agent.getProvinceCodes(),
					agent.getCityCodes(), agent.getAreaCodes());
		} else {
			// 用户类型 代理商
			SysUser user = sysUserService.findById(agent.getId());
			user.setLoginName(agent.getLoginName());
			user.setName(agent.getName());
			user.setAddress(agent.getAddress());
			user.setEmail(agent.getEmail());
			user.setPhone(agent.getPhone());
			sysUserService.update(user);

			// 权限修改
			// 移除旧权限
			userPermissionService.removePermissionsByUserId(agent.getId());

			// 赋予用户新权限
			userPermissionService.savePermissions(agent.getPermissions(), agent.getId());
		}
		mv.setViewName("redirect:/sys/getagent");
		return mv;
	}

	@RequestMapping(value = "/deleteagent", method = { RequestMethod.GET })
	public ModelAndView deleteAgent(HttpServletRequest request,
			@RequestParam(value = "id", required = true) Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/sys/getagent");
		// 移除权限关系
		userPermissionService.removePermissionsByUserId(id);

		// 移除代理商与地理关系
		userAreaService.removeRelSysuserAreas(userAreaService.findByAgentId(id));

		List<Integer> list = userLevelService.findUserIdByParentId(id);
		if (list.size() == 0) {
			// 移除对应用户关系
			List<RelSysuserLevel> levels = userLevelService.getUserLevelByUserId(id);
			for (RelSysuserLevel relSysuserLevel : levels) {
				userLevelService.delete(relSysuserLevel);
			}
		}
		sysUserService.deleteUser(id);
		return mv;
	}

	@RequestMapping(value = "/getagentinfo", method = { RequestMethod.GET })
	@ResponseBody
	public Map getAgentById(@RequestParam(value = "id", required = true) Integer id) {
		Map map = new HashMap();
		Agent agent = sysUserService.findById(id, Agent.class);
		List<RelSysuserPermission> list = userPermissionService.findBySysUser(agent.getId());
		map.put("agent", agent);
		map.put("permission", list);
		return map;
	}

	@RequestMapping(value = "/todelete", method = { RequestMethod.GET })
	@ResponseBody
	public Map toDeleteAgent(HttpServletRequest request, @RequestParam("id") Integer id) {
		Map map = new HashMap();
		int currentId = helperService.getAcctId(request.getSession());
		List<Integer> list = userLevelService.findUserIdByParentId(id);
		if (id == currentId) {
			map.put("msg", "你不能删除你自己！");
			map.put("canRemove", 0);

		} else if (list.size() > 0) {
			map.put("msg", "该用户下存在子代理商，不能直接删除！");
			map.put("canRemove", 0);
		} else {
			map.put("canRemove", 1);
		}
		return map;
	}
}
