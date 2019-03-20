package com.fanwe.live.model.custommsg.data;

import java.util.List;

/**
 * 直播间连麦信息数据实体
 */
public class DataLinkMicInfoModel
{

    private String play_rtmp_acc; //主播的acc播放地址
    private List<LinkMicItem> list_lianmai;

    //add

    /**
     * 是否有连麦用户
     *
     * @return
     */
    public boolean hasLinkMicItem()
    {
        return getLinkMicCount() > 0;
    }

    /**
     * 获得连麦用户数量
     *
     * @return
     */
    public int getLinkMicCount()
    {
        if (list_lianmai != null)
        {
            return list_lianmai.size();
        } else
        {
            return 0;
        }
    }

    /**
     * 本地用户是否正在连麦
     *
     * @return
     */
    public boolean isLocalUserLinkMic()
    {
        if (hasLinkMicItem())
        {
            for (LinkMicItem item : list_lianmai)
            {
                if (item.isLocalUserLinkMic())
                {
                    return true;
                }
            }
        }
        return false;
    }

    public String getPlay_rtmp_acc()
    {
        return play_rtmp_acc;
    }

    public void setPlay_rtmp_acc(String play_rtmp_acc)
    {
        this.play_rtmp_acc = play_rtmp_acc;
    }

    public List<LinkMicItem> getList_lianmai()
    {
        return list_lianmai;
    }

    public void setList_lianmai(List<LinkMicItem> list_lianmai)
    {
        this.list_lianmai = list_lianmai;
    }
}
