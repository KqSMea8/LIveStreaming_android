package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogConfirm;

/**
 * 申请连麦窗口
 */
public class LiveApplyPKDialog extends SDDialogConfirm
{
    public LiveApplyPKDialog(Activity activity)
    {
        super(activity);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setTextContent("申请PK中，等待对方应答...").setTextConfirm(null).setTextCancel("取消PK");
    }
}
