/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.MdGrade;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.SearchVo;

/**
 * 
 * <pre>
 *
 *
 * 创建日期: 2016年3月20日
 * 修改人 :  liul
 * 修改说明: 
 * </pre>
 */
@Service
@Repository
public class MdGradeService extends BaseService<MdGrade> {

	/**
	 * 查询Grade列表
	 *
	 * @param model
	 * @param search
	 * @return
	 */
	public List<MdGrade> selectMdGradeListFromModel(SearchVo search) {
		try {
			StringBuilder builder = new StringBuilder("from MdGrade model");

			List<MdGrade> grades = findList(builder.toString());
			return grades;
		} catch (RuntimeException e) {
			throw e;
		}
	}

}
