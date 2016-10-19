package com.tys.netty.dto;

import java.io.Serializable;

public class ReturnParameters implements Serializable {

    private static final long serialVersionUID = 5834547024427489180L;

    /** 返回状态 */
    private String state;

    /** 返回内容 */
    private String rtnContent;

    /** 错误码 */
    private String errorCode;

    public ReturnParameters() {
    }

    public ReturnParameters(String state, String rtnContent, String errorCode) {
        super();
        this.state = state;
        this.rtnContent = rtnContent;
        this.errorCode = errorCode;
    }

    public String getRtnContent() {
        return rtnContent;
    }

    public void setRtnContent(String rtnContent) {
        this.rtnContent = rtnContent;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String temp = "ReturnParameters [";
        temp = temp + ("state=" + String.valueOf(state) + ",");
        temp = temp + ("rtnContent=" + String.valueOf(rtnContent) + ", ");
        temp = temp + ("errorCode=" + String.valueOf(errorCode));
        temp = temp + "]";
        return temp;

    }

}
