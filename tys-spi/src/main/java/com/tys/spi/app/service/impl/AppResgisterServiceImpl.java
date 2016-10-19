package com.tys.spi.app.service.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqUserRegistDTO;
import com.tys.entity.MdUser;
import com.tys.service.MdUserService;
import com.tys.sms.SmsSender;
import com.tys.spi.app.service.AppRegisterService;
import com.tys.spi.app.service.SmsMessageService;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.constants.EnumConstants;
import com.tys.util.constants.ErrorCodeConstants;

@Service
public class AppResgisterServiceImpl implements AppRegisterService{
	
	private static final Map<String, String> smsCodeMap = new ConcurrentHashMap<String, String>();
	
	@Autowired
	private SmsMessageService smsMessageService;
	
	@Autowired
	private MdUserService mdUserService;
	
	@Override
	public Integer sendVerificationCode(String mobile,Integer type) {
    	
		String smsCode = MStrUtil.getRandomNumString(6);
		String msgContent = smsMessageService.getSmsMessageContentByType(smsCode, type);
		String result = SmsSender.batchSend(msgContent, mobile, false);
		String[] results = result.split(",");
		if("0".equals(results[1])){
			switch(type){
			case 0:
				smsCodeMap.put(mobile+"_register", smsCode+","+results[0]);
				break;
			case 1:
				smsCodeMap.put(mobile+"_resetpw", smsCode+","+results[0]);
				break;
			case 2:
				smsCodeMap.put(mobile+"_changephone", smsCode+","+results[0]);
				break;
			}
			
			return ErrorCodeConstants.SUCCESS;
		}
		return Integer.valueOf(results[1]);
	}

	@Override
	public boolean isUserExists(String account) {
		return mdUserService.isExistsByAccount(account);
	}

	@Override
	public boolean isRegistVerificationCodeInvalid(String verificationCode,String account) {
		try {
			//账号没有发送过验证码
			String storedCode = smsCodeMap.remove(account+"_register");
			if(MStrUtil.isNull(storedCode)){
				return true;
			}
			//验证码不正确
			String[] codes = storedCode.split(",");
			if(!verificationCode.equals(codes[0])){
				return true;
			}
			//验证码超过时效
			long strCodeCreateTime = Long.valueOf(codes[1]);
			if(System.currentTimeMillis() - strCodeCreateTime > 5*60000){//5分钟内有效
				return true;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean isResetVerificationCodeInvalid(String verificationCode,String account) {
		try {
			//账号没有发送过验证码
			String storedCode = smsCodeMap.remove(account+"_resetpw");
			if(MStrUtil.isNull(storedCode)){
				return true;
			}
			//验证码不正确
			String[] codes = storedCode.split(",");
			if(!verificationCode.equals(codes[0])){
				return true;
			}
			//验证码超过时效
			long strCodeCreateTime = Long.valueOf(codes[1]);
			if(System.currentTimeMillis() - strCodeCreateTime > 5*60000){//5分钟内有效
				return true;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}
	
	
	@Override
	public boolean isChangePhoneVerificationCodeInvalid(String verificationCode,String account) {
		try {
			//账号没有发送过验证码
			String storedCode = smsCodeMap.remove(account+"_changephone");
			if(MStrUtil.isNull(storedCode)){
				return true;
			}
			//验证码不正确
			String[] codes = storedCode.split(",");
			if(!verificationCode.equals(codes[0])){
				return true;
			}
			//验证码超过时效
			long strCodeCreateTime = Long.valueOf(codes[1]);
			if(System.currentTimeMillis() - strCodeCreateTime > 5*60000){//5分钟内有效
				return true;
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			return true;
		}
		return false;
	}

	@Override
	@Transactional
	public MdUser addAppUser(ReqUserRegistDTO userDTO) {
		MdUser mdUser = new MdUser();
		mdUser.setCustAcct(userDTO.getAcct());//默认手机号，后期可修改
		mdUser.setPhone(userDTO.getAcct());//注册仅使用手机号
		mdUser.setPw(MStrUtil.encodeMD5(userDTO.getPw()));
		mdUser.setSex(userDTO.getSex());
		mdUser.setCityCode(userDTO.getCityCode());
		mdUser.setType(EnumConstants.AppUserType.PARENTS.getValue());
		mdUser.setCreateTime(MDateUtil.getNowTime());
		mdUser.setAes(MStrUtil.getRandomString(16));
		mdUser.setName(userDTO.getSex() == 0 ? "先生" : "女士");
		mdUser.setPoints(20);//注册初始积分
		mdUser.setImei("");
		mdUser.setVersion("");
		this.mdUserService.save(mdUser);
		
		smsCodeMap.remove(mdUser.getCustAcct()+"_register");
		return mdUser;
	}

	@Override
	public boolean resetPw(String phone, String newPw) {
		MdUser user = mdUserService.findByAcct(phone);
		if(user != null){
			user.setPw(MStrUtil.encodeMD5(newPw));
			mdUserService.update(user);
			return true;
		}
		return false;
	}
	
	
	
	

}
