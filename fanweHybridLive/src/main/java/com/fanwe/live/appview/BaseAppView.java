package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.library.view.SDAppView;
import com.fanwe.live.R;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.scottsu.stateslayout.StatesLayout;

import org.xutils.x;

public class BaseAppView extends SDAppView
{
    public BaseAppView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public BaseAppView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BaseAppView(Context context)
    {
        super(context);
    }
    private StatesLayout mStateLayout;
    private PullToRefreshListView mPullToRefreshViewWrapper;
    @Override
    public void setContentView(int layoutId)
    {
        super.setContentView(layoutId);
        x.view().inject(this, this);
    }
    /**
     * 返回状态布局
     *
     * @return
     */
    public StatesLayout getmStateLayout() {
        return mStateLayout;
    }

    public void setmStateLayout(StatesLayout mStateLayout) {
        this.mStateLayout = mStateLayout;
    }

    public PullToRefreshListView getmPullToRefreshViewWrapper() {
        return mPullToRefreshViewWrapper;
    }

    public void setmPullToRefreshViewWrapper(PullToRefreshListView mPullToRefreshViewWrapper) {
        this.mPullToRefreshViewWrapper = mPullToRefreshViewWrapper;
    }
}
