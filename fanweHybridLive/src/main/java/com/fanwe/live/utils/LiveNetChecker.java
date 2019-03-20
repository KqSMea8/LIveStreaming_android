package com.fanwe.live.utils;

import android.app.Activity;
import android.view.View;

import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.dialog.LiveNetTipDialog;

/**
 * 网络检查类
 */
public class LiveNetChecker
{
    public static void check(final Activity activity, final CheckResultListener listener)
    {
        SDNetworkReceiver.NetworkType type = SDNetworkReceiver.getNetworkType(activity);
        switch (type)
        {
            case Mobile:
                new LiveNetTipDialog(activity).setmListener(new SDDialogCustom.SDDialogCustomListener()
                {
                    @Override
                    public void onClickCancel(View v, SDDialogCustom dialog)
                    {
                        if (listener != null)
                        {
                            listener.onRejected();
                        }
                    }

                    @Override
                    public void onClickConfirm(View v, SDDialogCustom dialog)
                    {
                        if (listener != null)
                        {
                            listener.onAccepted();
                        }
                    }

                    @Override
                    public void onDismiss(SDDialogCustom dialog)
                    {
                    }
                }).show();
                break;
            case None:
                SDToast.showToast("无网络");
                break;
            case Wifi:
                if (listener != null)
                {
                    listener.onAccepted();
                }
                break;
            default:
                break;
        }
    }

    public interface CheckResultListener
    {
        void onAccepted();

        void onRejected();
    }

}
