package com.android.avempace.service;

import com.android.avempace.models.ApiResponse;
import com.android.avempace.models.UserModel;
import com.android.avempace.models.UsersList;
import com.android.avempace.remote.ApiService;
import com.android.avempace.remote.ServiceBuilder;
import com.android.avempace.callbacks.UserCallBacks;
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

public class UserService {
    private UserCallBacks callBacks;
    private final ApiService apiService = ServiceBuilder.buildService();
    private SharedPreferenceHelper sharedPreferenceHelper;

    public UserService(UserCallBacks callBacks) {
        this.callBacks = callBacks;
        sharedPreferenceHelper = new SharedPreferenceHelper();
    }

    public UserModel getUserInfo(){
        String id = sharedPreferenceHelper.getString(MyUtils.USER_ID_KEY,"");
        String username = sharedPreferenceHelper.getString(MyUtils.USERNAME_KEY,"");
        String email = sharedPreferenceHelper.getString(MyUtils.EMAIL_KEY,"");
        return new UserModel(id, username, email);
    }

    public void getAllUsers(){
        try {
            Call<ApiResponse<UsersList>> apiCall = apiService.getAllUsers();
            apiCall.enqueue(new Callback<ApiResponse<UsersList>>() {
                @Override
                public void onResponse(@NotNull Call<ApiResponse<UsersList>> call, @NotNull Response<ApiResponse<UsersList>> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            callBacks.getAllUsersSuccess(response.body().getData().getUsersList());
                        }
                            
                    }else {
                        try {
                            JSONObject jObjError = new JSONObject(response.errorBody().string());
                            String errorMessage = jObjError.getString("message");
                            callBacks.getAllUsersFailure(errorMessage);
                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                            callBacks.getAllUsersFailure("Error");
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<UsersList>> call, Throwable t) {
                    callBacks.getAllUsersFailure(t.getMessage());
                }
            });
        }
        catch (Exception e){
            Timber.e(e);
            callBacks.getAllUsersFailure(e.getMessage());
        }
    }
    public void getRandomRoomName(){
        try {
            Call<ApiResponse<String>> apiCall = apiService.getRandomRoomName();
            apiCall.enqueue(new Callback<ApiResponse<String>>() {
                @Override
                public void onResponse(@NotNull Call<ApiResponse<String>> call, @NotNull Response<ApiResponse<String>> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            callBacks.getRoomNameSuccess(response.body().getData());
                        }

                    }else {
                        callBacks.getAllUsersFailure("Error, check your internet");
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<String>> call, Throwable t) {
                    callBacks.getAllUsersFailure(t.getMessage());
                }
            });
        }
        catch (Exception e){
            Timber.e(e);
            callBacks.getAllUsersFailure(e.getMessage());
        }
    }
}
