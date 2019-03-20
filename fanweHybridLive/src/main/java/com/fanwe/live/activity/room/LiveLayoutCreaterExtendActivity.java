package com.fanwe.live.activity.room;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.fanwe.auction.dialog.AuctionCreateAuctionDialog;
import com.fanwe.games.model.PluginModel;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.App_monitorActModel;
import com.fanwe.o2o.dialog.O2OShoppingPodCastDialog;
import com.fanwe.pay.LiveScenePayCreaterBusiness;
import com.fanwe.pay.LiveTimePayCreaterBusiness;
import com.fanwe.pay.dialog.LiveImportPriceDialog;
import com.fanwe.pay.dialog.LiveScenePriceDialog;
import com.fanwe.pay.model.App_live_live_payActModel;
import com.fanwe.pay.model.App_monitorLiveModel;
import com.fanwe.pay.room.RoomLivePayInfoCreaterView;
import com.fanwe.pay.room.RoomLiveScenePayInfoView;
import com.fanwe.shop.dialog.ShopMyStoreDialog;
import com.fanwe.shop.dialog.ShopPodcastGoodsDialog;

import static com.fanwe.live.LiveConstant.PluginClassName.P_LIVE_PAY;
import static com.fanwe.live.LiveConstant.PluginClassName.P_LIVE_PAY_SCENE;
import static com.fanwe.live.LiveConstant.PluginClassName.P_LIVE_PK;
import static com.fanwe.live.LiveConstant.PluginClassName.P_PAI;
import static com.fanwe.live.LiveConstant.PluginClassName.P_PODCAST_GOODS;
import static com.fanwe.live.LiveConstant.PluginClassName.P_SHOP;

/**
 * 主播界面扩展
 */
public class LiveLayoutCreaterExtendActivity extends LiveLayoutCreaterActivity implements LiveTimePayCreaterBusiness.LiveTimePayCreaterBusinessListener, LiveScenePayCreaterBusiness.LiveScenePayCreaterBusinessListener
{

    //-----------竞拍--------------
    private AuctionCreateAuctionDialog dialogCreateAuction;

    //-----------付费模式--------------
    private LiveTimePayCreaterBusiness timePayCreaterBusiness;

    private RoomLivePayInfoCreaterView roomLivePayInfoView;

