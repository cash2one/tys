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

import com.tys.dto.spi.req.ReqJxzxCommentDTO;
import com.tys.dto.spi.req.ReqJxzxInfoDTO;
import com.tys.dto.spi.req.ReqJxzxLikeDTO;
import com.tys.dto.spi.req.ReqJxzxListDTO;
import com.tys.dto.spi.rsp.RspJxzxCommentDTO;
import com.tys.dto.spi.rsp.RspJxzxInfoDTO;
import com.tys.dto.spi.rsp.RspJxzxLikeDTO;
import com.tys.dto.spi.rsp.RspJxzxListDTO;
import com.tys.entity.InfoAppNews;
import com.tys.entity.RelNewsComment;
import com.tys.service.InfoAppNewsService;
import com.tys.service.MdUserService;
import com.tys.service.RelNewsCommentService;
import com.tys.service.RelNewsLikeService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * 新闻资讯相关
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppNewsController {

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private MdUserService mdUserService;

	@Autowired
	private InfoAppNewsService infoAppNewsService;
	@Autowired
	private RelNewsLikeService relNewsLikeService;
	@Autowired
	private RelNewsCommentService relNewsCommentService;
	
	
	

	/**
	 * 家校资讯列表
	 * 
	 * @param session
	 * @param reqDTO
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/jxzxList", method = RequestMethod.POST)
	public @ResponseBody RspJxzxListDTO jxzxList(HttpSession session, @Valid @ModelAttribute ReqJxzxListDTO reqDTO,
			Errors errors) {
		RspJxzxListDTO result = new RspJxzxListDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {

			infoAppNewsService.findByCondition(result, reqDTO);

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 家校资讯详情
	 * 
	 * @param session
	 * @param reqDTO
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/jxzxInfo", method = RequestMethod.POST)
	public @ResponseBody RspJxzxInfoDTO jxzxInfo(HttpSession session, @Valid @ModelAttribute ReqJxzxInfoDTO reqDTO,
			Errors errors) {
		RspJxzxInfoDTO result = new RspJxzxInfoDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			
			Integer userId = userHelpService.getAcctId(session);
			if(userId == null){
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			InfoAppNews news = infoAppNewsService.findById(reqDTO.getNewsId());
			if(news != null){
				result.setInfo(news);
				
				//用户是否点赞
				if(relNewsLikeService.isUserLike(userId)){
					result.setIsLike(1);
				} else {
					result.setIsLike(0);
				}
				
				//总点赞数
				Long likecount = relNewsLikeService.findLikeCountByNewsId(reqDTO.getNewsId());
				result.setLikeCount(likecount.intValue());
				
				//评论数
				Long commentCount = relNewsCommentService.findCountByNewsId(reqDTO.getNewsId());
				result.setCommentCount(commentCount.intValue());
				
				result.setStatus(ErrorCodeConstants.SUCCESS);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	/**
	 * 家校资讯点赞
	 * 
	 * @param session
	 * @param reqDTO
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/jxzxLike", method = RequestMethod.POST)
	public @ResponseBody RspJxzxLikeDTO jxzxLike(HttpSession session, @Valid @ModelAttribute ReqJxzxLikeDTO reqDTO,
			Errors errors) {
		RspJxzxLikeDTO result = new RspJxzxLikeDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			
			//总点赞数
			Long likecount = relNewsLikeService.findLikeCountByNewsId(reqDTO.getNewsId());
			result.setLikeCount(likecount.intValue());
			result.setStatus(ErrorCodeConstants.SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		
		return result;
	}
	
	
	
	/**
	 * 家校资讯评论
	 * 
	 * @param session
	 * @param reqDTO
	 * @param errors
	 * @return
	 */
	@RequestMapping(value = "s/jxzxComment", method = RequestMethod.POST)
	public @ResponseBody RspJxzxCommentDTO jxzxComment(HttpSession session, @Valid @ModelAttribute ReqJxzxCommentDTO reqDTO,
			Errors errors) {
		RspJxzxCommentDTO result = new RspJxzxCommentDTO();
		if (errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
			return result;
		}
		try {
			Integer userId = userHelpService.getAcctId(session);
			if(userId == null){
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			RelNewsComment rel = new RelNewsComment();
			rel.setComment(reqDTO.getComment());
			rel.setNewsId(reqDTO.getNewsId());
			rel.setUserId(userId);
			rel.setCommentId(reqDTO.getCommentId());
			relNewsCommentService.save(rel);
			
			//查询评论数
			Long commentCount = relNewsCommentService.findCountByNewsId(reqDTO.getNewsId());
			result.setCommentCount(commentCount.intValue());
			
			result.setStatus(ErrorCodeConstants.SUCCESS);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		
		return result;
	}
	
	
	

}
