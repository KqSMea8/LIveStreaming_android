package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.view.SDRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;

public class SDPullToRefreshRecyclerView extends PullToRefreshBase<SDRecyclerView>
{

    public SDPullToRefreshRecyclerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public SDPullToRefreshRecyclerView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode,
                                       com.handmark.pulltorefresh.library.PullToRefreshBase.AnimationStyle style)
    {
        super(context, mode, style);
    }

    public SDPullToRefreshRecyclerView(Context context, com.handmark.pulltorefresh.library.PullToRefreshBase.Mode mode)
    {
        super(context, mode);
    }

    public SDPullToRefreshRecyclerView(Context context)
    {
        super(context);
    }

    @Override
    public com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation getPullToRefreshScrollDirection()
    {
        return Orientation.VERTICAL;
    }

    @Override
    protected SDRecyclerView createRefreshableView(Context context, AttributeSet attrs)
    {
        SDRecyclerView listView = new SDRecyclerView(context, attrs);
        listView.setId(com.handmark.pulltorefresh.library.R.id.recyclerview);
        return listView;
    }

    @Override
    protected boolean isReadyForPullEnd()
    {
        int count = getRefreshableView().getItemCount();
        if (count <= 0)
        {
            return true;
        } else
        {
            return getRefreshableView().isLastItemCompletelyVisible();
        }
    }

    @Override
    protected boolean isReadyForPullStart()
    {
        int count = getRefreshableView().getItemCount();
        if (count <= 0)
        {
            return true;
        } else
        {
            return getRefreshableView().isFirstItemCompletelyVisible();
        }
    }

}
