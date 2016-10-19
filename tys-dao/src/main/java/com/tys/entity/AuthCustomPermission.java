package com.tys.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_custom_permission database table.
 * 
 */
@Entity
@Table(name="auth_custom_permission")
@NamedQuery(name="AuthCustomPermission.findAll", query="SELECT a FROM AuthCustomPermission a")
public class AuthCustomPermission implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=50)
	private String name;

	@Column(name="type",length=11)
	private Integer type;
	public AuthCustomPermission() {
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}