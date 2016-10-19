/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 *
 */
public class ReqUserRegistDTO {

	@NotBlank
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String acct;

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9@._]{6,32}")//6~32位
	private String pw;

	@NotBlank
	@Pattern(regexp = "[0-9]{6}")//6位0-9
	private String code;

	@NotNull
	@Range(min=0, max=1)
	private Integer sex;

	@NotNull
	private Integer cityCode;

	public String getAcct() {
		return acct;
	}

	public void setAcct(String acct) {
		this.acct = acct;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}


}
