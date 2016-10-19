package com.tys.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.tys.dto.DataTableReqDTO;
import com.tys.dto.DataTableRspDTO;
import com.tys.entity.MdHomeWork;
import com.tys.service.MdHomeWorkService;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;

/**
 * 
 * @ClassName: ClassController.java
 * @Description: 作业管理类：处理增删改查功能
 * 
 * @author liul
 * @version V1.0
 * @Date 2016-2-28 下午02:02:10
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class HomeWorkController {

	@Resource
	private MdHomeWorkService mdHomeWorkService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
	}

	/**
	 * 获取作业列表
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/gethomework", method = { RequestMethod.GET })
	public ModelAndView getClasss(HttpServletRequest request, SearchVo search) {
		ModelAndView mv = new ModelAndView("school/homeworkes");
		return mv;
	}

	@RequestMapping(value = "/gethomeworkedt", method = { RequestMethod.GET })
	public void edtClass(HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto, SearchVo search) {
		MPage<MdHomeWork> page = mdHomeWorkService.findMdHomeWorkPageByModel(search, 1, 20);
		DataTableRspDTO<MdHomeWork> rsp = new DataTableRspDTO<MdHomeWork>();

		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/mergehomework", method = { RequestMethod.POST })
	public void addTeacher(HttpServletResponse response, MdHomeWork model, int editid) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			if (editid != 0) {// 更新
				model.setId(editid);
				MdHomeWork userTmp = mdHomeWorkService.findById(model.getId());
				userTmp.setWorkName(model.getWorkName());
				userTmp.setWorkContent(model.getWorkContent());
				userTmp.setClassId(model.getClassId());
				userTmp.setCourseId(model.getCourseId());
				userTmp.setAuthor(model.getAuthor());
				userTmp.setCompleteTime(model.getCompleteTime());
				userTmp.setState(model.getState());

				// BeanUtils.copyProperties(model, userTmp);
				mdHomeWorkService.update(userTmp);
				resultInfo.addData("notice", "修改作业信息成功");
			} else {
				// 完善信息
				// model.setYear(2008);

				// 新增老师信息
				mdHomeWorkService.save(model);
				resultInfo.addData("notice", "新增作业信息成功");
			}
			resultInfo.addData("result", "success");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作作业信息失败");
			e.printStackTrace();
		}

		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	@RequestMapping(value = "/removehomework", method = { RequestMethod.GET })
	public void removeClassHandler(HttpServletResponse response, String pagenum, MdHomeWork model) {
		ResultInfo resultInfo = new ResultInfo(true);

		try {
			mdHomeWorkService.delete(model);
			resultInfo.addData("notice", "删除作业信息成功");
		} catch (BeansException e) {
			resultInfo.addData("result", "fail");
			resultInfo.addData("notice", "操作作业信息失败");
			e.printStackTrace();
		}
		MUtil.outPrint(response, JSONObject.toJSON(resultInfo).toString());
	}

	/*
	 * public static String[] getNullPropertyNames(Object source) { final BeanWrapper src = new BeanWrapperImpl(source);
	 * java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
	 * 
	 * Set<String> emptyNames = new HashSet<String>(); for (java.beans.PropertyDescriptor pd : pds) { Object srcValue =
	 * src.getPropertyValue(pd.getName()); if (srcValue == null) emptyNames.add(pd.getName()); } String[] result = new
	 * String[emptyNames.size()]; return emptyNames.toArray(result); }
	 * 
	 * public static void copyPropertiesIgnoreNull(Object src, Object target) { BeanUtils.copyProperties(src, target,
	 * getNullPropertyNames(src)); }
	 */
}
