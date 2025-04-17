package com.android.avempace.utilities;

public class EventNotifier {
    private String roomName;
    private String username;

    public EventNotifier(String roomName, String username) {
        this.roomName = roomName;
        this.username = username;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
