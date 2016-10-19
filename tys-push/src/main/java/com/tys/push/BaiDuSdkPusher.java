package com.tys.push;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.AddDevicesToTagRequest;
import com.baidu.yun.push.model.AddDevicesToTagResponse;
import com.baidu.yun.push.model.CreateTagRequest;
import com.baidu.yun.push.model.CreateTagResponse;
import com.baidu.yun.push.model.DeleteTagRequest;
import com.baidu.yun.push.model.DeleteTagResponse;
import com.baidu.yun.push.model.DeviceInfo;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.baidu.yun.push.model.PushMsgToTagRequest;
import com.baidu.yun.push.model.PushMsgToTagResponse;
import com.tys.entity.MdPushPara;
import com.tys.util.MUtil;

/**
 * 百度云推送REST API
 * 
 * @author Administrator
 *
 */
public class BaiDuSdkPusher {

	public static final int DEVICE_TYPE_ANDROID = 3;
	public static final int DEVICE_TYPE_IOS = 4;

	private static final int MSG_EXPIRES = 3600 * 24 * 7;// 消息/通知过期时间,7天

	// 太阳升
	// ANDROID
	private static final String bdApikeyTys = "D3qougO1m4wfyEbxoL1xr8Gv";
	private static final String bdSecKeyTys = "aOTXdT4pt5mA1uqRjAbyjauwUpow3VP0";
	private static final BaiduPushClient bdPushClientTys = new BaiduPushClient(
			new PushKeyPair(bdApikeyTys, bdSecKeyTys), BaiduPushConstants.CHANNEL_REST_URL);

	// IOS
	private static final String bdApikeyTysIos = "Q9UqXwRgCQndC7ya0d5KmvpT";
	private static final String bdSecKeyTysIos = "OwMzCNAIGkF3mo9kNylh7j5yOg1TsiCn";
	private static final BaiduPushClient bdPushClientIosTys = new BaiduPushClient(
			new PushKeyPair(bdApikeyTysIos, bdSecKeyTysIos), BaiduPushConstants.CHANNEL_REST_URL);

	/**
	 * @param companyId
	 *            自定义商家id
	 * @param deviceType
	 *            设备类型，
	 * @return
	 */
	public static BaiduPushClient getPushClient(String companyId, int deviceType) {
		if (deviceType == DEVICE_TYPE_ANDROID) {
			return bdPushClientTys;
		} else {
			return bdPushClientIosTys;
		}
	}


