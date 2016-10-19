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
public class ReqChangePwDTO {

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9@._]{6,32}")//6~32位
	private String oldPw;

	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9@._]{6,32}")//6~32位
	private String newPw;

	public String getOldPw() {
		return oldPw;
	}

	public void setOldPw(String oldPw) {
		this.oldPw = oldPw;
	}

	public String getNewPw() {
		return newPw;
	}

	public void setNewPw(String newPw) {
		this.newPw = newPw;
	}

}
