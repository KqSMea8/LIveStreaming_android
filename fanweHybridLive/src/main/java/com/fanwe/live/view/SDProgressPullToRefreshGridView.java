package com.fanwe.live.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.fanwe.library.view.HeaderGridView;
import com.handmark.pulltorefresh.library.PullToRefreshAdapterViewBase;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

/**
 * Created by Administrator on 2016/7/5.
 */
public class SDProgressPullToRefreshGridView extends PullToRefreshAdapterViewBase<HeaderGridView>
{

    public SDProgressPullToRefreshGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDProgressPullToRefreshGridView(Context context, Mode mode, AnimationStyle style)
    {
        super(context, mode, style);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection()
    {
        return Orientation.VERTICAL;
    }

    public SDProgressPullToRefreshGridView(Context context, Mode mode)
    {
        super(context, mode);
    }

    public SDProgressPullToRefreshGridView(Context context)
    {
        super(context);
    }


    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs)
    {
        return new SDProgressLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
    }

    @Override
    protected HeaderGridView createRefreshableView(Context context, AttributeSet attributeSet)
    {
        HeaderGridView headerGridView = new HeaderGridView(context, attributeSet);
        headerGridView.setId(com.handmark.pulltorefresh.library.R.id.gridview);
        return headerGridView;
    }
}
