package com.weibo.interfaces;

import android.view.View;

/**
 * @包名 com.fanwe.xianrou.interfaces
 * @描述
 * @作者 Su
 * @创建时间 2017/3/24 18:21
 **/
public interface XRShareClickCallback
{
    void onShareQQClick(View view);

    void onShareWechatClick(View view);

    void onShareFriendsCircleClick(View view);

    void onShareWeiboClick(View view);

    void onShareQZoneClick(View view);
}
