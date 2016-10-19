/**
 * 
 */
package com.tys.service;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqJxzxListDTO;
import com.tys.dto.spi.rsp.RspJxzxListDTO;
import com.tys.entity.InfoAppNews;
import com.tys.util.MDateUtil;
import com.tys.util.constants.ErrorCodeConstants;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */	
@Service
@Repository
public class InfoAppNewsService extends BaseService<InfoAppNews> {

	
	public void findByCondition(RspJxzxListDTO result, ReqJxzxListDTO reqDTO) {
		String timeStart = null;
		String timeEnd = null;
		
		if(reqDTO.getTimeStart() != null){
			timeStart = MDateUtil.format(MDateUtil.DATETIME_PATTERN, reqDTO.getTimeStart());
		}
		
		if(reqDTO.getTimeEnd() != null){
			timeEnd = MDateUtil.format(MDateUtil.DATETIME_PATTERN, reqDTO.getTimeEnd());
		}
		

		StringBuilder sb = new StringBuilder();
		sb.append("select t from InfoAppNews t where t.isDeleted=0");
		
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
			sbtmp.append("select count(t.id) from InfoAppNews t where t.createTime>'").append(timeStart).append("'");
			count = findCount(sbtmp.toString());
			sb.append(" and t.createTime>'").append(timeStart).append("'");
		} else {
			//情况4
			//先查询数量
			StringBuilder sbtmp = new StringBuilder();
			sbtmp.append("select count(t.id) from InfoAppNews t where t.createTime>'").append(timeStart).append("'");
			sbtmp.append(" and t.createTime<'").append(timeEnd).append("'");
			count = findCount(sbtmp.toString());
			
			sb.append(" and t.createTime>'").append(timeStart).append("'");
			sb.append(" and t.createTime<'").append(timeEnd).append("'");
			
		}
		sb.append(" order by t.createTime desc limit 10");
		
		result.setList(findList(sb.toString(), InfoAppNews.class));
		if(count != null && result.getList() != null){
			result.setLeftCount((int) (count - result.getList().size()));
		}
		result.setStatus(ErrorCodeConstants.SUCCESS);
		
	}

	@Transactional
	public int deleteBySchoolId(Integer schoolId) {
		String sql = "update InfoAppNews set isDeleted=1 where schoolId="+schoolId;
		return executeUpdate(sql);
	}

	
	
	
	
}
