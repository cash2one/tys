/**
 * 
 */
package com.tys.spi.app.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.dto.spi.req.ReqHomeworkDTO;
import com.tys.dto.spi.req.ReqMySettingHomeworkDTO;
import com.tys.dto.spi.req.ReqSettingHomeworkDTO;
import com.tys.dto.spi.rsp.RspHomeWorkDTO;
import com.tys.dto.spi.rsp.RspMySettingHomeWorkDTO;
import com.tys.dto.spi.rsp.RspSettingHomeWorkDTO;
import com.tys.entity.MdHomeWork;
import com.tys.entity.MdPoint;
import com.tys.entity.MdUser;
import com.tys.push.service.PushService;
import com.tys.service.MdHomeWorkService;
import com.tys.service.MdPointsService;
import com.tys.service.RelTecherClassService;
import com.tys.service.RelUserStudentService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppHomeWorkController {
	
	@Autowired
	private UserHelpService userHelpService;
	@Autowired
	private PushService pushService;
	
	@Autowired
	private RelUserStudentService relUserStudentService;
	
	@Autowired
	private MdHomeWorkService mdHomeWorkService;
	
	@Autowired
	private MdPointsService mdPointsService;
	
	
	@Autowired
	private RelTecherClassService relTecherClassService;
	
	/**
	 * 家长下的作业列表
	 * @param session
	 * @param homeworkDTO
	 * @return
	 */
	@RequestMapping(value = "s/homework", method = RequestMethod.POST)
	public @ResponseBody RspHomeWorkDTO homework(HttpSession session, @Valid @ModelAttribute ReqHomeworkDTO homeworkDTO, Errors errors) {
		RspHomeWorkDTO result = new RspHomeWorkDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer serAccount = this.userHelpService.getAcctId(session);
			if(null == serAccount){
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			if(relUserStudentService.isUserNoChild(serAccount)){
				result.setStatus(ErrorCodeConstants.ERROR_USER_NO_STUENTE);
				return result;
			}
			
			result.setList(mdHomeWorkService.findByClassId(homeworkDTO.getClassId(), homeworkDTO.getLastTime()));
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;

	}
	
	
	/**
	 * 老师发布作业
	 * @param session
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "s/settingHomework", method = RequestMethod.POST)
	public RspSettingHomeWorkDTO settingHomework(HttpSession session, @Valid @ModelAttribute ReqSettingHomeworkDTO reqDTO, Errors errors) {
		RspSettingHomeWorkDTO result = new RspSettingHomeWorkDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer acctId = this.userHelpService.getAcctId(session);
			if(null == acctId){
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			if(!relTecherClassService.isTeachClass(acctId, reqDTO.getClassId())){
				result.setStatus(ErrorCodeConstants.ERROR_TEACHER_NO_CLASS);
				return result;
			}
			
			MdUser user = userHelpService.getMduser(session);
			MdHomeWork homework = new MdHomeWork();
			homework.setClassId(reqDTO.getClassId());
			homework.setWorkName("");
			homework.setWorkContent(reqDTO.getContent());
			homework.setCourseId(reqDTO.getCourseId());
			homework.setAuthor(user.getName());
			homework.setTeacherId(acctId);
			homework.setState(0);
			homework.setCompleteTime(new Date(reqDTO.getCommitTime()));
			mdHomeWorkService.save(homework);
			
			//发布作业，增加5积分
			user.setPoints(user.getPoints() + 5);
			// 添加积分明细
			MdPoint point = new MdPoint();
			point.setCreateBy(user.getId());
			point.setIncrement(5);
			point.setSource("发布作业");
			mdPointsService.save(point);
			
			result.setId(homework.getId());
			result.setStatus(ErrorCodeConstants.SUCCESS);
			
			//给设备推送作业
			pushService.sendNewHomeWorkMsg(homework);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	
	/**
	 * 老师布置的作业
	 * @param session
	 * @param reqDTO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "s/mySettingHomework", method = RequestMethod.POST)
	public RspMySettingHomeWorkDTO mySettingHomework(HttpSession session, @Valid @ModelAttribute ReqMySettingHomeworkDTO reqDTO, Errors errors) {
		RspMySettingHomeWorkDTO result = new RspMySettingHomeWorkDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer acctId = this.userHelpService.getAcctId(session);
			if(null == acctId){
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			
			result.setList(mdHomeWorkService.findByTeacherId(acctId, reqDTO));
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
		
	}
	
	

}
