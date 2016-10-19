/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Administrator
 *
 */
public class ReqNewVersionDTO {

	@NotBlank
	@Pattern(regexp = "\\d{1,3}.\\d{1,3}.\\d{1,3}")//匹配XXX.XXX.XXX
	private String version;
	
	
	private String companyId = "tys_1.0";
	
	@NotNull
	private Integer type;


	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	


}
