package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_file database table.
 * 
 */
@Entity
@Table(name="md_file")
@NamedQuery(name="MdFile.findAll", query="SELECT m FROM MdFile m")
public class MdFile extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;



	@Column(nullable=false, length=128)
	private String name;

	@Column(nullable=false, length=256)
	private String path;

	@Column(nullable=false, length=256)
	private String url;
	
	@Column(nullable=true)
	private Integer type;

	public MdFile() {
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}