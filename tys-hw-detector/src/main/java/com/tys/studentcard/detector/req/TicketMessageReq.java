package com.tys.studentcard.detector.req;

import io.netty.buffer.ByteBuf;

public class TicketMessageReq extends MessageReq {

    private String deviceId;

    private String imei;

    private String signalInfo;

    @Override
    protected boolean read0(ByteBuf byteBuf, int length) {
        deviceId = new String(byteBuf.readBytes(15).array());
        imei = new String(byteBuf.readBytes(15).array());
        signalInfo = new String(byteBuf.readBytes(2).array());
		return true;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getSignalInfo() {
        return signalInfo;
    }

    public void setSignalInfo(String signalInfo) {
        this.signalInfo = signalInfo;
    }
}
