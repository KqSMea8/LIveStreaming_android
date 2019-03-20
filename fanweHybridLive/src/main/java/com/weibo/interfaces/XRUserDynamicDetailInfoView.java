package com.weibo.interfaces;

import com.weibo.model.XRUserDynamicDetailResponseModel;

/**
 * 项目: FanweLive_android
 * 包名: com.fanwe.xianrou.interfaces
 * 描述:
 * 作者: Su
 * 日期: 2017/7/3 10:53
 **/
public interface XRUserDynamicDetailInfoView
{
    /**
     * 本地更新评论数
     * @param add
     * @return
     */
    int updateCommentCount(boolean add);

    void setInfoBean(XRUserDynamicDetailResponseModel.InfoBean mInfoBean, boolean forFavorite);

    XRUserDynamicDetailResponseModel.InfoBean getInfoBean();
}
