package com.tys.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tys.base.BaseEntity;


/**
 * The persistent class for the rel_student_equipment database table.
 * 
 */
@Entity
@Table(name="rel_student_equipment")
@NamedQuery(name="RelStudentEquipment.findAll", query="SELECT r FROM RelStudentEquipment r")
public class RelStudentEquipment extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;



	@Column(name="equipment_id", nullable=false)
	private int equipmentId;


	@Column(name="student_id", nullable=false)
	private int studentId;
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "equipment_id", insertable = false, updatable = false)
	private MdEquipment mdEquipment;
	

	public RelStudentEquipment() {
	}

	public int getEquipmentId() {
		return this.equipmentId;
	}

	public void setEquipmentId(int equipmentId) {
		this.equipmentId = equipmentId;
	}

	public int getStudentId() {
		return this.studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public MdEquipment getMdEquipment() {
		return mdEquipment;
	}

	public void setMdEquipment(MdEquipment mdEquipment) {
		this.mdEquipment = mdEquipment;
	}
	
	

}