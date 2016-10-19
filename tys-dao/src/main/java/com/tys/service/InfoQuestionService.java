/**
 * 
 */
package com.tys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqCommitQuestionDTO;
import com.tys.dto.spi.req.ReqQuestionListDTO;
import com.tys.dto.spi.rsp.RspQuestionListDTO;
import com.tys.entity.InfoQuestion;
import com.tys.entity.RelQuestionFile;
import com.tys.entity.slim.Question;
import com.tys.entity.slim.QuestionUpdate;
import com.tys.util.MDateUtil;
import com.tys.util.constants.ErrorCodeConstants;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class InfoQuestionService extends BaseService<InfoQuestion> {

	@Autowired
	private RelQuestionFileService relQuestionFileService;
	
	
	public void findByCondition(RspQuestionListDTO result, ReqQuestionListDTO dto) {
		String timeStart = null;
		String timeEnd = null;
		
		if(dto.getTimeStart() != null){
			timeStart = MDateUtil.format(MDateUtil.DATETIME_PATTERN, dto.getTimeStart());
		}
		
		if(dto.getTimeEnd() != null){
			timeEnd = MDateUtil.format(MDateUtil.DATETIME_PATTERN, dto.getTimeEnd());
		}
		

		StringBuilder sb = new StringBuilder();
		sb.append("select t from Question t where t.isDeleted=0");
		
		if(dto.getUserId() != null){
			//查询指定用户的问题
			sb.append(" and t.createBy=").append(dto.getUserId());
		}
		
		
		if(dto.getCityCode() != null){
			//查询指定城市的问题
			sb.append(" and t.cityCode=").append(dto.getCityCode());
		}
		
		
		//时间分4种情况
//		1.	首次加载，timeStart，timeEnd均为空，返回最新10条
//		2.	加载历史问题，仅timeStart为空，
//		3.	加载最新问题，仅timeEnd为空
//		4.	加载剩余新问题，timeStart，timeEnd均不为空，此场景出现在3.加载最新问题时，数量过多，无法一次性加载完，后台只返回最新的10~20条与剩余的新问题数量
		
		Long count = null;
		if(timeStart == null && timeEnd == null){
			//情况1，不需要额外条件
		} else if(timeStart == null && timeEnd != null){
			//情况2
			sb.append(" and t.createTime<'").append(timeEnd).append("'");
		} else if(timeStart != null && timeEnd == null){
			//情况3
			//先查询数量
			StringBuilder sbtmp = new StringBuilder();
			sbtmp.append("select count(t.id) from Question t where t.createTime>'").append(timeStart).append("'");
			count = findCount(sbtmp.toString());
			sb.append(" and t.createTime>'").append(timeStart).append("'");
		} else {
			//情况4
			//先查询数量
			StringBuilder sbtmp = new StringBuilder();
			sbtmp.append("select count(t.id) from Question t where t.createTime>'").append(timeStart).append("'");
			sbtmp.append(" and t.createTime<'").append(timeEnd).append("'");
			count = findCount(sbtmp.toString());
			
			sb.append(" and t.createTime>'").append(timeStart).append("'");
			sb.append(" and t.createTime<'").append(timeEnd).append("'");
			
		}
		sb.append(" order by t.createTime desc limit 10");
		
		result.setList(findList(sb.toString(), Question.class));
		if(count != null && result.getList() != null){
			result.setLeftCount((int) (count - result.getList().size()));
		}
		result.setStatus(ErrorCodeConstants.SUCCESS);
		
		
		//查询是否有更新数据
		
		if(dto.getLastUpdate() == null){
			return;
		}
		
		String updateTime = MDateUtil.format(MDateUtil.DATETIME_PATTERN, dto.getLastUpdate());
		
		StringBuilder sbupdate = new StringBuilder();
		sbupdate.append("select t from QuestionUpdate t where ");
		sbupdate.append("t.updateTime>'").append(updateTime).append("'");
		
		result.setUpdateList(findList(sbupdate.toString(), QuestionUpdate.class));
		
	}

	
	@Transactional
	public int createOrUpdate(Integer userId, ReqCommitQuestionDTO dto) {
		InfoQuestion question = null;
		if(dto.getQuestionId() != null){
			question = findById(dto.getQuestionId());
		}
		
		if(question == null){
			question = new InfoQuestion();
			question.setCreateBy(userId);
		}
		
		question.setTitle(dto.getTitle());
		question.setContent(dto.getContent());
		question.setPoints(dto.getPoints());
		question.setGradeId(dto.getGradeId());
		question.setCourseId(dto.getCourseId());
		update(question);
		
		String fileIds = dto.getFileIds();
		if(fileIds != null){
			String[] tmp = fileIds.split(",");
			for(String fileId : tmp){
				//创建问题与附件关系
				RelQuestionFile rel = new RelQuestionFile();
				rel.setQuestionId(question.getId());
				rel.setFileId(Integer.parseInt(fileId));
				relQuestionFileService.save(rel);
			}
		}
		
		return ErrorCodeConstants.SUCCESS;
		
	}
	
	
	
	
	
	
	
}
