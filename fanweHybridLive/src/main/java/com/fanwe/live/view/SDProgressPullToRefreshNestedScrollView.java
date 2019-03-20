package com.fanwe.live.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.live.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.internal.LoadingLayout;

/**
 * Created by Administrator on 2016/7/5.
 */
public class SDProgressPullToRefreshNestedScrollView extends PullToRefreshBase<NestedScrollView>
{

    public SDProgressPullToRefreshNestedScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDProgressPullToRefreshNestedScrollView(Context context, Mode mode, AnimationStyle style)
    {
        super(context, mode, style);
    }

    public SDProgressPullToRefreshNestedScrollView(Context context, Mode mode)
    {
        super(context, mode);
    }

    public SDProgressPullToRefreshNestedScrollView(Context context)
    {
        super(context);
    }

    @Override
    public Orientation getPullToRefreshScrollDirection()
    {
        return Orientation.VERTICAL;
    }

    @Override
    protected LoadingLayout createLoadingLayout(Context context, Mode mode, TypedArray attrs)
    {
        return new SDProgressLoadingLayout(context, mode, getPullToRefreshScrollDirection(), attrs);
    }

    @Override
    protected NestedScrollView createRefreshableView(Context context, AttributeSet attributeSet)
    {
        NestedScrollView nestedScrollView = new NestedScrollView(context, attributeSet);
        nestedScrollView.setId(R.id.scrollview);
        return nestedScrollView;
    }

    @Override
    protected boolean isReadyForPullEnd()
    {
        View child = getRefreshableView().getChildAt(0);
        if (child != null)
        {
            return getRefreshableView().getScrollY() >= (child.getHeight() - this.getHeight());
        } else
        {
            return false;
        }
    }

    @Override
    protected boolean isReadyForPullStart()
    {
        return getRefreshableView().getScrollY() == 0;
    }
}
