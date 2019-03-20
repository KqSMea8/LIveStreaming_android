package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

import java.util.List;

public class Index_focus_videoActModel extends BaseActModel
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<LiveRoomModel> list;
    private List<LivePlaybackModel> playback;

    public List<LiveRoomModel> getList()
    {
        return list;
    }

    public void setList(List<LiveRoomModel> list)
    {
        this.list = list;
    }

    public List<LivePlaybackModel> getPlayback()
    {
        return playback;
    }

    public void setPlayback(List<LivePlaybackModel> playback)
    {
        this.playback = playback;
    }
}
