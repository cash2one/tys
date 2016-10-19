package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the d_city database table.
 * 
 */
@Entity
@Table(name="d_city")
@NamedQuery(name="DCity.findAll", query="SELECT d FROM DCity d")
public class DCity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer code;

	@Column(nullable=false, length=20)
	private String name;

	@Column(nullable=false)
	private Integer provincecode;

	public DCity() {
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

	public Integer getProvincecode() {
		return this.provincecode;
	}

	public void setProvincecode(Integer provincecode) {
		this.provincecode = provincecode;
	}

}