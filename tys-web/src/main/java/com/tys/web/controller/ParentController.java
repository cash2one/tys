package com.tys.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.query.Param;
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
import com.tys.dto.spi.rsp.RspChildListInnerDTO;
import com.tys.entity.MdUser;
import com.tys.entity.slim.School2;
import com.tys.service.MdSchoolService;
import com.tys.service.MdUserService;
import com.tys.service.RelUserStudentService;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;
import com.tys.web.utils.ResultInfo;

/**
 * 
 * @ClassName: ParentController.java
 * @Description: 家长管理类：处理增删改查功能
 * 
 * @author wjc
 * @version V1.0
 * @Date 2016年3月17日17:16:44
 */
@Controller
@Scope("prototype")
@RequestMapping("/sys")
public class ParentController {

	@Resource
	private MdUserService mdUserService;

	@Resource
	private MdSchoolService mdSchoolService;

	@Resource
	private RelUserStudentService relUserStudentService;

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

	@RequestMapping(value = "/getparents", method = { RequestMethod.GET })
	public ModelAndView getParents() {
		ModelAndView mv = new ModelAndView("school/parents");
		return mv;
	}

	@RequestMapping(value = "/getparentlist", method = { RequestMethod.GET })
	public void getParentList(HttpServletResponse response, @ModelAttribute DataTableReqDTO reqDto) {
		MPage<MdUser> page = mdUserService.getUserByType(0, reqDto.getStart()/reqDto.getLength()+1, reqDto.getLength());
		DataTableRspDTO<MdUser> rsp = new DataTableRspDTO<MdUser>();
		rsp.setDraw(reqDto.getDraw());
		rsp.setRecordsTotal(page.getTotalCount());
		rsp.setRecordsFiltered(page.getTotalCount());
		rsp.setData(page.getResult());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}

	@RequestMapping(value = "/deleteparent", method = { RequestMethod.GET })
	public ModelAndView deleteParent(@Param("id") Integer id) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("redirect:/sys/getparents");
		MdUser user = mdUserService.findById(id);
		try {
			mdUserService.delete(user);
			mv.addObject("msg", 1);
		} catch (Exception e) {
			mv.addObject("msg", 0);
			e.printStackTrace();
		}
		return mv;
	}

	@RequestMapping(value = "/getschoollist", method = { RequestMethod.GET })
	@ResponseBody
	public ResultInfo getSchoolListByCity(HttpServletRequest request,
			@RequestParam(value = "province", required = true) Integer province,
			@RequestParam(value = "city", required = true) Integer city,
			@RequestParam(value = "area", required = true) Integer area) {
		ResultInfo result = new ResultInfo(true);
		List<School2> list = mdSchoolService.getSchoolListByCity(province, city, area);
		if (list.size() > 0) {
			result.addData("data", list);
		} else {
			result.addData("data", null);
		}
		return result;
	}

	@RequestMapping(value = "/getstudentlist", method = { RequestMethod.GET })
	public void getStudentList(HttpServletResponse response,@ModelAttribute DataTableReqDTO reqDto,
			@RequestParam(value = "userId", required = true) Integer userId) {
		List<RspChildListInnerDTO> list = relUserStudentService.findStudentList(userId);
		DataTableRspDTO<RspChildListInnerDTO> rsp = new DataTableRspDTO<RspChildListInnerDTO>();
		rsp.setDraw(reqDto.getDraw());
		rsp.setData(list);
		rsp.setRecordsTotal(list.size());
		MUtil.outPrint(response, JSONObject.toJSON(rsp).toString());
	}
}
