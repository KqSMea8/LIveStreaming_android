package com.weibo.interfaces;

import android.view.View;

/**
 * @包名 com.fanwe.xianrou.adapter
 * @描述
 * @作者 Su
 * @创建时间 2017/3/10 14:47
 **/
public interface XRCommonItemClickCallback<E>
{
//    void onItemClick(View itemView, E entity, int position, @Nullable View[] sharedElements, @Nullable String[] transitionNames, @Nullable Bundle data);

//    public interface SimpleItemClickCallback<T>
//    {
        void onItemClick(View itemView, E entity, int position);
//    }
}

