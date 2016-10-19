package com.tys.studentcard.detector.req;

import io.netty.buffer.ByteBuf;

/**
 * Created by zhutao on 15/1/24.
 */
public class AlarmReq extends MessageReq {

    private String deviceId;

    private String warnCase;

    @Override
    protected boolean read0(ByteBuf byteBuf, int length) {
        deviceId = new String(byteBuf.readBytes(15).array());
        warnCase = new String(byteBuf.readBytes(3).array());
        return true;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getWarnCase() {
        return warnCase;
    }

    public void setWarnCase(String warnCase) {
        this.warnCase = warnCase;
    }
}
