package com.example.graduate_project_tmap.bus.transfer;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TransferAPI {
    @GET("api/rest/pathinfo/getPathInfoByBus")
    Call<Transfer> getTransfer(@Query("serviceKey") String key, @Query("startX") String startX , @Query("startY") String startY , @Query("endX") String endX , @Query("endY") String endY , @Query("resultType") String type);

}

