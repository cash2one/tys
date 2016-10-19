package com.tys.studentcard.detector.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tys.entity.MdAttendance;
import com.tys.entity.MdDetector;
import com.tys.netty.util.DateUtil;
import com.tys.netty.util.StringUtil;
import com.tys.service.MdAttendanceService;
import com.tys.service.MdDetectorService;
import com.tys.studentcard.detector.dto.InfoAttendanceDTO;
import com.tys.studentcard.detector.dto.StudentInfo;
import com.tys.studentcard.detector.event.AttendanceMessageEvent;

@Service
public class InfoAttendanceServiceImpl implements ApplicationListener<AttendanceMessageEvent> {

    protected final static Logger logger = LoggerFactory.getLogger(InfoAttendanceServiceImpl.class);


    @Autowired
    private MdDetectorService mdDetectorService;
    @Autowired
    private MdAttendanceService mdAttendanceService;

    @Override
    public void onApplicationEvent(AttendanceMessageEvent event) {
        InfoAttendanceDTO dto = (InfoAttendanceDTO) event.getReqDto();
        String deviceId = dto.getDeviceId();

        List<StudentInfo> infos = dto.getStudentInfoList();

        if (infos == null) {
            return;
        }
        
        MdDetector detector = mdDetectorService.findByDeviceNo(deviceId);
        String imeiNoHeader = null;
        if(detector == null){
        	return;
        }
        
        imeiNoHeader = detector.getHead();
        
        for (StudentInfo studentInfo : infos) {
            MdAttendance entity = new MdAttendance();
            entity.setDeviceId(detector.getId());
            
            Character cOptType = (char) studentInfo.getOptType();
            entity.setOptType(Integer.valueOf(cOptType.toString()));
            entity.setCreateTime(studentInfo.getStartTime());

            try {
                String imeiNoLast = studentInfo.getCardId();
                if (StringUtil.isNotEmpty(imeiNoLast)) {
                    Long parsedImei = Long.parseLong(imeiNoLast, 16);
                    imeiNoLast = String.valueOf(parsedImei);
                    int len = imeiNoLast.length();
                    if (len > 9) {
                        imeiNoLast = imeiNoLast.substring(len - 9);
                    } else if (len < 8) {
                        imeiNoLast = String.format("%09d", parsedImei);
                    }
                }
                
                if (StringUtil.isNotEmpty(imeiNoHeader) && StringUtil.isNotEmpty(imeiNoLast)) {
                    StringBuilder sb = new StringBuilder(imeiNoHeader);
                    sb.append(imeiNoLast);
                    entity.setImei(sb.toString());
                }
                entity.setCreateTime(DateUtil.getNowTime());
                entity.setIsSend(0);
                mdAttendanceService.save(entity);
                
        		
            } catch (Exception e) {
                logger.error("保存探头上传的考勤信息失败！,DeviceId = " + StringUtil.isNotEmpty(imeiNoHeader));
                e.printStackTrace();
            }
        }
        
        // 数据库写完后，由定时器定时推送考勤到app
        
        

    }




}
