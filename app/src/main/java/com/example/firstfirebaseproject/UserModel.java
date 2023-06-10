package com.example.firstfirebaseproject;

public class UserModel {
    String room_no, user_no;
    String status;
    String price;
    String room_type;

    public UserModel(String room_no, String  user_no, String status, String price, String room_type){
        this.room_no = room_no;
        this.user_no = user_no;
        this.status = status;
        this.price = price;
        this.room_type = room_type;
    }
}
