/**
 * 
 */
package com.tys.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tys.entity.MdEquipment;
import com.tys.entity.SysUser;
import com.tys.excel.imp.ExcelImportService;
import com.tys.service.spi.EquipmentSpiService;
import com.tys.util.MDateUtil;
import com.tys.util.constants.EnumConstants.YesOrNo;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service("equipmentService")
public class EquipmentService extends BaseService<MdEquipment> implements ExcelImportService<MdEquipment>,EquipmentSpiService {
	
	@Autowired
	private MdEquipmentService mdEquipmentService;

	@Override
	public void importDb(List<MdEquipment> datas,HttpServletRequest request) {
		HttpSession session = request.getSession();
		SysUser sysUser = (SysUser)session.getAttribute("sysUser");
		if(null == sysUser){
			return;
		}
		
		for (MdEquipment entity : datas) {
			entity.setIsDeleted(YesOrNo.NO.getValue());
			entity.setState(Integer.valueOf(0));
			entity.setType(0);
			entity.setCreateBy(sysUser.getId());
			entity.setDealerId(entity.getCreateBy() == null ? -1:entity.getCreateBy());
			entity.setCreateTime(MDateUtil.getNowTime());
			entity.setUpdatedBy(entity.getCreateBy());
			entity.setUpdateTime(entity.getCreateTime());
			mdEquipmentService.save(entity);
		}
	}

	@Override
	public boolean validRepeat(MdEquipment line){
        return mdEquipmentService.isExistsByImei(line.getImei());
	}
	
	
}
