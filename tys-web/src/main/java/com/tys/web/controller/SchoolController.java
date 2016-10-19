package com.tys.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.DataTableReqDTO;
import com.tys.dto.DataTableRspDTO;
import com.tys.entity.InfoAppNews;
import com.tys.entity.MdSchool;
import com.tys.entity.SysUser;
import com.tys.helper.SysUserHelperService;
import com.tys.service.AddressService;
import com.tys.service.InfoAppNewsService;
import com.tys.service.MdClassService;
import com.tys.service.MdEquipmentService;
import com.tys.service.MdSchoolService;
import com.tys.service.MdStudentService;
import com.tys.service.MdUserService;
import com.tys.service.SysUserService;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;
import com.tys.web.utils.SysUserHelper;

/**
 * 
 * @ClassName: SchoolController.java
 * @Description: 学校管理类：处理增删改查功能
 * 
 * @author liul
 * @version V1.0
 * @Date 2016-2-28 下午02:02:10
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class SchoolController {

	@Resource
	private MdSchoolService mdSchoolService;

	@Resource
	private AddressService addressService;

	@Resource
	private MdClassService mdClassService;

	@Resource
	private MdStudentService mdStudentService;

	@Resource
	private MdUserService mdUserService;

	@Resource
	private SysUserService sysUserService;
	
	@Resource
	private MdEquipmentService mdEquipmentService;

	@Resource
	private SysUserHelperService sysUserHelperService;
	
	@Resource
	private InfoAppNewsService infoAppNewsService;
	
	

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}

	/**
	 * 返回学校管理首页
	 * 
	 * @param request
	 * @param search
	 * @return
	 */
	@RequestMapping(value = "/getschool", method = { RequestMethod.GET })
	public ModelAndView getSchool(HttpServletRequest request, SearchVo search) {
		ModelAndView mv = new ModelAndView("school/schools");
		return mv;
	}

	/**
	 * 获取学校分页列表显示
	 * 
	 * @param response
	 * @param reqDto
	 * @param search
	 */
	@RequestMapping(value = "/getschooledt")
	public void edtStudent(HttpServletRequest request, HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto, SearchVo search) {
		// SysUser sysUser = sysUserHelperService.getSysuser(request.getSession());
		// if (sysUser.getUserType() == Constants.USER_TYPE_AGENT) {
		// search.setAgentId(sysUser.getId());
		// }

		MPage<MdSchool> page = mdSchoolService.findSchoolPageByModel(search, reqDto.getPage(), reqDto.getLength());
		DataTableRspDTO<MdSchool> rsp = new DataTableRspDTO<MdSchool>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	/**
	 * 增加或修改学校信息
	 * 
	 * @param response
	 * @param model
	 * @param editid
	 */
	@RequestMapping(value = "/mergeschool", method = { RequestMethod.POST })
	public void addSchool(HttpServletRequest request, HttpServletResponse response, MdSchool model, int editid, String loginName, String password) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			if (editid != 0) {// 更新
				SysUser sysUser = sysUserService.findByAcct(loginName);
				if (null == sysUser) {
					sysUser = new SysUser();
					sysUser.setLoginName(loginName);
					sysUser.setName(model.getAdminName());
					password = MStrUtil.isNull(password) ? "123456" : password;
					sysUser.setPassword(MStrUtil.getMD5(password, loginName));
					sysUser.setUserType(3);
					sysUser.setProvinceCode(model.getProvinceId());
					sysUser.setCityCode(model.getCityId());
					sysUser.setAreaCode(model.getAreaId());
					sysUserService.save(sysUser);
				} else {
					sysUser.setLoginName(loginName);
					sysUser.setName(model.getAdminName());
					sysUserService.update(sysUser);
				}

				model.setId(editid);
				MdSchool modelTmp = mdSchoolService.findById(model.getId());
				modelTmp.setName(model.getName());

				BeanUtils.copyProperties(model, modelTmp,
						new String[] { "latitude", "longitude", "createBy", "createTime", "updateTime", "updatedBy" });
				mdSchoolService.update(modelTmp);
				resultInfo.addData("notice", "修改学校信息成功");
			} else {
				SysUser sysUser = sysUserService.findByAcct(loginName);
				if (null == sysUser) {
					sysUser = new SysUser();
					sysUser.setLoginName(loginName);
					sysUser.setPhone(model.getTel());
					sysUser.setName(model.getAdminName());
					sysUser.setPassword(MStrUtil.getMD5(password, loginName));
					sysUser.setUserType(3);
					sysUser.setProvinceCode(model.getProvinceId());
					sysUser.setCityCode(model.getCityId());
					sysUser.setAreaCode(model.getAreaId());
					sysUserService.save(sysUser);
				}

				int acctId = sysUserHelperService.getAcctId(request.getSession());

				// 完善信息
				model.setCreateTime(new Date());
				model.setIsDeleted(0);
				model.setUpdateTime(new Date());
				model.setCreateBy(acctId);
				model.setUpdatedBy(acctId);
				model.setAdminId(sysUser.getId());

				// 新增学校信息
				mdSchoolService.save(model);
				resultInfo.addData("notice", "新增学校信息成功");
			}
			resultInfo.addData("result", "success");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学校信息失败");
			e.printStackTrace();
		}

		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	/**
	 * 删除学校信息
	 * 
	 * @param response
	 * @param model
	 * @return 
	 */
	@RequestMapping(value = "/removeschool", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo removeSchoolHandler(HttpServletResponse response, MdSchool model) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {

			//查询班级，提示先删除班级。
			long count = mdClassService.findCountBySchoolId(model.getId());
			if(count > 0){
				resultInfo.addData("notice", "该学校已有"+count+"个班级，请先删除班级");
				return resultInfo;
			}
			
			//已分配该学校的设备，把school_id置空，库存转移到上级代理商。
			MdSchool school = mdSchoolService.findById(model.getId());
			if(school == null){
				resultInfo.addData("notice", "学校不存在");
				return resultInfo;
			}
			
			//更新库存等设备状态
			mdEquipmentService.updateStock(model.getId(), school.getCreateBy());
			
			//相关老师账号改成家长账号
			mdUserService.updateTeacherBySchoolId(model.getId());
			
			//删除相关APP资讯
			infoAppNewsService.deleteBySchoolId(model.getId());
			
			//TODO 删除考勤探头与学校关系
			
			
			// 删除学校
			school.setIsDeleted(1);
			mdSchoolService.update(school);

			// 删除学校管理员账号
			SysUser sysUser = sysUserService.findById(model.getAdminId());
			if (null != sysUser) {
				sysUser.setIsDeleted(1);
				sysUserService.update(sysUser);
			}
			resultInfo.addData("notice", "删除学校信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学校信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}

	/**
	 * 删除学校信息
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/batchremoveschool", method = { RequestMethod.GET })
	public void batchRemoveSchoolHandler(HttpServletResponse response, String ids, String adminIds) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {
			// 删除班级、教师、学生 TODO

			String[] idArray = ids.split(",");
			for (String id : idArray) {
				if (!MStrUtil.isNullOrEmpty(id)) {
					MdSchool model = new MdSchool();
					model.setId(Integer.parseInt(id));
					mdSchoolService.delete(model);
				}
			}

			String[] adminIdArray = adminIds.split(",");
			for (String adminId : adminIdArray) {
				if (!MStrUtil.isNullOrEmpty(adminId)) {
					SysUser sysUser = sysUserService.findById(Integer.parseInt(adminId));
					if (null != sysUser) {
						sysUserService.delete(sysUser);
					}
				}
			}

			resultInfo.addData("notice", "批量删除学校信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学校信息失败");
			e.printStackTrace();
		}
		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/schoolList", method = RequestMethod.GET)
	public @ResponseBody List<MdSchool> schoolList(@RequestParam(value = "areaCode", required = false) Integer areaCode) {
		if (areaCode == null) {
			return new ArrayList<MdSchool>();
		}
		return this.mdSchoolService.findByAreaCode(areaCode);

	}
	
	@RequestMapping(value = "/checkSchool",method = RequestMethod.GET)
	public @ResponseBody String checkSchool(@RequestParam(value = "schoolName", required = false) String schoolName,HttpSession session) {
		String result = "success";
		if(MStrUtil.isNullOrEmpty(schoolName)){
			result = "学校名称不能为空!";
			return result;
		}
		
		try {
			MdSchool condition = new MdSchool();
			condition.setName(schoolName);
			MdSchool school = mdSchoolService.getByCondition(condition);
			if(null == school){
				result = "学校不存在!";
				return result;
			}
			
			SysUser curUser = SysUserHelper.getSysUser(session);
			if(null == curUser){
				result = "当前会话已经失效!";
				return result;
			}
			
			if(Integer.valueOf(2).equals(curUser.getUserType())){
				if(school.getCreateBy() == null || !school.getId().equals(school.getCreateBy())){
					result = "没有权限分配给此学校!";
					return result;
				}
			}
		} catch (Exception e) {
			result = "系统异常!";
		}
		return result;
	}

}
