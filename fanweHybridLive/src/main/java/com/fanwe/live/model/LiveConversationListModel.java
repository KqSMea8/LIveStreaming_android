package com.fanwe.live.model;

import android.text.TextUtils;

import com.fanwe.live.IMHelper;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * Created by Administrator on 2016/12/29.
 */

public class LiveConversationListModel extends UserModel
{
    private String peer; //会话对方id
    private String text; //展示文字
    private long unreadNum; //未读数量
    private long time; //时间
    private String timeFormat; //时间格式化

    public void fillValue(UserModel user)
    {
        if (user == null)
        {
            return;
        }
        String id = user.getUser_id();
        if (TextUtils.isEmpty(id))
        {
            return;
        }
        if (!id.equals(getUser_id()))
        {
            return;
        }

        setHead_image(user.getHead_image());
        setNick_name(user.getNick_name());
        setSex(user.getSex());
        setV_icon(user.getV_icon());
        setUser_level(user.getUser_level());
    }

    public void fillValue(MsgModel msg)
    {
        if (msg == null)
        {
            return;
        }
        setPeer(msg.getConversationPeer());
        setText(msg.getCustomMsg().getConversationDesc());
        setUnreadNum(msg.getUnreadNum());
        setTime(msg.getTimestamp());
        setTimeFormat(msg.getTimestampFormat());
        setUser_id(msg.getConversationPeer());

        if (msg.isSelf())
        {
            //不能填充自己的信息
        } else
        {
            fillValue(msg.getCustomMsg().getSender());
        }
    }

    public void updateUnreadNumber()
    {
        setUnreadNum(IMHelper.getC2CUnreadNumber(peer));
    }

    public String getPeer()
    {
        return peer;
    }

    public void setPeer(String peer)
    {
        this.peer = peer;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public long getUnreadNum()
    {
        return unreadNum;
    }

    public void setUnreadNum(long unreadNum)
    {
        this.unreadNum = unreadNum;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getTimeFormat()
    {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat)
    {
        this.timeFormat = timeFormat;
    }
}
