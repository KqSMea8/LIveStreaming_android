package com.fanwe.live.model.custommsg.data;

/**
 * Created by Administrator on 2017/4/19.
 */

public class LinkMicLayoutParams
{
    private int image_layer;

    private float location_x; //x轴百分比
    private float location_y; //y轴百分比
    private float image_width; //宽度百分比
    private float image_height; //高度百分比

    public int getImage_layer()
    {
        return image_layer;
    }

    public void setImage_layer(int image_layer)
    {
        this.image_layer = image_layer;
    }

    public float getLocation_x()
    {
        return location_x;
    }

    public void setLocation_x(float location_x)
    {
        this.location_x = location_x;
    }

    public float getLocation_y()
    {
        return location_y;
    }

    public void setLocation_y(float location_y)
    {
        this.location_y = location_y;
    }

    public float getImage_width()
    {
        return image_width;
    }

    public void setImage_width(float image_width)
    {
        this.image_width = image_width;
    }

    public float getImage_height()
    {
        return image_height;
    }

    public void setImage_height(float image_height)
    {
        this.image_height = image_height;
    }
}
