package com.fanwe.live;

import com.fanwe.live.model.App_get_videoActModel;

/**
 * Created by Administrator on 2017/6/3.
 */

public interface ILiveInfo
{
    /**
     * 获得直播间id
     *
     * @return
     */
    int getRoomId();

    /**
     * 获得直播间聊天组id
     *
     * @return
     */
    String getGroupId();

    /**
     * 获得直播间主播id
     *
     * @return
     */
    String getCreaterId();

    /**
     * 获得房间信息
     *
     * @return
     */
    App_get_videoActModel getRoomInfo();

    /**
     * 是否是私密直播
     *
     * @return
     */
    boolean isPrivate();

    /**
     * 是否是回放
     *
     * @return
     */
    boolean isPlayback();

    /**
     * 是否是主播
     *
     * @return
     */
    boolean isCreater();

    /**
     * 获得sdk类型<br>
     * 0-腾讯云sdk；1-金山sdk
     *
     * @return
     */
    int getSdkType();

    //----------extend start----------

    /**
     * 是否正在竞拍
     *
     * @return
     */
    boolean isAuctioning();

    /**
     *   是否正在推流中
     */
    boolean isPushing();

    //----------extend end----------
}
