package com.fanwe.live.model.custommsg;

import com.fanwe.live.LiveConstant.CustomMsgType;
import com.fanwe.live.model.App_start_lianmaiActModel;

public class CustomMsgAcceptLinkMic extends CustomMsg
{

    private String play_rtmp_acc; //主播视频数据加速拉流地址
    private String play_rtmp2_acc; //连麦观众视频数据加速拉流地址
    private String push_rtmp2; //连麦观众推流地址
    private boolean pk;
    private long end_time;
    private int room_id;
    private String pk_theme;
    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public CustomMsgAcceptLinkMic()
    {
        super();
        setType(CustomMsgType.MSG_ACCEPT_LINK_MIC);
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public String getPk_theme() {
        return pk_theme;
    }

    public void setPk_theme(String pk_theme) {
        this.pk_theme = pk_theme;
    }

    public CustomMsgAcceptLinkMic(int room_id, long end_time, String theme, boolean is_pk)
    {
        super();
        this.pk=is_pk;
        this.end_time=end_time;
        this.room_id=room_id;
        this.pk_theme=theme;
        setType(CustomMsgType.MSG_ACCEPT_LINK_MIC);
    }
    public void fillValue(App_start_lianmaiActModel actModel)
    {
        if (actModel != null)
        {
            setPush_rtmp2(actModel.getPush_rtmp2());
            setPlay_rtmp2_acc(actModel.getPlay_rtmp2_acc());
            setPlay_rtmp_acc(actModel.getPlay_rtmp_acc());
        }
    }

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
