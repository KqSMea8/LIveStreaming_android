package com.fanwe.live.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2018/9/21 0021.
 */

public class PointItem{
    float x;float y;
    public PointItem(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public PointItem() {
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

}