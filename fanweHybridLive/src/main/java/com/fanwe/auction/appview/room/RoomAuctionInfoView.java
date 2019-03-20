package com.fanwe.auction.appview.room;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.activity.AuctionGoodsDetailActivity;
import com.fanwe.auction.event.EDoPaiSuccess;
import com.fanwe.auction.model.App_pai_user_get_videoActModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataInfoModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.custommsg.CustomMsgEndVideo;
import com.fanwe.live.model.custommsg.MsgModel;

/**
 * Created by Administrator on 2016/8/18.
 */
public class RoomAuctionInfoView extends RoomView
{
    public RoomAuctionInfoView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomAuctionInfoView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomAuctionInfoView(Context context)
    {
        super(context);
    }

    private CountDownTimer timer;

    private LinearLayout ll_auction_info;//竞拍信息位置
    private LinearLayout ll_remaining_time;//倒计时位置
    private LinearLayout ll_top_price;
    private TextView tv_top_price;//当前最高价
    private TextView tv_remaining_time;//剩余竞拍时间

    private App_pai_user_get_videoActModel actModel;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_auction_info;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        ll_auction_info = find(R.id.ll_auction_info);
        if (AppRuntimeWorker.getShow_hide_pai_view() == 1)
        {
            SDViewUtil.setVisible(ll_auction_info);
        }

        ll_top_price = find(R.id.ll_top_price);
        ll_top_price.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                PaiUserGoodsDetailDataInfoModel model = actModel.getDataInfo();
                if (model != null)
                {
                    int id = model.getId();
                    boolean isCreater = getLiveActivity().isCreater();
                    if (id > 0)
                    {
                        Intent intent = new Intent(getActivity(), AuctionGoodsDetailActivity.class);
                        intent.putExtra(AuctionGoodsDetailActivity.EXTRA_IS_ANCHOR, isCreater);
                        intent.putExtra(AuctionGoodsDetailActivity.EXTRA_ID, String.valueOf(id));
                        intent.putExtra(AuctionGoodsDetailActivity.EXTRA_IS_SMALL_SCREEN, 1);
                        getActivity().startActivity(intent);
                    } else
                    {
                        SDToast.showToast("id==0");
                    }
                }
            }
        });
        tv_top_price = find(R.id.tv_top_price);
        ll_remaining_time = find(R.id.ll_remaining_time);
        tv_remaining_time = find(R.id.tv_remaining_time);
    }

    public void bindAuctionDetailInfo(App_pai_user_get_videoActModel actModel)
    {
        this.actModel = actModel;
        PaiUserGoodsDetailDataInfoModel info = actModel.getDataInfo();
        if (info != null)
        {
            //接收请求详情成功后显示
            SDViewUtil.setVisible(ll_auction_info);
            SDViewBinder.setTextView(tv_top_price, Integer.toString(info.getLast_pai_diamonds()));
            setCountdownTime(info.getPai_left_time());
        }
    }

    private void setCountdownTime(final long left_time)
    {
        if (left_time > 0)
        {
            SDViewUtil.setVisible(ll_remaining_time);
        }
        if (timer != null)
        {
            timer.cancel();
        }
        timer = new CountDownTimer(left_time * 1000, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                final long hour = SDDateUtil.getDuringHours(millisUntilFinished);
                final long min = SDDateUtil.getDuringMinutes(millisUntilFinished);
                final long sec = SDDateUtil.getDuringSeconds(millisUntilFinished);
                tv_remaining_time.setText(Long.toString(hour) + "时" + Long.toString(min) + "分" + Long.toString(sec) + "秒");
            }

            @Override
            public void onFinish()
            {
                //本地隐藏拍卖锤子
                ll_remaining_time.setVisibility(View.INVISIBLE);
                tv_remaining_time.setText("已结束");
            }
        };
        timer.start();
    }

    @Override
    public void onMsgAuction(MsgModel msg)
    {
        super.onMsgAuction(msg);

        if (msg.getCustomMsgType() == LiveConstant.CustomMsgType.MSG_AUCTION_OFFER)
        {
            CustomMsgAuctionOffer customMsgAuctionOffer = msg.getCustomMsgAuctionOffer();
            SDViewBinder.setTextView(tv_top_price, Integer.toString(customMsgAuctionOffer.getPai_diamonds()));
            if (customMsgAuctionOffer.getYanshi() == 1)
            {
                setCountdownTime(customMsgAuctionOffer.getPai_left_time());
            }
        }
    }

    @Override
    public void onMsgEndVideo(CustomMsgEndVideo msg)
    {
        super.onMsgEndVideo(msg);
        //主播强行关闭直播间时
        setCountdownTime(0);
    }

    public void onEventMainThread(EDoPaiSuccess event)
    {
        String s_top_price = tv_top_price.getText().toString();
        int i_top_price = Integer.valueOf(s_top_price);
        if (event.last_pai_diamonds > i_top_price)
        {
            SDViewBinder.setTextView(tv_top_price, Integer.toString(event.last_pai_diamonds));
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        timer.cancel();
    }
}
