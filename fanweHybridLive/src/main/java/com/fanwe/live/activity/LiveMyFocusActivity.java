package com.fanwe.live.activity;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_focus_follow_ActModel;

import android.os.Bundle;
import android.view.View;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-15 下午1:58:48 类说明
 */
public class LiveMyFocusActivity extends LiveFocusFollowBaseActivity
{
	public static final String TAG = "LiveMyFocusActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		initTitle();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("粉丝");
		tv_empty_text.setText("你目前还没有粉丝");
	}

	@Override
	protected void request(final boolean isLoadMore)
	{
		CommonInterface.requestMy_focus(page, to_user_id, new AppRequestCallback<App_focus_follow_ActModel>()
		{
			@Override
			protected void onSuccess(SDResponse resp)
			{
				if (actModel.getStatus() == 1)
				{
					if(page == 1) {
						if(actModel.getList().size() == 0) {
							ll_empty.setVisibility(View.VISIBLE);
						} else {
							ll_empty.setVisibility(View.INVISIBLE);
						}
					}
					app_my_focusActModel = actModel;
					SDViewUtil.updateAdapterByList(listModel, actModel.getList(), adapter, isLoadMore);
				}
			}

			@Override
			protected void onFinish(SDResponse resp)
			{
				super.onFinish(resp);
				list.onRefreshComplete();
			}
		});
	}
}
