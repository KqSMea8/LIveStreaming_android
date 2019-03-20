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
 * Created by Administrator on 2016/11/18.
 */

public class RoomLivePayInfoCreaterView extends RoomView
{
    private TextView tv_live_viewer;
    private LinearLayout ll_tv_payinfo;
    private TextView tv_pay_info;
    private TextView tv_money;

    public RoomLivePayInfoCreaterView(Context context)
    {
        super(context);
        init();
    }

    public RoomLivePayInfoCreaterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomLivePayInfoCreaterView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_pay_creater_live_pay_info);
        tv_money = find(R.id.tv_money);
        ll_tv_payinfo = find(R.id.ll_tv_payinfo);
        tv_pay_info = find(R.id.tv_pay_info);
        tv_live_viewer = find(R.id.tv_live_viewer);
    }

    public void bindData(int fee)
    {
        SDViewBinder.setTextView(tv_money, Integer.toString(fee));
    }

    /**
     * 设置付费人数
     *
     * @param num
     */
    public void setViewerNum(int num)
    {
        tv_live_viewer.setText(Integer.toString(num));
    }

    public void setPayInfoCountDownTime(long lefttime)
    {
        if (lefttime > 0)
        {
            SDViewUtil.setVisible(ll_tv_payinfo);
            tv_pay_info.setText(Long.toString(lefttime / 1000));
        } else
        {
            SDViewUtil.setGone(ll_tv_payinfo);
        }
    }
}
