package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.live.R;
import com.fanwe.live.appview.LiveLargeGiftInfoView;
import com.fanwe.live.model.custommsg.CustomMsgLargeGift;

import java.util.LinkedList;

/**
 * 直播间大型礼物动画通知view
 */
public class RoomLargeGiftInfoView extends RoomLooperMainView<CustomMsgLargeGift>
{
    public RoomLargeGiftInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomLargeGiftInfoView(Context context)
    {
        super(context);
        init();
    }
    int data_type=1;

    public int getData_type() {
        return data_type;
    }

    public void setData_type(int data_type) {
        this.data_type = data_type;
    }

    private LiveLargeGiftInfoView view_gift_info;

    private LargeGiftInfoViewCallback mCallback;

    private void init()
    {
        setContentView(R.layout.view_room_large_gift_info);
        view_gift_info = (LiveLargeGiftInfoView) findViewById(R.id.view_gift_info);

        view_gift_info.setOnClickListener(this);
    }

    public void setCallback(LargeGiftInfoViewCallback callback)
    {
        mCallback = callback;
    }

    @Override
    protected void looperWork(final LinkedList<CustomMsgLargeGift> queue)
    {
        if (!view_gift_info.isPlaying())
        {
            view_gift_info.playMsg(queue.poll());
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == view_gift_info)
        {
            if (mCallback != null)
            {
                mCallback.onClickInfoView(view_gift_info.getMsg());
            }
        }
    }

    @Override
    public void onMsgLargeGift(CustomMsgLargeGift customMsgLargeGift)
    {
        super.onMsgLargeGift(customMsgLargeGift);
        if(customMsgLargeGift.getData_type()==data_type){
            offerModel(customMsgLargeGift);
        }
    }

    public interface LargeGiftInfoViewCallback
    {
        void onClickInfoView(CustomMsgLargeGift msg);
    }
}
