package com.example.firstfirebaseproject;

import com.google.firebase.firestore.PropertyName;

public class payment {
    @PropertyName("id")
    private String paymentId;
    @PropertyName("date")
    private String paymentDate;
    @PropertyName("room_no")
    private String paymentRoomNumber;
    @PropertyName("status")
    private String paymentStatus;
    @PropertyName("amount")
    private String pricePayment;

    public payment() {
        this.paymentId = "";
        this.paymentDate = "";
        this.paymentRoomNumber = "";
        this.paymentStatus = "";
        this.pricePayment = "";
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentRoomNumber() {
        return paymentRoomNumber;
    }

    public void setPaymentRoomNumber(String paymentRoomNumber) {
        this.paymentRoomNumber = paymentRoomNumber;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getPricePayment() {
        return pricePayment;
    }

    public void setPricePayment(String pricePayment) {
        this.pricePayment = pricePayment;
    }
}
