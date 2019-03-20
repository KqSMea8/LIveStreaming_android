package com.fanwe.live.dialog;

import android.app.Activity;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.appview.LiveChatC2CNewView;
import com.fanwe.live.event.ELivePrivateChatDialogDissmis;
import com.fanwe.live.model.LiveConversationListModel;

/**
 * Created by Administrator on 2016/7/16.
 */
public class LiveChatC2CDialog extends LiveBaseDialog
{
    public LiveChatC2CDialog(Activity activity)
    {
        super(activity);
        init();
    }

    private void init()
    {
        LiveChatC2CNewView liveChatC2CNewView = new LiveChatC2CNewView(getOwnerActivity());
        liveChatC2CNewView.setClickListener(new LiveChatC2CNewView.ClickListener()
        {
            @Override
            public void onClickBack()
            {
                dismiss();
            }
        });
        liveChatC2CNewView.setOnChatItemClickListener(new LiveChatC2CNewView.OnChatItemClickListener()
        {

            @Override
            public void onChatItemClickListener(LiveConversationListModel itemLiveChatListModel)
            {
                LivePrivateChatDialog dialog = new LivePrivateChatDialog(getOwnerActivity(), itemLiveChatListModel.getPeer());
                dialog.showBottom();
            }
        });
        setContentView(liveChatC2CNewView);
        liveChatC2CNewView.requestData();
        SDViewUtil.setHeight(liveChatC2CNewView, SDViewUtil.getScreenHeight() / 2);

        setCanceledOnTouchOutside(true);
        paddingLeft(0);
        paddingRight(0);
        paddingBottom(0);
    }


    /*私人聊天窗口点击外面关闭的时候关闭当前私聊*/
    public void onEventMainThread(ELivePrivateChatDialogDissmis event)
    {
        if (event.dialog_close_type == 0)
        {
            dismiss();
        }
    }
}
