package com.fanwe.auction.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 主播-预载新建实物竞拍（非必须）
 * Created by Administrator on 2016/11/8.
 */

public class App_pai_podcast_addpaidetailActModel extends BaseActModel
{
    private App_pai_podcast_addpaidetailDataModel data;

    public App_pai_podcast_addpaidetailDataModel getData() {
        return data;
    }

    public void setData(App_pai_podcast_addpaidetailDataModel data) {
        this.data = data;
    }
}
