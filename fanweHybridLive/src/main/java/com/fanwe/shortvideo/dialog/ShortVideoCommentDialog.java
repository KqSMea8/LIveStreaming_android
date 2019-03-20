package com.fanwe.shortvideo.dialog;

import android.app.Activity;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.dialog.LiveBaseDialog;
import com.fanwe.live.event.ELivePrivateChatDialogDissmis;
import com.fanwe.shortvideo.appview.mian.VideoCommentView;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;
import com.fanwe.shortvideo.model.VideoCommentListModel;

/**
 * @author wxy
 * Created by wxy on 2018/2/4.
 */
public class ShortVideoCommentDialog extends LiveBaseDialog {
    private ShortVideoDetailModel.VideoDetail detailModel;
    private SendMsgListener msgListener;
    public ShortVideoCommentDialog(Activity activity, ShortVideoDetailModel.VideoDetail detailModel) {
        super(activity);
        this.detailModel=detailModel;
        init();
    }

    private void init() {
        VideoCommentView videoCommentView = new VideoCommentView(getOwnerActivity());
        setContentView(videoCommentView);
        videoCommentView.requestData(detailModel.sv_id);
        SDViewUtil.setHeight(videoCommentView, SDViewUtil.getScreenHeight() / 2);
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

    public void setSendMsgListener(SendMsgListener listener){
        msgListener=listener;
    }

    public interface SendMsgListener{
        void onSendMsgClick(VideoCommentListModel.CommentItemModel model);
    }
}
