/**
 * 
 */
package com.tys.spi.app.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.base.BaseSpiRsp;
import com.tys.dto.spi.req.ReqChangePhoneDTO;
import com.tys.dto.spi.req.ReqFeedbackDTO;
import com.tys.dto.spi.req.ReqNewVersionDTO;
import com.tys.dto.spi.req.ReqPushParamDTO;
import com.tys.dto.spi.req.ReqQueryAdDTO;
import com.tys.dto.spi.req.ReqQueryPushMsgDTO;
import com.tys.dto.spi.req.ReqSmsCodeDTO;
import com.tys.dto.spi.rsp.RspNewVersionDTO;
import com.tys.dto.spi.rsp.RspQueryAppAdDTO;
import com.tys.dto.spi.rsp.RspQueryPushMsgDTO;
import com.tys.entity.InfoPushMsg;
import com.tys.entity.MdAppAd;
import com.tys.entity.MdFeedback;
import com.tys.entity.MdPushPara;
import com.tys.entity.MdSoftware;
import com.tys.entity.MdUser;
import com.tys.service.InfoPushMsgService;
import com.tys.service.MdAppAdService;
import com.tys.service.MdFeedbackService;
import com.tys.service.MdPushParaService;
import com.tys.service.MdSoftwareService;
import com.tys.spi.app.service.AppRegisterService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * 杂项
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppMiscController {

	@Autowired
	private AppRegisterService appRegisterService;

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private MdPushParaService mdPushParaService;

	@Autowired
	private MdFeedbackService mdFeedbackService;

	@Autowired
	private InfoPushMsgService infoPushMsgService;

	@Autowired
	private MdSoftwareService mdSoftwareService;
	
	@Autowired
	private MdAppAdService mdAppAdService;

	
	
	
	/**
	 * 短信验证码
	 * 
	 * @param smsCodeDTO
	 * @return
	 */
	@RequestMapping(value = "/n/smsCode", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp sendVerificationCode(@Valid @ModelAttribute ReqSmsCodeDTO smsCodeDTO,
			Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			// 发送注册是短信验证码
			Integer ret = appRegisterService.sendVerificationCode(smsCodeDTO.getPhone(), smsCodeDTO.getType());
			// 处理结果
			result.setStatus(ret);
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 变更手机
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "/n/changePhone", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp changePhone(HttpSession session, @Valid @ModelAttribute ReqChangePhoneDTO reqDTO,
			Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {

			// 校验验证码是否有效
			if (appRegisterService.isChangePhoneVerificationCodeInvalid(reqDTO.getCode(), reqDTO.getPhone())) {
				result.setStatus(ErrorCodeConstants.ERROR_VERIFICATIONCODE_INVALID);
				return result;
			}

			MdUser user = userHelpService.getMduser(session);
			if (user == null) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			// TODO 清除已登录的Session

			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (RuntimeException e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询推送消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "s/queryPushMsg", method = RequestMethod.POST)
	public @ResponseBody RspQueryPushMsgDTO queryPushMsg(HttpSession session,
			@Valid @ModelAttribute ReqQueryPushMsgDTO reqDTO, Errors errors) {
		RspQueryPushMsgDTO result = new RspQueryPushMsgDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			Integer userId = userHelpService.getAcctId(session);
			if (null == userId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			List<InfoPushMsg> list = infoPushMsgService.findByUserId(userId, reqDTO.getLastTime());
			result.setList(list);
			result.setStatus(ErrorCodeConstants.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}

		return result;
	}

	/**
	 * 查询地区广告
	 * 
	 * @return
	 */
	@RequestMapping(value = "n/queryAd", method = RequestMethod.POST)
	public @ResponseBody RspQueryAppAdDTO queryAd(@Valid @ModelAttribute ReqQueryAdDTO queryAdDTO, Errors errors) {
		RspQueryAppAdDTO result = new RspQueryAppAdDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			
			List<MdAppAd> adList = mdAppAdService.findByCityAndPosition(queryAdDTO.getCityCode(), queryAdDTO.getAdPosition());
			
			result.setList(adList);
			
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 更新推送id
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/updatePushId", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp updatePushId(HttpSession session,
			@Valid @ModelAttribute ReqPushParamDTO reqPushParamDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			Integer acctId = userHelpService.getAcctId(session);
			if (null == acctId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			// 添加用户的推送参数
			MdPushPara mdPushPara = mdPushParaService.findByAcctId(acctId);
			if (mdPushPara == null) {
				mdPushPara = new MdPushPara();
				mdPushPara.setUserId(acctId);
			}
			mdPushPara.setBdUserId(reqPushParamDTO.getUserId());
			mdPushPara.setBdChannelId(reqPushParamDTO.getChannelId());
			mdPushPara.setPlatform(reqPushParamDTO.getDeviceType());
			mdPushPara.setCompanyId(reqPushParamDTO.getCompanyId());
			mdPushPara.setCreateTime(new Date());
			mdPushParaService.update(mdPushPara);
			result.setStatus(ErrorCodeConstants.SUCCESS);

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 用户反馈
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/feedback", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp feedback(HttpSession session, @Valid @ModelAttribute ReqFeedbackDTO feedbackDTO,
			Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {

			Integer acctId = userHelpService.getAcctId(session);
			if (null == acctId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			MdFeedback feedback = new MdFeedback();
			feedback.setContent(feedbackDTO.getContent());
			feedback.setCreateBy(acctId);
			feedback.setUpdatedBy(acctId);
			mdFeedbackService.save(feedback);
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询新版本
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "n/newVersion", method = RequestMethod.POST)
	public @ResponseBody RspNewVersionDTO newVersion(HttpSession session,
			@Valid @ModelAttribute ReqNewVersionDTO reqDto, Errors errors) {
		RspNewVersionDTO result = new RspNewVersionDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {

			// final NumberFormat nf = new DecimalFormat("000");

			MdSoftware software = mdSoftwareService.findNewVer(reqDto.getType(), reqDto.getCompanyId());

			result.setStatus(ErrorCodeConstants.ERROR_NO_NEW_VERSION);// 默认无新版本

			if (software != null) {
				String versionStr = reqDto.getVersion();
				String[] tmp = versionStr.split("\\.");
				int mainVer = Integer.parseInt(tmp[0]);
				int secondVer = Integer.parseInt(tmp[1]);
				int modfyVer = Integer.parseInt(tmp[2]);
				int version = mainVer * 1000000 + secondVer * 1000 + modfyVer;

				if (version < software.getVersion()) {
					// 有新版本
					BeanUtils.copyProperties(software, result);
					int ver = software.getVersion();
					result.setVersion((ver / 1000000) + "." + ((ver / 1000) % 1000) + "." + (ver % 1000));

					result.setNeedUpdate(0);
					result.setStatus(ErrorCodeConstants.SUCCESS);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 未登录，无权访问
	 * 
	 * @return
	 */
	@RequestMapping(value = "n/noAuth")
	public @ResponseBody BaseSpiRsp noAuth(HttpSession session) {
		BaseSpiRsp result = new BaseSpiRsp();
		result.setStatus(ErrorCodeConstants.ERROR_APP_NO_LOGIN);
		return result;
	}

}
