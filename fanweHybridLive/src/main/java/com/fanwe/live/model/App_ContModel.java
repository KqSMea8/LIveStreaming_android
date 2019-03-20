package com.fanwe.live.model;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-6 下午2:38:38 类说明
 */
public class App_ContModel
{
    private String user_id;
    private String head_image;
    private int user_level;
    private int v_type;
    private String v_icon;
    private int sex;
    private String nick_name;
    private int num; // 贡献的数钱票数,从高到低排序

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

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
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

    public int getNum()
    {
        return num;
    }

    public void setNum(int num)
    {
        this.num = num;
    }

}
