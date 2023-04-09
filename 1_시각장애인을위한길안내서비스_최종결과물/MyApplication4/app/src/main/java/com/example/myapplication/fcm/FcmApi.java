package com.example.myapplication.fcm;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

//API 요청 Interface

public interface FcmApi {

    @Headers({
            "Authorization: key=AAAASWaQUKU:APA91bEmkXcjx07kJx77P2eLhbDofAaKs0Nq1za9Kfc5nOqjUJe3etq-NpEhoAWD49kHzWFsdJwz8n36_fzLmomH4Ki_18uYRAJyc4JZNnRGdBSAzfHkR6Ng-DD1JqoA4Ogw2sdU6qkK",
            "Content-Type: application/json"
    })
    @POST("/fcm/send")
    Call<ResponseBody> pushNotification(@Body FcmRequest fcmRequest);


}
