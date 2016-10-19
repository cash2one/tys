/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqResetPwDTO {

	@NotBlank
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone;

	@NotBlank
	@Pattern(regexp = "[0-9]{6}")//6位0-9
	private String code;

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9@._]{6,32}")//6~32位
	private String newPw;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNewPw() {
		return newPw;
	}

	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}




}
