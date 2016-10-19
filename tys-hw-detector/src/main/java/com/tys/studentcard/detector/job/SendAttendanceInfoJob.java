package com.tys.studentcard.detector.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.tys.entity.MdAttendance;
import com.tys.netty.util.DateUtil;
import com.tys.push.service.PushService;
import com.tys.service.MdAttendanceService;
import com.tys.util.MUtil;
import com.tys.util.custom.MPage;

public class SendAttendanceInfoJob {

	@Autowired
	private MdAttendanceService mdAttendanceService;
	
	@Autowired
	private PushService pushService;
	
	@Value("${attendance.max}")
	private int MAX_COUNT;

	public void work() {
		MUtil.log("SendAttendanceInfoJob work");
		//仅发3小时内的考勤
		Date startTime = DateUtil.getDateBeforeIHours(DateUtil.getNowTime(), 3);
		
		int page = 1;
		int pageCount = 0;
		do{
			MPage<MdAttendance> resultMap = mdAttendanceService.findPage4Job(page, MAX_COUNT, startTime);
			if (resultMap.getTotalCount() == 0) {
				return;
			}
			
			pageCount = resultMap.getPageCount();
			
			List<MdAttendance> list = resultMap.getResult();
			for(MdAttendance tmp : list){
				pushService.sendAttendanceMsg(tmp);
			}
			//TODO 批量推送
			MUtil.log("send Attendance count="+list.size());
			
			//更新这批设备发送状态
			mdAttendanceService.updateIsSend(page, MAX_COUNT, startTime, 1);
			
			//查下一页
			page++;
			
		} while(page <= pageCount);

		
	}
}
