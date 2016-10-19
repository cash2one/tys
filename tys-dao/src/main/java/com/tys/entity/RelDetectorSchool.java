package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the rel_detector_school database table.
 * 
 */
@Entity
@Table(name="rel_detector_school")
@NamedQuery(name="RelDetectorSchool.findAll", query="SELECT r FROM RelDetectorSchool r")
public class RelDetectorSchool extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;



	@Column(name="detector_id", nullable=false)
	private Integer detectorId;


	@Column(name="school_id", nullable=false)
	private Integer schoolId;


	public RelDetectorSchool() {
	}


	public Integer getDetectorId() {
		return this.detectorId;
	}

	public void setDetectorId(Integer detectorId) {
		this.detectorId = detectorId;
	}

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}


}