package com.fanwe.live.model;

import com.fanwe.baimei.model.BMHomeLiveCenterTabModel;
import com.fanwe.hybrid.model.BaseActListModel;

import java.util.List;

public class Index_indexActModel extends BaseActListModel
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<LiveRoomModel> list;
    private List<RocketModel> rocket_list;
    private List<LiveBannerModel> banner;
    private LiveTopicInfoModel cate;

    public List<RocketModel> getRocket_list() {
        return rocket_list;
    }

    public void setRocket_list(List<RocketModel> rocket_list) {
        this.rocket_list = rocket_list;
    }

    //百媚直播========================================
    private List<BMHomeLiveCenterTabModel> tag_list;
    //百媚直播========================================
    public List<LiveRoomModel> getList()
    {
        return list;
    }

    public void setList(List<LiveRoomModel> list)
    {
        this.list = list;
    }

    public List<LiveBannerModel> getBanner()
    {
        return banner;
    }

    public void setBanner(List<LiveBannerModel> banner)
    {
        this.banner = banner;
    }

    public LiveTopicInfoModel getCate()
    {
        return cate;
    }

    public void setCate(LiveTopicInfoModel cate)
    {
        this.cate = cate;
    }

    public List<BMHomeLiveCenterTabModel> getTag_list()
    {
        return tag_list;
    }

    public void setTag_list(List<BMHomeLiveCenterTabModel> tag_list)
    {
        this.tag_list = tag_list;
    }
}
