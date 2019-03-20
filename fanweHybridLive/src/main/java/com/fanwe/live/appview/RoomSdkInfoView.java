package com.fanwe.live.appview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.model.LiveQualityData;

/**
 * 直播间上行，下行等sdk信息
 */
public class RoomSdkInfoView extends RoomView
{
    private LinearLayout ll_down_speed;
    private TextView tv_down_speed;

    private LinearLayout ll_up_speed;
    private TextView tv_up_arrow;
    private TextView tv_up_speed;

    public RoomSdkInfoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomSdkInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomSdkInfoView(Context context)
    {
        super(context);
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        setContentView(R.layout.view_room_sdk_info);

        ll_down_speed = find(R.id.ll_down_speed);
        tv_down_speed = find(R.id.tv_down_speed);

        ll_up_speed = find(R.id.ll_up_speed);
        tv_up_arrow = find(R.id.tv_up_arrow);
        tv_up_speed = find(R.id.tv_up_speed);
    }

    /**
     * 更新显示数据
     *
     * @param data
     */
    public void updateLiveQuality(LiveQualityData data)
    {
        try
        {
            if (data != null)
            {
                double upSpeed = data.getSendKBps();
                double downSpeed = data.getRecvKBps();
                setUpSpeed(upSpeed);
                setDownSpeed(downSpeed);

                setUpQualityLevel(data.getSendLossRateQuality());
                if (ApkConstant.DEBUG)
                {
                    LogUtil.i(data.toString());
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 设置下行速率
     *
     * @param kBps
     */
    public void setDownSpeed(double kBps)
    {
        if (kBps <= 0)
        {
            SDViewUtil.setGone(ll_down_speed);
        } else
        {
            tv_down_speed.setText(formatSpeed(kBps));
            SDViewUtil.setVisible(ll_down_speed);
        }
    }

    /**
     * 设置上行速率
     *
     * @param kBps
     */
    public void setUpSpeed(double kBps)
    {
        if (kBps <= 0)
        {
            SDViewUtil.setGone(ll_up_speed);
        } else
        {
            tv_up_speed.setText(formatSpeed(kBps));
            SDViewUtil.setVisible(ll_up_speed);
        }
    }

    /**
     * 设置上行质量级别
     *
     * @param level
     */
    public void setUpQualityLevel(int level)
    {
        switch (level)
        {
            case LiveQualityData.QUALITY_NORMAL:
                tv_up_arrow.setTextColor(Color.WHITE);
                tv_up_speed.setTextColor(Color.WHITE);
                break;
            case LiveQualityData.QUALITY_WARNING:
                tv_up_arrow.setTextColor(Color.YELLOW);
                tv_up_speed.setTextColor(Color.YELLOW);
                break;
            case LiveQualityData.QUALITY_BAD:
                tv_up_arrow.setTextColor(Color.RED);
                tv_up_speed.setTextColor(Color.RED);
                break;
            default:
                break;
        }
    }

    private String formatSpeed(double kBps)
    {
        String result = kBps + "K";
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        return false;
    }
}
