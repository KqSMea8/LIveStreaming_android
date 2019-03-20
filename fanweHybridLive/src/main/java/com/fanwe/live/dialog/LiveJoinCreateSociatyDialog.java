package com.fanwe.live.dialog;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveSociatySearchJoinActivity;
import com.fanwe.live.activity.LiveSociatyUpdateEditActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * 加入创建公会对话框
 * Created by Administrator on 2016/9/26.
 */

public class LiveJoinCreateSociatyDialog extends SDDialogBase
{
    @ViewInject(R.id.tv_add_fam)
    private TextView tv_add_fam;//加入公会
    @ViewInject(R.id.tv_new_fam)
    private TextView tv_new_fam;//创建公会

    public LiveJoinCreateSociatyDialog(Activity activity)
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

        SDViewBinder.setTextView(tv_add_fam,"加入公会");
        SDViewBinder.setTextView(tv_new_fam,"创建公会");
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
                Intent intent = new Intent(getOwnerActivity(),LiveSociatySearchJoinActivity.class);
                getOwnerActivity().startActivity(intent);
                dismiss();
                break;
            case R.id.tv_new_fam:
                Intent intentNew = new Intent(getOwnerActivity(),LiveSociatyUpdateEditActivity.class);
                getOwnerActivity().startActivity(intentNew);
                dismiss();
                break;
        }
    }
}
