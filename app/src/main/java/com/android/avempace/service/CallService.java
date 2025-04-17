package com.android.avempace.service;

import com.android.avempace.callbacks.CallCallBacks;
import com.android.avempace.models.GroupCallRequest;
import com.android.avempace.models.SingleCallRequest;
import com.android.avempace.models.UserModel;
import com.android.avempace.remote.ApiService;
import com.android.avempace.remote.ServiceBuilder;
import com.android.avempace.utilities.MyUtils;
import com.android.avempace.utilities.SharedPreferenceHelper;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class CallService {

    private CallCallBacks callBacks;
    private final ApiService apiService = ServiceBuilder.buildService();
    private SharedPreferenceHelper sharedPreferenceHelper;

    public CallService(CallCallBacks callBacks) {
        this.callBacks = callBacks;
        sharedPreferenceHelper = new SharedPreferenceHelper();
    }


    public void sendSingleCallRequest(String fireBaseToken, String roomName){
        try {
            String username = sharedPreferenceHelper.getString(MyUtils.USERNAME_KEY,"");
            SingleCallRequest singleCallRequest = new SingleCallRequest(username, roomName, fireBaseToken);
            Call<Object> apiCall = apiService.sendSingleCallRequest(singleCallRequest);
            apiCall.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            callBacks.sendSingleCallSuccess();
                        }

                    }else {
                        callBacks.sendSingleCallFailure("Error, check your internet");
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    callBacks.sendSingleCallFailure(t.getMessage());
                }
            });
        }
        catch (Exception e){
            Timber.e(e);
            callBacks.sendSingleCallFailure(e.getMessage());
        }
    }
    public void sendGroupCallRequest(String roomName, List<String> tokensList){
        try {
            String username = sharedPreferenceHelper.getString(MyUtils.USERNAME_KEY,"");
            GroupCallRequest groupCallRequest = new GroupCallRequest(username, roomName, tokensList);
            Call<Object> apiCall = apiService.sendGroupCallRequest(groupCallRequest);
            apiCall.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(@NotNull Call<Object> call, @NotNull Response<Object> response) {
                    if(response.isSuccessful()){
                        if(response.body() != null) {
                            callBacks.sendSingleCallSuccess();
                        }

                    }else {
                        callBacks.sendSingleCallFailure("Error, check your internet");
                    }
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    callBacks.sendSingleCallFailure(t.getMessage());
                }
            });
        }
        catch (Exception e){
            Timber.e(e);
            callBacks.sendSingleCallFailure(e.getMessage());
        }
    }
}
