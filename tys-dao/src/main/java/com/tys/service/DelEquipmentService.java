/**
 * 
 */
package com.tys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tys.dto.query.EquipmentDTO;
import com.tys.entity.MdEquipment;
import com.tys.excel.imp.ExcelImportService;
import com.tys.util.custom.BaseService;

@Service("delEquipmentService")
public class DelEquipmentService extends BaseService<MdEquipment> implements ExcelImportService<EquipmentDTO> {

	@Autowired
	private MdEquipmentService mdEquipmentService;

	@Override
	public void importDb(List<EquipmentDTO> datas, HttpServletRequest request) {

		for (EquipmentDTO equipmentDTO : datas) {
			MdEquipment entity = this.mdEquipmentService.findByImei(equipmentDTO.getImei());
			if (null == entity || !Integer.valueOf(0).equals(entity.getState())) {
				continue;
			}
			this.mdEquipmentService.delete(entity);
		}
	}

}
