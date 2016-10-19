package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the rel_sysuser_permission database table.
 * 
 */
@Entity
@Table(name="rel_sysuser_permission")
@NamedQuery(name="RelSysuserPermission.findAll", query="SELECT r FROM RelSysuserPermission r")
public class RelSysuserPermission extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(name="permission_id", nullable=false)
	private Integer permissionId;

	@Column(name="sysuser_id", nullable=false)
	private Integer sysuserId;

	@ManyToOne
	@JoinColumn(name = "sysuser_id", insertable=false, updatable=false)
	private SysUser sysUser;

	@ManyToOne
	@JoinColumn(name = "permission_id", insertable=false, updatable=false)
	private AuthCustomPermission permission;
	
	
	public RelSysuserPermission() {
	}

	public Integer getPermissionId() {
		return this.permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

	public Integer getSysuserId() {
		return this.sysuserId;
	}

	public void setSysuserId(Integer sysuserId) {
		this.sysuserId = sysuserId;
	}

	public SysUser getSysUser() {
		return sysUser;
	}

	public void setSysUser(SysUser sysUser) {
		this.sysUser = sysUser;
	}

	public AuthCustomPermission getPermission() {
		return permission;
	}

	public void setPermission(AuthCustomPermission permission) {
		this.permission = permission;
	}
	
	

}