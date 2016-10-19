package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqPushParamDTO;
import com.tys.entity.MdPushPara;
import com.tys.util.custom.BaseService;

/**
 * 推送参数服务类
 * @author Administrator
 *
 */
@Service
@Repository
public class MdPushParaService extends BaseService<MdPushPara> {
	
//	@Transactional
//	public int update(int accId, ReqPushParamDTO pushParam){
//		StringBuilder sql = new StringBuilder();
//		sql.append("update MdPushPara set bdChannelId='").append(pushParam.getChannelId()).append("'");
//		sql.append(",").append("platform="+pushParam.getDeviceType());
//		sql.append(",").append("companyId='"+pushParam.getCompanyId()).append("'");
//		sql.append(",").append("bdUserId='"+pushParam.getUserId()).append("'");
//		sql.append(" where userId = " + accId);
//		return this.executeUpdate(sql.toString());
//	}

	public MdPushPara findByAcctId(Integer acctId) {
		StringBuilder sql = new StringBuilder();
		sql.append("from MdPushPara where userId="+acctId);
		return this.findUnique(sql.toString());
	}

	public List<MdPushPara> findPushParaByStudentId(Integer studentId) {

		StringBuilder sql = new StringBuilder();
		sql.append("from MdPushPara where userId in(select b.userId from RelUserStudent b where b.studentId = ");
		sql.append(studentId).append(")");
		
		return this.findList(sql.toString());
		
	}

}
