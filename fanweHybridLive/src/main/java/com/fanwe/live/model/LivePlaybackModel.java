package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/7/4.
 */
public class LivePlaybackModel
{
    private int room_id;
    private String group_id;
    private String user_id;
    private String city;
    private String title;
    private long begin_time;
    private int max_watch_number;
    private String nick_name;
    private String head_image;
    private String thumb_head_image;
    private int v_type;
    private String v_icon;
    private String playback_time;
    private String begin_time_format;
    private String watch_number_format;

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public String getGroup_id()
    {
        return group_id;
    }

    public void setGroup_id(String group_id)
    {
        this.group_id = group_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public long getBegin_time()
    {
        return begin_time;
    }

    public void setBegin_time(long begin_time)
    {
        this.begin_time = begin_time;
    }

    public int getMax_watch_number()
    {
        return max_watch_number;
    }

    public void setMax_watch_number(int max_watch_number)
    {
        this.max_watch_number = max_watch_number;
    }

    public String getNick_name()
    {
        return nick_name;
    }

    public void setNick_name(String nick_name)
    {
        this.nick_name = nick_name;
    }

    public String getHead_image()
    {
        return head_image;
    }

    public void setHead_image(String head_image)
    {
        this.head_image = head_image;
    }

    public String getThumb_head_image()
    {
        return thumb_head_image;
    }

    public void setThumb_head_image(String thumb_head_image)
    {
        this.thumb_head_image = thumb_head_image;
    }

    public int getV_type()
    {
        return v_type;
    }

    public void setV_type(int v_type)
    {
        this.v_type = v_type;
    }

    public String getV_icon()
    {
        return v_icon;
    }

    public void setV_icon(String v_icon)
    {
        this.v_icon = v_icon;
    }

    public String getPlayback_time()
    {
        return playback_time;
    }

    public void setPlayback_time(String playback_time)
    {
        this.playback_time = playback_time;
    }

    public String getBegin_time_format()
    {
        return begin_time_format;
    }

    public void setBegin_time_format(String begin_time_format)
    {
        this.begin_time_format = begin_time_format;
    }

    public String getWatch_number_format()
    {
        return watch_number_format;
    }

    public void setWatch_number_format(String watch_number_format)
    {
        this.watch_number_format = watch_number_format;
    }
}
