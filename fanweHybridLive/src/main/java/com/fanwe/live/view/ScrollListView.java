package com.fanwe.live.view;

import com.fanwe.library.utils.SDViewUtil;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AbsListView;
import android.widget.ListView;

public class ScrollListView extends ListView
{

	private int scrollState;
	private ScrollListener listenerScroll;
	private boolean isLoading;

	public void setListenerScroll(ScrollListener listenerScroll)
	{
		this.listenerScroll = listenerScroll;
	}

	public ScrollListView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
		init();
	}

	public ScrollListView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	public ScrollListView(Context context)
	{
		super(context);
		init();
	}

	private void init()
	{
		setOnScrollListener(new OnScrollListener()
		{

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
				ScrollListView.this.scrollState = scrollState;

				if (isLoading)
				{

				} else
				{
					if (listenerScroll != null)
					{
						if (SDViewUtil.isFirstItemTotallyVisible(ScrollListView.this))
						{
							isLoading = true;
							listenerScroll.onPullDownToRefresh(ScrollListView.this);
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{

			}
		});
	}

	public void onRefreshComplete()
	{
		isLoading = false;
	}

	@Override
	public void setSelection(int position)
	{
		if (scrollState == OnScrollListener.SCROLL_STATE_IDLE)
		{
			super.setSelection(position);
		} else
		{

		}
	}

	public interface ScrollListener
	{
		void onPullDownToRefresh(ScrollListView view);
	}

}
