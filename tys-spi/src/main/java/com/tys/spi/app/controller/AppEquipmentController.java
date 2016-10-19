package com.tys.spi.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tys.base.BaseSpiRsp;
import com.tys.dto.spi.req.ReqDeviceMonitorDTO;
import com.tys.dto.spi.req.ReqLocationDTO;
import com.tys.dto.spi.req.ReqOnlineDTO;
import com.tys.dto.spi.req.ReqSettingClassModeDTO;
import com.tys.dto.spi.rsp.RspLocationDTO;
import com.tys.dto.spi.rsp.RspOnlineDTO;
import com.tys.entity.MdLocation;
import com.tys.entity.MdRtData;
import com.tys.entity.MdUser;
import com.tys.netty.dto.ResponseMessage;
import com.tys.netty.message.togsm.ReqMessage;
import com.tys.netty.utils.ChannelHolder;
import com.tys.service.MdLocationService;
import com.tys.service.MdRtDataService;
import com.tys.spi.app.service.UserHelpService;
import com.tys.util.MUtil;
import com.tys.util.constants.EnumConstants;
import com.tys.util.constants.EnumConstants.YesOrNo;
import com.tys.util.constants.ErrorCodeConstants;

@Controller
@RequestMapping("/spi/app")
public class AppEquipmentController {

	@Autowired
	private UserHelpService userHelpService;

	@Autowired
	private MdRtDataService mdRtDataService;
	@Autowired
	private MdLocationService mdLocationService;

	/**
	 * 在线查询
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "s/online", method = RequestMethod.POST)
	public @ResponseBody RspOnlineDTO queryOnline(@Valid @ModelAttribute ReqOnlineDTO reqDTO, Errors errors) {
		RspOnlineDTO result = new RspOnlineDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			MdRtData mdRtData = mdRtDataService.findByImei(reqDTO.getImei());
			if (null == mdRtData) {
				result.setOnline(YesOrNo.NO.getValue());
			} else {
				result.setOnline(mdRtData.getIsOnline());
			}
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 查询挂载服务器
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "s/queryServer", method = RequestMethod.POST)
	public @ResponseBody RspOnlineDTO queryServer(@Valid @ModelAttribute ReqOnlineDTO reqDTO, Errors errors) {
		RspOnlineDTO result = new RspOnlineDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			MdRtData mdRtData = mdRtDataService.findByImei(reqDTO.getImei());
			if (null == mdRtData) {
				result.setStatus(ErrorCodeConstants.ERROR_EQUIPMENT_NOT_EXISTS);
				return result;
			} else {
				result.setIp(mdRtData.getCurIp());
				result.setPort(mdRtData.getCurHttpPort());
				result.setOnline(mdRtData.getIsOnline());
			}
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;

	}

	/**
	 * 从数据库中查最新位置
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "s/locQuery", method = RequestMethod.POST)
	public @ResponseBody RspLocationDTO locQuery(@Valid @ModelAttribute ReqLocationDTO reqDTO, Errors errors) {
		RspLocationDTO result = new RspLocationDTO();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {

			MdLocation location = mdLocationService.findNewbyImei(reqDTO.getImei());
			if (location != null) {
				if (location.getMapType() == null || location.getMapType() != reqDTO.getMapType()) {
					// 向指定地图商纠偏经纬度
					if (EnumConstants.MapType.BAIDU.getValue() == reqDTO.getMapType()) {
						String coords = location.getLongitude() + "," + location.getLatitude();
						String ret = MUtil.getStringByUrl(
								"http://api.map.baidu.com/geoconv/v1/?from=1&to=5&ak=3E331673dbeb74663cfe2885edc4f24a&coords="
										+ coords);
						JSONObject jsoncoord = new JSONObject(ret);
						if (jsoncoord.getInt("status") == 0) {
							// 获取成功
							JSONArray arr = jsoncoord.getJSONArray("result");
							JSONObject json_xy = arr.getJSONObject(0);
							double x = json_xy.getDouble("x");
							double y = json_xy.getDouble("y");

							location.setMapType(EnumConstants.MapType.BAIDU.getValue());
							location.setJLatitude(y);
							location.setJLongitude(x);
							mdLocationService.update(location);

						} else {
							// 纠偏失败
						}
					}
				}

				if (location.getMapType() == reqDTO.getMapType()) {
					result.setLatitude(location.getJLatitude());
					result.setLongitude(location.getJLongitude());
					result.setMapType(location.getMapType());
					result.setUpload(location.getCreateTime().getTime());
				} else {
					result.setLatitude(location.getLatitude());
					result.setLongitude(location.getLongitude());
					result.setMapType(location.getPositionType());
					result.setUpload(location.getCreateTime().getTime());
				}

				result.setStatus(ErrorCodeConstants.SUCCESS);
			} else {
				// 未定位
				result.setStatus(ErrorCodeConstants.ERROR_NO_LOCATION);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;

	}

	/**
	 * 请求设备上传位置
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "s/deviceLocation", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp deviceLocation(HttpSession session, @Valid @ModelAttribute ReqLocationDTO reqDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {

			String imei = reqDTO.getImei();
			if (imei != null) {

				ReqMessage msg = new ReqMessage();
				msg.setTerminalNo(imei);
				msg.setCommand("D");
				msg.setMessage("");
				ResponseMessage rsp = ChannelHolder.sendMesage(imei, msg);
				if (rsp.isSuccess()) {
					// 处理成功
					result.setStatus(ErrorCodeConstants.SUCCESS);
				} else {
					// 无响应/超时/出错
					result.setStatus(ErrorCodeConstants.ERROR_DEVICE_RSP_ERROR);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

	/**
	 * 请求设备回拨监听
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "s/deviceMonitor", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp deviceMonitor(HttpSession session, @Valid @ModelAttribute ReqDeviceMonitorDTO reqDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			MdUser user = userHelpService.getMduser(session);

			String imei = reqDTO.getImei();
			if (imei != null) {

				if (user == null) {
					result.setStatus(ErrorCodeConstants.ERROR_MEMBER_NOT_EXISTS);
					return result;
				}

				ReqMessage msg = new ReqMessage();
				msg.setTerminalNo(imei);
				msg.setCommand("Y");
				msg.setMessage(user.getPhone());
				ResponseMessage rsp = ChannelHolder.sendMesage(imei, msg);
				if (rsp.isSuccess()) {
					// 处理成功
					result.setStatus(ErrorCodeConstants.SUCCESS);
				} else {
					// 无响应/超时/出错
					result.setStatus(ErrorCodeConstants.ERROR_DEVICE_RSP_ERROR);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}
	
	
	/**
	 * 老师设置上课模式
	 * 
	 * @param reqDTO
	 * @return
	 */
	@RequestMapping(value = "s/settingClassMode", method = RequestMethod.POST)
	public @ResponseBody BaseSpiRsp settingClassMode(HttpSession session, @Valid @ModelAttribute ReqSettingClassModeDTO reqDTO, Errors errors) {
		BaseSpiRsp result = new BaseSpiRsp();
		if(errors.hasErrors()) {
			result.setStatus(ErrorCodeConstants.ERROR_PARA);
            return result;
        }
		try {
			//TODO 待处理
			result.setStatus(ErrorCodeConstants.SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			result.setStatus(ErrorCodeConstants.ERROR);
		}
		return result;
	}

}
