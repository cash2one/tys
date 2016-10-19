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
import com.tys.entity.RelSysuserLevel;
import com.tys.entity.SysUser;
import com.tys.excel.imp.ExcelImportService;
import com.tys.util.MDateUtil;
import com.tys.util.custom.BaseService;

@Service("returnEquipmentService")
public class ReturnEquipmentService extends BaseService<MdEquipment> implements ExcelImportService<EquipmentDTO> {

	@Autowired
	private MdEquipmentService mdEquipmentService;
	
	@Autowired
	private MdSchoolService mdSchoolService;
	
	@Autowired
	private RelSysuserLevelService relSysuserLevelService;
	
	@Autowired
	private SysUserService sysUserService;

	@Override
	public void importDb(List<EquipmentDTO> datas, HttpServletRequest request) {
		SysUser user = (SysUser)request.getSession().getAttribute("sysUser");
		//会话失效
		if(null== user){
			return;
		}
		
		

		for (EquipmentDTO equipmentDTO : datas) {
			MdEquipment entity = this.mdEquipmentService.findByImei(equipmentDTO.getImei());
			if (null == entity) {
				continue;
			}
			
			//已售设备或者入库状态的设备不能返还
			if(!Integer.valueOf(1).equals(entity.getState()) &&  !Integer.valueOf(2).equals(entity.getState())){
				continue;
			}
			
			if(Integer.valueOf(2).equals(entity.getState())){
				MdSchool school = mdSchoolService.findById(entity.getSchoolId());
				//学校不存在,无法返还
				if(null == school){
					continue;
				}
				
				//无权限操作该数据
				if(!user.getId().equals(school.getAdminId())){
					continue;
				}
				
				entity.setSchoolId(null);
				entity.setState(1);
				entity.setUpdatedBy(user.getId());
				entity.setUpdateTime(MDateUtil.getNowTime());
			}else if(Integer.valueOf(1).equals(entity.getState())){
				if(!user.getId().equals(entity.getDealerId())){
					//无权限操作该数据
					continue;
				}
				
				RelSysuserLevel relSysuserLevel = this.relSysuserLevelService.getParentByUserId(entity.getId());
				if(null == relSysuserLevel){
					//无上级经销商
					continue;
				}
				
				SysUser parentUser = sysUserService.findById(relSysuserLevel.getParentId());
				
				if(null == parentUser){
					//上级经销商不存在
					continue;
				}
				
				entity.setSchoolId(null);
				entity.setDealerId(parentUser.getId());
				if(parentUser.getUserType().equals(1)){
					entity.setState(0);
				}
				entity.setUpdatedBy(user.getId());
				entity.setUpdateTime(MDateUtil.getNowTime());
			}
			this.mdEquipmentService.update(entity);
		}
	}

}
