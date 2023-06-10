package com.example.firstfirebaseproject;

import com.google.firebase.firestore.PropertyName;

public class PaymentModel {
    @PropertyName("date")
    String date_of_res_payment;
    @PropertyName("id")
    String user_id_payment;
    @PropertyName("room_no")
    String room_no_payment;
    @PropertyName("status")
    String payment_status;
    @PropertyName("amount")
    String price_payment;
    public PaymentModel() {
        // Default constructor required for Firestore serialization
    }
    public PaymentModel(String  user_id_payment, String date_of_res_payment, String room_no_payment, String payment_status, String price_payment){
        this.room_no_payment = room_no_payment;
        this.date_of_res_payment = date_of_res_payment;
        this.user_id_payment = user_id_payment;
        this.payment_status = payment_status;
        this.price_payment = price_payment;
    }
}
