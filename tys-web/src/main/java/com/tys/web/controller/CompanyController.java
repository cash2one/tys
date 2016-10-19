package com.tys.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.tys.entity.RelSysuserPermission;
import com.tys.entity.SysUser;
import com.tys.entity.slim.Employee;
import com.tys.helper.SysUserHelperService;
import com.tys.service.RelSysuserPermissionService;
import com.tys.service.SysUserService;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;

/**
 * <pre>
 *	公司管理类
 *
 * 创建日期: 2016年3月8日
 * 修改人 :  liul
 * 修改说明:
 * </pre>
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class CompanyController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserHelperService helpService;

	@Resource
	private RelSysuserPermissionService userPermissionService;

	@RequestMapping(value = "/getemployee", method = { RequestMethod.GET })
	public ModelAndView getEmployee(HttpServletRequest request, SearchVo search) {
		ModelAndView mv = new ModelAndView("company/employees");
		return mv;
	}

	@RequestMapping(value = "/getemployeedt")
	public /* @ResponseBody DataTableRspDTO<Employee> */void getEmployeeDt(HttpServletResponse response,
			@ModelAttribute DataTableReqDTO reqDto) {

		MPage<Employee> page = sysUserService.findPageByType(4, reqDto.getStart() / reqDto.getLength() + 1,
				reqDto.getLength());
		DataTableRspDTO<Employee> rsp = new DataTableRspDTO<Employee>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
		// return rsp;
	}

	@RequestMapping(value = "/searchemployee")
	public void searchEmployeeDt(HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto,
			@RequestParam(value = "name", defaultValue = "", required = false) String name,
			@RequestParam(value = "company", defaultValue = "", required = false) String company) {

		String ids = "";
		List<SysUser> userList = sysUserService.findByName(company);

		for (SysUser sysUser : userList) {
			ids += sysUser.getId() + ",";
		}
		ids = ids.substring(0, ids.length() - 1);

		MPage<Employee> page = sysUserService.searchEmployee(reqDto.getStart() / reqDto.getLength() + 1,
				reqDto.getLength(), name, ids);
		DataTableRspDTO<Employee> rsp = new DataTableRspDTO<Employee>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/mergeemployee", method = RequestMethod.POST)
	public ModelAndView mergeEmployee(HttpServletRequest request, SysUser emp,
			@RequestParam("permissions") String permissions) {
		ModelAndView mv = new ModelAndView("redirect:/sys/getemployee");
		// 初始化权限ID数组
		String[] permission = permissions.split(",");
		if (emp.getId() == null) {
			emp.setPassword(MStrUtil.getMD5(emp.getPassword(), emp.getLoginName()));
			emp.setCreateBy(helpService.getAcctId(request.getSession()));
			emp.setUserType(4);
			sysUserService.save(emp);

			// 赋予注册用户权限
			for (String string : permission) {
				RelSysuserPermission userPermission = new RelSysuserPermission();
				userPermission.setSysuserId(sysUserService.findByAcct(emp.getLoginName()).getId());
				userPermission.setPermissionId(Integer.parseInt(string));
				userPermissionService.save(userPermission);
			}
		} else {
			SysUser user = sysUserService.findById(emp.getId());
			user.setLoginName(emp.getLoginName());
			user.setName(emp.getName());
			user.setPhone(emp.getPhone());
			user.setEmail(emp.getEmail());
			user.setDepartment(emp.getDepartment());
			sysUserService.update(user);

			// 权限修改
			// 移除旧权限
			List<RelSysuserPermission> permissionList = userPermissionService.findBySysUser(emp.getId());
			for (RelSysuserPermission relSysuserPermission : permissionList) {
				userPermissionService.delete(relSysuserPermission);
			}

			// 赋予用户新权限
			for (String string : permission) {
				RelSysuserPermission userPermission = new RelSysuserPermission();
				userPermission.setSysuserId(sysUserService.findByAcct(emp.getLoginName()).getId());
				userPermission.setPermissionId(Integer.parseInt(string));
				userPermissionService.save(userPermission);
			}
		}
		return mv;
	}

	@RequestMapping(value = "/toupdateemployee", method = RequestMethod.GET)
	@ResponseBody
	public ResultInfo toUpdateEmployee(@RequestParam("id") Integer id) {
		ResultInfo result = new ResultInfo(true);
		List<RelSysuserPermission> list = userPermissionService.findBySysUser(id);
		result.addData("permission", list);
		result.addData("employee", sysUserService.findById(id, Employee.class));
		return result;
	}

	@RequestMapping("/removeemployee")
	public ModelAndView removeEmpoyee(@RequestParam("id") Integer id) {
		ModelAndView mv = new ModelAndView("redirect:/sys/getemployee");
		sysUserService.deleteUser(id);
		return mv;
	}
}
