/**
 * 
 */
package com.tys.base;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Administrator
 *
 */
public class BaseDTO implements Serializable {

	private static final long serialVersionUID = -7498856253497986430L;

	private Integer id;

	private Long createBy;

	private Date createTime;

	private Long updatedBy;

	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getcreateBy() {
		return createBy;
	}

	public void setcreateBy(Long createBy) {
		this.createBy = createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
