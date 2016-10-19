package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the d_province database table.
 * 
 */
@Entity
@Table(name="d_province")
@NamedQuery(name="DProvince.findAll", query="SELECT d FROM DProvince d")
public class DProvince implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer code;

	@Column(nullable=false, length=20)
	private String name;

	public DProvince() {
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