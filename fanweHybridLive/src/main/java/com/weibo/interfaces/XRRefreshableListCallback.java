package com.weibo.interfaces;

/**
 * @包名 com.fanwe.xianrou.callback
 * @描述
 * @作者 Su
 * @创建时间 2017/3/14 15:58
 **/
public interface XRRefreshableListCallback extends XRCommonStateViewCallback
{
    void onListSwipeToRefresh();

    void onListPullToLoadMore();
}
