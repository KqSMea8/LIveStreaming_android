package com.fanwe.pay.room;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.pay.model.App_live_live_pay_deductActModel;

/**
 * Created by Administrator on 2016/11/24.
 */

public class RoomLivePayInfoViewerView extends RoomView
{
    private LinearLayout ll_tv_payinfo;
    private TextView tv_pay_info;

    private TextView tv_money;

    private TextView tv_time;

    private CountDownTimer timer;

    public RoomLivePayInfoViewerView(Context context)
    {
        super(context);
        init();
    }

    public RoomLivePayInfoViewerView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomLivePayInfoViewerView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_pay_viewer_live_pay_info);
        ll_tv_payinfo = find(R.id.ll_tv_payinfo);
        tv_pay_info = find(R.id.tv_pay_info);

        tv_money = find(R.id.tv_money);

        tv_time = find(R.id.tv_time);
    }

    public void bindData(App_live_live_pay_deductActModel model)
    {
        SDViewBinder.setTextView(tv_money, Integer.toString(model.getLive_fee()));
        SDViewBinder.setTextView(tv_time, Integer.toString(model.getTotal_minute()));
    }

    public void onPayModeCountDown(long leftTime)
    {
        long second = leftTime / 1000;
        if (second > 0)
        {
            SDViewUtil.setVisible(ll_tv_payinfo);
            tv_pay_info.setText(Long.toString(second));
        } else
        {
            SDViewUtil.setGone(ll_tv_payinfo);
        }
    }
}
