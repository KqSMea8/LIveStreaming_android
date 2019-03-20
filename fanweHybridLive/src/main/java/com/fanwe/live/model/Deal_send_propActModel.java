package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/7/21.
 */
public class Deal_send_propActModel extends BaseActModel
{

    private String from_msg;
    private String from_score;
    private String to_msg;
    private String to_ticket;
    private String to_user_id;
    private String prop_icon;

    public String getFrom_msg()
    {
        return from_msg;
    }

    public void setFrom_msg(String from_msg)
    {
        this.from_msg = from_msg;
    }

    public String getFrom_score()
    {
        return from_score;
    }

    public void setFrom_score(String from_score)
    {
        this.from_score = from_score;
    }

    public String getTo_msg()
    {
        return to_msg;
    }

    public void setTo_msg(String to_msg)
    {
        this.to_msg = to_msg;
    }

    public String getTo_ticket()
    {
        return to_ticket;
    }

    public void setTo_ticket(String to_ticket)
    {
        this.to_ticket = to_ticket;
    }

    public String getTo_user_id()
    {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id)
    {
        this.to_user_id = to_user_id;
    }

    public String getProp_icon()
    {
        return prop_icon;
    }

    public void setProp_icon(String prop_icon)
    {
        this.prop_icon = prop_icon;
    }
}
