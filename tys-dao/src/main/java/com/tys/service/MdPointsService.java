package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.MdPoint;
import com.tys.util.MDateUtil;
import com.tys.util.custom.BaseService;

@Service
@Repository
public class MdPointsService extends BaseService<MdPoint> {

	public List<MdPoint> findByAcctId(Integer acctId) {
		String sql = "select t from MdPoint t where t.createBy=" + acctId + " and t.createTime>'" + 
				MDateUtil.format(MDateUtil.YYYYMMDD_HHMM_DATA_FORMATE, System.currentTimeMillis() - 3600*24*7*1000)+"'";//仅查7天内的记录
		return findList(sql);
	}


}