package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

public class App_start_lianmaiActModel extends BaseActModel
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String play_rtmp_acc; //主播视频数据加速拉流地址
    private String play_rtmp2_acc; //连麦观众视频数据加速拉流地址

    private String push_rtmp2; //连麦观众推流地址


    public String getPlay_rtmp_acc()
    {
        return play_rtmp_acc;
    }

    public void setPlay_rtmp_acc(String play_rtmp_acc)
    {
        this.play_rtmp_acc = play_rtmp_acc;
    }

    public String getPlay_rtmp2_acc()
    {
        return play_rtmp2_acc;
    }

    public void setPlay_rtmp2_acc(String play_rtmp2_acc)
    {
        this.play_rtmp2_acc = play_rtmp2_acc;
    }

    public String getPush_rtmp2()
    {
        return push_rtmp2;
    }

    public void setPush_rtmp2(String push_rtmp2)
    {
        this.push_rtmp2 = push_rtmp2;
    }
}
