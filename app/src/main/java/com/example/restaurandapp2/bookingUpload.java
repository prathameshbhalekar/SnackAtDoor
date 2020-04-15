package com.example.restaurandapp2;

public class bookingUpload {
    private String name;
    private String tableNo;
    private String time;
    private String date;
    private String id;
    private String forTime;
    private boolean isVerified;
    public bookingUpload(){}
    public bookingUpload(String id,String name, String tableNo, String time, String date, String forTime,boolean isVerified) {
        this.name = name;
        this.id=id;
        this.tableNo = tableNo;
        this.time = time;
        this.date = date;
        this.isVerified=isVerified;
        this.forTime = forTime;
    }
    public String getId(){return id;}
    public String getName() {
        return name;
    }

    public String getTableNo() {
        return tableNo;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getForTime() {
        return forTime;
    }

    public boolean getisVerified(){return isVerified;}
}
