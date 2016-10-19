/**
 * 
 */
package com.tys.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqMySettingHomeworkDTO;
import com.tys.entity.MdHomeWork;
import com.tys.entity.slim.HomeWork;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdHomeWorkService extends BaseService<MdHomeWork> {

	@Autowired
	private MdUserService mdUserService;
	
	public List<HomeWork> findByClassId(Integer classId, Long lastTime) {
		
		StringBuilder sql = new StringBuilder();
		sql.append(" from HomeWork a where 1=1");
		if(lastTime != null){
			sql.append(" and a.createTime > '").append(MDateUtil.format(MDateUtil.DATETIME_PATTERN, lastTime)).append("'");
		} else {
			//日期为空，查询所有未过期作业
			sql.append(" and a.completeTime >= '").append(MDateUtil.format(MDateUtil.ISO_EXPANDED_DATE_FORMAT, System.currentTimeMillis())).append("'");
		}
		
		sql.append(" and a.classId=").append(classId);

		
		List<HomeWork> result = this.findList(sql.toString(), HomeWork.class);
		
		return result;
	}

	public List<HomeWork> findByTeacherId(Integer acctId, ReqMySettingHomeworkDTO reqDTO) {
		
		StringBuilder sql = new StringBuilder();
		
		sql.append(" from HomeWork a ");
		sql.append("where a.teacherId=").append(acctId);
		
		if(reqDTO.getTimeStart() != null){
			sql.append(" and a.createTime>'");
			sql.append(MDateUtil.format(MDateUtil.DATETIME_PATTERN, reqDTO.getTimeStart())).append("' ");
		}
		
		if(reqDTO.getTimeEnd() != null){
			sql.append(" and a.createTime<'");
			sql.append(MDateUtil.format(MDateUtil.DATETIME_PATTERN, reqDTO.getTimeEnd())).append("'");
		}
		sql.append(" order by a.createTime desc");
		
		List<HomeWork> result = this.findList(sql.toString(), HomeWork.class);
		
		return result;
	}

	@Override
	protected String buildSqlFromSearchVo(SearchVo model) {
		StringBuilder builder = new StringBuilder(" model where model.isDeleted = 0 ");
		builder.append(super.buildSqlFromSearchVo(model));
		if (!MStrUtil.isNullOrEmpty(model.getS_name())) {
			builder.append(" and model.className = '" + model.getS_name() + "'");
		}
		return super.buildSqlFromSearchVo(model);
	}

	public MPage<MdHomeWork> findMdHomeWorkPageByModel(SearchVo search, int page, int rows) {
		try {
			StringBuilder sqlStr = new StringBuilder("from MdHomeWork ");
			String wherecase = buildSqlFromSearchVo(search).toString();
			sqlStr.append(wherecase);
			sqlStr.append(" order by id");
			return findPageEx(sqlStr.toString(), page, rows, wherecase, MdHomeWork.class);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	@Transactional
	public int deleteByClassId(Integer classId) {
		String sql = "update MdHomeWork set isDeleted=1 where classId="+classId;
		return executeUpdate(sql);
	}

}
