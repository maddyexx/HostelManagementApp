package com.example.firstfirebaseproject;

public class RoomReservationModel {
    String date_of_res, user_id, room_no;

    public RoomReservationModel(String  user_id, String date_of_res, String room_no){
        this.room_no = room_no;
        this.date_of_res = date_of_res;
        this.user_id = user_id;
    }
}
