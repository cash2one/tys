/*
 * @(#)EnumConstants.java 2015年8月26日
 * 
 * Copy Right@
 */

package com.tys.util.constants;

/**
 * <pre>
 *
 *
 * 创建日期: 2015年8月26日
 * 修改人 :  
 * 修改说明: 
 * 评审人 ：
 * </pre>
 */
public class EnumConstants {

	public enum YesOrNo {

		YES(1), NO(0);

		private Integer value;

		private YesOrNo(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String strValue() {
			return String.valueOf(value);
		}
	}

	/**
	 * 短信验证类型 0：注册验证码,1：重置密码验证码,2：变更手机验证码
	 */
	public enum VerificationCodeType {

		REGISTER(0), RETRIEVE_PASSWORD(1), MODIFY_MOBILE(2);

		private Integer value;

		private VerificationCodeType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

	}
	
	
	public enum AppUserType {

		TEACHERE(1), PARENTS(0);

		private Integer value;

		private AppUserType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}

		public void setValue(Integer value) {
			this.value = value;
		}

		public String strValue() {
			return String.valueOf(value);
		}
	}
	
	
	
	public enum MapType {
		
		GPS(0), CELL(1),BAIDU(2), GOOGLE(3);
		
		private Integer value;
		
		private MapType(Integer value) {
			this.value = value;
		}
		
		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
		
		public String strValue() {
			return String.valueOf(value);
		}
	}
	
	
	public enum DeviceType {
		
		STUDENT_CARD(0);
		
		private Integer value;
		
		private DeviceType(Integer value) {
			this.value = value;
		}
		
		public Integer getValue() {
			return value;
		}
		
		public void setValue(Integer value) {
			this.value = value;
		}
		
		public String strValue() {
			return String.valueOf(value);
		}
	}
	
	

}