	/**
	 * 推送通知到单台IOS设备
	 * 
	 * @param pushClient
	 * @param channelId
	 * @param alert
	 * @param deployStatus
	 */
	public static boolean iosPushNotifySingle(BaiduPushClient pushClient, String channelId, String alert,
			int deployStatus) {
		try {

			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().addChannelId(channelId)
					.addMsgExpires(new Integer(MSG_EXPIRES)). // 设置message的有效时间
					addMessageType(1).addMessage(genNotifyIOS(alert)).addDeployStatus(2).addDeviceType(DEVICE_TYPE_IOS);

			// http request
			PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
			// Http请求结果解析打印
			MUtil.log("iosPushNotifySingle msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("iosPushNotifySingle requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 推送通知到TAG群组IOS设备
	 * 
	 * @param pushClient
	 * @param tagName
	 * @param alert
	 * @param deployStatus
	 */
	public static boolean iosPushNotifyTag(BaiduPushClient pushClient, String tagName, String alert,
			int deployStatus) {
		try {
			PushMsgToTagRequest request = new PushMsgToTagRequest().addTagName(tagName) // 设置tag组
					.addMsgExpires(new Integer(MSG_EXPIRES)).addMessageType(1).addMessage(genNotifyIOS(alert))
					.addDeployStatus(deployStatus).addDeviceType(DEVICE_TYPE_IOS);
			// http request
			PushMsgToTagResponse response = pushClient.pushMsgToTag(request);
			// Http请求结果解析打印
			MUtil.log("iosPushNotifyTag tagName:"+tagName+",msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("iosPushNotifyTag requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 推送通知到所有IOS设备
	 * 
	 * @param pushClient
	 * @param alert
	 * @param deployStatus
	 */
	public static boolean iosPushNotifyAll(BaiduPushClient pushClient, String alert, int deployStatus) {
		try {

			PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(MSG_EXPIRES))
					.addMessageType(1).addMessage(genNotifyIOS(alert))
					.addSendTime(System.currentTimeMillis() / 1000 + 120) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送
					.addDepolyStatus(deployStatus).addDeviceType(DEVICE_TYPE_IOS);

			// http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			// Http请求结果解析打印
			MUtil.log("iosPushNotifyAll msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("iosPushNotifyAll requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 推送消息到单台安卓设备
	 * 
	 * @param pushClient
	 * @param channelId
	 * @param json
	 *            消息使用json格式
	 */
	public static boolean androidPushMsgSingle(BaiduPushClient pushClient, String channelId, JSONObject json) {
		try {
			// 创建 Android的通知
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().addChannelId(channelId)
					.addMsgExpires(new Integer(MSG_EXPIRES)). // message有效时间
					addMessageType(0).// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
					addMessage(json.toString()).addDeviceType(DEVICE_TYPE_ANDROID);

			// http request
			PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
			// Http请求结果解析打印
			MUtil.log("androidPushMsgSingle msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("androidPushMsgSingle requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 推送消息到TAG群组Android设备
	 * 
	 * @param pushClient
	 * @param tagName
	 * @param json
	 * @return
	 */
	public static boolean androidPushMsgTag(BaiduPushClient pushClient, String tagName, JSONObject json) {
		try {
			PushMsgToTagRequest request = new PushMsgToTagRequest().addTagName(tagName)
					.addMsgExpires(new Integer(MSG_EXPIRES)).addMessageType(0).addMessage(json.toString())
					.addDeviceType(DEVICE_TYPE_ANDROID);
			// 5. http request
			PushMsgToTagResponse response = pushClient.pushMsgToTag(request);
			// Http请求结果解析打印
			MUtil.log("androidPushMsgTag tagName:"+tagName+",msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("androidPushMsgTag requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 推送消息到所有安卓设备
	 * 
	 * @param pushClient
	 * @param json
	 * @return
	 */
	public static boolean androidPushMsgAll(BaiduPushClient pushClient, JSONObject json) {
		try {
			PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(3600)).addMessageType(0)
					.addMessage(json.toString()) // 添加透传消息
					.addSendTime(System.currentTimeMillis() / 1000 + 60) // 设置定时推送时间，必需超过当前时间一分钟，单位秒
					.addDeviceType(DEVICE_TYPE_ANDROID);
			// http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);
			// Http请求结果解析打印
			MUtil.log("androidPushMsgAll msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime() + ",timerId: "
					+ response.getTimerId());
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("androidPushMsgAll requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 添加一个设备到指定tag群组
	 * 
	 * @param push
	 * @param tagName
	 * @return
	 */
	public static boolean addDeviceToTag(MdPushPara push, String tagName) {
		if (push == null)
			return false;

		try {
			AddDevicesToTagRequest request = new AddDevicesToTagRequest().addTagName(tagName)
					.addChannelIds(new String[] { push.getBdChannelId() }).addDeviceType(push.getPlatform());

			// http request
			AddDevicesToTagResponse response = getPushClient(push.getCompanyId(), push.getPlatform())
					.addDevicesToTag(request);

			// Http请求结果解析打印
			if (null != response) {

				StringBuilder strBuilder = new StringBuilder();
				strBuilder.append("addDeviceToTag devicesInTag：{");
				List<?> devicesInfo = response.getDevicesInfoAfterAdded();
				for (int i = 0; i < devicesInfo.size(); i++) {
					Object object = devicesInfo.get(i);
					if (i != 0) {
						strBuilder.append(",");
					}
					if (object instanceof DeviceInfo) {
						DeviceInfo deviceInfo = (DeviceInfo) object;
						strBuilder.append(
								"{channelId:" + deviceInfo.getChannelId() + ",result:" + deviceInfo.getResult() + "}");
					}
				}
				strBuilder.append("}");
				MUtil.log(strBuilder.toString());

				return true;
			}
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("addDeviceToTag requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 创建一个百度tag
	 * 
	 * @param tagName
	 * @return
	 */
	public static boolean createTag(String companyId, String tagName) {
		try {
			// 4. specify request arguments
			CreateTagRequest request = new CreateTagRequest().addTagName(tagName);
			// 5. http request
			// 安卓IOS通用
			CreateTagResponse response = getPushClient(companyId, DEVICE_TYPE_ANDROID).createTag(request);

			if (null != response) {
				MUtil.log(String.format("createTag tagName: %s, result: %d", response.getTagName(), response.getResult()));
				if (response.getResult() == 0)
					return true;
			}
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("createTag requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 删除一个百度tag
	 * @param companyId
	 * @param tagName
	 */
	public static boolean deleteTag(String companyId, String tagName) {
		try {
			// 4. specify request arguments
			DeleteTagRequest request = new DeleteTagRequest().addTagName(tagName);
			// 5. http request
			DeleteTagResponse response = getPushClient(companyId, DEVICE_TYPE_ANDROID).deleteTag(request);
			// Http请求结果解析打印
			MUtil.log(String.format("deleteTag tagName: %s, result: %d", response.getTagName(), response.getResult()));
			return true;
		} catch (PushClientException e) {
			e.printStackTrace();
		} catch (PushServerException e) {
			MUtil.log(String.format("deleteTag requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
					e.getErrorCode(), e.getErrorMsg()));
		}
		return false;
	}

	/**
	 * 生成Android推送通知参数
	 * 
	 * @param title
	 * @param description
	 * @return
	 */
	public static String genNotifyAndroid(String title, String description) {
		JSONObject json = new JSONObject();
		json.put("title", title);
		json.put("description", description);
		// 通知样式
		json.put("notification_builder_id", 0);
		// notification_builder_id为0时有效
		json.put("notification_basic_style", 7);

		// 用于打开URL
		// json.put("open_type", 1);
		// json.put("url", "http://developer.baidu.com");
		// json.put("user_confirm", 0);//打开前是否需要用户确认

		// 用于打开指定APP
		// json.put("open_type", 2);
		// json.put("pkg_content", "");
		// json.put("pkg_name", "com.testpush");
		// json.put("pkg_version", "0.1");

		// 添加自定义字段
		// JSONObject custom = new JSONObject();
		// custom.put("key1", "value1");
		// custom.put("key2", "value2");
		// json.put("custom_content", custom);

		return json.toString();
	}

	/**
	 * 生成IOS通知JSON数据
	 * 
	 * @param alert
	 * @return
	 */
	public static String genNotifyIOS(String alert) {
		JSONObject json = new JSONObject();
		JSONObject aps = new JSONObject();
		aps.put("alert", alert);
		json.put("aps", aps);
		// json.put("key1", "123");
		// json.put("key2", "345");
		return json.toString();
	}

	public static void main(String[] args) {
		String tagName = "tag_class_2";
		createTag("", tagName);
		deleteTag("", "pushdemo1");

		MdPushPara push = new MdPushPara();
		push.setBdChannelId("4235494397181300414");
		push.setBdUserId("685818297825153520");
		push.setPlatform(DEVICE_TYPE_ANDROID);
		addDeviceToTag(push, tagName);

	}

}
