package com.android.avempace.models;

public class SingleCallRequest {
    private String username;
    private String roomName;
    private String token;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public SingleCallRequest() {
    }

    public SingleCallRequest(String username, String roomName, String token) {
        this.username = username;
        this.roomName = roomName;
        this.token = token;
    }
}
