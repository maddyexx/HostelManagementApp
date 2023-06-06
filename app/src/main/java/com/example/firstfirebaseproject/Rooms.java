package com.example.firstfirebaseproject;

public class Rooms {
    private String guests_Rooms;
    private String room_no_Rooms;
    private String status_Rooms;
    private String room_id;

    public Rooms() {
        this.room_id = "";
        guests_Rooms = "";
        room_no_Rooms = "";
        status_Rooms = "";
    }

    public String getGuests_Rooms() {
        return guests_Rooms;
    }

    public void setGuests_Rooms(String guests_Rooms) {
        this.guests_Rooms = guests_Rooms;
    }

    public String getRoom_no_Rooms() {
        return room_no_Rooms;
    }

    public void setRoom_no_Rooms(String room_no_Rooms) {
        this.room_no_Rooms = room_no_Rooms;
    }

    public String getStatus_Rooms() {
        return status_Rooms;
    }

    public void setStatus_Rooms(String status_Rooms) {
        this.status_Rooms = status_Rooms;
    }

    public String getRoom_id() {
        return room_id;
    }

    public void setRoom_id(String room_id) {
        this.room_id = room_id;
    }
}
