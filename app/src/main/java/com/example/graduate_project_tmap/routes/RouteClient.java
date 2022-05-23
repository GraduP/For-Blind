package com.example.graduate_project_tmap.routes;

import static okhttp3.logging.HttpLoggingInterceptor.*;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RouteClient {

    private static RouteClient instance =null;
    private static RouteApi routeApi;
    private static final String BASE_URL = "https://apis.openapi.sk.com";
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

    private RouteClient() {

        logging.setLevel(Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        routeApi = retrofit.create(RouteApi.class);
    }

    public static RouteClient getInstance(){
        if (instance==null){
            instance = new RouteClient();
        }
        return instance;
    }

    public static RouteApi getRouteApi() {
        return routeApi;
    }

}