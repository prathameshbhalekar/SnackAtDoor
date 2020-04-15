package com.example.restaurandapp2;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Order {
    String name;
    String number;
    String id;
    String time;
    String order;
    String address;
    boolean delivered;
    public Order(){}
    public Order(String id,String name, String number, String time, String order, String address,boolean delivered) {
        this.name = name;
        this.id=id;
        this.delivered=delivered;
        this.number = number;
        this.time = time;
        this.address = address;
        this.order=order;
    }

    public String getId() {
        return id;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getTime() {
        return time;
    }

    public String getOrder() {
        return order;
    }

    public String getAddress() {
        return address;
    }
}
