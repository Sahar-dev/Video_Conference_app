package com.android.avempace.models;

import com.google.gson.annotations.SerializedName;

public class ApiResponse <T>  {

    @SerializedName("status")
    private String status;

    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
