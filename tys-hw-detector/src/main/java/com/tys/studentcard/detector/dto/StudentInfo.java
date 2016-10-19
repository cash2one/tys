package com.tys.studentcard.detector.dto;

import java.util.Date;

public class StudentInfo {

    private String cardId;

    private Date startTime;

    private byte optType;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public byte getOptType() {
        return optType;
    }

    public void setOptType(byte optType) {
        this.optType = optType;
    }
}
