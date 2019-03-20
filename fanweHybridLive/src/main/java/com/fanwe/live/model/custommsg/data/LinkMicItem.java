package com.fanwe.live.model.custommsg.data;

import android.text.TextUtils;

import com.fanwe.live.dao.UserModelDao;

/**
 * 小主播的连麦信息实体
 */
public class LinkMicItem
{
    private String user_id; //小主播id
    private String push_rtmp2; //小主播的推流地址
    private String play_rtmp2_acc; //小主播的acc播放地址
    private LinkMicLayoutParams layout_params;

    //add

    /**
     * 当前连麦信息是不是本地用户的信息
     *
     * @return
     */
    public boolean isLocalUserLinkMic()
    {
        String userId = UserModelDao.getUserId();
        if (TextUtils.isEmpty(userId))
        {
            return false;
        }
        return userId.equals(this.user_id);
    }

    public LinkMicLayoutParams getLayout_params()
    {
        return layout_params;
    }

    public void setLayout_params(LinkMicLayoutParams layout_params)
    {
        this.layout_params = layout_params;
    }

    public String getUser_id()
    {
        return user_id;
    }

    public void setUser_id(String user_id)
    {
        this.user_id = user_id;
    }

    public String getPush_rtmp2()
    {
        return push_rtmp2;
    }

    public void setPush_rtmp2(String push_rtmp2)
    {
        this.push_rtmp2 = push_rtmp2;
    }

    public String getPlay_rtmp2_acc()
    {
        return play_rtmp2_acc;
    }

    public void setPlay_rtmp2_acc(String play_rtmp2_acc)
    {
        this.play_rtmp2_acc = play_rtmp2_acc;
    }
}
