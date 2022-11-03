package com.example.track.entity;

import com.example.track.R;

public class Mine {

    private int imageId;
    private String title;
    private final int enter = R.id.recycle_mine_enter;

    public Mine(int imageId, String title) {
        this.imageId = imageId;
        this.title = title;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getEnter() {
        return enter;
    }
}
