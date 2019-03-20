package com.fanwe.pay.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogConfirm;

/**
 * 是否进入付费直播窗口
 */
public class LiveJoinPayDialog extends SDDialogConfirm
{
    public LiveJoinPayDialog(Activity activity)
    {
        super(activity);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        setTextContent("是否进入付费直播？")
                .setTextCancel("取消")
                .setTextConfirm("进入");
    }

    public void joinPaysetTextContent(int fee, int live_pay_type)
    {
        if (live_pay_type == 1)
        {
            setTextTitle("按场收费直播间");
            setTextContent("需要支付"+fee+"秀豆,是否进入？");
        } else
        {
            setTextTitle("按时收费直播间");
            setTextContent("需要支付"+fee+"秀豆/分钟,是否进入？");
        }
    }
}
