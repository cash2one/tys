package com.tys.netty.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -8915061904241671723L;

	// id
	private Long id;

	// 创建时间
	private Date createTime;

	// 创建人id
	private Long createBy;

	// 修改时间
	private Date updateTime;

	// 修改人id
	private Long updatedBy;

	// 是否被删除
	private Integer isDeleted; 

	private Long version;

	// 批量操作idList
	private List<Long> idList;

	private String ids;

	public List<Long> getIdList() {
		return idList;
	}

	public void setIdList(List<Long> idList) {
		this.idList = idList;
	}

	@Override
	public String toString() {
		return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss",
				SerializerFeature.WriteClassName,
				SerializerFeature.PrettyFormat);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getcreateBy() {
		return createBy;
	}

	public void setcreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
