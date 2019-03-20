package com.fanwe.auction.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;


/**
 * Created by Administrator on 2016/9/7.
 */
public class RoomOfferChangeView extends BaseAppView
{
    private FrameLayout fl_content;
    private ImageView image1;
    private ImageView image2;
    private TextView tv_num;
    private ISDLooper mHandler;//竞拍倒计时
    private TimerRunnable mRunnable;
    private long mil;//秒值
    private long second;//秒值
    private OnOkClickListener onOkClickListener;


    public RoomOfferChangeView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomOfferChangeView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomOfferChangeView(Context context)
    {
        super(context);
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_rechang;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        register();
    }

    private void register()
    {
        fl_content = find(R.id.fl_content);
        tv_num = find(R.id.tv_num);
        image1 = new ImageView(getContext());
        image1.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_auction_btn_submit));
        image2 = new ImageView(getContext());
        image2.setImageDrawable(getContext().getResources().getDrawable(R.drawable.ic_auction_btn_unsubmit));
        second = SDResourcesUtil.getResources().getInteger(R.integer.auction_btn_time);//初始化秒数
        tv_num.setText("");
        image1.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (onOkClickListener != null)
                {
                    if (onOkClickListener.onClick(view))
                    {
                        replaceView(fl_content, image2);
                        showTime(second);//秒值
                    }
                }
            }
        });

        fl_content.addView(image1);
    }

    public void setOnOkClickListener(OnOkClickListener onOkClickListener)
    {
        this.onOkClickListener = onOkClickListener;
    }

    public interface OnOkClickListener
    {
        boolean onClick(View view);
    }

    private void showTime(long mil)
    {
        if (mil == 0)
        {
            return;
        }
        this.mil = mil;
        if (mHandler == null)
        {
            mHandler = new SDSimpleLooper();
        }
        if (mRunnable == null)
        {
            mRunnable = new TimerRunnable();
        }
        mHandler.start(1000, mRunnable);
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (mHandler != null)
        {
            mHandler.stop();
        }
    }

    private class TimerRunnable implements Runnable
    {
        @Override
        public void run()
        {
            if (mil >= 0)
            {
                long sec = SDDateUtil.getDuringSeconds(mil * 1000);
                tv_num.setText("(" + Long.toString(sec) + ")");
                mil--;
            } else
            {
                replaceView(fl_content, image1);
                tv_num.setText("");
                mHandler.stop();
            }
        }
    }

}
