package com.android.avempace.callbacks;

public interface CallCallBacks {
    void sendSingleCallSuccess();
    void sendSingleCallFailure(String error);
    void sendGroupCallSuccess();
    void sendGroupCallFailure(String error);
}
