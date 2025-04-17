package com.android.avempace.service;

import com.android.avempace.models.UpdateTokenRequest;
import com.android.avempace.remote.ApiService;
import com.android.avempace.remote.ServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TokenService {
    private final ApiService apiService = ServiceBuilder.buildService();

    public void updateToken(String userId, String newToken){
        Call<Object> apiCall = apiService.updateToken(new UpdateTokenRequest(userId, newToken));
        apiCall.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
            }
        });
    }
}
