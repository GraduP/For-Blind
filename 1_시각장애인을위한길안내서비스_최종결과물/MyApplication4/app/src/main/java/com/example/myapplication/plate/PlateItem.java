package com.example.myapplication.plate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlateItem {
    @SerializedName("stId")
    @Expose
    private String stId;

    @SerializedName("stNm")
    @Expose
    private String stNm;

    @SerializedName("arsId")
    @Expose
    private String arsId;

    @SerializedName("busRouteId")
    @Expose
    private String busRouteId;

    @SerializedName("rtNm")
    @Expose
    private String rtNm;

    @SerializedName("firstTm")
    @Expose
    private String firstTm;

    @SerializedName("lastTm")
    @Expose
    private String lastTm;

    @SerializedName("term")
    @Expose
    private String term;

    @SerializedName("routeType")
    @Expose
    private String routeType;

    @SerializedName("nextBus")
    @Expose
    private String nextBus;

    @SerializedName("vehId1")
    @Expose
    private String  vehId1;

    @SerializedName("plainNo1")
    @Expose
    private  String plainNo1;

    @SerializedName("sectOrd1")
    @Expose
    private String sectOrd1;

    @SerializedName("stationNm1")
    @Expose
    private String stationNm1;

    @SerializedName("traTime1")
    @Expose
    private long traTime1;

    @SerializedName("traSpd1")
    @Expose
    private String traSpd1;

    @SerializedName("isArrive1")
    @Expose
    private String isArrive1;

    @SerializedName("repTm1")
    @Expose
    private String repTm1;

    @SerializedName("isLast1")
    @Expose
    private String isLast1;

    @SerializedName("busType1")
    @Expose
    private String busType1;

    public String getStId() {
        return stId;
    }

    public void setStId(String stId) {
        this.stId = stId;
    }

    public String getStNm() {
        return stNm;
    }

    public void setStNm(String stNm) {
        this.stNm = stNm;
    }

    public String getArsId() {
        return arsId;
    }

    public void setArsId(String arsId) {
        this.arsId = arsId;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public String getRtNm() {
        return rtNm;
    }

    public void setRtNm(String rtNm) {
        this.rtNm = rtNm;
    }

    public String getFirstTm() {
        return firstTm;
    }

    public void setFirstTm(String firstTm) {
        this.firstTm = firstTm;
    }

    public String getLastTm() {
        return lastTm;
    }

    public void setLastTm(String lastTm) {
        this.lastTm = lastTm;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getRouteType() {
        return routeType;
    }

    public void setRouteType(String routeType) {
        this.routeType = routeType;
    }

    public String getNextBus() {
        return nextBus;
    }

    public void setNextBus(String nextBus) {
        this.nextBus = nextBus;
    }

    public String getVehId1() {
        return vehId1;
    }

    public void setVehId1(String vehId1) {
        this.vehId1 = vehId1;
    }

    public String getPlainNo1() {
        return plainNo1;
    }

    public void setPlainNo1(String plainNo1) {
        this.plainNo1 = plainNo1;
    }

    public String getSectOrd1() {
        return sectOrd1;
    }

    public void setSectOrd1(String sectOrd1) {
        this.sectOrd1 = sectOrd1;
    }

    public String getStationNm1() {
        return stationNm1;
    }

    public void setStationNm1(String stationNm1) {
        this.stationNm1 = stationNm1;
    }

    public long getTraTime1() {
        return traTime1;
    }

    public void setTraTime1(long traTime1) {
        this.traTime1 = traTime1;
    }

    public String getTraSpd1() {
        return traSpd1;
    }

    public void setTraSpd1(String traSpd1) {
        this.traSpd1 = traSpd1;
    }

    public String getIsArrive1() {
        return isArrive1;
    }

    public void setIsArrive1(String isArrive1) {
        this.isArrive1 = isArrive1;
    }

    public String getRepTm1() {
        return repTm1;
    }

    public void setRepTm1(String repTm1) {
        this.repTm1 = repTm1;
    }

    public String getIsLast1() {
        return isLast1;
    }

    public void setIsLast1(String isLast1) {
        this.isLast1 = isLast1;
    }

    public String getBusType1() {
        return busType1;
    }

    public void setBusType1(String busType1) {
        this.busType1 = busType1;
    }
}
