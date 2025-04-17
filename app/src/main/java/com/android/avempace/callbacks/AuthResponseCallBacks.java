package com.android.avempace.callbacks;

import com.android.avempace.models.UserModel;

public interface AuthResponseCallBacks {
    void onLoginSuccess(UserModel userModel);
    void onLoginFailure(String error);
    void onRegisterSuccess();
    void onRegisterFailure(String error);
}
