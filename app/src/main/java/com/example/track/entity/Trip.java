package com.example.track.entity;

import java.io.Serializable;

public class Trip implements Serializable {
    private double mileage;
    private String time;
    private int overSpeedTime;
    private String startTime;
    private String endTime;
    private String startAddress;
    private String endAddress;
    private String startLo;
    private String endLo;
    private String startLa;
    private String endLa;
    private String username;

    @Override
    public String toString() {
        return "Trip{" +
                "mileage=" + mileage +
                ", time='" + time + '\'' +
                ", overSpeedTime=" + overSpeedTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", startAddress='" + startAddress + '\'' +
                ", endAddress='" + endAddress + '\'' +
                ", startLo='" + startLo + '\'' +
                ", endLo='" + endLo + '\'' +
                ", startLa='" + startLa + '\'' +
                ", endLa='" + endLa + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public Trip(double mileage, String time, int overSpeedTime, String startTime, String endTime, String startAddress, String endAddress, String startLo, String endLo, String startLa, String endLa, String username) {
        this.mileage = mileage;
        this.time = time;
        this.overSpeedTime = overSpeedTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.startLo = startLo;
        this.endLo = endLo;
        this.startLa = startLa;
        this.endLa = endLa;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public double getMileage() {
        return mileage;
    }

    public void setMileage(double mileage) {
        this.mileage = mileage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getOverSpeedTime() {
        return overSpeedTime;
    }

    public void setOverSpeedTime(int overSpeedTime) {
        this.overSpeedTime = overSpeedTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getStartLo() {
        return startLo;
    }

    public void setStartLo(String startLo) {
        this.startLo = startLo;
    }

    public String getEndLo() {
        return endLo;
    }

    public void setEndLo(String endLo) {
        this.endLo = endLo;
    }

    public String getStartLa() {
        return startLa;
    }

    public void setStartLa(String startLa) {
        this.startLa = startLa;
    }

    public String getEndLa() {
        return endLa;
    }

    public void setEndLa(String endLa) {
        this.endLa = endLa;
    }
}
