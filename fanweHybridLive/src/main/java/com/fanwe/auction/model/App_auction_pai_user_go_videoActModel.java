package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * Created by Administrator on 2016/10/8.
 */

public class App_auction_pai_user_go_videoActModel extends BaseActModel
{
    private int roomId;
    private String groupId;
    private String createrId;
    private String loadingVideoImageUrl;

    public int getRoomId()
    {
        return roomId;
    }

    public void setRoomId(int roomId)
    {
        this.roomId = roomId;
    }

    public String getGroupId()
    {
        return groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getCreaterId()
    {
        return createrId;
    }

    public void setCreaterId(String createrId)
    {
        this.createrId = createrId;
    }

    public String getLoadingVideoImageUrl()
    {
        return loadingVideoImageUrl;
    }

    public void setLoadingVideoImageUrl(String loadingVideoImageUrl)
    {
        this.loadingVideoImageUrl = loadingVideoImageUrl;
    }
}
