/**
 * 
 */
package com.tys.spi.app.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.base.BaseSpiRsp;
import com.tys.dto.spi.req.ReqAddChildrenDTO;
import com.tys.dto.spi.req.ReqBindDTO;
import com.tys.dto.spi.req.ReqSettingStudentDTO;
import com.tys.dto.spi.rsp.RspAddChildrenDTO;
import com.tys.dto.spi.rsp.RspBindDTO;
import com.tys.dto.spi.rsp.RspChildListDTO;
import com.tys.entity.MdEquipment;
import com.tys.entity.MdStudent;
import com.tys.entity.RelStudentEquipment;
import com.tys.entity.RelStudentGuardian;
import com.tys.entity.RelUserStudent;
import com.tys.service.MdEquipmentService;
import com.tys.service.MdStudentService;
import com.tys.service.RelStudentEquipmentService;
import com.tys.service.RelStudentGuardianService;
import com.tys.service.RelUserStudentService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.MDateUtil;
import com.tys.util.MStrUtil;
import com.tys.util.constants.EnumConstants;
import com.tys.util.constants.ErrorCodeConstants;

/**
 * @author Administrator
 *
 */
/**
 * 学生相关接口
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/spi/app")
public class AppStudentController {

	@Autowired
	private RelUserStudentService relUserStudentService;

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private MdStudentService mdStudentService;

	@Autowired
	private RelStudentGuardianService relStudentGuardianService;

	@Autowired
	private RelStudentEquipmentService relStudentEquipmentService;

	@Autowired
	private MdEquipmentService mdEquipmentService;

	/**
	 * 查询学生列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "s/childList", method = RequestMethod.POST)
	public @ResponseBody RspChildListDTO childList(HttpSession session) {
		RspChildListDTO result = new RspChildListDTO();
		Integer accountId = userHelpService.getAcctId(session);
		if (null == accountId) {
			result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
			return result;
		}

		try {
			result.setChildList(relUserStudentService.findStudentList(accountId));
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	@RequestMapping(value = "/s/bind", method = RequestMethod.POST)
	public @ResponseBody RspBindDTO bind(@Valid @ModelAttribute ReqBindDTO dto, Errors errors) {
		RspBindDTO result = new RspBindDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }

		MdEquipment mdEquipment = null;
		if (MStrUtil.isNotNull(dto.getImei())) {
			mdEquipment = mdEquipmentService.findByImei(dto.getImei());
		}

		if (null == mdEquipment) {
			result.setStatus(ErrorCodeConstants.ERROR_EQUIPMENT_NOT_EXISTS);
			return result;
		}

		try {
			//判断关系是否存在
			RelStudentEquipment relStudentEquipment = relStudentEquipmentService.findByStudentIdType(dto.getChildId(), EnumConstants.DeviceType.STUDENT_CARD.getValue());
			if(relStudentEquipment == null){
				relStudentEquipment = new RelStudentEquipment();
				relStudentEquipment.setStudentId(dto.getChildId());
				relStudentEquipment.setEquipmentId(mdEquipment.getId());
				relStudentEquipmentService.save(relStudentEquipment);
				result.setImei(mdEquipment.getImei());
				result.setStatus(ErrorCodeConstants.SUCCESS);
			} else {
				result.setStatus(ErrorCodeConstants.ERROR_DEVICE_ALREADY_BIND);
			}

			// TODO 推送
			
			
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 设置学生信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "s/settingChildren", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp settingChildren(@Valid @ModelAttribute ReqSettingStudentDTO dto, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		
		try {
			MdStudent student = mdStudentService.findById(dto.getChildId());
			if(null == student){
				return result;
			}
			
			
			if(null != dto.getSex())
				student.setSex(dto.getSex());
			if(null != dto.getClassId())
				student.setName(dto.getName());
			if(null != dto.getClassId())
				student.setClassId(dto.getClassId());
			if (null != dto.getBirthday()) {
				student.setBirthday(MDateUtil.parseDateStr(MDateUtil.ISO_EXPANDED_DATE_FORMAT, dto.getBirthday()));
			}
			mdStudentService.update(student);

			saveRelStudentGuardian(0, dto.getPhone0(), 1, dto.getChildId());
			saveRelStudentGuardian(1, dto.getPhone1(), 0, dto.getChildId());
			saveRelStudentGuardian(2, dto.getPhone2(), 0, dto.getChildId());
			saveRelStudentGuardian(3, dto.getPhone3(), 0, dto.getChildId());
			saveRelStudentGuardian(4, dto.getPhone4(), 0, dto.getChildId());
			saveRelStudentGuardian(5, dto.getPhone5(), 0, dto.getChildId());
			saveRelStudentGuardian(6, dto.getPhone6(), 0, dto.getChildId());
			saveRelStudentGuardian(7, dto.getPhone7(), 0, dto.getChildId());
			saveRelStudentGuardian(8, dto.getPhone8(), 0, dto.getChildId());
			saveRelStudentGuardian(9, dto.getPhone9(), 0, dto.getChildId());

			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	
	
	/**
	 * 添加学生
	 * 
	 * @return
	 */
	@Deprecated
	@RequestMapping(value = "s/addChildren", method = RequestMethod.POST)
	public @ResponseBody RspAddChildrenDTO addChildren(HttpSession session, @Valid @ModelAttribute ReqAddChildrenDTO dto, Errors errors) {
		RspAddChildrenDTO result = new RspAddChildrenDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			Integer accountId = userHelpService.getAcctId(session);
			if (null == accountId) {
				result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
				return result;
			}
			
			MdStudent student = new MdStudent();
			student.setSex(dto.getSex());
			student.setName(dto.getName());
			//TODO 校验班级是否存在
			student.setClassId(dto.getClassId());
			if (null != dto.getBirthday()) {
				student.setBirthday(new Date(dto.getBirthday()));
			}
			mdStudentService.save(student);
			
			//添加家长与学生关系
			RelUserStudent rel = new RelUserStudent();
			rel.setStudentId(student.getId());
			rel.setUserId(accountId);
			rel.setCreateBy(accountId);
			relUserStudentService.save(rel);
			
			result.setId(student.getId());
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	
	
	

	private void saveRelStudentGuardian(int index, String phone, Integer isMain, Integer studentId) {
		if (MStrUtil.isNotNull(phone) && studentId != null) {
			RelStudentGuardian rsg = relStudentGuardianService.findByStudentIdAndIndex(studentId, index);
			if(rsg == null){
				rsg = new RelStudentGuardian();
				rsg.setStudentId(studentId);
				rsg.setPhone(phone);
				rsg.setPhoneIndex(index);
				this.relStudentGuardianService.save(rsg);
			} else {
				rsg.setPhone(phone);
				this.relStudentGuardianService.update(rsg);
			}
		}
	}

}
