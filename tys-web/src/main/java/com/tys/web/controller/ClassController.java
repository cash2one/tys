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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.DataTableReqDTO;
import com.tys.dto.DataTableRspDTO;
import com.tys.entity.MdClass;
import com.tys.entity.MdUser;
import com.tys.entity.RelTecherClass;
import com.tys.helper.SysUserHelperService;
import com.tys.push.service.PushService;
import com.tys.service.AddressService;
import com.tys.service.MdClassService;
import com.tys.service.MdHomeWorkService;
import com.tys.service.MdStudentService;
import com.tys.service.MdUserService;
import com.tys.service.RelTecherClassService;
import com.tys.util.MUtil;
import com.tys.util.constants.Constants;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;

/**
 * 
 * @ClassName: ClassController.java
 * @Description: 班级管理类：处理增删改查功能
 * 
 * @author liul
 * @version V1.0
 * @Date 2016-2-28 下午02:02:10
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class ClassController {

	@Resource
	private MdClassService mdClassService;

	@Resource
	private AddressService addressService;

	@Resource
	private PushService pushService;

	@Resource
	private SysUserHelperService sysUserHelperService;

	@Resource
	private MdStudentService mdStudentService;

	@Resource
	private MdUserService mdUserService;

	@Resource
	private RelTecherClassService relTecherClassService;
	
	@Resource
	private MdHomeWorkService mdHomeWorkService;
	

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

	/**
	 * 获取班级列表
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/getclass", method = { RequestMethod.GET })
	public ModelAndView getClasss(HttpServletRequest request, SearchVo search) {
		ModelAndView mv = new ModelAndView("school/classes");
		return mv;
	}

	@RequestMapping(value = "/getclassedt", method = { RequestMethod.GET })
	public void edtClass(HttpServletRequest request, HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto, SearchVo search) {
		// SysUser sysUser = sysUserHelperService.getSysuser(request.getSession());
		// if (sysUser.getUserType() == Constants.USER_TYPE_AGENT) {
		// search.setAgentId(sysUser.getId());
		// }

		MPage<MdClass> page = mdClassService.findMdClassPageByModel(search, reqDto.getPage(), reqDto.getLength());
		DataTableRspDTO<MdClass> rsp = new DataTableRspDTO<MdClass>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/mergeclass", method = { RequestMethod.POST })
	public void addTeacher(HttpServletResponse response, MdClass model, int editid) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			int oldAdminId = 0;
			if (editid != 0) {// 更新
				model.setId(editid);
				MdClass userTmp = mdClassService.findById(model.getId());
				oldAdminId = userTmp.getAdminId() == null ? 0 : userTmp.getAdminId();
				userTmp.setClassName(model.getClassName());
				userTmp.setSchoolId(model.getSchoolId());
				userTmp.setGradeId(model.getGradeId());
				userTmp.setAdminId(model.getAdminId());

				// BeanUtils.copyProperties(model, userTmp);
				mdClassService.update(userTmp);
				resultInfo.addData("notice", "修改班级信息成功");
			} else {
				// 完善信息

				// 新增班级信息
				mdClassService.save(model);

				// 添加百度推送tag，添加用户与学生关系时，把用户添加到百度推送群组tag里面
				model.setPushTag("tag_class_" + model.getId());
				mdClassService.update(model);

				// 创建百度tag
				pushService.createTag(model.getPushTag());

				resultInfo.addData("notice", "新增班级信息成功");
			}

			// 若课程ID为空，删掉关联数据
			RelTecherClass relTecherClass = relTecherClassService.getTeachClass(oldAdminId, model.getId());
			if (relTecherClass != null && relTecherClass.getCourseId() == null) {
				relTecherClassService.delete(relTecherClass);
			}

			// 查询该班是否有班主任
			if (null != model.getAdminId()) {

				// 保存新数据
				boolean rtcFlag = relTecherClassService.isTeachClass(model.getAdminId(), model.getId());
				if (!rtcFlag) {
					RelTecherClass rtc = new RelTecherClass();
					rtc.setClassId(model.getId());
					rtc.setTecherId(model.getAdminId());
					rtc.setIsDeleted(0);
					relTecherClassService.save(rtc);
				}
			}

			resultInfo.addData("result", "success");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作班级信息失败");
			e.printStackTrace();
		}

		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/removeclass", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo removeClassHandler(HttpServletResponse response, String pagenum, MdClass model) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {

			//查询该班级学生，提示先删除学生
			Long count = mdStudentService.findCountByClassId(model.getId());
			if(count != null && count > 0){
				resultInfo.addData("notice", "该班级已有"+count+"名学生，请先移除该班级的学生");
				return resultInfo;
			}
			
			//删除老师与班级的任课关系
			relTecherClassService.deleteByClassId(model.getId());
			
			//删除该班级的的作业信息
			mdHomeWorkService.deleteByClassId(model.getId());
			
			MdClass clas = mdClassService.findById(model.getId());
			if(clas == null){
				resultInfo.addData("notice", "该班级不存在");
				return resultInfo;
			}
			// 同步删除百度tag
			String pushTag = clas.getPushTag();
			if (pushTag != null) {
				pushService.deleteTag(pushTag);
			}

			clas.setIsDeleted(1);
			mdClassService.update(clas);
			resultInfo.addData("notice", "删除班级信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作班级信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}

	@RequestMapping(value = "/getparentsclass", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getParentsClassHandler(HttpServletResponse response, int classId) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			List<MdUser> users = mdUserService.getParentsByClassId(Constants.USER_TYPE_PARENT, classId);
			resultInfo.addData("users", users);
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作班级信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}

	@RequestMapping(value = "/getteachersclass", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getTeachersClassHandler(HttpServletResponse response, int classId) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			List<RelTecherClass> users = relTecherClassService.getRelTecherClassByclassId(classId);
			resultInfo.addData("users", users);
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作班级信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}
}
