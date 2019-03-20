package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgEndVideo extends CustomMsg
{

    private int show_num;
    private int room_id;
    private String desc;

    public CustomMsgEndVideo()
    {
        super();
        setType(CustomMsgType.MSG_END_VIDEO);
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
    }

    public int getShow_num()
    {
        return show_num;
    }

    public void setShow_num(int show_num)
    {
        this.show_num = show_num;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

}
