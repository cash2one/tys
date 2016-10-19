package com.tys.studentcard.detector.processor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.tys.studentcard.detector.DetectorChannelHolder;
import com.tys.studentcard.detector.dto.InfoAttendanceDTO;
import com.tys.studentcard.detector.dto.StudentInfo;
import com.tys.studentcard.detector.event.AttendanceMessageEvent;
import com.tys.studentcard.detector.req.LeaveSchoolReq;
import com.tys.studentcard.detector.req.MessageReq;
import com.tys.studentcard.detector.res.LeaveSchoolRes;
import com.tys.util.MUtil;

import io.netty.channel.Channel;

@Service
public class LeaveSchoolProcessor implements IProcessor {

	@Autowired
	private ApplicationContext context;

	@Override
	public String getType() {
		return "09";
	}

	@Override
	public void dealWith(Channel channel, MessageReq messageReq) {
		LeaveSchoolReq leaveSchoolReq = (LeaveSchoolReq) messageReq;
		DetectorChannelHolder.put(leaveSchoolReq.getDeviceId(), channel);

		MUtil.log("Attendance Messag Request ===============Start");
		MUtil.log("Dev:" + leaveSchoolReq.getDeviceId() + " Len:" + leaveSchoolReq.getLength() + " Type:"
				+ leaveSchoolReq.getType() + " Sequence:" + leaveSchoolReq.getSequence() + " cardCount="+leaveSchoolReq.getStudentInfos().size());

		int pos = 1;
		final DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		for (StudentInfo studentInfo : leaveSchoolReq.getStudentInfos()) {
			MUtil.log("no:" + pos + "\tcard:" + studentInfo.getCardId() + "\toptType:" + (char)studentInfo.getOptType() + "\ttime:" + dateFormat.format(studentInfo.getStartTime()));
			pos++;
		}

		MUtil.log("Attendance Messag Request ===============End");

		//保存到数据库
		InfoAttendanceDTO dto = new InfoAttendanceDTO();
		dto.setDeviceId(leaveSchoolReq.getDeviceId());
		dto.setStudentInfoList(leaveSchoolReq.getStudentInfos());
		AttendanceMessageEvent event = new AttendanceMessageEvent();
		event.setReqDto(dto);
		context.publishEvent(event);

		//回复
		LeaveSchoolRes leaveSchoolRes = new LeaveSchoolRes();
		leaveSchoolRes.setType(getType());
		leaveSchoolRes.setSequence(leaveSchoolReq.getSequence());
		leaveSchoolRes.setValidFlag('1');
		channel.writeAndFlush(leaveSchoolRes);

	}
}
