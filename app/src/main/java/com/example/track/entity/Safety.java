package com.example.track.entity;



public class Safety {
    int id;
    String temperature;
    String insert_time;
    int warning_flag;

    public Safety(int id, String temperature, String insert_time, int warning_flag) {
        this.id = id;
        this.temperature = temperature;
        this.insert_time = insert_time;
        this.warning_flag = warning_flag;
    }

    public Safety() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(String insert_time) {
        this.insert_time = insert_time;
    }

    public int getWarning_flag() {
        return warning_flag;
    }

    public void setWarning_flag(int warning_flag) {
        this.warning_flag = warning_flag;
    }
}
