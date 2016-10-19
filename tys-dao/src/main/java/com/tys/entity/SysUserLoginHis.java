package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the sys_user_login_his database table.
 * 
 */
@Entity
@Table(name="sys_user_login_his")
@NamedQuery(name="SysUserLoginHis.findAll", query="SELECT s FROM SysUserLoginHis s")
public class SysUserLoginHis implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="login_ip", nullable=false, length=20)
	private String loginIp;

	@Column(name="login_name", nullable=false, length=30)
	private String loginName;

	public SysUserLoginHis() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

}