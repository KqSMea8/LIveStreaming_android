package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

/**
 * 接收后台管理员发送给主播的警告消息
 */
public class CustomMsgWarning extends CustomMsg {
    private int room_id;
    private String desc;

    public CustomMsgWarning() {
        super();
        setType(CustomMsgType.MSG_WARNING_BY_MANAGER);
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
