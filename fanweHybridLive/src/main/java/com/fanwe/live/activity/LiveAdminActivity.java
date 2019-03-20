package com.fanwe.live.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.dialog.SDDialogMenu.SDDialogMenuListener;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveAdminAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.model.App_set_adminActModel;
import com.fanwe.live.model.App_user_adminActModel;
import com.fanwe.live.model.App_user_adminModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-31 上午10:56:04 类说明
 */
public class LiveAdminActivity extends BaseTitleActivity
{
	@ViewInject(R.id.tv_number)
	private TextView tv_number;
	@ViewInject(R.id.list_admin)
	private ListView list_admin;

	private LiveAdminAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_live_user_admin);
		x.view().inject(this);
		init();
	}

	private void init()
	{
		initTitle();
		register();
		request_user_admin();
	}

	private void initTitle()
	{
		mTitle.setMiddleTextTop("管理员列表");
	}

	private void register()
	{
		list_admin.setOnItemLongClickListener(new OnItemLongClickListener()
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id)
			{
				if (adapter != null)
				{
					App_user_adminModel model = adapter.getItem((int) id);
					if (model != null)
					{
						String user_id = model.getUser_id();
						showBotDialog(user_id);
					}
				}
				return false;
			}
		});
	}

	// 管理员列表
	private void request_user_admin()
	{
		CommonInterface.requestUser_admin(new AppRequestCallback<App_user_adminActModel>()
		{
			@Override
			protected void onSuccess(SDResponse resp)
			{
				if (actModel.getStatus() == 1)
				{
					bindData(actModel);
				}
			}
		});
	}

	private void bindData(App_user_adminActModel actModel)
	{
		String text = "(" + actModel.getCur_num() + "/" + actModel.getMax_num() + ")";
		SDViewBinder.setTextView(tv_number, text);

		List<App_user_adminModel> list = actModel.getList();
		if (list != null && list.size() > 0)
		{
			adapter = new LiveAdminAdapter(list, this);
			list_admin.setAdapter(adapter);
		} else
		{
			list = new ArrayList<App_user_adminModel>();
			adapter = new LiveAdminAdapter(list, this);
			list_admin.setAdapter(adapter);
		}
	}

	private void showBotDialog(final String to_user_id)
	{
		String[] arrOption = new String[]
		{ "取消管理员" };


		SDDialogMenu dialog = new SDDialogMenu(this);

		dialog.setItems(arrOption);
		dialog.setmListener(new SDDialogMenuListener()
		{

			@Override
			public void onItemClick(View v, int index, SDDialogMenu dialog)
			{
				switch (index)
				{
				case 0: // 设置管理员或者取消
					requestset_admin(to_user_id);
					break;
				default:
					break;
				}
				dialog.dismiss();
			}

			@Override
			public void onDismiss(SDDialogMenu dialog)
			{
			}

			@Override
			public void onCancelClick(View v, SDDialogMenu dialog)
			{
			}
		});
		dialog.showBottom();
	}

	// 设置/取消管理员
	private void requestset_admin(String to_user_id)
	{
		CommonInterface.requestSet_admin(to_user_id, new AppRequestCallback<App_set_adminActModel>()
		{
			@Override
			protected void onSuccess(SDResponse resp)
			{
				if (actModel.getStatus() == 1)
				{
					if (actModel.getHas_admin() == 1)
					{
						SDToast.showToast("设置管理员成功");
					} else
					{
						SDToast.showToast("取消管理员成功");
					}
					request_user_admin();
				}
			}
		});
	}

}