package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the md_course database table.
 * 
 */
@Entity
@Table(name="md_course")
@NamedQuery(name="MdCourse.findAll", query="SELECT m FROM MdCourse m")
public class MdCourse implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=50)
	private String name;

	public MdCourse() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}