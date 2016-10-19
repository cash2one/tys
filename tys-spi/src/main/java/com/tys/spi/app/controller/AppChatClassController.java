/**
 * 
 */
package com.tys.spi.app.controller;

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
import com.tys.dto.spi.req.ReqChatClassMemberDTO;
import com.tys.dto.spi.req.ReqClassNoticeDTO;
import com.tys.dto.spi.req.ReqUserInfoDTO;
import com.tys.dto.spi.rsp.RspChatClassDTO;
import com.tys.dto.spi.rsp.RspChatClassMemberDTO;
import com.tys.dto.spi.rsp.RspMdUserDTO;
import com.tys.entity.MdChatClass;
import com.tys.entity.MdClass;
import com.tys.entity.RelChatClassUser;
import com.tys.service.MdChatClassService;
import com.tys.service.MdClassService;
import com.tys.service.MdUserService;
import com.tys.service.RelChatClassUserService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.MStrUtil;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppChatClassController {

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private RelChatClassUserService relChatClassUserService;
	
	@Autowired
	private MdUserService mdUserService;
	
	@Autowired
	private MdChatClassService mdChatClassService;
	
	
	@Autowired
	private MdClassService mdClassService;

	/**
	 * 查询群聊班级列表
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/chatClassList", method = RequestMethod.POST)
	public @ResponseBody RspChatClassDTO chatClassList(HttpSession session) {
		RspChatClassDTO result = new RspChatClassDTO();
		try {
			Integer userId = userHelpService.getAcctId(session);
			if (null == userId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			result.setList(relChatClassUserService.findByUserId(userId));
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询群聊成员列表
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/chatClassMember", method = RequestMethod.POST)
	public @ResponseBody RspChatClassMemberDTO queryChatClassMember(HttpSession session,@Valid @ModelAttribute ReqChatClassMemberDTO dto, Errors errors) {
		RspChatClassMemberDTO result = new RspChatClassMemberDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	
	/**
	 * 家长老师详情
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/userInfo", method = RequestMethod.POST)
	public @ResponseBody RspMdUserDTO userInfo(HttpSession session,@Valid @ModelAttribute ReqUserInfoDTO dto, Errors errors) {
		RspMdUserDTO result = new RspMdUserDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer userId = userHelpService.getAcctId(session);
			if (null == userId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			RspMdUserDTO mdUser = mdUserService.findByUserId(dto.getUserId());
			if(null ==  mdUser){
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			mdUser.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	
	/**
	 * 修改群公告
	 * 
	 * @param classDto
	 * @return
	 */
	@RequestMapping(value = "s/classNotice", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp modifyNotice(HttpSession session,@Valid @ModelAttribute ReqClassNoticeDTO dto, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer userId = userHelpService.getAcctId(session);
			if (null == userId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			MdChatClass mdChatClass = null;
			Integer chatClassId = dto.getChatClassId();
			if(null != chatClassId){
				mdChatClass = mdChatClassService.findById(chatClassId);
			}
			
			if(null == mdChatClass){
				result.setStatus(ErrorCodeConstants.ERROR_CHAT_CLASS_NOT_EXISTS);
				return result;
			}
			
			MdClass mdClass = mdClassService.findById(mdChatClass.getClassId());
			if(null == mdClass){
				result.setStatus(ErrorCodeConstants.ERROR_CLASS_NOT_EXISTS);
				return result;
			}else if(!userId.equals(mdClass.getAdminId())){
				result.setStatus(ErrorCodeConstants.ERROR_NOT_CLASS_ADMIN);
				return result;
			}
			
			RelChatClassUser relChatClassUser = relChatClassUserService.findByUserIdAndChatClassId(userId,dto.getChatClassId());
			if(null == relChatClassUser){
				result.setStatus(ErrorCodeConstants.ERROR_USER_NOT_BELONG_CHAT_CLASS);
				return result;
			}
			
			String content = dto.getContent();
			if(MStrUtil.isNotNull(content)){
				if(content.length() > 200)
					content = content.substring(0, 200);
				this.mdChatClassService.modifyNotice(chatClassId, content);
				result.setStatus(ErrorCodeConstants.SUCCESS);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

}
