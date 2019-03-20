package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fanwe.live.R;

/**
 * 直播间娃娃view
 */
public class RoomWawaView extends RoomView {

    public ImageView wawa_line;
    public ImageView wawa_stub;
    public FrameLayout bottom_view;
    public ImageView bottom_iv;

    public RoomWawaView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomWawaView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomWawaView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_wawa;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        wawa_line = find(R.id.wawa_line);
        wawa_stub = find(R.id.wawa_stub);
        bottom_view = find(R.id.bottom_view);
        bottom_iv = find(R.id.bottom_iv);

    }

}
