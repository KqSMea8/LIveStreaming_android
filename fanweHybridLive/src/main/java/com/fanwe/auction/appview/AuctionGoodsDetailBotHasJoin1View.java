package com.fanwe.auction.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.fanwe.auction.common.AuctionCommonInterface;
import com.fanwe.auction.model.App_auction_pai_user_go_videoActModel;
import com.fanwe.auction.model.App_pai_user_goods_detailActModel;
import com.fanwe.auction.model.PaiUserGoodsDetailDataInfoModel;
import com.fanwe.hybrid.dialog.SDProgressDialog;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.JoinLiveData;

/**
 * Created by Administrator on 2016/8/9.
 */
public class AuctionGoodsDetailBotHasJoin1View extends BaseAppView
{
    private App_pai_user_goods_detailActModel app_pai_user_goods_detailActModel;
    private boolean is_web_start;

    public App_pai_user_goods_detailActModel getApp_pai_user_goods_detailActModel()
    {
        return app_pai_user_goods_detailActModel;
    }

    public void setApp_pai_user_goods_detailActModel(App_pai_user_goods_detailActModel app_pai_user_goods_detailActModel)
    {
        this.app_pai_user_goods_detailActModel = app_pai_user_goods_detailActModel;
    }

    public boolean is_web_start()
    {
        return is_web_start;
    }

    public void setIs_web_start(boolean is_web_start)
    {
        this.is_web_start = is_web_start;
    }

    public AuctionGoodsDetailBotHasJoin1View(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public AuctionGoodsDetailBotHasJoin1View(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public AuctionGoodsDetailBotHasJoin1View(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.include_auction_goods_bottom_status_1);
        register();
    }

    private void register()
    {
        Button btn_take_part_in = find(R.id.btn_take_part_in);
        btn_take_part_in.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (is_web_start)
                {
                    requestPaiUserGoVideo();
                } else
                {
                    getActivity().finish();
                }
            }
        });
    }

    private void requestPaiUserGoVideo()
    {
        if (app_pai_user_goods_detailActModel != null && app_pai_user_goods_detailActModel.getDataInfo() != null)
        {
            PaiUserGoodsDetailDataInfoModel data= app_pai_user_goods_detailActModel.getDataInfo();

            AuctionCommonInterface.requestPaiUserGoVideo(data.getId(), new AppRequestCallback<App_auction_pai_user_go_videoActModel>()
            {
                private SDProgressDialog dialog = new SDProgressDialog(getActivity());

                @Override
                protected void onStart()
                {
                    super.onStart();
                    dialog.show();
                }

                @Override
                protected void onSuccess(SDResponse sdResponse)
                {
                    if (actModel.getStatus() == 1)
                    {
                        JoinLiveData joinLiveData = new JoinLiveData();

                        joinLiveData.setCreaterId(actModel.getCreaterId());
                        joinLiveData.setGroupId(actModel.getGroupId());
                        joinLiveData.setRoomId(actModel.getRoomId());
                        joinLiveData.setLoadingVideoImageUrl(actModel.getLoadingVideoImageUrl());

                        AppRuntimeWorker.joinLive(joinLiveData, getActivity());

                        getActivity().finish();
                    }
                }

                @Override
                protected void onFinish(SDResponse resp)
                {
                    super.onFinish(resp);
                    dialog.dismiss();
                }
            });
        }
    }
}
