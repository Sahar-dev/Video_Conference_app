package com.android.avempace.callbacks;

import com.android.avempace.models.UserModel;

public interface RecyclerCallBack {
    void onUserClicked(UserModel user, int position);
    void onUserLongClick(UserModel user, int position);
    void onSingleCallClicked(UserModel user, int position);
}
