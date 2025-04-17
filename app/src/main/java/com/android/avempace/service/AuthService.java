package com.android.avempace.service;

import com.android.avempace.models.ApiResponse;
import com.android.avempace.models.UserModel;
import com.android.avempace.models.LoginRequest;
import com.android.avempace.models.RegisterRequest;
import com.android.avempace.remote.ApiService;
import com.android.avempace.callbacks.AuthResponseCallBacks;
import com.android.avempace.remote.ServiceBuilder;
import com.android.avempace.utilities.MyUtils;
import com.android.avempace.utilities.SharedPreferenceHelper;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class AuthService {
    private AuthResponseCallBacks authResponseCallBacks;
    private final ApiService apiService = ServiceBuilder.buildService();
    private SharedPreferenceHelper sharedPreferenceHelper;
    public AuthService(AuthResponseCallBacks authResponseCallBacks) {
        this.authResponseCallBacks = authResponseCallBacks;
        sharedPreferenceHelper = new SharedPreferenceHelper();


    }

    public void login(LoginRequest loginRequest){
        try {
            Call<ApiResponse<UserModel>> apiCall = apiService.login(loginRequest);
            apiCall.enqueue(new Callback<ApiResponse<UserModel>>() {
                @Override
                public void onResponse(@NotNull Call<ApiResponse<UserModel>> call, @NotNull Response<ApiResponse<UserModel>> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            authResponseCallBacks.onLoginSuccess(response.body().getData());
                            sharedPreferenceHelper.insertString(MyUtils.TOKEN_KEY, response.body().getData().getToken());
                            sharedPreferenceHelper.insertString(MyUtils.USER_ID_KEY, response.body().getData().getId());
                            sharedPreferenceHelper.insertString(MyUtils.USERNAME_KEY, response.body().getData().getUsername());
                            sharedPreferenceHelper.insertString(MyUtils.EMAIL_KEY, response.body().getData().getEmail());
                        }
                    }else {
                        authResponseCallBacks.onLoginFailure("FAILED");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<UserModel>> call, Throwable t) {
                    authResponseCallBacks.onLoginFailure("FAILED "+ t);
                }
            });
        }
        catch (Exception e){
            Timber.e(e);
            authResponseCallBacks.onLoginFailure("FAILED "+ e.getMessage());
        }
    }

    public void register(RegisterRequest registerRequest){
        try {
            Call<ApiResponse<RegisterRequest>> apiCall = apiService.register(registerRequest);
            apiCall.enqueue(new Callback<ApiResponse<RegisterRequest>>() {
                @Override
                public void onResponse(@NotNull Call<ApiResponse<RegisterRequest>> call, @NotNull Response<ApiResponse<RegisterRequest>> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null)
                            authResponseCallBacks.onRegisterSuccess();
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String errorMessage = jObjError.getString("message");
                            authResponseCallBacks.onRegisterFailure(errorMessage);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            authResponseCallBacks.onRegisterFailure("Error");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<RegisterRequest>> call, Throwable t) {
                    authResponseCallBacks.onRegisterFailure(t.getMessage());
                }
            });
        }
        catch (Exception e){
            Timber.e(e);
            authResponseCallBacks.onRegisterFailure(e.getMessage());
        }
    }
}
