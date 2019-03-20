package com.fanwe.live.model.custommsg;

/**
 * 大型礼物通知消息
 */
public class CustomMsgLargeGift extends CustomMsg
{
    private int room_id;
    private String desc;

    public CustomMsgLargeGift()
    {
    }
    private int data_type;

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    public int getRoom_id()
    {
        return room_id;
    }

    public void setRoom_id(int room_id)
    {
        this.room_id = room_id;
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
