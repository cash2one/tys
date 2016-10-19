/**
 * 
 */
package com.tys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tys.dto.spi.req.ReqAttendanceHisDTO;
import com.tys.dto.spi.req.ReqClassAttendanceDTO;
import com.tys.dto.spi.rsp.RspClassAttendanceDTO;
import com.tys.dto.spi.rsp.RspClassAttendanceInnerDTO;
import com.tys.entity.MdAttendance;
import com.tys.entity.RelStudentEquipment;
import com.tys.entity.slim.Attendance;
import com.tys.entity.slim.MClass;
import com.tys.util.MDateUtil;
import com.tys.util.constants.EnumConstants;
import com.tys.util.constants.ErrorCodeConstants;
import com.tys.util.custom.BaseService;
import com.tys.util.custom.MPage;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class MdAttendanceService extends BaseService<MdAttendance> {
	
	
	@Autowired
	private RelStudentEquipmentService relStudentEquipmentService;
	
	

	public MPage<MdAttendance> findPage4Job(int page, int rows, Date createTime) {
		String wherecase = "t where t.isSend=0 and t.createTime>'"
				+ MDateUtil.formatDate(MDateUtil.DATETIME_PATTERN, createTime) + "'";
		String sql = "select t from MdAttendance " + wherecase + " order by t.id";
		return findPageEx(sql, page, rows, wherecase);
	}

	@Transactional
	public void updateIsSend(int page, int rows, Date createTime, int isSend) {
		int start = (page - 1) * rows;
		String sql = "update MdAttendance set isSend=" + isSend
				+ " where isSend=0 and createTime>'"
				+ MDateUtil.formatDate(MDateUtil.DATETIME_PATTERN, createTime) + "' limit " + start + "," + rows
				+ " order by id";
		executeUpdate(sql);
	}

	public List<Attendance> findByImei(String imei) {
		String sql = "from Attendance t where t.imei='" + imei + "' order by t.time";
		return findList(sql, Attendance.class);
	}
	
	@Transactional
	public List<Attendance> findByCondition(ReqAttendanceHisDTO reqDTO) {
		RelStudentEquipment rel = relStudentEquipmentService.findByStudentIdType(reqDTO.getChildId(), EnumConstants.DeviceType.STUDENT_CARD.getValue());
		if(rel == null || rel.getMdEquipment() == null)
			return new ArrayList<Attendance>();
		
		String imei = rel.getMdEquipment().getImei();
		
		Integer days = -7;
		Long date = System.currentTimeMillis();
		if (reqDTO.getDate() != null) {
			date = reqDTO.getDate();
		}
		if (reqDTO.getDays() != null) {
			days = 0 - reqDTO.getDays();
		}

		String start = MDateUtil.format(MDateUtil.DATETIME_PATTERN,
				MDateUtil.addDay(new Date(date), days).getTime());
		String end = MDateUtil.format(MDateUtil.DATETIME_PATTERN, MDateUtil.addDay(new Date(date), 1).getTime());
		String sql = "from Attendance t where t.imei='" + imei + "' and t.time>='" + start + "' and t.time<'" + end
				+ "' order by t.time desc";

		return findList(sql, Attendance.class);
	}

	@Transactional
	public List<Attendance> findFormRelStudentEquipment(Integer childId) {
		RelStudentEquipment rel = relStudentEquipmentService.findByStudentIdType(childId, EnumConstants.DeviceType.STUDENT_CARD.getValue());
		if(rel != null && rel.getMdEquipment() != null){
			return findByImei(rel.getMdEquipment().getImei());
		}
		return null;
	}

	@Transactional
	public void statisticsClassAttendance(RspClassAttendanceDTO result, Integer acctId, ReqClassAttendanceDTO reqDTO) {
		//查出老师做班主任的班级
		StringBuffer classSb = new StringBuffer();
		classSb.append("select t from MClass t where t.adminId=").append(acctId);
		List<MClass> classList = findList(classSb.toString(), MClass.class);
		
		if(classList.size() > 0){
			List<RspClassAttendanceInnerDTO> list = new ArrayList<RspClassAttendanceInnerDTO>();
			//查出学生列表
			for(MClass classEntity : classList){
				RspClassAttendanceInnerDTO att = new RspClassAttendanceInnerDTO();
				//学生总数
				long stuCount = findCount("select count(t.id) from MdStudent t where t.classId="+classEntity.getClassId());
				//走读生数量
				long esCount = findCount("select count(t.id) from MdStudent t where t.type=0 and t.classId="+classEntity.getClassId());
				
				//绑定硬件的学生列表,仅查走读生type=0
				StringBuffer sb = new StringBuffer();
				sb.append("select r.mdEquipment.imei from RelStudentEquipment r where r.studentId in (");
				sb.append("select t.id from MdStudent t where t.type=0 and t.classId=").append(classEntity.getClassId()).append(")");
				List<String> equList = findList(sb.toString(), String.class);
				
				//已出勤人数，当前判断条件为，只要该学生有至少一次进出校记录，就认为已出勤
				long attCount = 0;
				if(equList.size() > 0){
					String start = MDateUtil.format(MDateUtil.ISO_EXPANDED_DATE_FORMAT,new Date(reqDTO.getDate()).getTime());
					String end = MDateUtil.format(MDateUtil.ISO_EXPANDED_DATE_FORMAT, MDateUtil.addDay(new Date(reqDTO.getDate()), 1).getTime());
					StringBuffer attsb = new StringBuffer();
					attsb.append("select count(t.id) from MdAttendance t where t.createTime>='").append(start).append("'");
					attsb.append(" and t.createTime<'").append(end).append("'");
					String tmp = equList.toString();
					attsb.append(" and t.imei in(").append(tmp.subSequence(1, tmp.length()-1)).append(")");
					attsb.append(" group by t.imei");
					attCount = findCount(attsb.toString());
				}
				
				att.setClassId(classEntity.getClassId());
				att.setClassName(classEntity.getClassName());
				att.setGradeId(classEntity.getGrade().getId());
				att.setTotal((int) stuCount);
				att.setExternalStudent((int) esCount);
				att.setBindCount(equList.size());
				att.setAttendance((int) attCount);
				att.setDateTime(reqDTO.getDate());
				list.add(att);
			}
			result.setList(list);
			result.setStatus(ErrorCodeConstants.SUCCESS);
			
		} else {
			result.setStatus(ErrorCodeConstants.ERROR_TEACHER_NO_CLASS_ADMIN);
		}
	}

}
