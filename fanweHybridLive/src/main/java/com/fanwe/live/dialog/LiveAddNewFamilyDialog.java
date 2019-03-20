package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveFamilyUpdateEditActivity;
import com.fanwe.live.activity.LiveFamilysListActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 加入创建家族对话框
 * Created by Administrator on 2016/9/26.
 */

public class LiveAddNewFamilyDialog extends SDDialogBase
{
    @ViewInject(R.id.tv_add_fam)
    private TextView tv_add_fam;//加入家族
    @ViewInject(R.id.tv_new_fam)
    private TextView tv_new_fam;//创建家族

    public LiveAddNewFamilyDialog(Activity activity)
    {
        super(activity);
        init();
        initListener();
    }

    private void init()
    {
        setCanceledOnTouchOutside(true);
        setContentView(R.layout.dialog_live_add_new_family);
//        paddings(0);
        x.view().inject(this, getContentView());
    }

    private void initListener()
    {
        tv_add_fam.setOnClickListener(this);
        tv_new_fam.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.tv_add_fam:
                Intent intent = new Intent(getOwnerActivity(),LiveFamilysListActivity.class);
                getOwnerActivity().startActivity(intent);
                dismiss();
                break;
            case R.id.tv_new_fam:
                Intent intentNew = new Intent(getOwnerActivity(),LiveFamilyUpdateEditActivity.class);
                getOwnerActivity().startActivity(intentNew);
                dismiss();
                break;
        }
    }
}
