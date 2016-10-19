package com.tys.netty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import com.tys.entity.MdLocation;
import com.tys.netty.constant.Dict.CommondType;
import com.tys.netty.dto.PositionMsgDTO;
import com.tys.netty.dto.ResponseMessage;
import com.tys.netty.dto.event.ClientMessageEvent;
import com.tys.netty.util.DateUtil;
import com.tys.service.MdLocationService;

@Service
public class ProcessMessageServiceImpl implements ApplicationListener<ClientMessageEvent> {

	@Autowired
	private MdLocationService MdLocationService;

	@Override
	public void onApplicationEvent(ClientMessageEvent clientMessageEvent) {
		try {
			ResponseMessage responseMessage = (ResponseMessage) clientMessageEvent.getResponseMessage();
			String commond = responseMessage.getCommond();
			if (responseMessage.isSuccess()) {
				if (CommondType.P.getValue().equals(commond)) {
					// TODO 电子围栏保存
				} else if (CommondType.T.getValue().equals(commond)) {
					// TODO 亲情号码保存
				} else if ("M".equals(commond)) {
					// TODO 上课模式保存
				} else if ("E".equals(commond)) {
					// TODO 设置定时回报
				} else if ("C".equals(commond)) {
					// TODO 设置连续上传
				} else if ("I".equals(commond)) {
					// TODO 设置时区
				} else if ("K".equals(commond)) {
					// TODO 开关GPS
				} else if ("tracker,lowbattery,outfence1,outfence2,outfence3,infence1,infence2,SMS,SOS,AGPS,"
						.contains(commond + ",") || commond.matches("Q\\d\\d")) {// 匹配Q01,Q02等指令
					// TODO 保存位置
					PositionMsgDTO dto = (PositionMsgDTO) clientMessageEvent.getReqDto();

					MdLocation location = new MdLocation();
					if (dto.getLatitude() != null)
						location.setLatitude(dto.getLatitude());
					else
						location.setLatitude(dto.getParsedLatitude());

					if (dto.getLongitude() != null)
						location.setLongitude(dto.getLongitude());
					else
						location.setLongitude(dto.getParsedLongitude());

					if (null == location.getLatitude() && null == location.getLongitude()) {
						location.setLatitude(location.getJLatitude());
						location.setLongitude(location.getJLongitude());
					}

					location.setPositionType(dto.getPositionType());
					location.setImei(dto.getImeiNo());
					location.setCreateTime(DateUtil.valueOf(dto.getPositionTime(), DateUtil.DATETIME_PATTERN));
					MdLocationService.save(location);

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
