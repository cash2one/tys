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

import com.tys.dto.spi.rsp.RspChildListInnerDTO;
import com.tys.entity.MdClass;
import com.tys.entity.MdEquipment;
import com.tys.entity.MdSchool;
import com.tys.entity.MdStudent;
import com.tys.entity.RelStudentEquipment;
import com.tys.entity.RelUserStudent;
import com.tys.util.MCollectionUtils;
import com.tys.util.constants.EnumConstants;
import com.tys.util.custom.BaseService;

/**
 * @author Administrator
 *
 */
@Service
@Repository
public class RelUserStudentService extends BaseService<RelUserStudent> {

	@Autowired
	private RelStudentEquipmentService relStudentEquipmentService;

	public boolean isUserNoChild(Integer userId) {
		String sql = "select count(userId) from RelUserStudent where userId = " + userId;
		Long count = findCount(sql);
		if (count == null || count == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询用户下所有学生列表
	 * 
	 * @param condition
	 * @return
	 */
	@Transactional
	public List<RspChildListInnerDTO> findStudentList(Integer userId) {
		String hql = " from RelUserStudent where userId = " + userId;
		List<RelUserStudent> result = this.findList(hql);
		List<RspChildListInnerDTO> dtoList = new ArrayList<RspChildListInnerDTO>();
		if (MCollectionUtils.isNotEmpty(result)) {
			for (RelUserStudent entity : result) {
				RspChildListInnerDTO dto = new RspChildListInnerDTO();
				dto.setId(entity.getStudentId());

				MdStudent student = entity.getMdStudent();
				if (null != student) {
					dto.setName(student.getName());
					dto.setSex(student.getSex());
					dto.setClassId(student.getClassId());
					Date birthday = student.getBirthday();
					if (null != birthday) {
						dto.setBirthday(birthday);
					}

					MdClass mdClass = student.getMdClass();
					if (null != mdClass) {
						dto.setClassName(mdClass.getClassName());
						dto.setSchoolId(mdClass.getSchoolId());
						MdSchool mdSchool = mdClass.getMdSchool();
						if (null != mdSchool) {
							dto.setSchoolName(mdSchool.getName());
						}
					}

					// 查询绑定设备
					RelStudentEquipment relStudentEquipment = relStudentEquipmentService.findByStudentIdType(student.getId(),
							EnumConstants.DeviceType.STUDENT_CARD.getValue());
					if (relStudentEquipment != null) {
						MdEquipment equipment = relStudentEquipment.getMdEquipment();
						if (equipment != null) {
							dto.setImei(equipment.getImei());
						}
					}
				}
				dtoList.add(dto);
			}
		}
		return dtoList;
	}

	public RelUserStudent findByStuentIdAndPhone(Integer studentId, String phone) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from RelUserStudent t where t.isDeleted=0 and t.mdUser.phone='").append(phone).append("'");
		sb.append(" and t.studentId=").append(studentId);
		return findUnique(sb.toString());
	}

	public List<RelUserStudent> findByStuentId(Integer studentId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select t from RelUserStudent t where t.isDeleted=0 ");
		sb.append(" and t.studentId=").append(studentId);
		return findList(sb.toString());
	}
	
	
	public Long findCountByStuentId(Integer studentId){
		String sql = "select count(t.id) from RelUserStudent t where t.isDeleted=0 and t.studentId="+studentId;
		return findCount(sql);
	}
	
	
}
