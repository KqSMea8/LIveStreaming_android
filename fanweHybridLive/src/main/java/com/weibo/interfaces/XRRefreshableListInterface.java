package com.weibo.interfaces;

import java.util.List;

/**
 * @包名 com.fanwe.xianrou.common
 * @描述
 * @作者 Su
 * @创建时间 2017/3/20 9:49
 **/
public interface XRRefreshableListInterface<E> extends XRRefreshableInterface
{
    void setListData(List<E> data);

    void appendListData(List<E> data);
}
