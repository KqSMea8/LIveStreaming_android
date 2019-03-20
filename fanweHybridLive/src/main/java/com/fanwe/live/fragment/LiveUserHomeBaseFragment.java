package com.fanwe.live.fragment;

import android.os.Bundle;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.live.model.App_user_homeActModel;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-11 下午6:31:33 类说明
 */
public class LiveUserHomeBaseFragment extends BaseFragment
{
	public static final String EXTRA_OBJ = "extra_obj";

	protected App_user_homeActModel app_user_homeActModel;

	@Override
	protected void init()
	{
		super.init();
		getArgumentsInfo();
	}

	private void getArgumentsInfo()
	{
		Bundle bundle = getArguments();
		if (bundle != null)
		{
			app_user_homeActModel = (App_user_homeActModel) bundle.getSerializable(EXTRA_OBJ);
		}
	}
}
