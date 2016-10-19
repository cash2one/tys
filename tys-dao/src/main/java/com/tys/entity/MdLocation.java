package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the md_location database table.
 * 
 */
@Entity
@Table(name="md_location")
@NamedQuery(name="MdLocation.findAll", query="SELECT m FROM MdLocation m")
public class MdLocation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private Float accuracy;

	@Column(nullable=false)
	private Float altitude;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(nullable=false)
	private Float direction;

	@Column(nullable=false, length=30)
	private String imei;

	@Column(name="j_latitude")
	private Double jLatitude;

	@Column(name="j_longitude")
	private Double jLongitude;

	@Column(nullable=false)
	private Double latitude;

	@Column(nullable=false)
	private Double longitude;

	private Integer mapType;

	@Column(name="position_type", nullable=false)
	private Integer positionType;

	@Column(nullable=false)
	private Float speed;

	public MdLocation() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Float getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(Float accuracy) {
		this.accuracy = accuracy;
	}

	public Float getAltitude() {
		return this.altitude;
	}

	public void setAltitude(Float altitude) {
		this.altitude = altitude;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Float getDirection() {
		return this.direction;
	}

	public void setDirection(Float direction) {
		this.direction = direction;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Double getJLatitude() {
		return this.jLatitude;
	}

	public void setJLatitude(Double jLatitude) {
		this.jLatitude = jLatitude;
	}

	public Double getJLongitude() {
		return this.jLongitude;
	}

	public void setJLongitude(Double jLongitude) {
		this.jLongitude = jLongitude;
	}

	public Double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Integer getMapType() {
		return this.mapType;
	}

	public void setMapType(Integer mapType) {
		this.mapType = mapType;
	}

	public Integer getPositionType() {
		return this.positionType;
	}

	public void setPositionType(Integer positionType) {
		this.positionType = positionType;
	}

	public Float getSpeed() {
		return this.speed;
	}

	public void setSpeed(Float speed) {
		this.speed = speed;
	}

}