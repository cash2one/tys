package com.tys.dto.spi.req;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

public class ReqChangePhoneDTO{

	@NotBlank
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone;

	@NotBlank
	@Pattern(regexp = "[0-9]{6}")//6位0-9
	private String code;

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


}
