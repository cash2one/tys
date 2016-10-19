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
import com.tys.entity.MdStudent;
import com.tys.entity.RelStudentGuardian;
import com.tys.entity.RelUserStudent;
import com.tys.helper.SysUserHelperService;
import com.tys.push.service.PushService;
import com.tys.service.AddressService;
import com.tys.service.MdClassService;
import com.tys.service.MdStudentService;
import com.tys.service.MdUserService;
import com.tys.service.RelStudentGuardianService;
import com.tys.service.RelUserStudentService;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;

/**
 * 
 * @ClassName: StudentController.java
 * @Description: 学生管理类：处理增删改查功能
 * 
 * @author liul
 * @version V1.0
 * @Date 2016-2-28 下午02:02:10
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class StudentController {

	@Resource
	private MdStudentService mdStudentService;

	@Resource
	private AddressService addressService;

	@Resource
	private MdClassService mdClassService;

	@Resource
	private RelStudentGuardianService relStudentGuardianService;

	@Resource
	private RelUserStudentService relUserStudentService;

	@Resource
	private MdUserService mdUserService;

	@Resource
	private PushService pushService;

	@Resource
	private SysUserHelperService sysUserHelperService;

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
	 * 获取学生列表
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/getstudent", method = { RequestMethod.GET })
	public ModelAndView getStudent(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("school/students");
		return mv;
	}

	@RequestMapping(value = "/getstudentedt", method = { RequestMethod.GET })
	public void edtStudent(HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto, SearchVo search) {
		MPage<MdStudent> page = mdStudentService.findMdStudentPageByModel(search, reqDto.getPage(), reqDto.getLength());
		DataTableRspDTO<MdStudent> rsp = new DataTableRspDTO<MdStudent>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/mergestudent", method = { RequestMethod.POST })
	public void addStudent(HttpServletRequest request, HttpServletResponse response, MdStudent model, int editid, String phone) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			if (editid != 0) {// 更新
				model.setId(editid);
				MdStudent modelTmp = mdStudentService.findById(model.getId());
				modelTmp.setName(model.getName());
				modelTmp.setSex(model.getSex());
				modelTmp.setBirthday(model.getBirthday());
				modelTmp.setClassId(model.getClassId());

				// BeanUtils.copyProperties(model, modelTmp);
				mdStudentService.update(modelTmp);
				resultInfo.addData("notice", "修改学生信息成功");
			} else {
				int acctId = sysUserHelperService.getAcctId(request.getSession());
				// 完善信息
				model.setCreateTime(new Date());
				model.setIsDeleted(0);
				model.setUpdateTime(new Date());
				model.setCreateBy(acctId);
				model.setUpdatedBy(acctId);

				// 新增学生信息
				mdStudentService.save(model);
				resultInfo.addData("notice", "新增学生信息成功");
			}

			// 处理亲情号
			if (null != phone) {
				// 先查询该学生亲情号码关系是否存在
				RelStudentGuardian relStudentGuardian = relStudentGuardianService.findByStudentIdAndIndex(model.getId(), 0);
				if (relStudentGuardian == null) {
					// 新增
					relStudentGuardian = new RelStudentGuardian();
					relStudentGuardian.setPhoneIndex(0);
					relStudentGuardian.setStudentId(model.getId());
					relStudentGuardian.setPhone(phone);
					relStudentGuardianService.save(relStudentGuardian);
				} else {
					// 更新
					relStudentGuardian.setPhone(phone);
					relStudentGuardianService.update(relStudentGuardian);
				}

				// 为用户绑定该学生，先查询关系是否存在

				// 若存在家长与学生关联，先删除
				List<RelUserStudent> relUserStudentList = relUserStudentService.findByStuentId(model.getId());
				if (null != relUserStudentList && !relUserStudentList.isEmpty()) {
					for (RelUserStudent rus : relUserStudentList) {
						relUserStudentService.delete(rus);
					}
				}

				RelUserStudent relUserStudent = relUserStudentService.findByStuentIdAndPhone(model.getId(), phone);
				Integer userId;
				if (null == relUserStudent) {
					userId = mdUserService.findIdByPhone(phone);
					// userId为空的情况下不处理，后面如果用户注册，将自动添加关系
					if (null != userId) {
						relUserStudent = new RelUserStudent();
						relUserStudent.setStudentId(model.getId());
						relUserStudent.setUserId(userId);
						relUserStudentService.save(relUserStudent);
					}
				} else {
					userId = relUserStudent.getUserId();
				}

				// 把这个用户添加到百度tag群组里面，如果用户未注册，而后注册时，自动添加该关系
				MdClass mdClass = model.getMdClass();
				if (null != mdClass && null != userId) {
					String pushTag = mdClass.getPushTag();
					if (pushTag == null) {
						pushTag = "tag_class_" + mdClass.getId();
						// 先创建百度tag
						pushService.createTag(pushTag);
						mdClass.setPushTag(pushTag);
						mdClassService.update(mdClass);
					}
					pushService.addUserToTag(pushTag, userId);
				}
			}

			resultInfo.addData("result", "success");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学生信息失败");
			e.printStackTrace();
		}

		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/removestudent", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo removeStudentHandler(HttpServletResponse response, MdStudent model) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			//查询该学生是否被家长绑定，提示先删除绑定关系
			// 删除家长与学生关联表
			Long count = relUserStudentService.findCountByStuentId(model.getId());
			if (null != count && count > 0) {
				resultInfo.addData("notice", "已有"+count+"个家长绑定了该学生，请先移除该家长与学生的关系");
				return resultInfo;
			}
			
			// 删除亲情关联表
			relStudentGuardianService.deleteByStudentId(model.getId());

			// 删除学生
			model.setIsDeleted(1);
			mdStudentService.update(model);
			resultInfo.addData("notice", "删除学生信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学生信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}

	/**
	 * 删除教师信息
	 * 
	 * @param response
	 * @param model
	 */
	@RequestMapping(value = "/batchremovestudent", method = { RequestMethod.GET })
	public void batchRemoveTeacherHandler(HttpServletResponse response, String ids) {
		ResultInfo resultInfo = new ResultInfo(true);
		try {

			if (ids.length() >= 2) {
				ids = ids.substring(0, ids.length() - 1);
			}

			for (String id : ids.split(",")) {
				MdStudent model = mdStudentService.findById(Integer.parseInt(id));

				// 删除亲情关联表
				List<RelStudentGuardian> guardians = relStudentGuardianService.findByStudentId(Integer.parseInt(id));
				for (RelStudentGuardian rsg : guardians) {
					if (null != rsg) {
						relStudentGuardianService.delete(rsg);
					}
				}

				// 删除家长与学生关联表
				List<RelUserStudent> relUserStudentList = relUserStudentService.findByStuentId(model.getId());
				for (RelUserStudent rus : relUserStudentList) {
					if (null != rus) {
						relUserStudentService.delete(rus);
					}
				}

				// 删除学生
				mdStudentService.delete(model);
			}

			resultInfo.addData("notice", "批量删除学生信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学生信息失败");
			e.printStackTrace();
		}
		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/getstudentparent", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getStudentParentHandler(HttpServletResponse response, int id) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			List<RelUserStudent> relListInfo = relUserStudentService.findByStuentId(id);
			resultInfo.addData("relListInfo", relListInfo);
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作学生信息失败");
			e.printStackTrace();
		}
		// MUtil.outPrint(response, JSONObject.toJSONString(resultInfo, SerializerFeature.DisableCircularReferenceDetect));
		// MUtil.outPrint(response, JSONObject.toJSONString(resultInfo, SerializerFeature.DisableCircularReferenceDetect));
		return resultInfo;
	}

	@RequestMapping(value = "/getstudentclass", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getStudentClassHandler(HttpServletResponse response, int id) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			MdClass relInfo = mdClassService.findById(id);
			resultInfo.addData("relInfo", relInfo);
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作教师信息失败");
			e.printStackTrace();
		}
		return resultInfo;
	}
}
