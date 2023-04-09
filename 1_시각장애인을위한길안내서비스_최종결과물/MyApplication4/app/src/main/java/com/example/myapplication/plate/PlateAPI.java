package com.example.myapplication.plate;

import com.example.myapplication.station.Station;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PlateAPI {
    @GET("api/rest/arrive/getArrInfoByRoute")
    Call<Plate> getPlate(@Query("serviceKey") String key, @Query("stId") String stId ,@Query("busRouteId") String busRouteId,@Query("ord") String ord ,@Query("resultType") String type);
}
