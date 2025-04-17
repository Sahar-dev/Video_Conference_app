package com.android.avempace.remote;

import android.telephony.mbms.GroupCall;

import com.android.avempace.models.ApiResponse;
import com.android.avempace.models.GroupCallRequest;
import com.android.avempace.models.SingleCallRequest;
import com.android.avempace.models.UpdateTokenRequest;
import com.android.avempace.models.UserModel;
import com.android.avempace.models.LoginRequest;
import com.android.avempace.models.RegisterRequest;
import com.android.avempace.models.UsersList;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("/")
    public Call<Object> testApiCall();

    @POST("/api/auth/signin")
    Call<ApiResponse<UserModel>> login(@Body LoginRequest loginRequest);

    @POST("/api/auth/signup")
    Call<ApiResponse<RegisterRequest>> register(@Body RegisterRequest registerRequest);

    @GET("/api/users/all")
    Call<ApiResponse<UsersList>> getAllUsers();

    @GET("/api/roomName")
    Call<ApiResponse<String>> getRandomRoomName();

    @POST("/api/call/single")
    Call<Object> sendSingleCallRequest(@Body SingleCallRequest singleCallRequest);

    @POST("/api/call/group")
    Call<Object> sendGroupCallRequest(@Body GroupCallRequest groupCallRequest);

    @POST("/api/token/update")
    Call<Object> updateToken(@Body UpdateTokenRequest updateTokenRequest);
}
