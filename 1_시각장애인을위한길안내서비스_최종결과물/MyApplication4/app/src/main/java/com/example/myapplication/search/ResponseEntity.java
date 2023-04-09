package com.example.myapplication.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseEntity {
    @Expose
    @SerializedName("result")
    private ResultEntity result;
    @Expose
    @SerializedName("page")
    private PageEntity page;
    @Expose
    @SerializedName("record")
    private RecordEntity record;
    @Expose
    @SerializedName("status")
    private String status;
    @Expose
    @SerializedName("service")
    private ServiceEntity service;

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }

    public PageEntity getPage() {
        return page;
    }

    public void setPage(PageEntity page) {
        this.page = page;
    }

    public RecordEntity getRecord() {
        return record;
    }

    public void setRecord(RecordEntity record) {
        this.record = record;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }
}
