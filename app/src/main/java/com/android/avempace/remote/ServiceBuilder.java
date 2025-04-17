package com.android.avempace.remote;

import com.android.avempace.utilities.SharedPreferenceHelper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceBuilder {
    private static final boolean HTTP_LOG_ENABLED = true;
    private final static String API_BASE_URL = "https://remote-app-server.herokuapp.com";

    private static HttpLoggingInterceptor getLoggingInterceptor(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if(HTTP_LOG_ENABLED)
            loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }
    private final static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new AuthInterceptor(new SharedPreferenceHelper()))
            .addInterceptor(getLoggingInterceptor())
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build();

    public static ApiService buildService() {
        return retrofit.create(ApiService.class);
    }

}
