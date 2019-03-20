package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.fanwe.live.R;

/**
 * PC端直播 横屏消息输入框(需抽取基类 封装)
 * {@link RoomPCLivePortControlView} 竖屏输入框
 */
public class RoomPCSendMsgLandView extends ARoomSendMsgView {

    public RoomPCSendMsgLandView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomPCSendMsgLandView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomPCSendMsgLandView(Context context) {
        super(context);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_live_pc_send_msg_land;
    }

    @Override
    protected void initSendMsgView() {
        sl_btn = find(R.id.sl_btn);
        iv_pop_msg_handle = find(R.id.iv_pop_msg_handle);
        et_content = find(R.id.et_content);
        tv_send = find(R.id.tv_send);
        tv_send.setOnClickListener(this);
    }

    @Override
    protected void onClickGift(View view) {
        mListener.onClickGift(view);
    }
}
