package com.fanwe.live.activity.room;

import com.fanwe.live.ILiveInfo;

public interface ILiveActivity extends ILiveInfo
{

    /**
     * 打开直播间输入框
     *
     * @param content
     */
    void openSendMsg(String content);

}
