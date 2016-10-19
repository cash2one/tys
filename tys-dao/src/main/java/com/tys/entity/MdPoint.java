package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the md_points database table.
 * 
 */
@Entity
@Table(name="md_points")
@NamedQuery(name="MdPoint.findAll", query="SELECT m FROM MdPoint m")
public class MdPoint implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time", nullable=false)
	private Date createTime;

	@Column(name="created_by")
	private Integer createBy;

	@Column(nullable=false)
	private Integer increment;

	@Column(name="is_deleted")
	private Integer isDeleted;

	@Column(length=50)
	private String source;

	public MdPoint() {
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


	public Integer getCreateBy() {
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	public Integer getIncrement() {
		return this.increment;
	}

	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}