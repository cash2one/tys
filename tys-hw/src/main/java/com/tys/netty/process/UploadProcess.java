package com.tys.netty.process;

import java.io.IOException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.tys.netty.dto.AddressInfo;
import com.tys.netty.dto.PositionMsgDTO;
import com.tys.netty.dto.ResponseMessage;
import com.tys.netty.dto.event.ClientMessageEvent;
import com.tys.netty.message.fromgsm.AbstractRecvGsmMessage;
import com.tys.netty.message.fromgsm.UploadReqMessage;
import com.tys.netty.util.DateUtil;
import com.tys.netty.util.HttpClientUtils;
import com.tys.netty.utils.ChannelHolder;
import com.tys.util.MStrUtil;
import com.tys.util.NumberUtil;

import io.netty.channel.Channel;

@Service
public class UploadProcess implements IProcess {
	
	
	
	@Override
	public String getType() {
		return "upload";
	}

	@Autowired
	private ApplicationContext context;

	@Override
	public void dealWith(Channel channel, AbstractRecvGsmMessage message, String msg) {
		UploadReqMessage uploadReqMessage = (UploadReqMessage) message;

		String cmd = uploadReqMessage.getCommand();

		if (MStrUtil.isNull(cmd)) {
			ChannelHolder.notify(uploadReqMessage.getTerminalNo(), "upload_tracker", null, null, "error");
			channel.writeAndFlush("ON;");
			return;
		}

		Integer positionType = null;// 定位模式，1：GPS，2：基站
		AddressInfo addressInfo = null;
		if (null != uploadReqMessage.getLatitude() && null != uploadReqMessage.getLongitude()) {
			try {
				addressInfo = HttpClientUtils.gpsToTencent(uploadReqMessage.getLatitude(),
						uploadReqMessage.getLongitude());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (null == addressInfo) {
			positionType = 2;
			String sCel = createPostionURL(uploadReqMessage);
			try {
				addressInfo = HttpClientUtils.fetchCell(sCel, "0");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			positionType = 1;
			uploadReqMessage.setGpsDate(String.valueOf(System.currentTimeMillis()));
			addressInfo.setDate(String.valueOf(System.currentTimeMillis()));
		}

		if (null != addressInfo) {
			try {
				addressInfo.setPositionType(positionType);
				addressInfo.setAddress(HttpClientUtils.accuratePositioning(addressInfo.getLat(), addressInfo.getLng()));
			} catch (IOException e) {
				e.printStackTrace();
			}

			// 保存轨迹
			processReturnMsg(uploadReqMessage, addressInfo);

			if ("tracker".equals(cmd)) {
				ChannelHolder.notify(uploadReqMessage.getTerminalNo(), "upload_tracker",
						uploadReqMessage.getParameters(), addressInfo, null);
				channel.writeAndFlush("ON;");
				return;
			} else if (cmd.matches("Q\\d\\d")) {
				ChannelHolder.notify(uploadReqMessage.getTerminalNo(), "Q01_tracker", uploadReqMessage.getParameters(),
						addressInfo, null);
				channel.writeAndFlush("ON;");
				return;
			}

			// 回复数据
			String rsp2GsmMsg = createResponseMsg(uploadReqMessage, cmd, addressInfo);
			channel.writeAndFlush(rsp2GsmMsg);
		}

	}

	private void processReturnMsg(UploadReqMessage uploadReqMessage, AddressInfo addressInfo) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setImeiNo(uploadReqMessage.getTerminalNo());
		responseMessage.setCommond(uploadReqMessage.getCommand());
		responseMessage.setSuccess(true);
		responseMessage.setParameters(uploadReqMessage.getParameters());

		PositionMsgDTO dto = new PositionMsgDTO();
		dto.setImeiNo(uploadReqMessage.getTerminalNo());
		dto.setGpsStatus(uploadReqMessage.getGpsStatus());
		dto.setBattery(uploadReqMessage.getBattery());
		dto.setLatitude(uploadReqMessage.getLatitude());
		dto.setLongitude(uploadReqMessage.getLongitude());
		dto.setTerminalStatus(uploadReqMessage.getTerminalStatus());
		dto.setGsmSignal(""+ uploadReqMessage.getGsmSignal());
		dto.setParsedLatitude(addressInfo.getLat());
		dto.setParseLongitude(addressInfo.getLng());
		if (!"AGPS".equalsIgnoreCase(uploadReqMessage.getCommand())) {
			dto.setAlarmType(uploadReqMessage.getCommand());
			dto.setAlarmDate(DateUtil.valueOf(uploadReqMessage.getGpsDate(), DateUtil.DATETIME_PATTERN));
		}

		String gpsDate = uploadReqMessage.getGpsDate();
		try {
			gpsDate = DateUtil.dateToString(new Date(Long.valueOf(gpsDate)), DateUtil.DATETIME_PATTERN);
		} catch (Exception e) {
			gpsDate = null;
		}

		if (MStrUtil.isNull(gpsDate)) {
			gpsDate = DateUtil.dateToString(new Date(), DateUtil.DATETIME_PATTERN);
		}
		dto.setPositionTime(gpsDate);
		dto.setPositionType(addressInfo.getPositionType());
		dto.setAddress(addressInfo.getAddress());

		if (!NumberUtil.equals(Double.valueOf(0), dto.getLatitude())
				&& !NumberUtil.equals(Double.valueOf(0), dto.getLongitude())
				&& !"AGPS".equalsIgnoreCase(uploadReqMessage.getCommand())) {
			context.publishEvent(new ClientMessageEvent(responseMessage, dto));
		}
	}

	private String createPostionURL(UploadReqMessage uploadReqMessage) {
		String mnc = uploadReqMessage.getMnc();
		String mcc = uploadReqMessage.getMcc();
		Integer lac1 = null;
		
		if (MStrUtil.isNotNull(uploadReqMessage.getLac1())) {
			lac1 = Integer.parseInt(uploadReqMessage.getLac1(), 16);
		}
		Integer cellId1 = null;
		if (MStrUtil.isNotNull(uploadReqMessage.getCellId1())) {
			cellId1 = Integer.parseInt(uploadReqMessage.getCellId1(), 16);
		}
		Integer sign1 = 100;
		if (MStrUtil.isNotNull(uploadReqMessage.getSign1())) {
			sign1 = Integer.parseInt(uploadReqMessage.getSign1(), 16);
		}

		String cel1 = null;
		if (lac1 != null && cellId1 != null && sign1 != null) {
			cel1 = "CELL0=" + mcc + "*" + mnc + "*" + cellId1 + "*" + lac1 + "*" + sign1;
		}

		Integer lac2 = null;
		if (MStrUtil.isNotNull(uploadReqMessage.getLac2())) {
			lac2 = Integer.parseInt(uploadReqMessage.getLac2(), 16);
		}
		Integer cellId2 = null;
		if (MStrUtil.isNotNull(uploadReqMessage.getCellId2())) {
			cellId2 = Integer.parseInt(uploadReqMessage.getCellId2(), 16);
		}

		Integer sign2 = 100;
		if (MStrUtil.isNotNull(uploadReqMessage.getSign2())) {
			sign2 = Integer.parseInt(uploadReqMessage.getSign2(), 16);
		}

		String cel2 = null;
		if (lac2 != null && cellId2 != null && sign2 != null) {
			if (MStrUtil.isNull(cel1)) {
				cel2 = "CELL0=" + mcc + "*" + mnc + "*" + cellId2 + "*" + lac2 + "*" + sign2;
			} else {
				cel2 = "&CELL1=" + mcc + "*" + mnc + "*" + cellId2 + "*" + lac2 + "*" + sign2;
			}
		}

		Integer lac3 = null;
		if (MStrUtil.isNotNull(uploadReqMessage.getLac3())) {
			lac3 = Integer.parseInt(uploadReqMessage.getLac3(), 16);
		}
		Integer cellId3 = null;
		if (MStrUtil.isNotNull(uploadReqMessage.getCellId3())) {
			cellId3 = Integer.parseInt(uploadReqMessage.getCellId3(), 16);
		}

		Integer sign3 = 100;
		if (MStrUtil.isNotNull(uploadReqMessage.getSign3())) {
			sign3 = Integer.parseInt(uploadReqMessage.getSign3(), 16);
		}

		String cel3 = null;
		if (lac3 != null && cellId3 != null && sign3 != null) {
			if (MStrUtil.isNull(cel1) && MStrUtil.isNull(cel2)) {
				cel3 = "CELL0=" + mcc + "*" + mnc + "*" + cellId3 + "*" + lac3 + "*" + sign3;
			} else if (MStrUtil.isNotNull(cel1) && MStrUtil.isNotNull(cel2)) {
				cel3 = "&CELL2=" + mcc + "*" + mnc + "*" + cellId3 + "*" + lac3 + "*" + sign3;
			} else {
				cel3 = "&CELL1=" + mcc + "*" + mnc + "*" + cellId3 + "*" + lac3 + "*" + sign3;
			}
		}

		StringBuilder cel = new StringBuilder();
		if (MStrUtil.isNotNull(cel1)) {
			cel.append(cel1);
		}
		if (MStrUtil.isNotNull(cel2)) {
			cel.append(cel2);
		}
		if (MStrUtil.isNotNull(cel3)) {
			cel.append(cel3);
		}

		String sCel = cel.toString();
		return sCel;
	}

	private String createResponseMsg(UploadReqMessage uploadReqMessage, String tracker, AddressInfo addressInfo) {
		StringBuilder responseMsg = new StringBuilder();
		if ("AGPS".equalsIgnoreCase(tracker)) {
			String mes = "ii,limei:" + uploadReqMessage.getTerminalNo() + ",AGPS,"
					+ addressInfo.getLat() + "," + addressInfo.getLng() + ",OK;";
			responseMsg.append(mes);
		} else {
			String address = addressInfo.getAddress();
			responseMsg.append("tt," + tracker + ",");

			StringBuilder lastMsg = new StringBuilder();
			if ("SMS".equalsIgnoreCase(tracker)) {
				lastMsg.append("短信查询,");
			} else if ("SOS".equalsIgnoreCase(tracker)) {
				lastMsg.append("请注意！有紧急求助,");
			} else if ("lowbattery".equalsIgnoreCase(tracker)) {
				lastMsg.append("电量过低！请及时充电,");
			} else if ("outfence1".equalsIgnoreCase(tracker)) {
				lastMsg.append("请注意！离开一号围栏,");
			} else if ("outfence2".equalsIgnoreCase(tracker)) {
				lastMsg.append("请注意！离开二号围栏,");
			} else if ("outfence3".equalsIgnoreCase(tracker)) {
				lastMsg.append("请注意！离开三号围栏,");
			} else if ("infence1".equalsIgnoreCase(tracker)) {
				lastMsg.append("请注意！进入一号围栏,");
			} else if ("infence2".equalsIgnoreCase(tracker)) {
				lastMsg.append("请注意！进入二号围栏,");
			}
			String gpsStatus = uploadReqMessage.getGpsStatus();
			if ("A".equals(gpsStatus)) {
				lastMsg.append("GPS定位,");
			} else {
				lastMsg.append("基站定位,");
			}

			lastMsg.append("时间:" + uploadReqMessage.getGpsDate() + ",");

			if (null != uploadReqMessage.getBattery()) {
				lastMsg.append("电量:" + uploadReqMessage.getBattery() + "%,");
			}

			if (MStrUtil.isNotNull(address)) {
				lastMsg.append("地址:" + address);
			}
			responseMsg.append(stringToHexString(lastMsg.toString()).toUpperCase() + ";");
		}
		return responseMsg.toString();
	}

	public static String stringToHexString(String strPart) {
		String hexString = "";
		for (int i = 0; i < strPart.length(); i++) {
			int ch = (int) strPart.charAt(i);
			String strHex = Integer.toHexString(ch);
			while (strHex.length() < 4) {
				strHex = "0" + strHex;
	        }
			hexString = hexString + strHex;
		}
		return hexString;
	}
	
}
