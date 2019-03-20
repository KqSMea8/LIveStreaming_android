package com.fanwe.auction.model;

/**
 * Created by Administrator on 2016/8/11.
 */
public class MessageGetListDataListItemModel
{
    private String send_user_name;
    private String send_user_avatar;
    private String content;
    private String create_date;

    private String id;
    private String send_id;
    private String user_id;
    private String type;
    private String create_time;
    private String create_time_ymd;
    private String create_time_y;
    private String create_time_m;
    private String create_time_d;
    private String is_read;
    private int send_status;
    private boolean flag;

    public String getSend_user_name()
    {
        return send_user_name;
    }

    public void setSend_user_name(String send_user_name)
    {
        this.send_user_name = send_user_name;
    }

    public String getSend_user_avatar()
    {
        return send_user_avatar;
    }

    public void setSend_user_avatar(String send_user_avatar)
    {
        this.send_user_avatar = send_user_avatar;
    }

    public boolean isFlag()
    {
        return flag;
    }

    public void setFlag(boolean flag)
    {
        this.flag = flag;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getSend_id()
    {
        return send_id;
    }

    public void setSend_id(String send_id)
    {
        this.send_id = send_id;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getCreate_time()
    {
        return create_time;
    }

    public void setCreate_time(String create_time)
    {
        this.create_time = create_time;
    }

    public String getCreate_date()
    {
        return create_date;
    }

    public void setCreate_date(String create_date)
    {
        this.create_date = create_date;
    }

    public String getCreate_time_ymd()
    {
        return create_time_ymd;
    }

    public void setCreate_time_ymd(String create_time_ymd)
    {
        this.create_time_ymd = create_time_ymd;
    }

    public String getCreate_time_m()
    {
        return create_time_m;
    }

    public void setCreate_time_m(String create_time_m)
    {
        this.create_time_m = create_time_m;
    }

    public String getCreate_time_y()
    {
        return create_time_y;
    }

    public void setCreate_time_y(String create_time_y)
    {
        this.create_time_y = create_time_y;
    }

    public String getCreate_time_d()
    {
        return create_time_d;
    }

    public void setCreate_time_d(String create_time_d)
    {
        this.create_time_d = create_time_d;
    }

    public String getIs_read()
    {
        return is_read;
    }

    public void setIs_read(String is_read)
    {
        this.is_read = is_read;
    }

    public int getSend_status()
    {
        return send_status;
    }

    public void setSend_status(int send_status)
    {
        this.send_status = send_status;
    }
}
