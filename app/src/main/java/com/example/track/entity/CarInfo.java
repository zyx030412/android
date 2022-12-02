package com.example.track.entity;

public class CarInfo {
    private String license_plate;
    private int track_type;
    private int power_type;
    private int total_weight;
    private int approved_load;
    private double length;
    private double width;
    private double height;
    private int axis_number;
    private String username;


    public CarInfo(String license_plate, int track_type, int power_type, int total_weight, int approved_load, double length, double width, double height, int axis_number, String username) {
        this.license_plate = license_plate;
        this.track_type = track_type;
        this.power_type = power_type;
        this.total_weight = total_weight;
        this.approved_load = approved_load;
        this.length = length;
        this.width = width;
        this.height = height;
        this.axis_number = axis_number;
        this.username = username;
    }

    public String getLicense_plate() {
        return license_plate;
    }

    public void setLicense_plate(String license_plate) {
        this.license_plate = license_plate;
    }

    public int getTrack_type() {
        return track_type;
    }

    public void setTrack_type(int track_type) {
        this.track_type = track_type;
    }

    public int getPower_type() {
        return power_type;
    }

    public void setPower_type(int power_type) {
        this.power_type = power_type;
    }

    public int getTotal_weight() {
        return total_weight;
    }

    public void setTotal_weight(int total_weight) {
        this.total_weight = total_weight;
    }

    public int getApproved_load() {
        return approved_load;
    }

    public void setApproved_load(int approved_load) {
        this.approved_load = approved_load;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getAxis_number() {
        return axis_number;
    }

    public void setAxis_number(int axis_number) {
        this.axis_number = axis_number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
