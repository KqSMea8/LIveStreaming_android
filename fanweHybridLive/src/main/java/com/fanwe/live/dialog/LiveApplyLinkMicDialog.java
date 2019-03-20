package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.dialog.SDDialogConfirm;

/**
 * 申请连麦窗口
 */
public class LiveApplyLinkMicDialog extends SDDialogConfirm
{
    public LiveApplyLinkMicDialog(Activity activity)
    {
        super(activity);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        setTextContent("申请连麦中，等待对方应答...").setTextConfirm(null).setTextCancel("取消连麦");
    }
}
