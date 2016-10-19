package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the md_app_running_days database table.
 * 
 */
@Entity
@Table(name="md_app_running_days")
@NamedQuery(name="MdAppRunningDay.findAll", query="SELECT m FROM MdAppRunningDay m")
public class MdAppRunningDay implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;
	
	private Integer points;

	@Temporal(TemporalType.DATE)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="created_by", nullable=false)
	private Integer createdBy;

	public MdAppRunningDay() {
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

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}
	
	

}