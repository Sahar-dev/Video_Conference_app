package com.android.avempace.models;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    @SerializedName("firebase_token")
    private String fireBaseToken;

    public RegisterRequest() {
    }

    public RegisterRequest(String username, String email, String password, String fireBaseToken) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.fireBaseToken = fireBaseToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFireBaseToken() {
        return fireBaseToken;
    }

    public void setFireBaseToken(String fireBaseToken) {
        this.fireBaseToken = fireBaseToken;
    }
}
