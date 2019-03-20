package com.fanwe.baimei.model.custommsg;

import com.fanwe.live.model.custommsg.CustomMsg;

/**
 * Created by yhz on 2017/6/14.
 */

public class BMCustomMsgPushStatus extends CustomMsg
{
    private int room_id;
    private String fonts_color;
    private int is_push = 1;// 1 是 0 否 是否正在推流 本地默认推流
    private String desc;
    private String desc2;

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public String getFonts_color()
    {
        return fonts_color;
    }

    public void setFonts_color(String fonts_color)
    {
        this.fonts_color = fonts_color;
    }

    public int getIs_push()
    {
        return is_push;
    }

    public void setIs_push(int is_push)
    {
        this.is_push = is_push;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public String getDesc2()
    {
        return desc2;
    }

    public void setDesc2(String desc2)
    {
        this.desc2 = desc2;
    }
}
