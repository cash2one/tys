package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the md_attendance database table.
 * 
 */
@Entity
@Table(name="md_attendance")
@NamedQuery(name="MdAttendance.findAll", query="SELECT m FROM MdAttendance m")
public class MdAttendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="device_id", nullable=false)
	private Integer deviceId;
	
	@Column(name="is_send", nullable=false)
	private Integer isSend;

	@Column(nullable=false, length=30)
	private String imei;

	@Column(name="opt_type", nullable=false)
	private Integer optType = 0;



	public MdAttendance() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		if(createTime == null)
			createTime = new Date();
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(Integer deviceId) {
		this.deviceId = deviceId;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getOptType() {
		return this.optType;
	}

	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	public Integer getIsSend() {
		return isSend;
	}

	public void setIsSend(Integer isSend) {
		this.isSend = isSend;
	}


	
}