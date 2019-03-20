package com.fanwe.live.model;

/**
 * Created by Administrator on 2016/8/8.
 */
public class PlayBackData
{

    private int roomId;
    private String loadingVideoImageUrl;
    //add 回放视频创建类型 0：移动端直播； 1：PC端直播；
    private int create_type;

    public int getCreate_type() {
        return create_type;
    }

    public void setCreate_type(int create_type) {
        this.create_type = create_type;
    }

    public String getLoadingVideoImageUrl()
    {
        return loadingVideoImageUrl;
    }

    public PlayBackData setLoadingVideoImageUrl(String loadingVideoImageUrl)
    {
        this.loadingVideoImageUrl = loadingVideoImageUrl;
        return this;
    }

    public int getRoomId()
    {
        return roomId;
    }

    public PlayBackData setRoomId(int roomId)
    {
        this.roomId = roomId;
        return this;
    }
}
