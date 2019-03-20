package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.R;
import com.handmark.pulltorefresh.library.internal.SDUtils;

public class PullToRefreshScrollListView extends PullToRefreshBase<ScrollListView>
{
	public PullToRefreshScrollListView(Context context)
	{
		super(context);
	}

	public PullToRefreshScrollListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullToRefreshScrollListView(Context context, Mode mode)
	{
		super(context, mode);
	}

	public PullToRefreshScrollListView(Context context, Mode mode, AnimationStyle style)
	{
		super(context, mode, style);
	}

	@Override
	public com.handmark.pulltorefresh.library.PullToRefreshBase.Orientation getPullToRefreshScrollDirection()
	{
		return Orientation.VERTICAL;
	}

	@Override
	protected ScrollListView createRefreshableView(Context context, AttributeSet attrs)
	{
		ScrollListView listView = new ScrollListView(context, attrs);
		listView.setId(R.id.listview);
		return listView;
	}

	@Override
	protected boolean isReadyForPullEnd()
	{
		return SDUtils.isLastItemTotallyVisible(getRefreshableView());
	}

	@Override
	protected boolean isReadyForPullStart()
	{
		return SDUtils.isFirstItemTotallyVisible(getRefreshableView());
	}
}
