package com.tys.studentcard.detector.req;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.tys.studentcard.detector.dto.StudentInfo;

import io.netty.buffer.ByteBuf;

public class LeaveSchoolReq extends MessageReq {

    private String deviceId;

    private String cardNum;

    private List<StudentInfo> studentInfos;


    @Override
    protected boolean read0(ByteBuf byteBuf, int length) {
        try {
            deviceId = new String(byteBuf.readBytes(15).array());
            DecimalFormat df = new DecimalFormat(TWO_STR_FORMAT);

            cardNum = new String(byteBuf.readBytes(2).array());
            int cardNumValue = df.parse(cardNum).intValue();
            studentInfos = new ArrayList<StudentInfo>();
            final DateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
            for (int i = 0; i < cardNumValue; i++) {
                StudentInfo studentInfo = new StudentInfo();
                studentInfo.setCardId(new String(byteBuf.readBytes(8).array()));
                studentInfo.setStartTime(dateFormat.parse(new String(byteBuf.readBytes(14).array())));
                studentInfo.setOptType(byteBuf.readByte());
                studentInfos.add(studentInfo);
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
		return true;
    }


    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public List<StudentInfo> getStudentInfos() {
        return studentInfos;
    }

    public void setStudentInfos(List<StudentInfo> studentInfos) {
        this.studentInfos = studentInfos;
    }
}
