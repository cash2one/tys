package com.tys.spi.app.sec;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.alibaba.fastjson.JSON;
import com.tys.dto.spi.rsp.RspLoginRspDTO;
import com.tys.entity.MdPushPara;
import com.tys.entity.MdUser;
import com.tys.im.tls_sigature.TlsSigature;
import com.tys.im.tls_sigature.TlsSigature.GenTLSSignatureResult;
import com.tys.service.MdPushParaService;
import com.tys.service.MdUserService;
import com.tys.service.RelUserStudentService;
import com.tys.base.BaseSpiRsp;
import com.tys.util.MDateUtil;
import com.tys.util.MFileUtil;
import com.tys.util.MStrUtil;
import com.tys.util.MUtil;
import com.tys.util.constants.ErrorCodeConstants;

public class UserLoginHandler
		implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

	@Autowired
	private MdUserService mdUserService;

	@Autowired
	private MdPushParaService mdPushParaService;

	@Autowired
	private RelUserStudentService relUserStudentService;

	@Value("${im.sdkappid}")
	private long T_SDK_APPID;

	@Value("${im.accounttype}")
	private int T_ACCOUNT_TYPE;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) {
		MdUser mdUser = mdUserService.findByAcct(authentication.getName());
		
		//限定同一账号同一手机登录
		String imei = mdUser.getImei();
		String userImei = request.getParameter("imei");
		if(!MStrUtil.isNull(imei) && !imei.equals(userImei)){
			if(!imei.equals(userImei)){
				BaseSpiRsp result = new BaseSpiRsp();
				result.setStatus(ErrorCodeConstants.ERROR_APP_LOGIN_NOT_SAME_PHONE);
				MUtil.outPrint(response, JSON.toJSONString(result));
				return;
			}
		}
		
		//更新手机imei和version
		MdUser entity = new MdUser();
		entity.setVersion(request.getParameter("version"));
		entity.setImei(request.getParameter("imei"));
//		this.mdUserService.updateAfterLogin(mdUser);
		this.mdUserService.update(mdUser);
		
		//推送参数
		String userId = request.getParameter("userId");
		String channelId = request.getParameter("channelId");
		String deviceType = request.getParameter("deviceType");
		String companyId = request.getParameter("companyId");
		if(!MStrUtil.isNull(userId) && !MStrUtil.isNull(channelId) && !MStrUtil.isNull(deviceType)){
			MdPushPara mdPushPara = mdPushParaService.findByAcctId(mdUser.getId());
			if(mdPushPara == null)
				mdPushPara = new MdPushPara();
			mdPushPara.setUserId(mdUser.getId());
			mdPushPara.setBdUserId(userId);
			mdPushPara.setBdChannelId(channelId);
			mdPushPara.setPlatform(Integer.valueOf(deviceType));
			mdPushPara.setCompanyId(companyId == null ? "tys_1.0" : companyId);
			mdPushPara.setCreateTime(new Date());
			mdPushParaService.update(mdPushPara);
		}
		
		String onlyLogin = request.getParameter("onlyLogin");
		
		
		
		if(onlyLogin == null){
			
			RspLoginRspDTO loginRspDTO = new RspLoginRspDTO();
			BeanUtils.copyProperties(mdUser, loginRspDTO);
			loginRspDTO.setStatus(ErrorCodeConstants.SUCCESS);
			loginRspDTO.setAccountId(mdUser.getId());
			//查询孩子列表
			loginRspDTO.setChildList(this.relUserStudentService.findStudentList(mdUser.getId()));
			//TODO 查询亲情号码
			
			
			//TODO 云通讯相关参数获取
//			String privateKey = MFileUtil.readToStr(MUtil.getPrjClassPath() + "im/private_key");
//			
//			try {
//				GenTLSSignatureResult ret = TlsSigature.GenTLSSignatureEx(T_SDK_APPID, ""+mdUser.getId(), privateKey, (long)3600*24*30);//30天
//				if (0 == ret.urlSig.length()) {
//					MUtil.log("GenTLSSignatureEx failed: " + ret.errMessage);
//				} else {
//					loginRspDTO.setAccountType(T_ACCOUNT_TYPE);
//					loginRspDTO.setAppIdAt3rd(T_SDK_APPID);
//					loginRspDTO.setSdkAppId(T_SDK_APPID);
//					loginRspDTO.setUserSig(ret.urlSig);
//					
//				}
	//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
			
			MUtil.outPrint(response, JSON.toJSONString(loginRspDTO, true));
		} else {
			//仅登录。不查询数据
			BaseSpiRsp result = new BaseSpiRsp();
			result.setStatus(ErrorCodeConstants.SUCCESS);
			MUtil.outPrint(response, JSON.toJSONString(result));
		}
		
		
		//把账号id保存到session中，方便后面更新数据
		request.getSession().setAttribute("accountId", mdUser.getId());
		
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		BaseSpiRsp result = new BaseSpiRsp();
		result.setStatus(ErrorCodeConstants.ERROR_APP_LOGIN_CHECK_FAILD);
		MUtil.outPrint(response, JSON.toJSONString(result));
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		BaseSpiRsp result = new BaseSpiRsp();
		result.setStatus(ErrorCodeConstants.SUCCESS);
		MUtil.outPrint(response, JSON.toJSONString(result));
	}

}