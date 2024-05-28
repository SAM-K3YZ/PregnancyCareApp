package com.finalYearProject.myapplication.model;

import com.google.firebase.Timestamp;

public class UserModel {

    private String phone;
    private String username;
    private String email;
    private String conceptionDate;
    private String firstName;
    private String lastName;
    private Timestamp createdTimestamp;
    private String userId;
    private String fcmToken;

    public UserModel(){

    }

    public UserModel(String phone, String username, String email, String conceptionDate, String firstName, String lastName, Timestamp createdTimestamp, String userId) {
        this.phone = phone;
        this.username = username;
        this.email = email;
        this.conceptionDate = conceptionDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdTimestamp = createdTimestamp;
        this.userId = userId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConceptionDate() {
        return conceptionDate;
    }

    public void setConceptionDate(String conceptionDate) {
        this.conceptionDate = conceptionDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Timestamp getCreatedTimestamp() {
        return createdTimestamp;
    }

    public void setCreatedTimestamp(Timestamp createdTimestamp) {
        this.createdTimestamp = createdTimestamp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }
}
