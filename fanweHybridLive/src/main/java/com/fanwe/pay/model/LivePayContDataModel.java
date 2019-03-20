package com.fanwe.pay.model;

import com.fanwe.live.utils.LiveUtils;

/**
 * Created by Administrator on 2016/11/30.
 */

public class LivePayContDataModel
{
    private int room_id;
    private int total_time;
    private String total_time_format;
    private String totoal_minute;
    private String total_diamonds;
    private String user_id;
    private String head_image;
    private int user_level;
    private int sex;
    private String nick_name;
    private String v_icon;
    private String start_time;

    public String getStart_time()
    {
        return start_time;
    }

    public void setStart_time(String start_time)
    {
        this.start_time = start_time;
    }

    public int getSexResId()
    {
        return LiveUtils.getSexImageResId(sex);
    }

    public int getLevelImageResId()
    {
        return LiveUtils.getLevelImageResId(user_level);
    }

    public String getTotal_time_format()
    {
        return total_time_format;
    }

    public void setTotal_time_format(String total_time_format)
    {
        this.total_time_format = total_time_format;
    }

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public int getUser_level()
    {
        return user_level;
    }

    public void setUser_level(int user_level)
    {
        this.user_level = user_level;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getV_icon()
    {
        return v_icon;
    }

    public void setV_icon(String v_icon)
    {
        this.v_icon = v_icon;
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getTotal_diamonds()
    {
        return total_diamonds;
    }

    public void setTotal_diamonds(String total_diamonds)
    {
        this.total_diamonds = total_diamonds;
    }

    public long getTotal_time()
    {
        return total_time;
    }

    public void setTotal_time(int total_time)
    {
        this.total_time = total_time;
        this.totoal_minute = Integer.toString(total_time / 60);
    }

    public String getTotoal_minute()
    {
        return totoal_minute;
    }

    public void setTotoal_minute(String totoal_minute)
    {
        this.totoal_minute = totoal_minute;
    }
}
