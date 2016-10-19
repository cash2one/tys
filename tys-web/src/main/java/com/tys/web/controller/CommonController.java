package com.tys.web.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.entity.DArea;
import com.tys.entity.DCity;
import com.tys.entity.DProvince;
import com.tys.entity.MdClass;
import com.tys.entity.MdCourse;
import com.tys.entity.MdGrade;
import com.tys.entity.MdSchool;
import com.tys.entity.MdUser;
import com.tys.entity.SysUser;
import com.tys.helper.SysUserHelperService;
import com.tys.service.AddressService;
import com.tys.service.MdClassService;
import com.tys.service.MdCourseService;
import com.tys.service.MdGradeService;
import com.tys.service.MdSchoolService;
import com.tys.service.MdUserService;
import com.tys.util.constants.Constants;
import com.tys.util.custom.SearchVo;
import com.tys.web.utils.ResultInfo;

/**
 * 
 * <pre>
 *
 *
 * 创建日期: 2016年3月20日
 * 修改人 :  liul
 * 修改说明: 公共控制类，只用来处理公共的数据加载。eg:下拉框数据获取等
 * </pre>
 */
@Controller
@RequestMapping("/sys")
@Scope("prototype")
public class CommonController {

	@Resource
	private SysUserHelperService sysUserHelperService;

	@Resource
	private AddressService addressService;

	@Resource
	private MdGradeService mdGradeService;

	@Resource
	private MdClassService mdClassService;

	@Resource
	private MdSchoolService mdSchoolService;

	@Resource
	private MdUserService mdUserService;

	@Resource
	private MdCourseService mdCourseService;
	
	@Resource
	private SysUserHelperService helpService;

	@RequestMapping(value = "/getaddressjson", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getcitysJsonHandler(HttpServletRequest request,
			@RequestParam(value = "type", required = false) int type,
			@RequestParam(value = "addrcode", required = false) Integer addrCode) {
		ResultInfo resultInfo = new ResultInfo(true);
		if (type == 1) {// 获取省份列表
			List<DProvince> addressList = addressService.getDProvinces();
			resultInfo.addData("data", addressList);
		} else if (type == 2) {// 获取市级列表
			List<DCity> addressList = addressService.getDCityByProvinceCode(addrCode);
			resultInfo.addData("data", addressList);
		} else if (type == 3) {// 获取区县列表
			List<DArea> addressList = addressService.getDAreaByCityID(addrCode);
			resultInfo.addData("data", addressList);
		}
		return resultInfo;
	}

	@RequestMapping(value = "/getschooljson", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getSchoolJsonHandler(HttpServletRequest request, SearchVo search) {
		ResultInfo resultInfo = new ResultInfo(true);
		// int acctId = sysUserHelperService.getAcctId(request.getSession());
		// search.setAdminId(acctId);

		List<MdSchool> schoolList = mdSchoolService.selectMdSchoolListFromModel(search);
		resultInfo.addData("data", schoolList);
		return resultInfo;
	}

	@RequestMapping(value = "/getclassjson", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getClassJsonHandler(HttpServletRequest request, SearchVo search,
			@RequestParam(value = "schoolId", defaultValue = "0", required = false) int schoolId) {
		ResultInfo resultInfo = new ResultInfo(true);
		search.setS_schoolId(schoolId);

		List<MdClass> classList = mdClassService.selectMdSchoolListFromModel(search);
		resultInfo.addData("data", classList);
		return resultInfo;
	}

	@RequestMapping(value = "/getgradejson", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getGradeJsonHandler(HttpServletRequest request, SearchVo search) {
		ResultInfo resultInfo = new ResultInfo(true);
		List<MdGrade> gradeList = mdGradeService.selectMdGradeListFromModel(search);
		resultInfo.addData("data", gradeList);
		return resultInfo;
	}

	@RequestMapping(value = "/getcoursejson", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getCourseJsonHandler(HttpServletRequest request, SearchVo search) {
		ResultInfo resultInfo = new ResultInfo(true);
		List<MdCourse> infoList = mdCourseService.findAll();
		resultInfo.addData("data", infoList);
		return resultInfo;
	}

	@RequestMapping(value = "/getteacherjson", method = { RequestMethod.GET })
	public @ResponseBody ResultInfo getTeacherJsonHandler(HttpServletRequest request, SearchVo search) {
		ResultInfo resultInfo = new ResultInfo(true);
		search.setUserType(Constants.USER_TYPE_TEACHER);
		List<MdUser> infoList = mdUserService.selectMdUserListFromModel(search);
		resultInfo.addData("data", infoList);
		return resultInfo;
	}

	@RequestMapping(value = "/getaddress", method = RequestMethod.GET)
	@ResponseBody
	public List getCityByCode(HttpServletRequest request, @RequestParam(value = "type", defaultValue = "0", required = false) int type,
			@RequestParam(value = "code", defaultValue = "0", required = false) int code) {
		SysUser user = helpService.getSysuser(request.getSession());
		return addressService.getAddress(type, code, user);
	}
}
