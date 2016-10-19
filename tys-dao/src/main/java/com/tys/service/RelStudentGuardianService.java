/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelStudentGuardian;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class RelStudentGuardianService extends BaseService<RelStudentGuardian> {

	public RelStudentGuardian findByStudentIdAndIndex(Integer studentId, int index) {
		String sql = "from RelStudentGuardian where phoneIndex=" + index + " and studentId=" + studentId;
		return findUnique(sql);
	}

	public List<RelStudentGuardian> findByStudentId(Integer studentId) {
		String sql = "from RelStudentGuardian where studentId=" + studentId;
		return findList(sql);
	}

	/**
	 * 删除学生id对应的所有亲情号码
	 * @param studentId
	 * @return
	 */
	@Transactional
	public int deleteByStudentId(Integer studentId) {
		String sql = "delete from RelStudentGuardian where studentId=" + studentId;
		return executeUpdate(sql);
	}

}
