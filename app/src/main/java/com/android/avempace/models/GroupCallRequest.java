package com.android.avempace.models;

import java.util.List;

public class GroupCallRequest {

    private String username;
    private String roomName;
    private List<String> tokensList;

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

    public List<String> getTokensList() {
        return tokensList;
    }

    public void setTokensList(List<String> tokensList) {
        this.tokensList = tokensList;
    }

    public GroupCallRequest() {
    }

    public GroupCallRequest(String username, String roomName, List<String> tokensList) {
        this.username = username;
        this.roomName = roomName;
        this.tokensList = tokensList;
    }
}
