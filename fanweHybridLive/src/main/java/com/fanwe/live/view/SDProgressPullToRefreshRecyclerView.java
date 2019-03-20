package com.fanwe.live.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.internal.LoadingLayout;

public class SDProgressPullToRefreshRecyclerView extends SDPullToRefreshRecyclerView
{

    public SDProgressPullToRefreshRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDProgressPullToRefreshRecyclerView(Context context, Mode mode, AnimationStyle style)
    {
        super(context, mode, style);
    }

    public SDProgressPullToRefreshRecyclerView(Context context, Mode mode)
    {
        super(context, mode);
    }

    public SDProgressPullToRefreshRecyclerView(Context context)
    {
        super(context);
    }

    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs)
    {
        return new SDProgressLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
    }

}
