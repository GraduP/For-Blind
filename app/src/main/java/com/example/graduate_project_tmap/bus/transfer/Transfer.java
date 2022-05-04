package com.example.graduate_project_tmap.bus.transfer;

import com.example.graduate_project_tmap.bus.ComMsgHeader;
import com.example.graduate_project_tmap.bus.MsgHeader;
import com.example.graduate_project_tmap.bus.station.MsgBody;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Transfer {

    @SerializedName("comMsgHeader")
    @Expose
    private ComMsgHeader comMsgHeader;
    @SerializedName("msgHeader")
    @Expose
    private MsgHeader msgHeader;
    @SerializedName("msgBody")
    @Expose
    private TMsgBody TmsgBody;

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

    public TMsgBody getTmsgBody() {
        return TmsgBody;
    }

    public void setTmsgBody(MsgBody msgBody) {
        this.TmsgBody = TmsgBody;
    }
}

