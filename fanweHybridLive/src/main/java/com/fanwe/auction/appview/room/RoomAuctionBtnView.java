package com.fanwe.auction.appview.room;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.auction.activity.AuctionGoodsDetailActivity;
import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.event.EDoPaiSuccess;
import com.fanwe.auction.event.EGinsengShootMarginSuccess;
import com.fanwe.auction.model.App_pai_user_dopaiActModel;
import com.fanwe.auction.model.App_pai_user_get_videoActModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataInfoModel;
import com.fanwe.auction.model.custommsg.CustomMsgAuctionOffer;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.custommsg.MsgModel;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Administrator on 2016/8/19.
 */
public class RoomAuctionBtnView extends RoomView
{
    public RoomAuctionBtnView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomAuctionBtnView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomAuctionBtnView(Context context)
    {
        super(context);
    }

    private RoomOfferChangeView ll_rechange_view;
    private LinearLayout ll_parent_last_pai_diamonds;
    private TextView tv_last_pai_diamonds;

    private App_pai_user_get_videoActModel actModel;

    private int mLast_pai_diamonds;//最高价

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_auction_btn_view;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        register();
    }

    private void register()
    {
        ll_parent_last_pai_diamonds = find(R.id.ll_parent_last_pai_diamonds);
        if (AppRuntimeWorker.getShow_hide_pai_view() == 1)
        {
            SDViewUtil.setVisible(ll_parent_last_pai_diamonds);
        }
        ll_rechange_view = find(R.id.ll_rechange_view);
        ll_rechange_view.setOnOkClickListener(new RoomOfferChangeView.OnOkClickListener()
        {
            @Override
            public boolean onClick(View v)
            {
                return verifyRequestParams();
            }
        });
        tv_last_pai_diamonds = find(R.id.tv_last_pai_diamonds);
    }

    public void bindData(App_pai_user_get_videoActModel app_pai_user_goods_detailActModel)
    {
        this.actModel = app_pai_user_goods_detailActModel;
        PaiUserGoodsDetailDataInfoModel model = app_pai_user_goods_detailActModel.getDataInfo();
        if (model != null)
        {
            this.mLast_pai_diamonds = model.getLast_pai_diamonds();
            SDViewBinder.setTextView(tv_last_pai_diamonds, model.getJj_diamonds());
            if (model.getStatus() == 0)
            {
                SDViewUtil.setVisible(ll_parent_last_pai_diamonds);
            } else
            {
                SDViewUtil.setGone(ll_parent_last_pai_diamonds);
            }
        }
    }

    private boolean verifyRequestParams()
    {
        if (actModel == null)
        {
            return false;
        }

        PaiUserGoodsDetailDataInfoModel model = actModel.getDataInfo();
        if (model == null)
        {
            return false;
        }

        int has_join = actModel.getData().getHas_join();
        if (has_join == 0)
        {
            startGinsengShootMarginActivity();
            return false;
        }
        requestPaiUserDopai(model.getId());
        return true;
    }

    private void requestPaiUserDopai(final int id)
    {
        AuctionCommonInterface.requestPaiUserDopai(id, mLast_pai_diamonds, new AppRequestCallback<App_pai_user_dopaiActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {

                } else if (actModel.getStatus() == 10052)
                {
                    startGinsengShootMarginActivity();
                } else if (actModel.getStatus() == 10053)
                {
                    mLast_pai_diamonds = actModel.getPai_diamonds();
                    ;
                    EDoPaiSuccess eDoPaiSuccess = new EDoPaiSuccess();
                    eDoPaiSuccess.last_pai_diamonds = actModel.getPai_diamonds();
                    SDEventManager.post(eDoPaiSuccess);
                }
            }
        });
    }

    //未交保证金
    private void startGinsengShootMarginActivity()
    {
        if (actModel != null && actModel.getDataInfo() != null)
        {
            PaiUserGoodsDetailDataInfoModel model = actModel.getDataInfo();

            Intent intent = new Intent(getActivity(), AuctionGoodsDetailActivity.class);
            intent.putExtra(AuctionGoodsDetailActivity.EXTRA_IS_ANCHOR, getLiveActivity().isCreater());
            intent.putExtra(AuctionGoodsDetailActivity.EXTRA_ID, String.valueOf(model.getId()));
            intent.putExtra(AuctionGoodsDetailActivity.EXTRA_IS_SMALL_SCREEN, 1);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onMsgAuction(MsgModel msg)
    {
        super.onMsgAuction(msg);

        int type = msg.getCustomMsgType();
        if (LiveConstant.CustomMsgType.MSG_AUCTION_OFFER == type)
        {
            CustomMsgAuctionOffer customMsgAuctionOffer = msg.getCustomMsgAuctionOffer();
            this.mLast_pai_diamonds = customMsgAuctionOffer.getPai_diamonds();
        } else if (LiveConstant.CustomMsgType.MSG_AUCTION_NOTIFY_PAY == type)
        {
            SDViewUtil.setGone(ll_parent_last_pai_diamonds);
        } else if (LiveConstant.CustomMsgType.MSG_AUCTION_PAY_SUCCESS == type)
        {
            SDViewUtil.setGone(ll_parent_last_pai_diamonds);
        } else if (LiveConstant.CustomMsgType.MSG_AUCTION_FAIL == type)
        {
            SDViewUtil.setGone(ll_parent_last_pai_diamonds);
        } else if (LiveConstant.CustomMsgType.MSG_AUCTION_SUCCESS == type)
        {
            SDViewUtil.setGone(ll_parent_last_pai_diamonds);
        }
    }

    //保证金缴纳成功
    public void onEventMainThread(EGinsengShootMarginSuccess event)
    {
        if (actModel != null && actModel.getData() != null)
        {
            actModel.getData().setHas_join(1);
        }
    }

}
