/**
 * 
 */
package com.tys.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.MdEquipment;
import com.tys.util.MCollectionUtils;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.constants.EnumConstants.YesOrNo;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdEquipmentService extends BaseService<MdEquipment> {
	
	@Autowired
	private MdSchoolService  mdSchoolService;
	
	@Autowired
	private RelSysuserLevelService  relSysuserLevelService;
	
	@Autowired
	private SysUserService  sysUserService;
	

	public MdEquipment findByImei(String imei) {
		String sql = "select c from MdEquipment c where c.imei='" + imei + "' and isDeleted = 0";
		return findUnique(sql);
	}
	
	public Boolean isExistsByImei(String imei){
		String sql = "select c from MdEquipment c where c.imei='" + imei + "' and isDeleted = 0";
		MdEquipment entity =  findUnique(sql);
		return null == entity ? false : true;
	}
	
	@Transactional
	public void add(MdEquipment model) {
		if(null == model || MStrUtil.isNull(model.getImei()) || isExistsByImei(model.getImei())){
			return;
		}
		
		
		MdEquipment entity = new MdEquipment();
		entity.setImei(model.getImei());
		entity.setIsDeleted(YesOrNo.NO.getValue());
		entity.setState(Integer.valueOf(0));
		entity.setType(model.getType());
		entity.setDealerId(model.getCreateBy() == null ? -1:model.getCreateBy());
		entity.setCreateTime(MDateUtil.getNowTime());
		entity.setUpdatedBy(entity.getCreateBy());
		entity.setUpdateTime(entity.getCreateTime());
		this.save(entity);
	}
	
	@Transactional
	public void assign(MdEquipment model) {
		MdEquipment entity = this.findById(model.getId());
		if(null == entity){
			return;
		}
		if(model.getDealerId() != null){
			entity.setDealerId(model.getDealerId());
			entity.setState(Integer.valueOf(1));
		}else if(model.getSchoolId() != null){
			entity.setSchoolId(model.getSchoolId());
			entity.setState(Integer.valueOf(2));
		}
		
		entity.setCreateTime(MDateUtil.getNowTime());
		entity.setUpdateTime(MDateUtil.getNowTime());
		this.update(entity);
	}
	
	
	public MPage<MdEquipment> findEquipmetnStockPageByModel(SearchVo search, int page, int rows) {
		try {
			StringBuilder sqlStr = new StringBuilder("from MdEquipment  ");
			StringBuilder wherecase = new StringBuilder(" where isDeleted = 0  and state < 3");
			buildCondition(search, wherecase);
			sqlStr.append(wherecase);
			sqlStr.append(" order by id desc");
			return findPageEx(sqlStr.toString(), page, rows, wherecase.toString(), MdEquipment.class);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	private void buildCondition(SearchVo search, StringBuilder sqlStr) {
		Integer userType = search.getUserType();
		if(Integer.valueOf(3).equals(userType)){
			List<Integer> schoolIdList = this.mdSchoolService.findShoolIdsByAdminId(search.getUserId());
			if(MCollectionUtils.isNotEmpty(schoolIdList)){
				StringBuilder schoolIds = new StringBuilder();
				for (Integer schoolId : schoolIdList) {
					schoolIds.append(schoolId+",");
				}
				sqlStr.append(" and schoolId in("+schoolIds.substring(0, schoolIds.length() - 1)+")");
			}
		}else if(Integer.valueOf(2).equals(userType)){
			List<Integer> dealerIdList = this.relSysuserLevelService.findUserIdByParentId(search.getUserId());
			if(MCollectionUtils.isNotEmpty(dealerIdList)){
				StringBuilder strDealerIds = new StringBuilder(search.getUserId()+",");
				for (Integer dealerId : dealerIdList) {
					strDealerIds.append(dealerId+",");
				}
				sqlStr.append(" and dealerId in("+strDealerIds.substring(0, strDealerIds.length() - 1)+")");
			}
		}
		
		String imeiNo = search.getImeiNo();
		if(MStrUtil.isNotNull(imeiNo)){
			sqlStr.append(" and  imei like '%"+imeiNo+"%'");
		}
	}
	
	public MPage<MdEquipment> findEquipmetnSalesPageByModel(SearchVo search, int page, int rows) {
		try {
			StringBuilder sqlStr = new StringBuilder("from MdEquipment  ");
			StringBuilder  wherecase = new StringBuilder(" where  isDeleted = 0  and state = 3");//buildSqlFromSearchVo(search).toString();
			buildCondition(search, wherecase);
			
			sqlStr.append(wherecase);
			buildCondition(search, sqlStr);
			sqlStr.append(wherecase);
			sqlStr.append(" order by id desc");
			MPage<MdEquipment>  result = findPageEx(sqlStr.toString(), page, rows, wherecase.toString(), MdEquipment.class);
			
			Set<Integer> schoolIdSet = new HashSet<Integer>();
			Set<Integer> dealerIdSet = new HashSet<Integer>();
			
			for (MdEquipment entity : result.getResult()) {
				schoolIdSet.add(entity.getSchoolId());
				dealerIdSet.add(entity.getDealerId());
			}
			
			Map<Integer,String> schoolMap = this.mdSchoolService.findMapByIds(schoolIdSet);
			Map<Integer,String> dealerMap = this.sysUserService.findMapByIds(dealerIdSet);
			for (MdEquipment entity : result.getResult()) {
				entity.setSchoolName(schoolMap.get(entity.getSchoolId()));
				entity.setDealerName(dealerMap.get(entity.getDealerId()));
			}
			
			return result;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Transactional
	public void updateStock(Integer schoolId, Integer parentDealerId) {
		
		//未绑定设备的，把库存状态改为已分配经销商
		executeUpdate("update MdEquipment set state=1 where schoolId="+schoolId + " and state=3");
		
		//库存转移到上级代理商。
		executeUpdate("update MdEquipment set dealerId= " +parentDealerId +",schoolId=NULL where schoolId="+schoolId);
		
	}
	
	
}
