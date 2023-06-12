package com.example.firstfirebaseproject;

public class Reservation {
    private String arrivalDate;
    private String numberOfGuests;
    private String contactInformation;
    private String roomType;

    public Reservation() {
        // Default constructor required for Firestore
    }

    public Reservation(String arrivalDate, String numberOfGuests, String contactInformation, String roomType) {
        this.arrivalDate = arrivalDate;
        this.numberOfGuests = numberOfGuests;
        this.contactInformation = contactInformation;
        this.roomType = roomType;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public String getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(String numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
// Getters and setters
    // ...
}