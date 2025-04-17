package com.android.avempace.callbacks;

import com.android.avempace.models.UserModel;

import java.util.List;

public interface UserCallBacks {
    public void getAllUsersSuccess(List<UserModel> list);
    public void getAllUsersFailure(String error);
    public void getRoomNameSuccess(String roomName);
    public void getRoomNameFailure(String error);
}
