package com.example.graduate_project_tmap.bus.transfer;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitTransferClient {
    private static RetrofitTransferClient instance =null;
    private static TransferAPI TransferAPI;
    private static final String BASE_URL = "http://ws.bus.go.kr/";

    private RetrofitTransferClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        TransferAPI = retrofit.create(TransferAPI.class);
    }

    public static RetrofitTransferClient getInstance(){
        if (instance==null){
            instance = new RetrofitTransferClient();
        }
        return instance;
    }
    public static TransferAPI getTransferAPI() {
        return TransferAPI;
    }
}
