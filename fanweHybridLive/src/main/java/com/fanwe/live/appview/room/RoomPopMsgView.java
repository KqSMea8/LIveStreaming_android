package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.live.R;
import com.fanwe.live.model.custommsg.CustomMsgPopMsg;
import com.fanwe.live.view.LivePopMsgView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 弹幕消息
 *
 * @author Administrator
 * @date 2016-5-20 下午5:21:09
 */
public class RoomPopMsgView extends RoomLooperMainView<CustomMsgPopMsg>
{
    public RoomPopMsgView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomPopMsgView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomPopMsgView(Context context)
    {
        super(context);
    }

    private static final long DURATION_LOOPER = 500;

    private LivePopMsgView view_pop_msg0;
    private LivePopMsgView view_pop_msg1;

    private List<LivePopMsgView> listView;


    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_pop_msg;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        view_pop_msg0 = find(R.id.view_pop_msg0);
        view_pop_msg1 = find(R.id.view_pop_msg1);

        listView = new ArrayList<>();

        listView.add(view_pop_msg0);
        listView.add(view_pop_msg1);
    }

    @Override
    public void onMsgPopMsg(CustomMsgPopMsg msg)
    {
        super.onMsgPopMsg(msg);
        offerModel(msg);
    }

    @Override
    protected void startLooper(long period)
    {
        super.startLooper(DURATION_LOOPER);
    }

    @Override
    protected void looperWork(LinkedList<CustomMsgPopMsg> queue)
    {
        for (LivePopMsgView item : listView)
        {
            if (item.canPlay())
            {
                item.playMsg(queue.poll());
            }
        }
    }

}
