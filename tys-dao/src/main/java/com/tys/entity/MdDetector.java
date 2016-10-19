package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the md_detector database table.
 * 
 */
@Entity
@Table(name="md_detector")
@NamedQuery(name="MdDetector.findAll", query="SELECT m FROM MdDetector m")
public class MdDetector extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;


	@Column(nullable=false, length=200)
	private String desc;

	@Column(name="detector_no", nullable=false, length=30)
	private String detectorNo;

	@Column(nullable=false, length=6)
	private String head;


	public MdDetector() {
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDetectorNo() {
		return this.detectorNo;
	}

	public void setDetectorNo(String detectorNo) {
		this.detectorNo = detectorNo;
	}

	public String getHead() {
		return this.head;
	}

	public void setHead(String head) {
		this.head = head;
	}

}