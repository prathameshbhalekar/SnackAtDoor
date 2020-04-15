package com.example.restaurandapp2;

public class user {
    String email;
    String uid;
    String address;
    String name;
    String phone;
    user(){}

    public user(String email, String uid, String address, String name, String phone) {
        this.email = email;
        this.uid = uid;
        this.address = address;
        this.name = name;
        this.phone = phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public String getUid() {
        return uid;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }
}
