package com.example.track.entity;

public class Setting {

    private String text;
    private int imgId;


    public Setting(String text, int imgId) {
        this.text = text;
        this.imgId = imgId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }
}
