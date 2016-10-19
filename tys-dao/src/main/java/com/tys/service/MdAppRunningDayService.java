/**
 * 
 */
package com.tys.service;

import java.util.Date;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.tys.entity.MdAppRunningDay;
import com.tys.util.MDateUtil;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdAppRunningDayService extends BaseService<MdAppRunningDay> {

	/**
	 * 查询是否连续签到7天，第8天后重复
	 * @param id
	 * @return 
	 */
	public boolean isRun7Days(Integer userId) {
		String sql = "from MdAppRunningDay where created_by="+userId+" order by createTime desc limit 6,1";//往前取第7条数据
		MdAppRunningDay tmp = findUnique(sql);
		if(tmp != null){
			int sp = MDateUtil.getDateSpace(tmp.getCreateTime(), new Date());
			//判断前第7次签到时间是否相差7天与当天是否奖励100积分
			if(sp == 7 && tmp.getPoints() == 100){
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断今天是否已签到
	 * @param userId
	 * @return
	 */
	public boolean isCheckInToday(Integer userId) {
		String sql = "from MdAppRunningDay where created_by="+userId+" order by createTime desc";
		MdAppRunningDay tmp = findUnique(sql);
		if(tmp != null){
			int sp = MDateUtil.getDateSpace(tmp.getCreateTime(), new Date());
			if(sp == 0){
				return true;
			}
		}
		return false;
	}
	
}
