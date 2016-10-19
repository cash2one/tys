package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.RelStudentEquipment;
import com.tys.util.custom.BaseService;

@Service
@Repository
public class RelStudentEquipmentService extends BaseService<RelStudentEquipment> {

	public RelStudentEquipment findByStudentIdType(Integer id, Integer type) {
		String sql = "from RelStudentEquipment t where t.mdEquipment.type="+type+" and t.studentId="+ id;
		return findUnique(sql);
	}
	
	public Integer findStuendtIdByImei(String imei) {
		String sql = "select t.studentId from RelStudentEquipment t where t.mdEquipment.imei="+imei;
		return findUnique(sql, Integer.class);
	}

	
}
