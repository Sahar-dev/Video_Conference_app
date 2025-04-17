package com.android.avempace.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UsersList implements Serializable {
    @SerializedName("usersList")
    List<UserModel> usersList;

    public List<UserModel> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<UserModel> usersList) {
        this.usersList = usersList;
    }
}
