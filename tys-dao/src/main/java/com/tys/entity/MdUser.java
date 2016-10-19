package com.tys.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the md_user database table.
 * 
 */
@Entity
@Table(name = "md_user")
@NamedQuery(name = "MdUser.findAll", query = "SELECT m FROM MdUser m")
public class MdUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 16)
	private String aes;

	@Temporal(TemporalType.DATE)
	@Column(nullable = false)
	private Date birthday;

	@Column(name = "city_code", nullable = false)
	private Integer cityCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_time", nullable = false)
	private Date createTime;

	@Column(name = "cust_acct", nullable = false, length = 50)
	private String custAcct;

	@Column(nullable = false, length = 128)
	private String imei;

	@Column(name = "is_deleted", nullable = false)
	private Integer isDeleted = 0;

	@Column(nullable = false, length = 50)
	private String name;

	@Column(nullable = false, length = 30)
	private String phone;

	@Column(name = "points", nullable = false)
	private Integer points;

	@Column(nullable = false, length = 64)
	private String pw;

	@Column(nullable = false)
	private Integer sex;

	@Column(nullable = false)
	private Integer type;

	@Column(nullable = false, name = "img_id")
	private Integer imgId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "update_time", nullable = false)
	private Date updateTime;

	@Column(nullable = false, length = 16)
	private String version;

	@Column(nullable = false, name = "school_id")
	private Integer schoolId;

	@ManyToOne
	@JoinColumn(name = "school_id", insertable = false, updatable = false)
	private MdSchool mdSchool;

	public MdUser() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAes() {
		return this.aes;
	}

	public void setAes(String aes) {
		this.aes = aes;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Integer getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(Integer cityCode) {
		this.cityCode = cityCode;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCustAcct() {
		return this.custAcct;
	}

	public void setCustAcct(String custAcct) {
		this.custAcct = custAcct;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getPoints() {
		return this.points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public String getPw() {
		return this.pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getImgId() {
		return imgId;
	}

	public void setImgId(Integer imgId) {
		this.imgId = imgId;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public MdSchool getMdSchool() {
		return mdSchool;
	}

	public void setMdSchool(MdSchool mdSchool) {
		this.mdSchool = mdSchool;
	}

}