package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.live.R;

/**
 * Created by shibx on 2017/2/6.
 * PC端直播间 聊天页面(代替fragment)
 */

public class RoomPCMessageView extends RoomView implements ARoomSendMsgView.RoomSendMsgViewListener
{

    private RoomMsgView mMsgView;

    private RoomPCSendMsgPortView mRoomPCSendMsgView;

    private RoomMessageViewListener mListener;

    public RoomPCMessageView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomPCMessageView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomPCMessageView(Context context)
    {
        super(context);
        init();
    }

    public RoomPCMessageView(Context context, RoomMessageViewListener listener)
    {
        super(context);
        this.mListener = listener;
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_live_room_message;
    }

    protected void init()
    {
        mMsgView = find(R.id.rmv_frag_tab_msg);
        mRoomPCSendMsgView = find(R.id.rsmv_frag_tab_msg);
        initRoomSendMsgView();
    }


    private void initRoomSendMsgView()
    {
        mRoomPCSendMsgView.setRoomSendMsgViewListener(this);
    }

    @Override
    public void onClickGift(View view)
    {
        //调用监听器接口方法
        //在activity内实现具体操作
        mListener.showGiftViewPort(view);
    }

    public interface RoomMessageViewListener
    {
        void showGiftViewPort(View view);
    }
}
