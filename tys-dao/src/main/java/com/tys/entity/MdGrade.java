package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_grade database table.
 * 
 */
@Entity
@Table(name="md_grade")
@NamedQuery(name="MdGrade.findAll", query="SELECT m FROM MdGrade m")
public class MdGrade extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(nullable=false, length=50)
	private String name;

	public MdGrade() {
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}