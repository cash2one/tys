/**
 * 
 */
package com.tys.dto.spi.rsp;

import java.util.Date;
import java.util.List;

import com.tys.base.BaseSpiRsp;
import com.tys.util.MDateUtil;

/**
 * @author Administrator
 *
 */
public class RspLoginRspDTO extends BaseSpiRsp {

	private static final long serialVersionUID = -1345155802994063131L;

	private Integer accountId;

	private String custAcct;

	private String name;
	
	private Integer sex;

	private Integer cityCode;

	private String phone;

	private Date birthday;

	private Integer points;

	private Integer type;

	private String aes;
	
	private List<RspChildListInnerDTO> childList;

	//云通讯相关
	private Integer accountType;
	private Long appIdAt3rd;
	private Long sdkAppId;
	private String userSig;
	
	
	public String getCustAcct() {
		return custAcct;
	}

	public void setCustAcct(String custAcct) {
		this.custAcct = custAcct;
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

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getAes() {
		return aes;
	}

	public void setAes(String aes) {
		this.aes = aes;
	}

	public List<RspChildListInnerDTO> getChildList() {
		return childList;
	}

	public void setChildList(List<RspChildListInnerDTO> childList) {
		this.childList = childList;
	}


	public String getBirthday() {
		if(birthday != null)
			return MDateUtil.formatDate(MDateUtil.ISO_EXPANDED_DATE_FORMAT, birthday);
		return null;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getAccountType() {
		return accountType;
	}

	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}

	public Long getAppIdAt3rd() {
		return appIdAt3rd;
	}

	public void setAppIdAt3rd(Long appIdAt3rd) {
		this.appIdAt3rd = appIdAt3rd;
	}

	public Long getSdkAppId() {
		return sdkAppId;
	}

	public void setSdkAppId(Long sdkAppId) {
		this.sdkAppId = sdkAppId;
	}

	public String getUserSig() {
		return userSig;
	}

	public void setUserSig(String userSig) {
		this.userSig = userSig;
	}

	
	
}
