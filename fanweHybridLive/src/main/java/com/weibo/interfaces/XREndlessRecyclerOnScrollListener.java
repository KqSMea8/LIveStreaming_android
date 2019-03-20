package com.weibo.interfaces;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * @包名 com.fanwe.xianrou.common
 * @描述 RecycleView上拉加载回调监听器
 * @作者 Su
 * @创建时间 2017/3/22 10:27
 **/
public abstract class XREndlessRecyclerOnScrollListener extends RecyclerView.OnScrollListener
{
    private static final String UNSUPPORTED_LAYOUT_MANAGER_EXCEPTION="Unsupported LayoutManager. " +
            "This listener only supports LinearLayoutManager、GridLayoutManager or StaggeredGridLayoutManager ";

    private int mLayoutManagerOrientation;


    public abstract void onLoadMore();

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState)
    {
        super.onScrollStateChanged(recyclerView, newState);

        if (newState == RecyclerView.SCROLL_STATE_IDLE
                && !canScroll(recyclerView)  //判断是否已滚动到底部
                && getScrollOffset(recyclerView) > 0) //屏蔽已不能滚动,但列表实际高度未超过1屏的情况;
        {
            onLoadMore();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy)
    {
        super.onScrolled(recyclerView, dx, dy);

        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();

        if (layoutManager instanceof LinearLayoutManager)
        {
            mLayoutManagerOrientation = ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager)
        {
            mLayoutManagerOrientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else
        {
            throw new IllegalArgumentException(UNSUPPORTED_LAYOUT_MANAGER_EXCEPTION);
        }
    }

    private boolean canScroll(RecyclerView recyclerView)
    {
        return (mLayoutManagerOrientation == RecyclerView.VERTICAL ? recyclerView.canScrollVertically(1)
                : recyclerView.canScrollHorizontally(1));
    }

    private int getScrollOffset(RecyclerView recyclerView)
    {
        return (mLayoutManagerOrientation == RecyclerView.VERTICAL ? recyclerView.computeVerticalScrollOffset()
                : recyclerView.computeHorizontalScrollOffset());
    }


}