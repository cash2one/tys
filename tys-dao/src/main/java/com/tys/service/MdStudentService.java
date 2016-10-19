package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.MdStudent;
import com.tys.util.MStrUtil;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;

@Service
@Repository
public class MdStudentService extends BaseService<MdStudent> {

	@Override
	protected String buildSqlFromSearchVo(SearchVo model) {
		StringBuilder builder = new StringBuilder(" model where model.isDeleted = 0 ");
		if (!MStrUtil.isNullOrEmpty(model.getS_name())) {
			builder.append(" and model.name like '%" + model.getS_name() + "%'");
			model.setS_name(null);
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_parentPhone())) {
			builder.append(" and model.id in (select studentId from RelUserStudent rus where rus.mdUser.phone like '%" + model.getS_parentPhone()
					+ "%')");
			model.setS_parentPhone(null);
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_parentName())) {
			builder.append(" and model.id in (select studentId from RelUserStudent rus where rus.mdUser.name like '%" + model.getS_parentName()
					+ "%')");
			model.setS_parentName(null);
		}
		builder.append(super.buildSqlFromSearchVo(model));
		return builder.toString();
	}

	@Transactional
	public MPage<MdStudent> findMdStudentPageByModel(SearchVo search, int page, int rows) {
		try {
			StringBuilder sqlStr = new StringBuilder("from MdStudent ");
			String wherecase = buildSqlFromSearchVo(search).toString();
			sqlStr.append(wherecase);
			sqlStr.append(" order by createTime desc");
			return findPageEx(sqlStr.toString(), page, rows, wherecase, MdStudent.class);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public long findCountByClassId(Integer classId) {
		String sql = "select count(t.id) from MdStudent t where t.classId="+classId;
		return findCount(sql);
	}
}
