package com.tys.push.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.tys.entity.InfoPushMsg;
import com.tys.entity.MdAttendance;
import com.tys.entity.MdClass;
import com.tys.entity.MdHomeWork;
import com.tys.entity.MdPushPara;
import com.tys.entity.RelPushUser;
import com.tys.push.BaiDuSdkPusher;
import com.tys.service.InfoPushMsgService;
import com.tys.service.MdPushParaService;
import com.tys.service.RelPushUserService;
import com.tys.service.RelStudentEquipmentService;
import com.tys.util.MDateUtil;
import com.tys.util.MUtil;

@Service
public class PushService {

	@Autowired
	private RelStudentEquipmentService relStudentEquipmentService;

	@Autowired
	private RelPushUserService relPushUserService;

	@Autowired
	private MdPushParaService mdPushParaService;
	@Autowired
	private InfoPushMsgService infoPushMsgService;

	/**
	 * 新作业发送通知
	 * 
	 * @param homework
	 */
	public void sendNewHomeWorkMsg(MdHomeWork homework) {

		MdClass mdClass = homework.getMdClass();
		if (mdClass == null) {
			return;
		}

		String tagName = mdClass.getPushTag();

		JSONObject json = new JSONObject();
		json.put("id", homework.getId());
		json.put("type", 100);
		json.put("pushTime", homework.getCreateTime().getTime());
		JSONObject extraData = new JSONObject();
		extraData.put("title", "新作业通知");
		extraData.put("content", "您的孩子有一条新作业   " + homework.getWorkName());

		json.put("extraData", extraData);

		// 执行群组推送
		pushTag("tys_1.0", tagName, json);

	}

	/**
	 * 考勤通知
	 * 
	 * @param homework
	 */
	public void sendAttendanceMsg(MdAttendance attendance) {

		// 找到指定用户的推送参数

		JSONObject json = new JSONObject();
		json.put("id", attendance.getId());
		json.put("type", 100);
		json.put("pushTime", attendance.getCreateTime().getTime());
		JSONObject extraData = new JSONObject();
		extraData.put("title", "新考勤信息");

		String content = MDateUtil.format(MDateUtil.YYYYMMDD_HHMM_DATA_FORMATE, attendance.getCreateTime().getTime());
		switch (attendance.getOptType()) {
		case 0:
			content += " 进入学校";
			break;
		case 1:
			content += " 离开学校";
			break;
		case 2:
			content += " 进入学校，学生卡电量已不足";
			break;
		case 3:
			content += " 离开学校，学生卡电量已不足";
			break;
		default:
			content += " 考勤更新";
			break;
		}
		extraData.put("content", "您的孩子于 " + content);

		json.put("extraData", extraData);

		// 查询学生id
		Integer studentId = relStudentEquipmentService.findStuendtIdByImei(attendance.getImei());

		// 找到关联的家长
		List<MdPushPara> list = mdPushParaService.findPushParaByStudentId(studentId);
		for (MdPushPara tmp : list) {
			//TODO 待优化
			pushSignal(tmp, json);
		}
	}

	/**
	 * 执行推送，安卓推送消息，IOS推送通知
	 * 
	 * @param push
	 * @param content
	 * @param userPhone1
	 * @return
	 */
	private boolean pushSignal(MdPushPara pushPara, JSONObject json) {
		// 保存到推送历史
		InfoPushMsg msg = new InfoPushMsg();
		msg.setType(json.getIntValue("type"));
		msg.setUserId(pushPara.getUserId());
		msg.setExtraData(json.getJSONObject("extraData").toJSONString());
		infoPushMsgService.save(msg);

		if (pushPara.getPlatform() == BaiDuSdkPusher.DEVICE_TYPE_IOS) {
			String title = json.getJSONObject("extraData").getString("title");
			String content = json.getJSONObject("extraData").getString("content");
			return BaiDuSdkPusher.iosPushNotifySingle(
					BaiDuSdkPusher.getPushClient(pushPara.getCompanyId(), BaiDuSdkPusher.DEVICE_TYPE_IOS),
					pushPara.getBdChannelId(), title + ":" + content, 0);
		} else {
			return BaiDuSdkPusher.androidPushMsgSingle(
					BaiDuSdkPusher.getPushClient(pushPara.getCompanyId(), BaiDuSdkPusher.DEVICE_TYPE_ANDROID),
					pushPara.getBdChannelId(), json);
		}
	}
	
	
	
	/**
	 * 把用户添加到百度tag群组中
	 * @param tagName
	 * @param userId
	 */
	public void addUserToTag(String tagName, int userId) {
		
		MdPushPara pushPara = mdPushParaService.findByAcctId(userId);
		if(pushPara == null)
			return;//用户未上传过百度推送参数
		
		if(BaiDuSdkPusher.addDeviceToTag(pushPara, tagName)){
			//添加到数据库
			RelPushUser rel = new RelPushUser();
			rel.setTagName(tagName);
			rel.setUserId(userId);
			relPushUserService.save(rel);
		}
	}
	
	
	
	/**
	 * 创建一个百度群组tag
	 * @param tagName
	 */
	public void createTag(String tagName){
		boolean ret = BaiDuSdkPusher.createTag("tys_1.0", tagName);
		if(ret){
		}
	}
	
	
	/**
	 * 删除一个百度群组tag
	 * @param tagName
	 */
	public void deleteTag(String tagName){
		boolean ret = BaiDuSdkPusher.deleteTag("tys_1.0", tagName);
		if(ret){
			//把群组下的所有关系删除
			int count = relPushUserService.deleteByTagName(tagName);
			MUtil.log("delete rel_push_user count="+count);
		}
	}
	
	

	/**
	 * 执行群组推送，安卓推送消息，IOS推送通知
	 * 
	 * @param companyId
	 * @param tagName
	 * @param deployStatus
	 * @param json
	 * @return
	 */
	private void pushTag(String companyId, String tagName, JSONObject json) {
		// 保存到推送历史
		List<Integer> list = relPushUserService.findUserIdByTagName(tagName);
		int type = json.getIntValue("type");
		JSONObject extraData = json.getJSONObject("extraData");
		String extraDataStr = extraData.toJSONString();
		for (Integer userId : list) {
			if (userId != null) {
				InfoPushMsg msg = new InfoPushMsg();
				msg.setType(type);
				msg.setUserId(userId);
				msg.setExtraData(extraDataStr);
				infoPushMsgService.save(msg);
			}
		}

		String title = extraData.getString("title");
		String content = extraData.getString("content");
		BaiDuSdkPusher.iosPushNotifyTag(BaiDuSdkPusher.getPushClient(companyId, BaiDuSdkPusher.DEVICE_TYPE_IOS),
				tagName, title + ":" + content, 0);
		BaiDuSdkPusher.androidPushMsgTag(BaiDuSdkPusher.getPushClient(companyId, BaiDuSdkPusher.DEVICE_TYPE_ANDROID),
				tagName, json);
	}

	

}
