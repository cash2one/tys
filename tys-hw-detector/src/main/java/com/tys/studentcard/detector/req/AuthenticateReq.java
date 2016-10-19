package com.tys.studentcard.detector.req;

import io.netty.buffer.ByteBuf;

public class AuthenticateReq extends MessageReq {

    private String deviceId;

    private String imei;

    @Override
    protected boolean read0(ByteBuf byteBuf, int length) {
        if (byteBuf.readableBytes() >= length) {
            deviceId = new String(byteBuf.readBytes(15).array());
            imei = new String(byteBuf.readBytes(15).array());
        } else {
            byteBuf.readBytes(length);
        }
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
}
