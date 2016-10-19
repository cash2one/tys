/**
 * 
 */
package com.tys.spi.app.service;

/**
 * @author Administrator
 *
 */
public interface SmsMessageService {
	
	/**
	 * 通过短信类型获取短信内容
	 * @param smsCode 验证码
	 * @param type
	 * @return
	 */
	public String getSmsMessageContentByType(String smsCode , Integer type);

}
