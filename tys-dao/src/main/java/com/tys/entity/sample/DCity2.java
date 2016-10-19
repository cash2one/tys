package com.tys.entity.sample;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 一、复制一个主类，改类名
 * 二、把不需要的属性直接删除，现只剩下name和code
 */
@Entity
@Table(name = "d_city")
public class DCity2 {

	
	@Id
	private Integer code;

	private String name;


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


}