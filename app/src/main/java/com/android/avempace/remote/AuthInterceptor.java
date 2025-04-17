package com.android.avempace.remote;

import androidx.annotation.NonNull;

import com.android.avempace.utilities.SharedPreferenceHelper;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class AuthInterceptor implements Interceptor {

    SharedPreferenceHelper sharedPreferenceHelper;
    private String token = null;

    public AuthInterceptor(SharedPreferenceHelper sharedPreferenceHelper) {
        this.sharedPreferenceHelper = sharedPreferenceHelper;
    }


    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {

        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();


        HttpUrl url = originalHttpUrl.newBuilder()
                .build();
        Request.Builder builder = originalRequest.newBuilder()
                .addHeader("Content-Type", "application/json")
                .url(url);
        if (sharedPreferenceHelper.getToken() != null) {
            Timber.d("Token has been found :D");
            builder.addHeader("Authorization", sharedPreferenceHelper.getToken());
        }

        Request request = builder
                .build();
        return chain.proceed(request);
    }
}
