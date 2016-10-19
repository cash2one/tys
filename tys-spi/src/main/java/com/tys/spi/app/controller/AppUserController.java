/**
 * 
 */
package com.tys.spi.app.controller;

import java.util.Date;
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

import com.tys.base.BaseSpiRsp;
import com.tys.dto.spi.req.ReqChangePwDTO;
import com.tys.dto.spi.req.ReqResetPwDTO;
import com.tys.dto.spi.req.ReqSettingDTO;
import com.tys.dto.spi.req.ReqUserRegistDTO;
import com.tys.dto.spi.rsp.RspAttendanceDTO;
import com.tys.dto.spi.rsp.RspCheckInDTO;
import com.tys.dto.spi.rsp.RspPointListDTO;
import com.tys.dto.spi.rsp.RspPointsDTO;
import com.tys.entity.MdAppRunningDay;
import com.tys.entity.MdPoint;
import com.tys.entity.MdUser;
import com.tys.service.MdAppRunningDayService;
import com.tys.service.MdPointsService;
import com.tys.service.MdUserService;
import com.tys.spi.app.service.AppRegisterService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.MStrUtil;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * 个人中心相关
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppUserController {

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private MdUserService mdUserService;
	@Autowired
	private MdPointsService mdPointsService;
	@Autowired
	private MdAppRunningDayService mdAppRunningDayService;
	@Autowired
	private AppRegisterService appRegisterService;
	
	/**
	 * 用户注册
	 * 
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/n/userRegist", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp register(@Valid @ModelAttribute ReqUserRegistDTO userDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			// 校验用户是否已经注册
			String account = userDTO.getAcct();
			if (appRegisterService.isUserExists(account)) {
				result.setStatus(ErrorCodeConstants.ERROR_APP_USER_EXISTS);
				return result;
			}

			// 校验验证码是否有效
			String code = userDTO.getCode();
			if (appRegisterService.isRegistVerificationCodeInvalid(code, account)) {
				result.setStatus(ErrorCodeConstants.ERROR_VERIFICATIONCODE_INVALID);
				return result;
			}
			MdUser user = this.appRegisterService.addAppUser(userDTO);

			// 添加积分明细
			MdPoint point = new MdPoint();
			point.setCreateBy(user.getId());
			point.setIncrement(20);
			point.setSource("新用户注册赠送");
			mdPointsService.save(point);

			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 重置密码
	 * 
	 * @param userDTO
	 * @return
	 */
	@RequestMapping(value = "/n/resetPw", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp resetPw(@Valid @ModelAttribute ReqResetPwDTO resetPwDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		// 校验验证码是否有效
		String code = resetPwDTO.getCode();
		if (appRegisterService.isResetVerificationCodeInvalid(code, resetPwDTO.getPhone())) {
			result.setStatus(ErrorCodeConstants.ERROR_VERIFICATIONCODE_INVALID);
			return result;
		}
		
		if(resetPwDTO.getNewPw() != null){
			if(appRegisterService.resetPw(resetPwDTO.getPhone(), resetPwDTO.getNewPw()))
				result.setStatus(ErrorCodeConstants.SUCCESS);
		}
		
		return result;
	}
	
	
	/**
	 * 设置个人信息
	 * 
	 * @param settingDTO
	 * @return
	 */
	@RequestMapping(value = "s/setting", method = RequestMethod.POST)
	public @ResponseBody RspAttendanceDTO setting(HttpSession session, @Valid @ModelAttribute ReqSettingDTO settingDTO, Errors errors) {
		RspAttendanceDTO result = new RspAttendanceDTO();
		String custAcct = settingDTO.getCustAcct();
		if(custAcct != null){
			if(!custAcct.matches("^[A-Za-z][A-Za-z0-9@_.]{5,32}")){
				//不是以字母开头，仅能设置成自己的手机
				MdUser user = userHelpService.getMduser(session);
				if(!custAcct.equals(user.getPhone())){
					result.setStatus(ErrorCodeConstants.ERROR_PARA);
		            return result;
				}
			}
		}
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer acctId = userHelpService.getAcctId(session);
			if (null == acctId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			this.mdUserService.update(acctId, settingDTO);
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 修改密码
	 * 
	 * @param reqModifyPsdDTO
	 * @return
	 */
	@RequestMapping(value = "s/changePw", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp changePw(HttpSession session, @Valid @ModelAttribute ReqChangePwDTO reqModifyPsdDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		
		try {
			MdUser user = userHelpService.getMduser(session);
			if (null == user) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			if (!user.getPw().equals(MStrUtil.encodeMD5(reqModifyPsdDTO.getOldPw()))) {
				result.setStatus(ErrorCodeConstants.ERROR_OLD_PW);
				return result;
			}

			user.setPw(MStrUtil.encodeMD5(reqModifyPsdDTO.getNewPw()));
			this.mdUserService.update(user);
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询积分
	 * 
	 * @return
	 */
	@RequestMapping(value = "s/queryPoints", method = RequestMethod.POST)
	public @ResponseBody RspPointsDTO queryPoints(HttpSession session) {
		RspPointsDTO result = new RspPointsDTO();
		try {
			MdUser user = userHelpService.getMduser(session);
			if (null == user) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			result.setPoints(user.getPoints());
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询积分明细
	 * 
	 * @return
	 */
	@RequestMapping(value = "s/pointsList", method = RequestMethod.POST)
	public @ResponseBody RspPointListDTO pointsList(HttpSession session) {
		RspPointListDTO result = new RspPointListDTO();
		try {
			Integer acctId = userHelpService.getAcctId(session);
			if (null == acctId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			List<MdPoint> list = mdPointsService.findByAcctId(acctId);

			result.setPointList(list);
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 签到
	 * 
	 * @return
	 */
	@RequestMapping(value = "s/checkIn", method = RequestMethod.POST)
	public @ResponseBody RspCheckInDTO checkIn(HttpSession session) {
		RspCheckInDTO result = new RspCheckInDTO();

		try {
			MdUser user = userHelpService.getMduser(session);
			if (null == user) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			// 判断今天是否已签到
			if (mdAppRunningDayService.isCheckInToday(user.getId())) {
				result.setStatus(ErrorCodeConstants.ERROR_CAN_NOT_CHECK_IN);
				return result;
			}

			int points = 20;

			// 连续签到，第7天得100分
			if (mdAppRunningDayService.isRun7Days(user.getId())) {
				points = 100;
			}

			// 签到记录
			MdAppRunningDay runningday = new MdAppRunningDay();
			runningday.setCreatedBy(user.getId());
			runningday.setCreateTime(new Date());
			runningday.setPoints(points);
			mdAppRunningDayService.save(runningday);

			// 增加积分
			user.setPoints(user.getPoints() + points);
			mdUserService.update(user);

			// 添加积分明细
			MdPoint point = new MdPoint();
			point.setCreateBy(user.getId());
			point.setIncrement(points);
			if (points > 20) {
				point.setSource("签到,连续签到7天");
			} else {
				point.setSource("签到");
			}
			mdPointsService.save(point);

			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

}
