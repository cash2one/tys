/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelTecherClass;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class RelTecherClassService extends BaseService<RelTecherClass> {

	public boolean isTeachClass(Integer techerId, Integer classId) {
		String sql = "select count(id) from RelTecherClass where techerId=" + techerId + " and classId=" + classId;
		Long count = findCount(sql);
		if (count == null || count == 0) {
			return false;
		} else {
			return true;
		}
	}

	public RelTecherClass getTeachClass(Integer techerId, Integer classId) {
		String sql = "from RelTecherClass where techerId=" + techerId + " and classId=" + classId;
		return findUnique(sql);
	}

	public List<RelTecherClass> getRelTecherClassByTeacherIds(String teacherIds) {
		String sqlString = "from RelTecherClass where techerId in(" + teacherIds + ")";
		return findList(sqlString);
	}

	public List<RelTecherClass> getRelTecherClassByclassId(int classId) {
		String sqlString = "from RelTecherClass where classId=" + classId;
		return findList(sqlString);
	}

	@Transactional
	public int deleteByClassId(Integer classId) {
		String sql = "update RelTecherClass set isDeleted=1 where classId=" + classId;
		return executeUpdate(sql);
	}

	@Transactional
	public int deleteByTeacherId(Integer techerId) {
		String sql = "update RelTecherClass set isDeleted=1 where techerId=" + techerId;
		return executeUpdate(sql);
	}

}
