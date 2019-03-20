package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_followActModel extends BaseActModel
{
    private static final long serialVersionUID = 1L;

    private int has_focus; // 1-已关注，0-未关注
    private String follow_msg; //关注成功后im的消息内容

    public String getFollow_msg()
    {
        return follow_msg;
    }

    public void setFollow_msg(String follow_msg)
    {
        this.follow_msg = follow_msg;
    }

    public int getHas_focus()
    {
        return has_focus;
    }

    public void setHas_focus(int has_focus)
    {
        this.has_focus = has_focus;
    }

}
