package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.model.Deal_send_propActModel;

/**
 * 私聊礼物消息
 */
public class CustomMsgPrivateGift extends CustomMsg
{

    private String from_msg; // example:送给你一个小黄瓜
    private String from_score; // example:你的经验值+10
    private String to_msg; // example:收到一个小黄瓜,获得1钱票,可以去个人主页>我的收益 查看哦
    private String to_ticket; // example:1
    private String to_user_id; // example:1
    private String prop_icon; // example:http://www.ss.cc/aa.png
    private int prop_id; // 礼物id

    public CustomMsgPrivateGift()
    {
        super();
        setType(CustomMsgType.MSG_PRIVATE_GIFT);
    }

    public void fillData(Deal_send_propActModel model)
    {
        if (model != null)
        {
            from_msg = model.getFrom_msg();
            from_score = model.getFrom_score();
            to_msg = model.getTo_msg();
            to_ticket = model.getTo_ticket();
            to_user_id = model.getTo_user_id();
            prop_icon = model.getProp_icon();
        }
    }

    public int getProp_id()
    {
        return prop_id;
    }

    public void setProp_id(int prop_id)
    {
        this.prop_id = prop_id;
    }

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

    @Override
    public String getConversationDesc()
    {
        return "[礼物]";
    }

}
