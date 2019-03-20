package com.weibo.manager;

import android.support.annotation.IntRange;

/**
 * @包名 com.fanwe.xianrou.manager
 * @描述 分页请求状态辅助类
 * @作者 Su
 * @创建时间 2017/3/14 16:45
 **/
public class XRPageRequestStateHelper
{
    private int firstPage;
    private int currentPage;
    private boolean hasNextPage = true;


    public XRPageRequestStateHelper(@IntRange(from = 0) int firstPage)
    {
        this.firstPage = firstPage;
        this.currentPage = firstPage;
    }

    public XRPageRequestStateHelper( )
    {
        this(1);
    }

    public int getCurrentPage()
    {
        return currentPage;
    }

    public void turnToNextPage()
    {
        this.currentPage++;
        this.hasNextPage=true;
    }

    public boolean hasNextPage()
    {
        return hasNextPage;
    }

    public void setLastPage()
    {
        this.hasNextPage = false;
    }

    public void resetStates()
    {
        currentPage = firstPage;
        hasNextPage = true;
    }


}
