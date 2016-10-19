package com.tys.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 实体类公共部分 注：仅部分实体类继承
 * 
 * @author Administrator
 *
 */
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "is_deleted", nullable = false)
	private Integer isDeleted = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Column(name = "created_by")
	private Integer createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false)
	private Date updateTime;

	@Column(name = "updated_by")
	private Integer updatedBy;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreateTime() {
		if (createTime == null)
			createTime = new Date();
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		if (updateTime == null)
			updateTime = new Date();
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Integer updatedBy) {
		this.updatedBy = updatedBy;
	}

}
