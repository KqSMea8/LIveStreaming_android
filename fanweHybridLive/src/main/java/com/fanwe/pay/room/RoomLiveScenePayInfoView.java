package com.fanwe.pay.room;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;

/**
 * Created by Administrator on 2017/1/5.
 */

public class RoomLiveScenePayInfoView extends RoomView
{
    private TextView tv_money;
    private LinearLayout ll_live_viewer;
    private TextView tv_live_viewer;

    public RoomLiveScenePayInfoView(Context context)
    {
        super(context);
        init();
    }

    public RoomLiveScenePayInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomLiveScenePayInfoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_scene_pay_info);
        tv_money = find(R.id.tv_money);
        ll_live_viewer = find(R.id.ll_live_viewer);
        tv_live_viewer = find(R.id.tv_live_viewer);

        if (getLiveActivity().isCreater())
        {
            SDViewUtil.setVisible(ll_live_viewer);
        }
    }

    public void bindData(int live_fee)
    {
        SDViewBinder.setTextView(tv_money, Integer.toString(live_fee));
    }

    /**
     * 设置按场付费直播人数
     */
    public void setScenePayLiveViewerNum(int num)
    {
        tv_live_viewer.setText(Integer.toString(num));
    }
}
