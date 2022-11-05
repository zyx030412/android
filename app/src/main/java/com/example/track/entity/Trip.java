package com.example.track.entity;

import java.io.Serializable;

public class Trip implements Serializable {
    private int id;
    private int mileage;
    private int time;
    private int ifOverSpeed;

    public Trip(int id, int mileage, int time, int ifOverSpeed) {
        this.id = id;
        this.mileage = mileage;
        this.time = time;
        this.ifOverSpeed = ifOverSpeed;
    }

    public Trip() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getIfOverSpeed() {
        return ifOverSpeed;
    }

    public void setIfOverSpeed(int ifOverSpeed) {
        this.ifOverSpeed = ifOverSpeed;
    }
}
