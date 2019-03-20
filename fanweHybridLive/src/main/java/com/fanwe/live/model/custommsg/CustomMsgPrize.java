package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant;

/**
 * 大型礼物通知消息
 */
public class CustomMsgPrize extends CustomMsg
{
    private int room_id;
    private int is_animated;
    private String icon;
    private int to_user_id;
    private String fonts_color;
    private String desc;
    private String desc2;
    private String top_title;

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public int getIs_animated() {
        return is_animated;
    }

    public void setIs_animated(int is_animated) {
        this.is_animated = is_animated;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(int to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getFonts_color() {
        return fonts_color;
    }

    public void setFonts_color(String fonts_color) {
        this.fonts_color = fonts_color;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc2() {
        return desc2;
    }

    public void setDesc2(String desc2) {
        this.desc2 = desc2;
    }

    public String getTop_title() {
        return top_title;
    }

    public void setTop_title(String top_title) {
        this.top_title = top_title;
    }

    public CustomMsgPrize()
    {
        super();
        setType(LiveConstant.CustomMsgType.MSG_PRIZE);
    }
}
