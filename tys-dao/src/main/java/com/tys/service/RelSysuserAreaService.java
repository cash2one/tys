package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelSysuserArea;
import com.tys.util.MUtil;
import com.tys.util.custom.BaseService;

@Service
@Repository
public class RelSysuserAreaService extends BaseService<RelSysuserArea> {
	
	public List<RelSysuserArea> findByAgentId(int agentId){
		String sql=" from RelSysuserArea t where t.isDeleted=0 and t.agentId="+agentId;
		return findList(sql);
	}
	
	@Transactional
	public void newRelSysuserArea(int agentType, int userId, int agentId, String provinceCode, String cityCode,
			String areaCode) {
		try {
			if (agentType == 0) {
				if (provinceCode.contains(",")) {
					String[] provinces = provinceCode.split(",");
					for (String string : provinces) {
						RelSysuserArea userArea = new RelSysuserArea();
						userArea.setAgentId(agentId);
						userArea.setCreateBy(userId);
						userArea.setProvinceCode(Integer.parseInt(string));
						this.save(userArea);
					}
				}else{
					RelSysuserArea userArea = new RelSysuserArea();
					userArea.setAgentId(agentId);
					userArea.setCreateBy(userId);
					userArea.setProvinceCode(Integer.parseInt(provinceCode));
					this.save(userArea);
				}
			} else if (agentType == 1) {
				if (cityCode.contains(",")) {
					String[] cities = cityCode.split(",");
					for (String string : cities) {
						RelSysuserArea userArea = new RelSysuserArea();
						userArea.setAgentId(agentId);
						userArea.setCreateBy(userId);
						userArea.setProvinceCode(Integer.parseInt(provinceCode));
						userArea.setCityCode(Integer.parseInt(string));
						this.save(userArea);
					}
				}else{
					RelSysuserArea userArea = new RelSysuserArea();
					userArea.setAgentId(agentId);
					userArea.setCreateBy(userId);
					userArea.setProvinceCode(Integer.parseInt(provinceCode));
					userArea.setCityCode(Integer.parseInt(cityCode));
					this.save(userArea);
				}
			} else {
				if (areaCode.contains(",")) {
					String[] areas = areaCode.split(",");
					for (String string : areas) {
						RelSysuserArea userArea = new RelSysuserArea();
						userArea.setAgentId(agentId);
						userArea.setCreateBy(userId);
						userArea.setProvinceCode(Integer.parseInt(provinceCode));
						userArea.setCityCode(Integer.parseInt(cityCode));
						userArea.setAreaCode(Integer.parseInt(string));
						this.save(userArea);
					}
				}else{
					RelSysuserArea userArea = new RelSysuserArea();
					userArea.setAgentId(agentId);
					userArea.setCreateBy(userId);
					userArea.setProvinceCode(Integer.parseInt(provinceCode));
					userArea.setCityCode(Integer.parseInt(cityCode));
					userArea.setAreaCode(Integer.parseInt(areaCode));
					this.save(userArea);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void removeRelSysuserAreas(List<RelSysuserArea> list){
		try {
			if(list.size()>0){
				for (RelSysuserArea relSysuserArea : list) {
					relSysuserArea.setIsDeleted(1);
					this.update(relSysuserArea);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
