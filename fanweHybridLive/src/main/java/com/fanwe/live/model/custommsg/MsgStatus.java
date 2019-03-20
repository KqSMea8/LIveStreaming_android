package com.fanwe.live.model.custommsg;

public enum MsgStatus
{
    /**
     * 非法值
     */
    Invalid,
    /**
     * 发送失败
     */
    SendFail,
    /**
     * 发送中
     */
    Sending,
    /**
     * 发送成功
     */
    SendSuccess,
    /**
     * 被标记为已删除
     */
    HasDeleted;
}
