package com.fanwe.hybrid.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.activity.MainActivity;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.library.fragment.SDBaseFragment;
import com.sunday.eventbus.SDBaseEvent;

import org.xutils.x;

/**
 * @author 作者 yhz
 * @version 创建时间：2015-2-5 上午10:27:37 类说明 基类Fragment
 */
public class BaseFragment extends SDBaseFragment
{
    protected SDDialogProgress mDialog;

    protected void dimissDialog()
    {
        if (mDialog != null)
        {
            mDialog.dismiss();
            mDialog = null;
        }
    }

    protected void showProgressDialog(String msg)
    {
        if (mDialog == null)
        {
            mDialog = new SDDialogProgress(getActivity());
        }
        mDialog.setTextMsg(msg);
        mDialog.show();
    }

    protected void dismissProgressDialog()
    {
        dimissDialog();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        x.view().inject(this, view);
        init();
        super.onViewCreated(view, savedInstanceState);
    }

    protected void init()
    {

    }

    @Override
    public void onEventMainThread(SDBaseEvent event)
    {

    }

    public BaseActivity getBaseActivity()
    {
        return (BaseActivity) getActivity();
    }

    public MainActivity getMainActivity()
    {
        Activity activity = getActivity();
        if (activity != null)
        {
            if (activity instanceof MainActivity)
            {
                return ((MainActivity) activity);
            }
        }
        return null;
    }

    @Override
    protected int onCreateContentView()
    {
        // TODO Auto-generated method stub
        return 0;
    }
}
