package com.fanwe.shop.model.custommsg;

import com.fanwe.live.model.custommsg.CustomMsg;

/**
 * Created by Administrator on 2016/12/5.
 */

public class CustomMsgBaseShop extends CustomMsg
{
    private int room_id; //房间ID
    private int post_id;// 主播ID
    private String desc;//消息

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
