package com.tys.dto.spi.rsp;

import com.tys.base.BaseSpiRsp;

public class RspNewVersionDTO extends BaseSpiRsp {

	private static final long serialVersionUID = 4489216044888640234L;

	private String version;
	private String companyId;
	private String url;
	private Integer needUpdate;
	private String desc;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(Integer needUpdate) {
		this.needUpdate = needUpdate;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}


}
