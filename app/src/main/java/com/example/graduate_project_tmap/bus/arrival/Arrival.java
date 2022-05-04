package com.example.graduate_project_tmap.bus.arrival;

import com.example.graduate_project_tmap.bus.ComMsgHeader;
import com.example.graduate_project_tmap.bus.MsgHeader;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Arrival {   @SerializedName("comMsgHeader")
@Expose
private ComMsgHeader comMsgHeader;
    @SerializedName("msgHeader")
    @Expose
    private MsgHeader msgHeader;
    @SerializedName("msgBody")
    @Expose
    private AMsgBody AMsgBody;

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

    public AMsgBody getAMsgBody() {
        return AMsgBody;
    }

    public void setAMsgBody(AMsgBody AMsgBody) {
        this.AMsgBody = AMsgBody;
    }
}
