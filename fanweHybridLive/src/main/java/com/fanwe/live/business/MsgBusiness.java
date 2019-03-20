package com.fanwe.live.business;

import com.fanwe.live.model.custommsg.MsgModel;

/**
 * 消息业务类
 */
public class MsgBusiness
{
    /**
     * 对已经解析好的消息进行分发
     *
     * @param msg     已经解析好的消息
     * @param groupId 要筛选的消息群组id
     */
    public void parseMsg(MsgModel msg, String groupId)
    {
        String peer = msg.getConversationPeer();
        if (peer.equals(groupId))
        {
            onMsgGroup(msg);
        } else
        {
            onMsgC2C(msg);
        }
    }

    /**
     * 群组消息
     *
     * @param msg
     */
    protected void onMsgGroup(MsgModel msg)
    {

    }

    /**
     * C2C消息
     *
     * @param msg
     */
    protected void onMsgC2C(MsgModel msg)
    {

    }
}
