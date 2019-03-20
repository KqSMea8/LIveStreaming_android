package com.fanwe.live.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUserHomeActivity;
import com.fanwe.live.adapter.LiveContAdapter;
import com.fanwe.live.model.App_ContActModel;
import com.fanwe.live.model.App_ContModel;
import com.fanwe.live.view.SDProgressPullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-6 下午9:18:22 类说明
 */
public class LiveContBaseFragment extends BaseFragment
{
	@ViewInject(R.id.list)
	protected SDProgressPullToRefreshListView list;

	protected List<App_ContModel> listModel = new ArrayList<App_ContModel>();
	protected LiveContAdapter adapter;
	protected App_ContActModel app_ContActModel;

	protected int page = 1;

	@Override
	protected int onCreateContentView()
	{
		return R.layout.frag_cont_list;
	}

	@Override
	protected void init()
	{
		super.init();
		register();
	}

	protected void register()
	{
		list.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				App_ContModel model = adapter.getItem((int) id);
				if (model != null)
				{
					String userid = model.getUser_id();
					if (!TextUtils.isEmpty(userid))
					{
						Intent intent = new Intent(getActivity(), LiveUserHomeActivity.class);
						intent.putExtra(LiveUserHomeActivity.EXTRA_USER_ID, model.getUser_id());
						startActivity(intent);
					} else
					{
						SDToast.showToast("userid为空");
					}
				}

			}
		});
		list.setMode(Mode.BOTH);
		list.setOnRefreshListener(new OnRefreshListener2<ListView>()
		{

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				refreshViewer();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView)
			{
				loadMoreViewer();
			}
		});

		adapter = new LiveContAdapter(listModel, getActivity());
		list.setAdapter(adapter);

		refreshViewer();
	}

	private void loadMoreViewer()
	{
		if (app_ContActModel != null)
		{
			if (app_ContActModel.getHas_next() == 1)
			{
				page++;
				requestCont(true);
			} else
			{
				list.onRefreshComplete();
				SDToast.showToast("没有更多数据");
			}
		} else
		{
			refreshViewer();
		}
	}

	private void refreshViewer()
	{
		page = 1;
		requestCont(false);
	}

	protected void requestCont(final boolean isLoadMore)
	{

	}
}
