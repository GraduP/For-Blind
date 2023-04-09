package com.example.myapplication.bus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArrivalItem {
    @SerializedName("adirection")
    @Expose
    private String adirection;
    @SerializedName("arrmsg1")
    @Expose
    private String arrmsg1;
    @SerializedName("arrmsg2")
    @Expose
    private String arrmsg2;
    @SerializedName("arrmsgSec1")
    @Expose
    private String arrmsgSec1;
    @SerializedName("arrmsgSec2")
    @Expose
    private String arrmsgSec2;
    @SerializedName("arsId")
    @Expose
    private String arsId;
    @SerializedName("busRouteId")
    @Expose
    private String busRouteId;
    @SerializedName("busType1")
    @Expose
    private String busType1;
    @SerializedName("busType2")
    @Expose
    private String busType2;
    @SerializedName("congestion")
    @Expose
    private String congestion;
    @SerializedName("deTourAt")
    @Expose
    private String deTourAt;
    @SerializedName("firstTm")
    @Expose
    private String firstTm;
    @SerializedName("gpsX")
    @Expose
    private String gpsX;
    @SerializedName("gpsY")
    @Expose
    private String gpsY;
    @SerializedName("isArrive1")
    @Expose
    private String isArrive1;
    @SerializedName("isArrive2")
    @Expose
    private String isArrive2;
    @SerializedName("isFullFlag1")
    @Expose
    private String isFullFlag1;
    @SerializedName("isFullFlag2")
    @Expose
    private String isFullFlag2;
    @SerializedName("isLast1")
    @Expose
    private String isLast1;
    @SerializedName("isLast2")
    @Expose
    private String isLast2;
    @SerializedName("lastTm")
    @Expose
    private String lastTm;
    @SerializedName("nextBus")
    @Expose
    private String nextBus;
    @SerializedName("nxtStn")
    @Expose
    private String nxtStn;
    @SerializedName("posX")
    @Expose
    private String posX;
    @SerializedName("posY")
    @Expose
    private String posY;
    @SerializedName("repTm1")
    @Expose
    private String repTm1;
    @SerializedName("rerdieDiv1")
    @Expose
    private String rerdieDiv1;
    @SerializedName("rerdieDiv2")
    @Expose
    private String rerdieDiv2;
    @SerializedName("rerideNum1")
    @Expose
    private String rerideNum1;
    @SerializedName("rerideNum2")
    @Expose
    private String rerideNum2;
    @SerializedName("routeType")
    @Expose
    private String routeType;
    @SerializedName("rtNm")
    @Expose
    private String rtNm;
    @SerializedName("sectNm")
    @Expose
    private String sectNm;
    @SerializedName("sectOrd1")
    @Expose
    private String sectOrd1;
    @SerializedName("sectOrd2")
    @Expose
    private String sectOrd2;
    @SerializedName("stId")
    @Expose
    private String stId;
    @SerializedName("stNm")
    @Expose
    private String stNm;
    @SerializedName("staOrd")
    @Expose
    private String staOrd;
    @SerializedName("stationNm1")
    @Expose
    private String stationNm1;
    @SerializedName("stationNm2")
    @Expose
    private String stationNm2;
    @SerializedName("stationTp")
    @Expose
    private String stationTp;
    @SerializedName("term")
    @Expose
    private String term;
    @SerializedName("traSpd1")
    @Expose
    private String traSpd1;
    @SerializedName("traSpd2")
    @Expose
    private String traSpd2;
    @SerializedName("traTime1")
    @Expose
    private long traTime1;
    @SerializedName("traTime2")
    @Expose
    private long traTime2;
    @SerializedName("vehId1")
    @Expose
    private String  vehId1;
    @SerializedName("vehId2")
    @Expose
    private String  vehId2;

    public String getAdirection() {
        return adirection;
    }

    public void setAdirection(String adirection) {
        this.adirection = adirection;
    }

    public String getVehId2() {
        return vehId2;
    }

    public void setVehId2(String vehId2) {
        this.vehId2 = vehId2;
    }

    public String getVehId1() {
        return vehId1;
    }

    public void setVehId1(String vehId1) {
        this.vehId1 = vehId1;
    }

    public long getTraTime2() {
        return traTime2;
    }

    public void setTraTime2(long traTime2) {
        this.traTime2 = traTime2;
    }

    public long getTraTime1() {
        return traTime1;
    }

    public void setTraTime1(long traTime1) {
        this.traTime1 = traTime1;
    }

    public String getTraSpd2() {
        return traSpd2;
    }

    public void setTraSpd2(String traSpd2) {
        this.traSpd2 = traSpd2;
    }

    public String getTraSpd1() {
        return traSpd1;
    }

    public void setTraSpd1(String traSpd1) {
        this.traSpd1 = traSpd1;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStationTp() {
        return stationTp;
    }

    public void setStationTp(String stationTp) {
        this.stationTp = stationTp;
    }

    public String getStationNm2() {
        return stationNm2;
    }

    public void setStationNm2(String stationNm2) {
        this.stationNm2 = stationNm2;
    }

    public String getStationNm1() {
        return stationNm1;
    }

    public void setStationNm1(String stationNm1) {
        this.stationNm1 = stationNm1;
    }

    public String getStaOrd() {
        return staOrd;
    }

    public void setStaOrd(String staOrd) {
        this.staOrd = staOrd;
    }

    public String getStNm() {
        return stNm;
    }

    public void setStNm(String stNm) {
        this.stNm = stNm;
    }

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getSectOrd2() {
        return sectOrd2;
    }

    public void setSectOrd2(String sectOrd2) {
        this.sectOrd2 = sectOrd2;
    }

    public String getSectOrd1() {
        return sectOrd1;
    }

    public void setSectOrd1(String sectOrd1) {
        this.sectOrd1 = sectOrd1;
    }

    public String getSectNm() {
        return sectNm;
    }

    public void setSectNm(String sectNm) {
        this.sectNm = sectNm;
    }

    public String getRtNm() {
        return rtNm;
    }

    public void setRtNm(String rtNm) {
        this.rtNm = rtNm;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getRerideNum2() {
        return rerideNum2;
    }

    public void setRerideNum2(String rerideNum2) {
        this.rerideNum2 = rerideNum2;
    }

    public String getRerideNum1() {
        return rerideNum1;
    }

    public void setRerideNum1(String rerideNum1) {
        this.rerideNum1 = rerideNum1;
    }

    public String getRerdieDiv2() {
        return rerdieDiv2;
    }

    public void setRerdieDiv2(String rerdieDiv2) {
        this.rerdieDiv2 = rerdieDiv2;
    }

    public String getRerdieDiv1() {
        return rerdieDiv1;
    }

    public void setRerdieDiv1(String rerdieDiv1) {
        this.rerdieDiv1 = rerdieDiv1;
    }

    public String getRepTm1() {
        return repTm1;
    }

    public void setRepTm1(String repTm1) {
        this.repTm1 = repTm1;
    }

    public String getPosY() {
        return posY;
    }

    public void setPosY(String posY) {
        this.posY = posY;
    }

    public String getPosX() {
        return posX;
    }

    public void setPosX(String posX) {
        this.posX = posX;
    }

    public String getNxtStn() {
        return nxtStn;
    }

    public void setNxtStn(String nxtStn) {
        this.nxtStn = nxtStn;
    }

    public String getNextBus() {
        return nextBus;
    }

    public void setNextBus(String nextBus) {
        this.nextBus = nextBus;
    }

    public String getLastTm() {
        return lastTm;
    }

    public void setLastTm(String lastTm) {
        this.lastTm = lastTm;
    }

    public String getIsLast2() {
        return isLast2;
    }

    public void setIsLast2(String isLast2) {
        this.isLast2 = isLast2;
    }

    public String getIsLast1() {
        return isLast1;
    }

    public void setIsLast1(String isLast1) {
        this.isLast1 = isLast1;
    }

    public String getIsFullFlag2() {
        return isFullFlag2;
    }

    public void setIsFullFlag2(String isFullFlag2) {
        this.isFullFlag2 = isFullFlag2;
    }

    public String getIsFullFlag1() {
        return isFullFlag1;
    }

    public void setIsFullFlag1(String isFullFlag1) {
        this.isFullFlag1 = isFullFlag1;
    }

    public String getIsArrive2() {
        return isArrive2;
    }

    public void setIsArrive2(String isArrive2) {
        this.isArrive2 = isArrive2;
    }

    public String getIsArrive1() {
        return isArrive1;
    }

    public void setIsArrive1(String isArrive1) {
        this.isArrive1 = isArrive1;
    }

    public String getGpsY() {
        return gpsY;
    }

    public void setGpsY(String gpsY) {
        this.gpsY = gpsY;
    }

    public String getGpsX() {
        return gpsX;
    }

    public void setGpsX(String gpsX) {
        this.gpsX = gpsX;
    }

    public String getFirstTm() {
        return firstTm;
    }

    public void setFirstTm(String firstTm) {
        this.firstTm = firstTm;
    }

    public String getDeTourAt() {
        return deTourAt;
    }

    public void setDeTourAt(String deTourAt) {
        this.deTourAt = deTourAt;
    }

    public String getCongestion() {
        return congestion;
    }

    public void setCongestion(String congestion) {
        this.congestion = congestion;
    }

    public String getBusType2() {
        return busType2;
    }

    public void setBusType2(String busType2) {
        this.busType2 = busType2;
    }

    public String getBusType1() {
        return busType1;
    }

    public void setBusType1(String busType1) {
        this.busType1 = busType1;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public String getArsId() {
        return arsId;
    }

    public void setArsId(String arsId) {
        this.arsId = arsId;
    }

    public String getArrmsgSec2() {
        return arrmsgSec2;
    }

    public void setArrmsgSec2(String arrmsgSec2) {
        this.arrmsgSec2 = arrmsgSec2;
    }

    public String getArrmsgSec1() {
        return arrmsgSec1;
    }

    public void setArrmsgSec1(String arrmsgSec1) {
        this.arrmsgSec1 = arrmsgSec1;
    }

    public String getArrmsg2() {
        return arrmsg2;
    }

    public void setArrmsg2(String arrmsg2) {
        this.arrmsg2 = arrmsg2;
    }

    public String getArrmsg1() {
        return arrmsg1;
    }

    public void setArrmsg1(String arrmsg1) {
        this.arrmsg1 = arrmsg1;
    }
}