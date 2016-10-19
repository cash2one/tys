/**
 * 
 */
package com.tys.dto.spi.rsp;

import java.util.Date;
import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.entity.MdCourse;
import com.tys.util.MDateUtil;

/**
 * @author Administrator
 *
 */
public class RspMdUserDTO extends BaseSpiRsp {

	private static final long serialVersionUID = -3126168008931614287L;

	private String name;

	private String sex;

	private Date birthday;

	private String custAcct;

	private Integer cityCode;

	private List<MdCourse> courseList;

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
		if(birthday != null)
			return MDateUtil.formatDate(MDateUtil.ISO_EXPANDED_DATE_FORMAT, birthday);
		return null;
	}

	public void setBirthday(Date birthday) {
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

	public List<MdCourse> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<MdCourse> courseList) {
		this.courseList = courseList;
	}

}
