package com.example.graduate_project_tmap.bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ComMsgHeader {

    @SerializedName("errMsg")
    @Expose
    private Object errMsg;
    @SerializedName("requestMsgID")
    @Expose
    private Object requestMsgID;
    @SerializedName("responseMsgID")
    @Expose
    private Object responseMsgID;
    @SerializedName("responseTime")
    @Expose
    private Object responseTime;
    @SerializedName("returnCode")
    @Expose
    private Object returnCode;
    @SerializedName("successYN")
    @Expose
    private Object successYN;

    public Object getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(Object errMsg) {
        this.errMsg = errMsg;
    }

    public Object getRequestMsgID() {
        return requestMsgID;
    }

    public void setRequestMsgID(Object requestMsgID) {
        this.requestMsgID = requestMsgID;
    }

    public Object getResponseMsgID() {
        return responseMsgID;
    }

    public void setResponseMsgID(Object responseMsgID) {
        this.responseMsgID = responseMsgID;
    }

    public Object getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Object responseTime) {
        this.responseTime = responseTime;
    }

    public Object getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(Object returnCode) {
        this.returnCode = returnCode;
    }

    public Object getSuccessYN() {
        return successYN;
    }

    public void setSuccessYN(Object successYN) {
        this.successYN = successYN;
    }

}