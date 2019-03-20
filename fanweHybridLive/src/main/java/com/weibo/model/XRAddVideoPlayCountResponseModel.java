package com.weibo.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * @包名 com.fanwe.xianrou.model
 * @描述
 * @作者 Su
 * @创建时间 2017/4/16 12:10
 **/
public class XRAddVideoPlayCountResponseModel extends BaseActModel
{
    /**
     * video_count : 4
     */

    private String video_count;

    public String getVideo_count()
    {
        return video_count;
    }

    public void setVideo_count(String video_count)
    {
        this.video_count = video_count;
    }

}
