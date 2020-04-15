package com.example.restaurandapp2;

public class accountClass {
    String name;
    String email;
    String number;
    String address;
    accountClass(){}
    accountClass(String name,String email,String number,String address){
        this.name=name;
        this.email=email;
        this.number=number;
        this.address=address;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return address;
    }
}
