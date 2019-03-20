package com.fanwe.shortvideo.appview.mian;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_pop_msgActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgText;
import com.fanwei.jubaosdk.common.util.ToastUtil;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

public class VideoSendMsgView extends RoomView {
    private EditText et_content;
    private TextView tv_send;
    private String strContent;
    private String sv_id;
    private String user_id;
    private static final int MAX_INPUT_LENGTH = 200;
    private UpdateCommentNum updateCommentNum;

    public VideoSendMsgView(Context context) {
        this(context, null);
    }

    public VideoSendMsgView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoSendMsgView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_video_send_msg;
    }

    protected void init() {
        et_content = find(R.id.et_content);
        tv_send = find(R.id.tv_send);
//        SDViewUtil.setInvisible(this);
        register();
    }

    public void setSvId(String sv_id){
        this.sv_id=sv_id;
    }

    public void setHintText(String hintText,String toUserId){
        et_content.setHint(hintText);
        this.user_id=toUserId;
    }

    public void setContent(String content)
    {
        if (content == null)
        {
            content = "";
        }
        et_content.setText(content);
        et_content.setSelection(et_content.getText().length());
        SDKeyboardUtil.showKeyboard(et_content, 100);
    }

    private void register() {

        et_content.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER) {
                    return true;
                }
                return false;
            }
        });

        tv_send.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (validateContent()) {
                    sendMessage();
                }
            }
        });
    }

    private boolean validateContent() {
        if (!SDNetworkReceiver.isNetworkConnected(getActivity())) {
            SDToast.showToast("无网络");
            return false;
        }

        strContent = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(strContent)) {
            SDToast.showToast("请输入内容");
            return false;
        }

        if (strContent.contains("\n")) {
            strContent = strContent.replace("\n", "");
        }

        if (strContent.length() > MAX_INPUT_LENGTH) {
            strContent = strContent.substring(0, MAX_INPUT_LENGTH);
        }

        return true;
    }

    protected void sendMessage() {
        Log.e("bmbmbmbmbm",user_id+"user_id");
        CommonInterface.requestAddComment(sv_id, strContent, user_id,new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.isOk()) {
                    et_content.setText("");
                    SDToast.showToast("发布评论成功");
                    SDViewUtil.setInvisible(VideoSendMsgView.this);
                    updateCommentNum.updateNum();
                }
            }

            @Override
            protected void onError(SDResponse resp) {

            }
        });
    }

    @Override
    protected boolean onTouchDownOutside(MotionEvent ev) {
        SDViewUtil.setInvisible(this);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        SDViewUtil.setInvisible(this);
        return true;
    }

    @Override
    public void onVisibilityChanged(View view, int visibility) {
        super.onVisibilityChanged(view, visibility);
        if (view == this) {
            if (View.VISIBLE == visibility) {
                SDKeyboardUtil.showKeyboard(et_content);
            } else {
                SDKeyboardUtil.hideKeyboard(et_content);
            }
        }
    }
    public void setUpdateCommentNumListener(UpdateCommentNum updateCommentNum){
        this.updateCommentNum=updateCommentNum;
    }
    public interface UpdateCommentNum{
        void updateNum();
    }
}
