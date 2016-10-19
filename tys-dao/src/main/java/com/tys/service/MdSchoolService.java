/**
 * 
 */
package com.tys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.dto.spi.req.ReqSchoolDTO;
import com.tys.entity.MdSchool;
import com.tys.entity.slim.School;
import com.tys.entity.slim.School2;
import com.tys.util.MCollectionUtils;
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
public class MdSchoolService extends BaseService<MdSchool> {

	public List<School> findByCondtion(ReqSchoolDTO schoolDto) {

		StringBuilder hql = new StringBuilder("from MdSchool a where a.isDeleted = 0 ");

		Integer cityCode = schoolDto.getCityCode();
		if (null != cityCode) {
			hql.append(" and a.dCity.code = " + cityCode);
		}

		Integer areaCode = schoolDto.getAreaCode();
		if (null != areaCode) {
			hql.append(" and a.dArea.code = " + areaCode);
		}

		String name = schoolDto.getName();
		if (MStrUtil.isNotNull(name)) {
			hql.append(" and a.name like '%" + name + "%'");
		}

		Integer classId = schoolDto.getClassId();
		if (null != classId) {
			hql.append(" and exists (select 1 from MdClass b where b.schoolId = a.id and b.id = " + classId + ")");
		}

		Double latitude = schoolDto.getLatitude();
		Double longitude = schoolDto.getLongitude();
		if (null != latitude && null != longitude) {
			// TODO 这里要细化
			hql.append(" and abs(a.latitude - " + latitude + ") < 0.045");
			hql.append(" and abs(a.latitude - " + longitude + ") < 0.049");
		}

		List<MdSchool> list = this.findList(hql.toString());
		// 寻找优化方法，直接查询到School实体出来
		List<School> listSchool = new ArrayList<School>();
		for (MdSchool tmp : list) {
			School s = new School();
			BeanUtils.copyProperties(tmp, s);
			s.setSchoolId(tmp.getId());
			listSchool.add(s);
		}
		return listSchool;
	}

	@Override
	protected String buildSqlFromSearchVo(SearchVo model) {
		StringBuilder builder = new StringBuilder(" model where model.isDeleted = 0 ");
		if (!MStrUtil.isNullOrEmpty(model.getS_name())) {
			builder.append(" and model.name like '%" + model.getS_name() + "%'");
			model.setS_name(null);
		}
		if (!MStrUtil.isNullOrEmpty(model.getS_schoolAdminId())) {
			builder.append(" and model.adminId = '" + model.getS_schoolAdminId() + "'");
			model.setS_schoolAdminId(null);
		}
		builder.append(super.buildSqlFromSearchVo(model));
		return builder.toString();
	}

	/**
	 * 查询
	 *
	 * @param model
	 * @param search
	 * @return
	 */
	public List<MdSchool> selectMdSchoolListFromModel(SearchVo search) {
		try {
			StringBuilder builder = new StringBuilder("from MdSchool ");
			builder.append(buildSqlFromSearchVo(search));
			builder.append(" order by createTime desc");

			List<MdSchool> schools = findList(builder.toString());
			System.out.println("ddddddddddddddd" + schools != null ? schools.size() : 0);
			return schools != null ? schools : new ArrayList<MdSchool>();
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public MPage<MdSchool> findSchoolPageByModel(SearchVo search, int page, int rows) {
		try {
			StringBuilder sqlStr = new StringBuilder("from MdSchool ");
			String wherecase = buildSqlFromSearchVo(search).toString();
			sqlStr.append(wherecase);
			sqlStr.append(" order by createTime desc");
			return findPageEx(sqlStr.toString(), page, rows, wherecase, MdSchool.class);
		} catch (RuntimeException e) {
			throw e;
		}
	}

	public List<School2> getSchoolListByCity(int pro, int city, int area) {
		String sql = "from School2 s where 1=1";
		sql += " and s.provinceId = " + pro;
		sql += " and s.cityId = " + city;
		sql += " and s.areaId = " + area;
		return findList(sql, School2.class);
	}

	public List<MdSchool> findByAreaCode(Integer areaCode) {
		String sql = "from MdSchool a where a.isDeleted = 0 and a.areaId = " + areaCode;
		return this.findList(sql);
	}

	public List<Integer> findShoolIdsByAdminId(Integer adminId) {
		List<Integer> schoolIds = new ArrayList<Integer>();
		if (null == adminId) {
			return schoolIds;
		}
		String sql = "select a.id from MdSchool a where a.isDeleted = 0 and a.adminId = " + adminId;
		List<MdSchool> result = this.findList(sql);

		if (MCollectionUtils.isNotEmpty(result)) {
			for (Integer id : schoolIds) {
				schoolIds.add(id);
			}
		}
		return schoolIds;
	}

	public Map<Integer, String> findMapByIds(Set<Integer> ids) {
		Map<Integer, String> resultMap = new HashMap<Integer, String>();
		if (MCollectionUtils.isEmpty(ids)) {
			return resultMap;
		}
		StringBuilder sql = new StringBuilder("from MdSchool a where a.isDeleted = 0 and a.id in( ");
		Integer pos = 0;
		for (Integer id : ids) {
			if (pos == ids.size() - 1) {
				sql.append(id);
			} else {
				sql.append(id + ",");
			}
			pos++;
		}
		sql.append(")");
		List<MdSchool> result = this.findList(sql.toString());

		if (MCollectionUtils.isNotEmpty(result)) {
			for (MdSchool entity : result) {
				resultMap.put(entity.getId(), entity.getName());
			}
		}
		return resultMap;
	}

	public MdSchool getByCondition(MdSchool condition) {
		StringBuilder hql = new StringBuilder("from MdSchool a where a.isDeleted = 0 ");

		Integer provinceId = condition.getProvinceId();
		if (null != provinceId) {
			hql.append(" and a.provinceId = " + provinceId);
		}

		Integer cityId = condition.getCityId();
		if (null != cityId) {
			hql.append(" and a.cityId = " + cityId);
		}

		Integer areaId = condition.getAreaId();
		if (null != areaId) {
			hql.append(" and a.areaId = " + areaId);
		}

		String name = condition.getName();
		if (MStrUtil.isNotNull(name)) {
			hql.append(" and a.name like '%" + name + "%'");
		}

		List<MdSchool> list = this.findList(hql.toString());
		if (MCollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

}
