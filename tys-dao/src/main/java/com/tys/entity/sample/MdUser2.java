package com.tys.entity.sample;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 一、复制一个主类，改类名
 * 二、把不需要的字段直接删除，现只剩下name,phone,city_code
 * 三、把关联类部分内容DCity2添加进来，如下46行代码
 * 四、在service中使用如下代码查询
 *  String sql = " from MdUser2 t where X=X";
	findUnique(sql, MdUser2.class);
 */
@Entity
@Table(name = "md_user")
public class MdUser2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;


	private String name;

	private String phone;

	@Column(name = "city_code")
	private Integer cityCode;
	
	//第三步，把关联类部分内容DCity2添加进来
	//fetch=FetchType.EAGER为性能调优选项，FetchType.EAGER查询时立即加载该类，FetchType.LAZY仅当该类的get方法被调用时从数据库查询
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "city_code", insertable=false, updatable=false)
	private DCity2 dCity2;
	

	public MdUser2() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCityCode() {
		return cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public DCity2 getdCity2() {
		return dCity2;
	}

	public void setdCity2(DCity2 dCity2) {
		this.dCity2 = dCity2;
	}

	

}