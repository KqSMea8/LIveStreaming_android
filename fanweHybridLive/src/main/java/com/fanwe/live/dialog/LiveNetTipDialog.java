package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogConfirm;

/**
 * Created by Administrator on 2016/7/11.
 */
public class LiveNetTipDialog extends SDDialogConfirm
{
    public LiveNetTipDialog(Activity activity)
    {
        super(activity);
    }

    @Override
    protected void init()
    {
        super.init();
        setTextContent("当前处于数据网络下，会耗费较多流量，是否继续？").setTextCancel("否").setTextConfirm("是").show();
    }
}
