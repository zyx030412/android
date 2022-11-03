package com.example.track.entity;

public class Item3 {

    private int id;
    private String str;
    private final String watch = "正常";


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getWatch() {
        return watch;
    }



    public Item3(int id, String str) {
        this.id = id;
        this.str = str;
    }
}
