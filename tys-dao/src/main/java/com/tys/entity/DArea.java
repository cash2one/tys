package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the d_area database table.
 * 
 */
@Entity
@Table(name="d_area")
@NamedQuery(name="DArea.findAll", query="SELECT d FROM DArea d")
public class DArea implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer code;

	@Column(nullable=false)
	private Integer citycode;
	

	@Column(nullable=false, length=20)
	private String name;

	public DArea() {
	}

	public Integer getCitycode() {
		return this.citycode;
	}

	public void setCitycode(Integer citycode) {
		this.citycode = citycode;
	}

	public Integer getCode() {
		return this.code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}