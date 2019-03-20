package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/7/21.
 */
public class ConversationUnreadMessageModel
{
    private String peer;
    private long unreadnum;

    public String getPeer()
    {
        return peer;
    }

    public void setPeer(String peer)
    {
        this.peer = peer;
    }

    public long getUnreadnum()
    {
        return unreadnum;
    }

    public void setUnreadnum(long unreadnum)
    {
        this.unreadnum = unreadnum;
    }
}
