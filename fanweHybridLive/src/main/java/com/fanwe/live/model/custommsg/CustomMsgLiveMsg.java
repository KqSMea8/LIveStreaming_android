package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;

public class CustomMsgLiveMsg extends CustomMsg
{
    private String fonts_color; // 字体颜色
    private String desc;

    public CustomMsgLiveMsg()
    {
        super();
        setType(CustomMsgType.MSG_LIVE_MSG);
    }

    public String getFonts_color()
    {
        return fonts_color;
    }

    public void setFonts_color(String fonts_color)
    {
        this.fonts_color = fonts_color;
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
