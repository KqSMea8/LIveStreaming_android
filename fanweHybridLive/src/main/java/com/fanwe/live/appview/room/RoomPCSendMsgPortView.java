package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.fanwe.live.R;

public class RoomPCSendMsgPortView extends ARoomSendMsgView {

    public RoomPCSendMsgPortView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomPCSendMsgPortView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomPCSendMsgPortView(Context context) {
        super(context);
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_live_pc_send_msg_port;
    }

    @Override
    protected void initSendMsgView() {
        sl_btn = find(R.id.sl_btn);
        iv_pop_msg_handle = find(R.id.iv_pop_msg_handle);
        et_content = find(R.id.et_content);
        tv_send = find(R.id.tv_send);
        iv_gift = find(R.id.iv_gift);
        tv_send.setOnClickListener(this);
        iv_gift.setOnClickListener(this);
    }

    @Override
    protected void onClickGift(View view) {
        mListener.onClickGift(view);
    }
}
