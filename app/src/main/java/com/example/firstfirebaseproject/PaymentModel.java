package com.example.firstfirebaseproject;

public class PaymentModel {
    String date_of_res_payment, user_id_payment, room_no_payment, payment_status;

    public PaymentModel(String  user_id_payment, String date_of_res_payment, String room_no_payment, String payment_status){
        this.room_no_payment = room_no_payment;
        this.date_of_res_payment = date_of_res_payment;
        this.user_id_payment = user_id_payment;
        this.payment_status = payment_status;
    }
}
