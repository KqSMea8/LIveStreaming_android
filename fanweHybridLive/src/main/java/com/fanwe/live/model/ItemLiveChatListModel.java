package com.fanwe.live.model;

import com.fanwe.live.model.custommsg.CustomMsg;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-30 下午1:10:28 类说明
 */
public class ItemLiveChatListModel extends UserModel
{
    private String peer;
    private boolean isSelf;// 是否自己发的显示内容
    private String text;// 聊天显示文字
    private long unreadnum;// 聊天显示未读数量
    private long time;
    private String timestampFormat;
    private boolean isRefreshUnReadNum=true;

    public void setAllParams(MsgModel msg)
    {
        CustomMsg customMsg = msg.getCustomMsg();
        UserModel user = msg.getCustomMsg().getSender();
        MsgModel msgModel = msg;

        setPeer(msg.getConversationPeer());
        setSelf(msgModel.isSelf());
        setText(customMsg.getConversationDesc());
        setTime(msgModel.getTimestamp());
        setTimestampFormat(msgModel.getTimestampFormat());

        if (msgModel.isSelf())
        {
            setUser_id(msgModel.getConversationPeer());
        } else
        {
            setUser_id(user.getUser_id());
            setNick_name(user.getNick_name());
            setHead_image(user.getHead_image());
            setSex(user.getSex());
            setV_icon(user.getV_icon());
            setUser_level(user.getUser_level());
        }
    }

    public boolean isRefreshUnReadNum()
    {
        return isRefreshUnReadNum;
    }

    public void setRefreshUnReadNum(boolean refreshUnReadNum)
    {
        isRefreshUnReadNum = refreshUnReadNum;
    }

    public boolean isSelf()
    {
        return isSelf;
    }

    public void setSelf(boolean isSelf)
    {
        this.isSelf = isSelf;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public long getUnreadnum()
    {
        return unreadnum;
    }

    public void setUnreadnum(long unreadnum)
    {
        this.unreadnum = unreadnum;
    }

    public long getTime()
    {
        return time;
    }

    public void setTime(long time)
    {
        this.time = time;
    }

    public String getTimestampFormat()
    {
        return timestampFormat;
    }

    public void setTimestampFormat(String timestampFormat)
    {
        this.timestampFormat = timestampFormat;
    }

    @Override
    public boolean equals(Object o)
    {
        return super.equals(o);
    }

    public String getPeer()
    {
        return peer;
    }

    public void setPeer(String peer)
    {
        this.peer = peer;
    }
}
