package com.tys.entity.slim;

import java.io.Serializable;
import javax.persistence.*;

import com.alibaba.fastjson.annotation.JSONType;

import java.util.Date;


/**
 * The persistent class for the md_attendance database table.
 * 
 */
@JSONType(ignores={"imei"})
@Entity
@Table(name="md_attendance")
public class Attendance implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date time;

	@Column(name="device_id", nullable=false)
	private Integer detetorId;

	@Column(name="opt_type", nullable=false)
	private Integer optType = 0;
	
	
	private String imei;



	public Attendance() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getOptType() {
		return this.optType;
	}

	public void setOptType(Integer optType) {
		this.optType = optType;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Integer getDetetorId() {
		return detetorId;
	}

	public void setDetetorId(Integer detetorId) {
		this.detetorId = detetorId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}
	
	

}