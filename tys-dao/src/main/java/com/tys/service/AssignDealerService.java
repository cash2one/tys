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
import com.tys.entity.SysUser;
import com.tys.excel.imp.ExcelImportService;
import com.tys.util.MCollectionUtils;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service("assignDealerService")
public class AssignDealerService extends BaseService<MdEquipment> implements ExcelImportService<EquipmentDTO> {
	
	@Autowired
	private MdEquipmentService mdEquipmentService;
	
	@Autowired
	private SysUserService  sysUserService;
	
	@Autowired
	private RelSysuserLevelService  relSysuserLevelService;

	@Override
	public void importDb(List<EquipmentDTO> datas,HttpServletRequest request) {
		
		
		SysUser curUser = (SysUser)request.getSession().getAttribute("sysUser");
		if(null == curUser){
			return ;
		}
		
		
		String agentPhone = request.getParameter("agentPhone");
		if(MStrUtil.isNullOrEmpty(agentPhone)){
			return;
		}
		
		SysUser user = sysUserService.findByPhone(agentPhone);
		if(null == user){
			return;
		}
		
		if(Integer.valueOf(2).equals(curUser.getUserType())){
			List<Integer> dealerIdList = this.relSysuserLevelService.findUserIdByParentId(curUser.getId());
			if(MCollectionUtils.isEmpty(dealerIdList)){
				return ;
			}
			
			if(!dealerIdList.contains(user.getId())){
				return ;
			}
		}
		
		for (EquipmentDTO equipmentDTO : datas) {
			MdEquipment mdEquipment = mdEquipmentService.findByImei(equipmentDTO.getImei());
			if(null == mdEquipment || !Integer.valueOf(0).equals(mdEquipment.getState())){
				continue;
			}
			
			mdEquipment.setDealerId(user.getId());
			mdEquipment.setUpdateTime(MDateUtil.getNowTime());
			mdEquipment.setState(1);
			this.mdEquipmentService.update(mdEquipment);
		}
		
		
		/*Map<String,Integer> userMap = new HashMap<String,Integer>();
		for (EquipmentDTO equipmentDTO : datas) {
			String key = String.valueOf(equipmentDTO.getProviceId()) + "_" + String.valueOf(equipmentDTO.getCityId())
					+ "_" + String.valueOf(equipmentDTO.getAreaId()) + "_" + String.valueOf(equipmentDTO.getUserName());
			if(!userMap.containsKey(key)){
				SysUser condition = new SysUser();
				condition.setProvinceCode(null == equipmentDTO.getProviceId() ? null : equipmentDTO.getProviceId().intValue());
				condition.setCityCode(null == equipmentDTO.getCityId()?null:equipmentDTO.getCityId().intValue());
				condition.setAreaCode(null == equipmentDTO.getAreaId()?null:equipmentDTO.getAreaId().intValue());
				condition.setName(equipmentDTO.getUserName());
				SysUser entity = sysUserService.findByCondition(condition);
				if(null != entity){
					userMap.put(key, entity.getId());
				}
			}
			
			Integer dealerId = userMap.get(key);
			if(null == dealerId || MStrUtil.isNull(equipmentDTO.getImei())){
				continue;
			}
			
			MdEquipment  mdEquipment = mdEquipmentService.findByImei(equipmentDTO.getImei());
			if(null == mdEquipment || !Integer.valueOf(0).equals(mdEquipment.getState())){
				continue;
			}
			
			mdEquipment.setDealerId(dealerId);
			mdEquipment.setUpdateTime(MDateUtil.getNowTime());
			mdEquipment.setState(1);
			this.mdEquipmentService.update(mdEquipment);*/
		}
	
}
