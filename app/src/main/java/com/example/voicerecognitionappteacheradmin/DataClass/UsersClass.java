package com.example.voicerecognitionappteacheradmin.DataClass;

public class UsersClass {
    String username;
    String email;
    String address;
    String firebase_uid;
    String firstname;
    String lastname;
    String phone;
    String date_time_registered;
    String role;


    public UsersClass() {
    }

    public UsersClass(String username, String email, String address, String firebase_uid, String firstname, String lastname, String phone, String date_time_registered, String role) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.firebase_uid = firebase_uid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.date_time_registered = date_time_registered;
        this.role = role;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirebase_uid() {
        return firebase_uid;
    }

    public void setFirebase_uid(String firebase_uid) {
        this.firebase_uid = firebase_uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate_time_registered() {
        return date_time_registered;
    }

    public void setDate_time_registered(String date_time_registered) {
        this.date_time_registered = date_time_registered;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
