/**
 * 
 */
package com.tys.spi.app.service;

import com.tys.dto.spi.req.ReqUserRegistDTO;
import com.tys.entity.MdUser;

/**
 * App用户注册接口
 * @author Administrator
 *
 */
public interface AppRegisterService {
	
	/**
	 * APP注册时发生验证码
	 * @param mobile
	 * @param type 0：注册验证码,1：重置密码验证码,2：变更手机验证码
	 * @return
	 */
	public Integer sendVerificationCode(String mobile,Integer type);
	
	/**
	 * 根据用户名判断用户是否存在
	 * @param phone
	 * @return
	 */
	public boolean isUserExists(String account);
	
	/**
	 * 判断注册验证码是否无效
	 * @param account
	 * @param verificationCode
	 * @return 无效-true , 有效 - false
	 */
	public boolean isRegistVerificationCodeInvalid(String verificationCode,String account);
	
	/**
	 * 判断重置密码验证码是否无效
	 * @param account
	 * @param verificationCode
	 * @return 无效-true , 有效 - false
	 */
	public boolean isResetVerificationCodeInvalid(String verificationCode,String account);
	
	/**
	 * 判断变更手机验证码是否无效
	 * @param account
	 * @param verificationCode
	 * @return 无效-true , 有效 - false
	 */
	public boolean isChangePhoneVerificationCodeInvalid(String verificationCode, String account);
	
	public MdUser addAppUser(ReqUserRegistDTO userDTO);

	public boolean resetPw(String phone, String string);

	
	
	

}
