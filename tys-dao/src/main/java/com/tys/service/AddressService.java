package com.tys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.DArea;
import com.tys.entity.DCity;
import com.tys.entity.DProvince;
import com.tys.entity.SysUser;
import com.tys.util.custom.BaseService;

/**
 * <pre>
 *	管理省份、市级、区县类
 *
 * 创建日期: 2016-3-6
 * 修改人 :  liul
 * 修改说明: 
 * </pre>
 */
@Service
@Repository
public class AddressService extends BaseService<DProvince> {

	/**
	 * 根据市级Code 查询该市级下所有区县
	 * 
	 * @param cityCode
	 * @return
	 */
	public List<DArea> getDAreaByCityID(int cityCode) {
		String sql = " from DArea t where t.citycode=" + cityCode;
		return findList(sql, DArea.class);
	}

	/**
	 * 根据省份Code 查询该省份下所有市级
	 * 
	 * @param provinceCode
	 * @return
	 */
	public List<DCity> getDCityByProvinceCode(int provinceCode) {
		String sql = " from DCity t where t.provincecode=" + provinceCode;
		return findList(sql, DCity.class);
	}

	/**
	 * 获取所有省份列表
	 * 
	 * @return
	 */
	public List<DProvince> getDProvinces() {
		String sql = " from DProvince t where 1=1";
		return findList(sql, DProvince.class);
	}
	
	public List getAddress(int type,int code,SysUser user){
		List list = new ArrayList();
		String sql="";
		if(type==0){
			if(user.getUserType()<=1){
				sql=" from DProvince t";
			}else{
				sql=" from DProvince t where t.code in (select p.provinceCode from RelSysuserArea p where p.agentId="+user.getId()+")";
			}
			
			List<DProvince> proList = findList(sql,DProvince.class);
			for (DProvince dProvince : proList) {
				Map map = new HashMap();
				map.put("id", dProvince.getCode());
				map.put("text", dProvince.getName());
				list.add(map);
			}
		}else if(type==1){
			if(user.getUserType()<=1){
				sql="from DCity t where t.provincecode ="+code;
			}else{
				if(user.getAgentType()<type){
					sql="from DCity t where t.provincecode="+code;
				}else{
					sql="from DCity t where t.code in (select p.cityCode from RelSysuserArea p where p.agentId="+user.getId()+")";
				}
			}
			
			List<DCity> cList = findList(sql,DCity.class);
			for (DCity dCity : cList) {
				Map map = new HashMap();
				map.put("id", dCity.getCode());
				map.put("text", dCity.getName());
				list.add(map);
			}
		}else if(type==2){
			if(user.getUserType()<=1){
				sql="from DArea t where t.citycode="+code;
			}else{
				if(user.getAgentType()<type){
					sql="from DArea t where t.citycode ="+code;
				}else{
					sql="from DArea t where t.code in (select p.areaCode from RelSysuserArea p where p.agentId="+user.getId()+")";
				}
			}
			
			List<DArea> dList = findList(sql,DArea.class);
			for (DArea dArea : dList) {
				Map map = new HashMap();
				map.put("id", dArea.getCode());
				map.put("text", dArea.getName());
				list.add(map);
			}
		}	
		return list;
	}

	public DCity findCityById(Integer cityCode) {
		String sql = "select t from DCity t where t.code="+cityCode;
		return findUnique(sql, DCity.class);
	}
	
	
}