    //-----------按场付费---------------
    private LiveScenePayCreaterBusiness scenePayCreaterBusiness;
    private RoomLiveScenePayInfoView roomLiveScenePayInfoView;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);

        timePayCreaterBusiness = new LiveTimePayCreaterBusiness(this);
        timePayCreaterBusiness.setBusinessListener(this);

        scenePayCreaterBusiness = new LiveScenePayCreaterBusiness(this);
        scenePayCreaterBusiness.setBusinessListener(this);
    }

    @Override
    public void onBsCreaterRequestMonitorSuccess(App_monitorActModel actModel)
    {
        super.onBsCreaterRequestMonitorSuccess(actModel);
        //开启付费模式监听启动回调
        timePayCreaterBusiness.onRequestMonitorSuccess(actModel);
        scenePayCreaterBusiness.onRequestMonitorSuccess(actModel);
    }

    /**
     * 点击普通插件
     *
     * @param model
     */
    @Override
    protected void onClickCreaterPluginNormal(PluginModel model)
    {
        super.onClickCreaterPluginNormal(model);
        if (P_PAI.equalsIgnoreCase(model.getClass_name()))
        {
            clickPai();
        } else if (P_SHOP.equalsIgnoreCase(model.getClass_name()))
        {
            clickShop();
        } else if (P_PODCAST_GOODS.equalsIgnoreCase(model.getClass_name()))
        {
            clickPodcast_goods();
        } else if (P_LIVE_PAY.equalsIgnoreCase(model.getClass_name()))
        {
            clickPayMode(model);
        } else if (P_LIVE_PAY_SCENE.equalsIgnoreCase(model.getClass_name()))
        {
            clickPayScene();
        }
    }

    protected void clickPai()
    {
        auctionBusiness.requestCreateAuctionAuthority(null);
    }

    protected void clickShop()
    {
        if (AppRuntimeWorker.getIsOpenWebviewMain())
        {
            O2OShoppingPodCastDialog dialog = new O2OShoppingPodCastDialog(this, getCreaterId());
            dialog.showBottom();
        } else
        {
            ShopMyStoreDialog dialog = new ShopMyStoreDialog(this, getCreaterId(), isCreater());
            dialog.showBottom();
        }
    }

    protected void clickPodcast_goods()
    {
        ShopPodcastGoodsDialog dialog = new ShopPodcastGoodsDialog(this, isCreater(), getCreaterId());
        dialog.showBottom();
    }

    protected void clickPayMode(PluginModel model)
    {
        if (model.getIs_active() == 1)
        {
            mRoomCreaterBottomView.showMenuPayMode(false);
        } else
        {
            clickSwitchPay();
            mRoomCreaterBottomView.showMenuPayMode(true);
        }
    }

    //-----------竞拍--------------
    @Override
    public void onAuctionRequestCreateAuthoritySuccess()
    {
        super.onAuctionRequestCreateAuthoritySuccess();
        if (dialogCreateAuction == null)
        {
            dialogCreateAuction = new AuctionCreateAuctionDialog(this, AppRuntimeWorker.getPai_virtual_btn(), AppRuntimeWorker.getPai_real_btn(), getCreaterId());
        }
        dialogCreateAuction.showBottom();
    }

    @Override
    public void onAuctionRequestCreateAuthorityError(String msg)
    {
        super.onAuctionRequestCreateAuthorityError(msg);
        new SDDialogConfirm(this).setTextContent(msg).setTextGravity(Gravity.CENTER).setTextConfirm("确定").setTextCancel("").setmListener(new SDDialogCustom.SDDialogCustomListener()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {

            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {

            }
        }).show();
    }

    //付费直播start=========================

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        //正在付费
        LogUtil.e("LiveLayoutCreaterExtendActivity====onLiveRequestRoomInfoSuccess");
        if (actModel.getLive_fee() > 0)
        {
            LogUtil.e("LiveLayoutCreaterExtendActivity====Live_fee=" + actModel.getLive_fee());
            //按场付费
            if (actModel.getLive_pay_type() == 1)
            {
                addRoomLiveScenePayInfoView();
                roomLiveScenePayInfoView.bindData(actModel.getLive_fee());
                updateHeatRank(actModel.getHeat_rank());
            } else
            {
                addLivePayModeView();
                roomLivePayInfoView.bindData(actModel.getLive_fee());
            }
        }
    }

    //按时付费start========================
    @Override
    protected void onClickCreaterPlugin(PluginModel model)
    {
        super.onClickCreaterPlugin(model);
        mRoomCreaterBottomView.showMenuPayMode(false);
    }

    @Override
    public void onTimePayCreaterRequestLiveLive_paySuccess(App_live_live_payActModel actModel)
    {
        if (roomLivePayInfoView != null)
        {
            roomLivePayInfoView.bindData(actModel.getLive_fee());
        }
    }

    @Override
    public void onTimePayCreaterShowHideUpgrade(boolean show)
    {
        mRoomCreaterBottomView.showMenuPayModeUpgrade(show);
    }

    @Override
    public void onTimePayCreaterShowHideSwitchPay(boolean show)
    {
        mRoomCreaterBottomView.showMenuPayMode(show);
    }

    @Override
    public void onTimePayCreaterCountDown(long leftTime)
    {
        if (roomLivePayInfoView != null)
        {
            roomLivePayInfoView.setPayInfoCountDownTime(leftTime);
        }
    }

    @Override
    public void onTimePayCreaterShowPayModeView()
    {
        addLivePayModeView();
    }

    @Override
    public void onTimePayCreaterRequestMonitorSuccess(App_monitorLiveModel actModel)
    {
        //主播刷新秀豆
        getLiveBusiness().setTicket(actModel.getTicket());
        if (roomLivePayInfoView != null)
        {
            //设置付费人数
            roomLivePayInfoView.setViewerNum(actModel.getLive_viewer());
        }
    }


    private void addLivePayModeView()
    {
        if (roomLivePayInfoView == null)
        {
            roomLivePayInfoView = new RoomLivePayInfoCreaterView(LiveLayoutCreaterExtendActivity.this);
            replaceView(fl_live_pay_mode, roomLivePayInfoView);
        }
    }

    @Override
    protected void onClickMenuPayMode(View v)
    {
        super.onClickMenuPayMode(v);
        clickSwitchPay();
    }

    @Override
    protected void onClickMenuPayUpagrade(View v)
    {
        super.onClickMenuPayUpagrade(v);
        onClickUpagrade();
    }

    private void clickSwitchPay()
    {
        final LiveImportPriceDialog LiveImportPriceDialog = new LiveImportPriceDialog(this);
        LiveImportPriceDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {

            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                if (!timePayCreaterBusiness.isAllowLivePay())
                {
                    SDToast.showToast("亲!您未达到付费条件!");
                    return;
                }
                int price = LiveImportPriceDialog.getImportPrice();
                if (price < LiveImportPriceDialog.getLive_pay_min())
                {
                    SDToast.showToast("不能低于" + LiveImportPriceDialog.getLive_pay_min());
                    LiveImportPriceDialog.resetMinPrice();
                    return;
                }
                timePayCreaterBusiness.requestSwitchPayMode(LiveImportPriceDialog.getImportPrice());
                dialog.dismiss();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                dialog.dismiss();
            }
        });
        LiveImportPriceDialog.show();
    }

    private void onClickUpagrade()
    {
        SDDialogConfirm dialog = new SDDialogConfirm(this);
        dialog.setTextTitle("确定提档？");
        dialog.setTextContent("");
        dialog.setCancelable(false);
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {

            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                timePayCreaterBusiness.requestPayModeUpgrade();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (timePayCreaterBusiness != null)
        {
            timePayCreaterBusiness.onDestroy();
        }
    }
    //按时付费 end====================================
    //按场付费start===================================

    protected void clickPayScene()
    {
        final LiveScenePriceDialog liveScenePriceDialog = new LiveScenePriceDialog(this);
        liveScenePriceDialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {

            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                int price = liveScenePriceDialog.getImportPrice();
                if (price < liveScenePriceDialog.getLive_pay_scene_min())
                {
                    SDToast.showToast("不能低于" + liveScenePriceDialog.getLive_pay_scene_min());
                    liveScenePriceDialog.resetMinPrice();
                    return;
                }
                scenePayCreaterBusiness.requestPayScene(price);
                dialog.dismiss();
            }
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                dialog.dismiss();
            }
        });
        liveScenePriceDialog.show();
    }

    @Override
    public void onScenePayCreaterShowView()
    {
        addRoomLiveScenePayInfoView();
    }

    @Override
    public void onScenePayCreaterSuccess(App_live_live_payActModel actModel)
    {
        roomLiveScenePayInfoView.bindData(actModel.getLive_fee());
    }

    @Override
    public void onScenePayCreaterRequestMonitorSuccess(App_monitorLiveModel app_monitorActModel)
    {
        getLiveBusiness().setTicket(app_monitorActModel.getTicket());
        if (roomLiveScenePayInfoView != null)
        {
            roomLiveScenePayInfoView.setScenePayLiveViewerNum(app_monitorActModel.getLive_viewer());
        }
    }

    private void addRoomLiveScenePayInfoView()
    {
        if (roomLiveScenePayInfoView == null)
        {
            roomLiveScenePayInfoView = new RoomLiveScenePayInfoView(LiveLayoutCreaterExtendActivity.this);
            replaceView(fl_live_pay_mode, roomLiveScenePayInfoView);
        }
    }


    //按场付费end===================================
    //付费直播end===================================
}
