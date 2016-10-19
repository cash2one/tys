package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the md_rt_data database table.
 * 
 */
@Entity
@Table(name="md_rt_data")
@NamedQuery(name="MdRtData.findAll", query="SELECT m FROM MdRtData m")
public class MdRtData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Integer curHttpPort;

	@Column(nullable=false, length=20)
	private String curIp;

	@Column(nullable=false)
	private Integer curPort;

	@Column(nullable=false, length=50)
	private String imei;

	@Column(nullable=false)
	private Integer isOnline;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date lastBreakTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	private Date connectTime;

	public MdRtData() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCurHttpPort() {
		return this.curHttpPort;
	}

	public void setCurHttpPort(Integer curHttpPort) {
		this.curHttpPort = curHttpPort;
	}

	public String getCurIp() {
		return this.curIp;
	}

	public void setCurIp(String curIp) {
		this.curIp = curIp;
	}

	public Integer getCurPort() {
		return this.curPort;
	}

	public void setCurPort(Integer curPort) {
		this.curPort = curPort;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getIsOnline() {
		return this.isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Date getLastBreakTime() {
		return this.lastBreakTime;
	}

	public void setLastBreakTime(Date lastBreakTime) {
		this.lastBreakTime = lastBreakTime;
	}

	public Date getConnectTime() {
		return connectTime;
	}

	public void setConnectTime(Date connectTime) {
		this.connectTime = connectTime;
	}

	
	
}