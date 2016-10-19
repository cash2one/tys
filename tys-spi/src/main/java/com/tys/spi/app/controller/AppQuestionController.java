/**
 * 
 */
package com.tys.spi.app.controller;

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
import com.tys.dto.spi.req.ReqAcceptAnswerDTO;
import com.tys.dto.spi.req.ReqAnswerListDTO;
import com.tys.dto.spi.req.ReqCommitQuestionDTO;
import com.tys.dto.spi.req.ReqQuestionDTO;
import com.tys.dto.spi.req.ReqQuestionListDTO;
import com.tys.dto.spi.rsp.RspAnswerListDTO;
import com.tys.dto.spi.rsp.RspQuestionInfoDTO;
import com.tys.dto.spi.rsp.RspQuestionListDTO;
import com.tys.entity.InfoQuestion;
import com.tys.entity.InfoQuestionAnswer;
import com.tys.service.InfoQuestionAnswerService;
import com.tys.service.InfoQuestionService;
import com.tys.service.RelQuestionFileService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * 同步解题相关
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppQuestionController {

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private InfoQuestionService infoQuestionService;
	@Autowired
	private RelQuestionFileService relQuestionFileService;
	@Autowired
	private InfoQuestionAnswerService infoQuestionAnswerService;

	/**
	 * 问题列表
	 * 
	 * @param session
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/questionList", method = RequestMethod.POST)
	public @ResponseBody RspQuestionListDTO questionList(HttpSession session,
			@Valid @ModelAttribute ReqQuestionListDTO dto, Errors errors) {
		RspQuestionListDTO result = new RspQuestionListDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {

			infoQuestionService.findByCondition(result, dto);

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 提问/更新问题
	 * 
	 * @param session
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/commitQuestion", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp commitQuestion(HttpSession session, @Valid @ModelAttribute ReqCommitQuestionDTO dto,
			Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			Integer userId = this.userHelpService.getAcctId(session);
			if (null == userId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			int ret = infoQuestionService.createOrUpdate(userId, dto);

			result.setStatus(ret);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 删除问题
	 * 
	 * @param session
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/deleteQuestion", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp deleteQuestion(HttpSession session, @Valid @ModelAttribute ReqQuestionDTO dto,
			Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			Integer userId = this.userHelpService.getAcctId(session);
			if (null == userId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}

			InfoQuestion question = infoQuestionService.findById(dto.getQuestionId());
			if (question != null) {
				question.setIsDeleted(1);
				infoQuestionService.update(question);

				// 删除附件
				relQuestionFileService.deleteByQuestionId(dto.getQuestionId());
			}

			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 问题详情
	 * 
	 * @param session
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/questionInfo", method = RequestMethod.POST)
	public @ResponseBody RspQuestionInfoDTO questionInfo(HttpSession session, @Valid @ModelAttribute ReqQuestionDTO dto,
			Errors errors) {
		RspQuestionInfoDTO result = new RspQuestionInfoDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			InfoQuestion question = infoQuestionService.findById(dto.getQuestionId());

			if (question != null) {

				if (question.getUpdateTime() != null && dto.getLastUpdate() != null
						&& question.getUpdateTime().getTime() == dto.getLastUpdate()) {
					result.setStatus(ErrorCodeConstants.SUCCESS);
					return result;
				}

				BeanUtils.copyProperties(question, result);

				result.setFiles(relQuestionFileService.findByQuestionId(dto.getQuestionId()));

				result.setStatus(ErrorCodeConstants.SUCCESS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 获取解答列表
	 * 
	 * @param session
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/answerList", method = RequestMethod.POST)
	public @ResponseBody RspAnswerListDTO answerList(HttpSession session, @Valid @ModelAttribute ReqAnswerListDTO dto,
			Errors errors) {
		RspAnswerListDTO result = new RspAnswerListDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			List<InfoQuestionAnswer> list = infoQuestionAnswerService.findByQuestionId(dto.getQuestionId(),
					dto.getLastTime());
			result.setList(list);
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 采纳解答
	 * 
	 * @param session
	 * @param dto
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/acceptAnswer", method = RequestMethod.POST)
	public @ResponseBody RspAnswerListDTO acceptAnswer(HttpSession session,
			@Valid @ModelAttribute ReqAcceptAnswerDTO dto, Errors errors) {
		RspAnswerListDTO result = new RspAnswerListDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}

		try {
			InfoQuestion question = infoQuestionService.findById(dto.getQuestionId());
			if (question != null) {
				question.setAnswerId(dto.getAnswerId());
				infoQuestionService.update(question);
				result.setStatus(ErrorCodeConstants.SUCCESS);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

}
