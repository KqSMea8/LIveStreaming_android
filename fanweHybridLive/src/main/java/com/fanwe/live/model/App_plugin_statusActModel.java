package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2017/1/9.
 */

public class App_plugin_statusActModel extends BaseActModel
{
    private String id;
    private String child_id;
    private String name;
    private String image;
    private int type;
    private String class_name;
    private int is_active;
    private int is_enable;
    private int is_recharge;//是否进入充值页面

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getChild_id()
    {
        return child_id;
    }

    public void setChild_id(String child_id)
    {
        this.child_id = child_id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getClass_name()
    {
        return class_name;
    }

    public void setClass_name(String class_name)
    {
        this.class_name = class_name;
    }

    public int getIs_active()
    {
        return is_active;
    }

    public void setIs_active(int is_active)
    {
        this.is_active = is_active;
    }

    public int getIs_enable()
    {
        return is_enable;
    }

    public void setIs_enable(int is_enable)
    {
        this.is_enable = is_enable;
    }

    public int getIs_recharge()
    {
        return is_recharge;
    }

    public void setIs_recharge(int is_recharge)
    {
        this.is_recharge = is_recharge;
    }
}
