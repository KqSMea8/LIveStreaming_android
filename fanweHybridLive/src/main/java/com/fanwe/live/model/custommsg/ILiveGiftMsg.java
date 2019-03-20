package com.fanwe.live.model.custommsg;

import com.fanwe.live.model.UserModel;

/**
 * 直播间连发显示消息
 */
public interface ILiveGiftMsg
{
    UserModel getMsgSender();

    /**
     * 设置要显示的数量
     *
     * @param showNum
     */
    void setShowNum(int showNum);

    /**
     * 获得要显示的数量
     *
     * @return
     */
    int getShowNum();
    int getAddNum();
    /**
     * 该消息是否已经被view占有播放
     *
     * @return
     */
    boolean isTaked();

    void setTaked(boolean taked);

    /**
     * 是否可以被播放
     *
     * @return
     */
    boolean canPlay();
}
