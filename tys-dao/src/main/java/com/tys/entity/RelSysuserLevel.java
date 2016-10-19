package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the rel_user_student database table.
 * 
 */
@Entity
@Table(name = "rel_sysuser_level")
@NamedQuery(name = "RelSysuserLevel.findAll", query = "SELECT r FROM RelSysuserLevel r")
public class RelSysuserLevel implements Serializable {

	private static final long serialVersionUID = 4579914370218000785L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "parent_id")
	private Integer parentId;

	@Column(name = "parent_level")
	private Integer parentLevel;

	@Column(name = "sysuser_id")
	private Integer sysUserId;

	public RelSysuserLevel() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getParentLevel() {
		return parentLevel;
	}

	public void setParentLevel(Integer parentLevel) {
		this.parentLevel = parentLevel;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}
	
	

}