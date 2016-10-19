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
import com.tys.entity.MdSchool;
import com.tys.entity.SysUser;
import com.tys.excel.imp.ExcelImportService;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.custom.BaseService;

@Service("assignSchoolService")
public class AssignSchoolService extends BaseService<MdEquipment> implements ExcelImportService<EquipmentDTO> {
	
	@Autowired
	private MdEquipmentService mdEquipmentService;
	
	@Autowired
	private MdSchoolService  mdSchoolService;

	@Override
	public void importDb(List<EquipmentDTO> datas,HttpServletRequest request) {
		String schoolName = request.getParameter("schoolName");
		if(MStrUtil.isNullOrEmpty(schoolName)){
			return;
		}
		
		MdSchool condition = new MdSchool();
		condition.setName(schoolName);
		MdSchool school = mdSchoolService.getByCondition(condition);
		if(null == school){
			return;
		}
		
		
		SysUser curUser = (SysUser)request.getSession().getAttribute("sysUser");
		if(null == curUser){
			return ;
		}
		
		if(Integer.valueOf(2).equals(curUser.getUserType())){
			if(school.getCreateBy() == null || !school.getId().equals(school.getCreateBy())){
				return ;
			}
		}
		
		
		for (EquipmentDTO equipmentDTO : datas) {
			MdEquipment  mdEquipment = mdEquipmentService.findByImei(equipmentDTO.getImei());
			if(null == mdEquipment || !Integer.valueOf(1).equals(mdEquipment.getState())){
				continue;
			}
			
			mdEquipment.setSchoolId(school.getId());
			mdEquipment.setUpdateTime(MDateUtil.getNowTime());
			mdEquipment.setState(2);
			this.mdEquipmentService.update(mdEquipment);
		} 
		
		/*Map<String,Integer> userMap = new HashMap<String,Integer>();
		for (EquipmentDTO equipmentDTO : datas) {
			String key = String.valueOf(equipmentDTO.getProviceId()) + "_" + String.valueOf(equipmentDTO.getCityId())
					+ "_" + String.valueOf(equipmentDTO.getAreaId()) + "_" + String.valueOf(equipmentDTO.getUserName());
			if(!userMap.containsKey(key)){
				MdSchool condition = new MdSchool();
				condition.setProvinceId(null == equipmentDTO.getProviceId() ? null : equipmentDTO.getProviceId().intValue());
				condition.setCityId(null == equipmentDTO.getCityId()?null:equipmentDTO.getCityId().intValue());
				condition.setAreaId(null == equipmentDTO.getAreaId()?null:equipmentDTO.getAreaId().intValue());
				condition.setName(equipmentDTO.getUserName());
				
				MdSchool entity = mdSchoolService.getByCondition(condition);
				if(null != entity){
					userMap.put(key, entity.getId());
				}
			}
			
			Integer schoolId = userMap.get(key);
			if(null == schoolId || MStrUtil.isNull(equipmentDTO.getImei())){
				continue;
			}
			
			MdEquipment  mdEquipment = mdEquipmentService.findByImei(equipmentDTO.getImei());
			if(null == mdEquipment || !Integer.valueOf(1).equals(mdEquipment.getState())){
				continue;
			}
			
			mdEquipment.setSchoolId(schoolId);
			mdEquipment.setUpdateTime(MDateUtil.getNowTime());
			mdEquipment.setState(2);
			this.mdEquipmentService.update(mdEquipment);
		}*/
	}

	
	
}
