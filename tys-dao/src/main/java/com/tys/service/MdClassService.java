package com.tys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.dto.spi.req.ReqClassDTO;
import com.tys.entity.MdClass;
import com.tys.entity.slim.MClass;
import com.tys.util.MStrUtil;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;
import com.tys.util.custom.SearchVo;

@Service
@Repository
public class MdClassService extends BaseService<MdClass> {

	@Autowired
	private RelTecherClassService relTecherClassService;

	@Override
	protected String buildSqlFromSearchVo(SearchVo model) {
		StringBuilder builder = new StringBuilder(" model where model.isDeleted = 0 ");
		if (!MStrUtil.isNullOrEmpty(model.getS_name())) {
			builder.append(" and model.className like '%" + model.getS_name() + "%'");
		}
		builder.append(super.buildSqlFromSearchVo(model));
		return builder.toString();
	}

	public MPage<MdClass> findMdClassPageByModel(SearchVo search, int page, int rows) {
		try {
			StringBuilder sqlStr = new StringBuilder("from MdClass ");
			String wherecase = buildSqlFromSearchVo(search).toString();
			sqlStr.append(wherecase);
			sqlStr.append(" order by createTime desc");
			return findPageEx(sqlStr.toString(), page, rows, wherecase, MdClass.class);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	/**
	 * 查询
	 *
	 * @param model
	 * @param search
	 * @return
	 */
	public List<MdClass> selectMdSchoolListFromModel(SearchVo search) {
		try {
			StringBuilder builder = new StringBuilder("from MdClass ");
			builder.append(buildSqlFromSearchVo(search));
			builder.append(" order by createTime desc");

			List<MdClass> classList = findList(builder.toString());
			return classList;
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<MClass> findByCondtion(ReqClassDTO condition) {

		Integer schoolId = condition.getSchoolId();
		if (null != schoolId) {
			StringBuilder sql = new StringBuilder(" from MClass a where a.isDeleted = 0 ");
			sql.append(" and a.schoolId = " + schoolId);
			return this.findList(sql.toString(), MClass.class);
		}

		Integer teacherId = condition.getTeacherId();
		if (null != teacherId) {
			StringBuilder sql = new StringBuilder(" from MClass a where a.isDeleted = 0 ");
			sql.append(" and a.classId in(select b.classId from RelTecherClass b where b.techerId=").append(teacherId).append(")");
			return this.findList(sql.toString(), MClass.class);
		}

		Integer studentId = condition.getStudentId();
		if (null != studentId) {
			StringBuilder sql = new StringBuilder(" from MClass a where a.isDeleted = 0 ");
			sql.append(" and a.classId in(select b.classId from RelTecherClass b where b.techerId=").append(teacherId).append(")");
			return this.findList(sql.toString(), MClass.class);
		}

		return new ArrayList<MClass>();
	}

	public String findTagById(Integer id) {
		String sql = "select t.pushTag from MdClass t where t.id=" + id;
		return findUnique(sql, String.class);
	}

	public long findCountBySchoolId(Integer schoolId) {
		String sql = "select count(t.id) from MdClass t where t.isDeleted=0 and t.schoolId="+schoolId;
		return findCount(sql);
	}
}
