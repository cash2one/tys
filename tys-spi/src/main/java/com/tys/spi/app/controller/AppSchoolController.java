/**
 * 
 */
package com.tys.spi.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.dto.spi.req.ReqAttendanceDTO;
import com.tys.dto.spi.req.ReqAttendanceHisDTO;
import com.tys.dto.spi.req.ReqClassAttendanceDTO;
import com.tys.dto.spi.req.ReqClassDTO;
import com.tys.dto.spi.req.ReqSchoolDTO;
import com.tys.dto.spi.rsp.RspAttendanceDTO;
import com.tys.dto.spi.rsp.RspAttendanceHisDTO;
import com.tys.dto.spi.rsp.RspClassAttendanceDTO;
import com.tys.dto.spi.rsp.RspClassDTO;
import com.tys.dto.spi.rsp.RspQueryCourseDTO;
import com.tys.dto.spi.rsp.RspSchoolDTO;
import com.tys.entity.slim.Attendance;
import com.tys.service.MdAttendanceService;
import com.tys.service.MdClassService;
import com.tys.service.MdCourseService;
import com.tys.service.MdSchoolService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * 学校相关接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppSchoolController {

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private MdSchoolService mdSchoolService;
	@Autowired
	private MdClassService mdClassService;
	@Autowired
	private MdAttendanceService mdAttendanceService;
	@Autowired
	private MdCourseService mdCourseService;

	/**
	 * 查询班级列表
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/queryClass", method = RequestMethod.POST)
	public @ResponseBody RspClassDTO queryClass(@Valid @ModelAttribute ReqClassDTO classDto, Errors errors) {
		RspClassDTO result = new RspClassDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			result.setStatus(ErrorCodeConstants.SUCCESS);

			result.setList(mdClassService.findByCondtion(classDto));

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询学校列表
	 * 
	 * @param schoolDTO
	 * @return
	 */
	@RequestMapping(value = "s/querySchool", method = RequestMethod.POST)
	public @ResponseBody RspSchoolDTO querySchool(@Valid @ModelAttribute ReqSchoolDTO schoolDTO, Errors errors) {
		RspSchoolDTO result = new RspSchoolDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			result.setList(mdSchoolService.findByCondtion(schoolDTO));

			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询今天考勤
	 * 
	 * @param reqDTO
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "s/attendance", method = RequestMethod.POST)
	public @ResponseBody RspAttendanceDTO attendance(HttpSession session,
			@Valid @ModelAttribute ReqAttendanceDTO reqDTO, Errors errors) {
		RspAttendanceDTO result = new RspAttendanceDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			Integer serAccount = this.userHelpService.getAcctId(session);
			if (null == serAccount) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			if (reqDTO.getChildId() != null) {

				List<Attendance> list = mdAttendanceService.findFormRelStudentEquipment(reqDTO.getChildId());
				result.setList(list);
				result.setStatus(ErrorCodeConstants.SUCCESS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 历史考勤
	 * 
	 * @param homeworkDTO
	 * @return
	 */
	@RequestMapping(value = "s/attendanceHistory", method = RequestMethod.POST)
	public @ResponseBody RspAttendanceHisDTO attendanceHistory(HttpSession session,
			@Valid @ModelAttribute ReqAttendanceHisDTO reqDTO, Errors errors) {
		RspAttendanceHisDTO result = new RspAttendanceHisDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			Integer serAccount = this.userHelpService.getAcctId(session);
			if (null == serAccount) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			if (reqDTO.getChildId() != null) {

				List<Attendance> list = mdAttendanceService.findByCondition(reqDTO);
				if (list.size() > 0) {
					result.setList(list);
					result.setImei(list.get(0).getImei());
					result.setStatus(ErrorCodeConstants.SUCCESS);
				} else {
					result.setStatus(ErrorCodeConstants.ERROR_NO_ATTENDANCE);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 班级考勤统计数据
	 * 
	 * @param homeworkDTO
	 * @return
	 */
	@RequestMapping(value = "s/classAttendance", method = RequestMethod.POST)
	public @ResponseBody RspClassAttendanceDTO classAttendance(HttpSession session,
			@Valid @ModelAttribute ReqClassAttendanceDTO reqDTO, Errors errors) {
		RspClassAttendanceDTO result = new RspClassAttendanceDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			Integer acctId = this.userHelpService.getAcctId(session);
			if (null == acctId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			mdAttendanceService.statisticsClassAttendance(result, acctId, reqDTO);

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 班级考勤统计数据
	 * 
	 * @param homeworkDTO
	 * @return
	 */
	@RequestMapping(value = "s/queryCourse", method = RequestMethod.POST)
	public @ResponseBody RspQueryCourseDTO queryCourse() {
		RspQueryCourseDTO result = new RspQueryCourseDTO();
		try {
			result.setList(mdCourseService.findAll());
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

}
