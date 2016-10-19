package com.tys.studentcard.detector.dto;

import java.util.Date;
import java.util.List;

import com.tys.base.BaseDTO;

public class InfoAttendanceDTO extends BaseDTO {

    private static final long serialVersionUID = 6699869351131322650L;

    /***/
    private String imeiNo;

    /***/
    private String deviceId;

    /***/
    private String studentId;

    /***/
    private Date startTime;

    /***/
    private Integer optType;

    private List<StudentInfo> studentInfoList;

    private Date attendanceStartTime;

    private Date attendanceEndTime;

    private Integer isSend;

    private Integer sendCount;

    public String getImeiNo() {
        return imeiNo;
    }

    public void setImeiNo(String imeiNo) {
        this.imeiNo = imeiNo;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getOptType() {
        return optType;
    }

    public void setOptType(Integer optType) {
        this.optType = optType;
    }

    public Date getAttendanceStartTime() {
        return attendanceStartTime;
    }

    public void setAttendanceStartTime(Date attendanceStartTime) {
        this.attendanceStartTime = attendanceStartTime;
    }

    public Date getAttendanceEndTime() {
        return attendanceEndTime;
    }

    public void setAttendanceEndTime(Date attendanceEndTime) {
        this.attendanceEndTime = attendanceEndTime;
    }

    public List<StudentInfo> getStudentInfoList() {
        return studentInfoList;
    }

    public void setStudentInfoList(List<StudentInfo> studentInfoList) {
        this.studentInfoList = studentInfoList;
    }

    public Integer getIsSend() {
        return isSend;
    }

    public void setIsSend(Integer isSend) {
        this.isSend = isSend;
    }

    public Integer getSendCount() {
        return sendCount;
    }

    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

}
