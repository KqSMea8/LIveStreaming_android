package com.fanwe.auction.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.auction.model.App_pai_user_goods_detailActModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataInfoModel;
import com.fanwe.library.looper.ISDLooper;
import com.fanwe.library.looper.impl.SDSimpleLooper;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * Created by Administrator on 2016/8/10.
 */
public class AuctionGoodsDetailStatus0View extends BaseAppView
{
    private TextView tv_last_pai_diamonds;
    private TextView tv_hour;
    private TextView tv_min;
    private TextView tv_sec;

    private ISDLooper handler = new SDSimpleLooper();

    public AuctionGoodsDetailStatus0View(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionGoodsDetailStatus0View(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionGoodsDetailStatus0View(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_auction_goods_detail_status_0);
        register();
    }

    private void register()
    {
        tv_last_pai_diamonds = find(R.id.tv_last_pai_diamonds);
        tv_hour = find(R.id.tv_hour);
        tv_min = find(R.id.tv_min);
        tv_sec = find(R.id.tv_sec);
    }

    public void bindData(App_pai_user_goods_detailActModel actModel)
    {
        if (actModel != null && actModel.getData() != null && actModel.getData().getInfo() != null)
        {
            PaiUserGoodsDetailDataInfoModel info = actModel.getData().getInfo();
            SDViewBinder.setTextView(tv_last_pai_diamonds, Integer.toString(info.getLast_pai_diamonds()));
            setCountdownTime(info.getPai_left_time());
        }
    }

    private void setCountdownTime(final long left_time)
    {
        handler.start(0, 1000, new Runnable()
        {
            long time = left_time;

            @Override
            public void run()
            {
                if (time >= 0)
                {
                    final long hour = SDDateUtil.getDuringHours(time * 1000);
                    final long min = SDDateUtil.getDuringMinutes(time * 1000);
                    final long sec = SDDateUtil.getDuringSeconds(time * 1000);

                    if (hour < 10)
                    {
                        tv_hour.setText("0" + Long.toString(hour));
                    } else
                    {
                        tv_hour.setText(Long.toString(hour));
                    }
                    if (min < 10)
                    {
                        tv_min.setText("0" + Long.toString(min));
                    } else
                    {
                        tv_min.setText(Long.toString(min));
                    }
                    if (sec < 10)
                    {
                        tv_sec.setText("0" + Long.toString(sec));
                    } else
                    {
                        tv_sec.setText(Long.toString(sec));
                    }
                    time--;
                    if (time <= 0)
                    {
                        if (countdownInterface != null)
                        {
                            countdownInterface.onCountdownEnd();
                        }
                    }
                } else
                {
                    handler.stop();
                }
            }
        });
    }

    private CountdownInterface countdownInterface;

    public void setCountdownInterface(CountdownInterface countdownInterface)
    {
        this.countdownInterface = countdownInterface;
    }

    public interface CountdownInterface
    {
        void onCountdownEnd();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        handler.stop();
        super.onDetachedFromWindow();
    }
}
