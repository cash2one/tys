package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

public class ReqSmsCodeDTO{

	@NotBlank
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone;

	@NotNull
	@Range(min=0, max=2)
	private Integer type;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
