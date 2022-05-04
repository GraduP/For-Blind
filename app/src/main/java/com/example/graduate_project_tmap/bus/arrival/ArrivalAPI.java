package com.example.graduate_project_tmap.bus.arrival;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ArrivalAPI {
    @GET("/api/rest/stationinfo/getStationByUid\n")
    Call<Arrival> getArrival(@Query("serviceKey") String key, @Query("arsId") String arsId , @Query("resultType") String type);

}
