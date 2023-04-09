package com.example.myapplication.fcm;

import static okhttp3.OkHttpClient.Builder;
import static okhttp3.logging.HttpLoggingInterceptor.*;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

//Retrofit Client 싱글톤

public class FcmClient {
    private static FcmClient instance = null;
    private static FcmApi fcmApi;
    private static final String BASE_URL = "https://fcm.googleapis.com";
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private FcmClient() {

        logging.setLevel(Level.BODY);
        Builder httpClient = new Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        fcmApi = retrofit.create(FcmApi.class);

    }

    public static FcmClient getInstance() {
        if (instance == null) {
            instance = new FcmClient();
        }
        return instance;
    }


        public static FcmApi getFcmApi () {
            return fcmApi;
        }
    }
