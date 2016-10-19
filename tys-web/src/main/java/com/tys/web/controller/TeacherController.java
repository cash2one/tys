package com.tys.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.tys.entity.MdUser;
import com.tys.entity.RelTecherClass;
import com.tys.service.AddressService;
import com.tys.service.MdUserService;
import com.tys.service.RelTecherClassService;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.constants.Constants;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;

/**
 * 
 * @ClassName: TeacherController.java
 * @Description: 教师管理类：处理增删改查功能
 * 
 * @author liul
 * @version V1.0
 * @Date 2016-2-28 下午02:02:10
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class TeacherController {

	@Resource
	private MdUserService mdUserService;

	@Resource
	private AddressService addressService;

	@Resource
	private RelTecherClassService relTecherClassService;

	/**
	 * 设置response编码,当返回结果为json时,不使用spring内置配置,直接使用response向客户端写流<br/>
	 * 使用json时调用该方法,不使用则不需调用
	 * 
	 * @param response
	 */
	@SuppressWarnings("unused")
	private void commonResponse(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache", "no-cache");
		response.setContentType("text/javascript; charset=utf-8");
	}

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}

	@RequestMapping(value = "/index", method = { RequestMethod.GET })
	public String toLogin() {
		return "index";
	}

	/**
	 * 获取教师列表
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/getteacher", method = { RequestMethod.GET })
	public ModelAndView getTeacher(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("school/teachers");
		return mv;
	}

	@RequestMapping(value = "/getteacheredt", method = { RequestMethod.GET })
	public void edtTeacher(HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto, SearchVo search) {
		MPage<MdUser> page = mdUserService.findMdTeacherPageByModel(search, reqDto.getPage(), reqDto.getLength());
		DataTableRspDTO<MdUser> rsp = new DataTableRspDTO<MdUser>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/mergeteacher", method = { RequestMethod.POST })
	public void addTeacher(HttpServletResponse response, MdUser model, int editid,
			@RequestParam(value = "classId", required = false) Integer classId, @RequestParam(value = "courseId", required = false) Integer courseId) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			if (editid != 0) {// 更新
				model.setId(editid);
				MdUser userTmp = mdUserService.findById(model.getId());
				userTmp.setName(model.getName());
				userTmp.setSex(model.getSex());
				userTmp.setPhone(model.getPhone());
				userTmp.setBirthday(model.getBirthday());
				userTmp.setCustAcct(model.getPhone());

				// BeanUtils.copyProperties(model, userTmp);
				mdUserService.update(userTmp);

				// 更新关系表
				// 添加教师课程班级关联表
				/*
				 * if (classId != 0 && courseId != 0) { relTecherClassService.findById() RelTecherClass relTeacherClass = new RelTecherClass();
				 * relTeacherClass.setTecherId(model.getId()); relTeacherClass.setClassId(classId); relTeacherClass.setCourseId(courseId);
				 * relTeacherClass.setIsDeleted(0); relTecherClassService.update(relTeacherClass); }
				 */

				resultInfo.addData("notice", "修改教师信息成功");
			} else {
				SearchVo search = new SearchVo();
				search.setS_phone(model.getPhone());
				List<MdUser> users = mdUserService.selectMdUserListFromModel(search);
				if (!users.isEmpty()) {// 判断是否已存在与user表中
					MdUser tmp = users.get(0);
					if (tmp.getType() == Constants.USER_TYPE_TEACHER) {
						resultInfo.addData("notice", "该教师信息已存在！");
					} else {
						tmp.setType(Constants.USER_TYPE_TEACHER);
						resultInfo.addData("notice", "注册教师信息成功");
						mdUserService.update(tmp);

						// 添加教师课程班级关联表
						if (classId != null && courseId != null) {
							RelTecherClass relTeacherClass = new RelTecherClass();
							relTeacherClass.setTecherId(tmp.getId());
							relTeacherClass.setClassId(classId);
							relTeacherClass.setCourseId(courseId);
							relTeacherClass.setIsDeleted(0);
							relTecherClassService.save(relTeacherClass);
						}
					}
					MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
					return;
				}

				// 完善信息
				model.setCustAcct(model.getPhone());
				model.setType(Constants.USER_TYPE_TEACHER);
				model.setAes(MStrUtil.getRandomString(16));
				model.setCityCode(123123);
				model.setCreateTime(new Date());
				model.setPw(MStrUtil.encodeMD5("123456"));
				model.setIsDeleted(0);
				model.setPoints(0);
				model.setUpdateTime(new Date());
				model.setImei("");
				model.setVersion("1.0");

				// 新增老师信息
				mdUserService.save(model);
				resultInfo.addData("notice", "新增教师信息成功");

				// 添加教师课程班级关联表
				if (classId != null && courseId != null) {
					RelTecherClass relTeacherClass = new RelTecherClass();
					relTeacherClass.setTecherId(model.getId());
					relTeacherClass.setClassId(classId);
					relTeacherClass.setCourseId(courseId);
					relTeacherClass.setIsDeleted(0);
					relTecherClassService.save(relTeacherClass);
				}
			}
			resultInfo.addData("result", "success");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作教师信息失败");
			e.printStackTrace();
		}

		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/removeteacher", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo removeTeacherHandler(HttpServletResponse response, MdUser user) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {
			
			// 删除教师班级关联表
			relTecherClassService.deleteByTeacherId(user.getId());
			
			// 删除教师，实际上是把账号类型改为家长类型
			MdUser teacher = mdUserService.findById(user.getId());
			if(teacher == null){
				resultInfo.addData("notice", "教师不存在");
				return resultInfo;
			}
			teacher.setType(0);
			mdUserService.update(teacher);
			
			resultInfo.addData("notice", "删除教师信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作教师信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}

	/**
	 * 批量删除教师信息
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/batchremoveteacher", method = { RequestMethod.GET })
	public void batchRemoveTeacherHandler(HttpServletResponse response, String ids) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {

			if (ids.length() >= 2) {
				ids = ids.substring(0, ids.length() - 1);
			}
			// 删除教师班级关联表
			List<RelTecherClass> infos = relTecherClassService.getRelTecherClassByTeacherIds(ids);
			for (RelTecherClass rtcClass : infos) {
				relTecherClassService.delete(rtcClass);
			}

			// 删除教师
			String[] idArray = ids.split(",");
			for (String id : idArray) {
				if (!MStrUtil.isNullOrEmpty(id)) {
					MdUser model = new MdUser();
					model.setId(Integer.parseInt(id));
					mdUserService.delete(model);
				}
			}

			resultInfo.addData("notice", "批量删除教师信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作教师信息失败");
			e.printStackTrace();
		}
		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/getclassesteacher", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getTeachersClassHandler(HttpServletResponse response, int id) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			List<RelTecherClass> teacherClassInfo = relTecherClassService.getRelTecherClassByTeacherIds(id + "");
			resultInfo.addData("teacherClassInfo", teacherClassInfo);
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作教师信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}
}
