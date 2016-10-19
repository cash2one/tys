package com.tys.studentcard.detector.processor;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tys.service.MdDetectorService;
import com.tys.studentcard.detector.DetectorChannelHolder;
import com.tys.studentcard.detector.req.AuthenticateReq;
import com.tys.studentcard.detector.req.MessageReq;
import com.tys.studentcard.detector.res.AuthenticateRes;
import com.tys.util.MUtil;

import io.netty.channel.Channel;

@Service
public class AuthenticateProcessor implements IProcessor {

	@Autowired
	private MdDetectorService mdDetectorService;

	@Override
	public String getType() {
		return "10";
	}

	@Override
	public void dealWith(Channel channel, MessageReq messageReq) {
		AuthenticateReq authenticateReq = (AuthenticateReq) messageReq;

		String deviceId = authenticateReq.getDeviceId();
		DetectorChannelHolder.put(deviceId, channel);

		char validFlag = '2';
		if (mdDetectorService.isExistsByDeviceId(deviceId)) {
			validFlag = '1';
		}

		MUtil.log("AuthenticateReq Mask:" + authenticateReq.getMask() + " Length:" + authenticateReq.getLength()
				+ " Type:" + authenticateReq.getType() + " Sequence:" + authenticateReq.getSequence() + " DeviceId:"
				+ authenticateReq.getDeviceId() + " Imeino:" + authenticateReq.getImei());

		AuthenticateRes authenticateRes = new AuthenticateRes();
		authenticateRes.setSequence(messageReq.getSequence());
		authenticateRes.setType(getType());
		authenticateRes.setValidFlag(validFlag);
		authenticateRes.setNowTime(new Date());
		channel.writeAndFlush(authenticateRes);

		MUtil.log("AuthenticateRsp Sequence:" + authenticateRes.getSequence() + " Type:"
				+ authenticateRes.getType() + " ValidFlag:" + authenticateRes.getValidFlag());

	}
}
