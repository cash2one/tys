/**
 * 
 */
package com.tys.service;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.entity.RelQuestionFile;
import com.tys.entity.slim.QuestionFile;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class RelQuestionFileService extends BaseService<RelQuestionFile> {

	
	
	@Transactional
	public int deleteByQuestionId(Integer questionId) {
		// 先把附件找出来删除文件
//		String sql = "select t.fileId from RelQuestionFile t where t.questionId="+questionId;
//		List<Integer> idList = findList(sql,Integer.class);
		//TODO 这里完成了资源管理后补充
		
		return executeUpdate("delete from RelQuestionFile where questionId=" + questionId);
	}

	public List<QuestionFile> findByQuestionId(Integer questionId) {
		String sql = "select t.questionFile from RelQuestionFile t where t.questionId="+questionId;
		List<QuestionFile> idList = findList(sql, QuestionFile.class);
		
		return idList;
	}

}
