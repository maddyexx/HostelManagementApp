package com.example.firstfirebaseproject;

public class User {
    private String uid;
    private String phone;
    private String email;
    private String password;
    private String fname;

    public User() {
        this.uid = "";
        this.phone = "";
        this.email = "";
        this.password = "";
        this.fname = "";
    }

    public String getFname(String s) {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
