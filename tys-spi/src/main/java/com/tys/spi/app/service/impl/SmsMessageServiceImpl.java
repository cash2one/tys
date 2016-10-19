package com.tys.spi.app.service.impl;

import org.springframework.stereotype.Service;

import com.tys.spi.app.service.SmsMessageService;

/**
 * @author Administrator
 *
 */
@Service
public class SmsMessageServiceImpl implements SmsMessageService {
	
	public final String MESSAGE_CONTENT="亲爱的用户,您的验证码是:%s(平安家校通%s验证码,请在5分钟内完成验证),如非本人操作,请忽略本短信";

	/* (non-Javadoc)
	 * @see com.tys.spi.app.service.SmsMessageService#getSmsMessageContentByType(java.lang.String, java.lang.Integer)
	 */
	@Override
	public String getSmsMessageContentByType(String smsCode, Integer type) {
		String strType = null;
		switch (type) {
			case 1:
				strType = "重置密码";
				break;
			case 2:
				strType = "变更手机";
				break;
			default:
				strType = "手机注册";
				break;
		}
		return String.format(MESSAGE_CONTENT,String.valueOf(smsCode),strType);
	}

}
