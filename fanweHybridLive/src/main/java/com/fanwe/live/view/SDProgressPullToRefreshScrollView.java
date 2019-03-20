package com.fanwe.live.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

/**
 * Created by Administrator on 2016/7/5.
 */
public class SDProgressPullToRefreshScrollView extends PullToRefreshScrollView
{

    public SDProgressPullToRefreshScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDProgressPullToRefreshScrollView(Context context, Mode mode, AnimationStyle style)
    {
        super(context, mode, style);
    }

    public SDProgressPullToRefreshScrollView(Context context, Mode mode)
    {
        super(context, mode);
    }

    public SDProgressPullToRefreshScrollView(Context context)
    {
        super(context);
    }


    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs)
    {
        return new SDProgressLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
    }
}
