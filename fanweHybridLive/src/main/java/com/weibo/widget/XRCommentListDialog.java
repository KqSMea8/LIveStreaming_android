package com.weibo.widget;

import android.app.Activity;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.dialog.LiveBaseDialog;
import com.fanwe.live.event.ELivePrivateChatDialogDissmis;
import com.fanwe.shortvideo.appview.mian.*;
import com.weibo.model.XRCommentListModel;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class XRCommentListDialog extends LiveBaseDialog {
    private int weibo_id;

    public XRCommentListDialog(Activity activity, int weibo_id) {
        super(activity);
        this.weibo_id = weibo_id;
        init();
    }

    private void init() {
        XRCommentView commentView = new XRCommentView(getOwnerActivity());
        setContentView(commentView);
        commentView.requestData(weibo_id);
        SDViewUtil.setHeight(commentView, SDViewUtil.getScreenHeight() / 2);
        setCanceledOnTouchOutside(true);
        paddingLeft(0);
        paddingRight(0);
        paddingBottom(0);
    }

    /*私人聊天窗口点击外面关闭的时候关闭当前私聊*/
    public void onEventMainThread(ELivePrivateChatDialogDissmis event) {
        if (event.dialog_close_type == 0) {
            dismiss();
        }
    }
}
