package com.tys.studentcard.detector.req;

import io.netty.buffer.ByteBuf;

public class QueryStatusReq extends MessageReq {


    private String deviceId;


    private String versionInfo;


    private String minitorInfo;


    @Override
    protected boolean read0(ByteBuf byteBuf, int length) {
        if (byteBuf.readableBytes() >= length) {
            deviceId = new String(byteBuf.readBytes(18).array());
            versionInfo = new String(byteBuf.readBytes(18).array());
            minitorInfo = new String(byteBuf.readBytes(72).array());
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

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
    }

    public String getMinitorInfo() {
        return minitorInfo;
    }

    public void setMinitorInfo(String minitorInfo) {
        this.minitorInfo = minitorInfo;
    }
}
