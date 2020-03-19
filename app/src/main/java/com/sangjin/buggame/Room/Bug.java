package com.sangjin.buggame.Room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bug {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String bugName;
    private int bugImg;
    private String address;
    private double longitude;
    private double latitude;
    private String date;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBugName() {
        return bugName;
    }

    public void setBugName(String bugName) {
        this.bugName = bugName;
    }

    public int getBugImg() {
        return bugImg;
    }

    public void setBugImg(int bugImg) {
        this.bugImg = bugImg;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Bug{" +
                "id=" + id +
                ", bugName='" + bugName + '\'' +
                ", bugImg=" + bugImg +
                ", address='" + address + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", date='" + date + '\'' +
                '}';
    }
}

