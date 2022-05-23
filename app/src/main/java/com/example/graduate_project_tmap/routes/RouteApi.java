package com.example.graduate_project_tmap.routes;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RouteApi {

    @POST("/tmap/routes/pedestrian")
    @Headers({
            "appKey: l7xx98299ca0fd2d41859cb522c8efcfc7c0"
    })
    @FormUrlEncoded
    Call<Route> getRoutes(@Query("version") String version,@Field("startX") String startX, @Field("startY") String startY,
                          @Field("endX") String endX, @Field("endY") String endY,
                          @Field("startName") String startName, @Field("endName") String endName);
}
