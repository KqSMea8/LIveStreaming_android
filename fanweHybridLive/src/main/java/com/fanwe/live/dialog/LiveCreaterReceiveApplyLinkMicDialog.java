package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;

import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.live.model.custommsg.CustomMsgApplyLinkMic;

/**
 * 主播收到连麦申请窗口
 */
public class LiveCreaterReceiveApplyLinkMicDialog extends SDDialogConfirm
{
    private CustomMsgApplyLinkMic mMsgApplyLinkMic;
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public LiveCreaterReceiveApplyLinkMicDialog(Activity activity, CustomMsgApplyLinkMic msg)
    {
        super(activity);
        this.mMsgApplyLinkMic = msg;

        setTextContent(msg.getSender().getNick_name() + "向你发来连麦请求").setTextCancel("拒绝").setTextConfirm("接受");
        setCallback(new SDDialogCustomCallback()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                if (clickListener != null)
                {
                    clickListener.onClickReject();
                }
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                if (clickListener != null)
                {
                    clickListener.onClickAccept();
                }
            }
        });
    }

    @Override
    public void show()
    {
        startDismissRunnable(10 * 1000);
        super.show();
    }

    public interface ClickListener
    {
        void onClickAccept();

        void onClickReject();
    }


}
