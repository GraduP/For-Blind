package com.example.myapplication.plate;

import com.example.myapplication.transfer.ComMsgHeader;
import com.example.myapplication.transfer.MsgHeader;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Plate {
    @SerializedName("comMsgHeader")
    @Expose
    private ComMsgHeader comMsgHeader;
    @SerializedName("msgHeader")
    @Expose
    private MsgHeader msgHeader;
    @SerializedName("msgBody")
    @Expose
    private PmsgBody pmsgBody;

    public ComMsgHeader getComMsgHeader() {
        return comMsgHeader;
    }

    public void setComMsgHeader(ComMsgHeader comMsgHeader) {
        this.comMsgHeader = comMsgHeader;
    }

    public MsgHeader getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(MsgHeader msgHeader) {
        this.msgHeader = msgHeader;
    }

    public PmsgBody getPmsgBody() {
        return pmsgBody;
    }

    public void setPmsgBody(PmsgBody pmsgBody) {
        this.pmsgBody = pmsgBody;
    }

}
