/**
 * 
 */
package com.tys.dto.spi.req;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * @author Administrator
 *
 */
public class ReqSettingStudentDTO {

	@NotNull
	private Integer childId;

	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone0;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone1;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone2;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone3;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone4;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone5;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone6;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone7;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone8;
	
	@Pattern(regexp = "^1\\d{10}$")//以1开头11位数字
	private String phone9;
	
	@Length(min=1, max=50)
	private String name;

	@Range(min=0, max=1)
	private Integer sex;

	@Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")//日期判断yyyy-MM-dd
	private String birthday;

	
	private Integer classId;

	public Integer getChildId() {
		return childId;
	}

	public void setChildId(Integer childId) {
		this.childId = childId;
	}

	public String getPhone0() {
		return phone0;
	}

	public void setPhone0(String phone0) {
		this.phone0 = phone0;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getPhone4() {
		return phone4;
	}

	public void setPhone4(String phone4) {
		this.phone4 = phone4;
	}

	public String getPhone5() {
		return phone5;
	}

	public void setPhone5(String phone5) {
		this.phone5 = phone5;
	}

	public String getPhone6() {
		return phone6;
	}

	public void setPhone6(String phone6) {
		this.phone6 = phone6;
	}

	public String getPhone7() {
		return phone7;
	}

	public void setPhone7(String phone7) {
		this.phone7 = phone7;
	}

	public String getPhone8() {
		return phone8;
	}

	public void setPhone8(String phone8) {
		this.phone8 = phone8;
	}

	public String getPhone9() {
		return phone9;
	}

	public void setPhone9(String phone9) {
		this.phone9 = phone9;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

}
