/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 *
 */
public class ReqSettingDTO {

	@Length(min=1, max=50)
	private String name;
	
	@Range(min=0, max=1)
	private String sex;
	
	
	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")//日期判断yyyy-MM-dd
	private String birthday;
	
	//这里有两个判断条件，放到controller里面判断
	//@Pattern(regexp = "^[A-Za-z][A-Za-z0-9@_.]{5,32}")//字母开头，不含其他特殊字符
	private String custAcct;
	
	private Integer cityCode;
	
	private Integer imgId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getCustAcct() {
		return custAcct;
	}

	public void setCustAcct(String custAcct) {
		this.custAcct = custAcct;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Integer getImgId() {
		return imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

}
