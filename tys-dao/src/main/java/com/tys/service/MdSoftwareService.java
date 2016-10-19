package com.tys.service;

import org.springframework.stereotype.Service;

import com.tys.entity.MdSoftware;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;
@Service
public class MdSoftwareService extends BaseService<MdSoftware>{

	public MdSoftware findNewVer(Integer type, String companyId) {
		String sql = "select t from MdSoftware t where t.type="+type + " and t.companyId='"+companyId+"' order by t.version desc";
		return findUnique(sql);
	}


	public MPage<MdSoftware> findSoftwareList(int mPageNo, int mPageSize, String name, int type){
		String whereCase = " t where t.isDeleted = 0";
		if (!"".equals(name) && name != null) {
			whereCase += " and t.alias like '%" + name + "%'";
		}
		if (type != -1) {
			whereCase += " and t.type = " + type;
		}
		String sql = " from MdSoftware"+whereCase;
		return findPageEx(sql, mPageNo, mPageSize, whereCase);
	}
}
