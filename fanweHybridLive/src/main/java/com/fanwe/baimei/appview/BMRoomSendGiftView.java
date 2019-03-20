package com.fanwe.baimei.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewGroup;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveSendGiftView;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_pop_propActModel;
import com.fanwe.live.model.App_propActModel;
import com.fanwe.live.model.App_propNewActModel;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.LiveGiftModel;
import com.fanwe.live.model.PackgeModel;
import com.fanwe.live.model.custommsg.CustomMsgPrivateGift;
import com.fanwe.shortvideo.fragment.VideoDetailContainerFragment;
import com.fanwe.shortvideo.model.ShortVideoDetailModel;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

public class BMRoomSendGiftView extends RoomView {

    public BMRoomSendGiftView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public BMRoomSendGiftView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BMRoomSendGiftView(Context context) {
        super(context);
    }

    private SDSelectViewGroup svg_tabs;
    private SDTabUnderline view_tab_diamond;
    private SDTabUnderline view_tab_coin;

    private LiveSendGiftView view_send_gift_diamond;
    private BMLiveSendGiftCoinView view_send_gift_coin;

    private SDAnim mAnimVisible;
    private SDAnim mAnimInvisible;

    private App_propNewActModel mGiftActModel; //礼物接口实体
    private PackgeModel packgeModel; //背包接口实体
    private boolean isShortVideo = false;
    private String sv_id = "";
    private VideoDetailContainerFragment videoDetailContainerFragment;

    @Override
    protected int onCreateContentView() {
        return R.layout.bm_view_room_send_gift;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        svg_tabs = (SDSelectViewGroup) findViewById(R.id.svg_tabs);
        view_tab_diamond = (SDTabUnderline) findViewById(R.id.view_tab_diamond);
        view_tab_coin = (SDTabUnderline) findViewById(R.id.view_tab_coin);
    }

    /**
     * 当前是否是秀豆模式
     *
     * @return
     */
    private boolean isDiamond() {
        if (AppRuntimeWorker.isUseGameCurrency()) {
            if (svg_tabs.getSelectViewManager().getSelectedIndex() == 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return true;
        }
    }
    public void updataPackgeGift(){
        view_send_gift_diamond.requestPackgeGift();
    }
    public void HideSendGiftView() {
        getSendGiftViewDiamond().HideSendGiftView();
    }

    /**
     * 是否是小视频中送礼物
     *
     * @param isShortVideo
     */
    public void setIsShortVideo(VideoDetailContainerFragment fragment, boolean isShortVideo, String sv_id) {
        this.videoDetailContainerFragment = fragment;
        this.isShortVideo = isShortVideo;
        this.sv_id = sv_id;
    }
    /**
     * 绑定数据
     */
    public void bindData() {
        if (mGiftActModel == null) {
            CommonInterface.requestNewGift(new AppRequestCallback<App_propNewActModel>() {
                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if (actModel.isOk()) {
                        mGiftActModel = actModel;
                        bindDataInternal();
                    }
                }
            });
        }
//        if (packgeModel == null) {
//            CommonInterface.requestPackgeGift(new AppRequestCallback<PackgeModel>() {
//                @Override
//                protected void onSuccess(SDResponse sdResponse) {
//                    if (actModel.getStatus()==1) {
//                        packgeModel = actModel;
//                        bindDataInternal();
//                    }
//                }
//            });
//        }
        else {
//            if (isDiamond()) {
                getSendGiftViewDiamond();
//            } else {
//                getSendGiftViewCoin();
//            }
        }
    }

    public void bindDataInternal() {
//        if (SDCollectionUtil.isEmpty(mGiftActModel.getCoins_list())) {
            SDViewUtil.setGone(svg_tabs);
            toggleView(R.id.fl_container_send_gift, getSendGiftViewDiamond());
//        } else {
//            SDViewUtil.setVisible(svg_tabs);
//            view_tab_diamond.setTextTitle("秀豆礼物");
//            view_tab_coin.setTextTitle("游戏币礼物");
//
//            svg_tabs.getSelectViewManager().addSelectCallback(new SDSelectManager.SelectCallback<View>() {
//                @Override
//                public void onNormal(int index, View item) {
//                }
//
//                @Override
//                public void onSelected(int index, View item) {
//                    if (index == 0) {
//                        toggleView(R.id.fl_container_send_gift, getSendGiftViewDiamond());
//                    } else {
//                        toggleView(R.id.fl_container_send_gift, getSendGiftViewCoin());
//                    }
//                }
//            });
//            svg_tabs.getSelectViewManager().performClick(0);
////        }
//        }
    }

    /**
     * 返回发送秀豆礼物view
     *
     * @return
     */
    public LiveSendGiftView getSendGiftViewDiamond() {
        if (view_send_gift_diamond == null) {
            view_send_gift_diamond = new LiveSendGiftView(getContext());
            view_send_gift_diamond.setCallback(new LiveSendGiftView.SendGiftViewCallback() {
                @Override
                public void onClickSend(LiveGiftModel model, int num, int is_plus,int is_packge,String json) {
                    sendGift(model, num, is_plus, true,is_packge,json);
                }

                @Override
                public void hide() {
                    getVisibilityHandler().setInvisible(true);
                }
            });
            if (mGiftActModel != null) {
                view_send_gift_diamond.setDataGift(mGiftActModel.getList());
            }
//            if (packgeModel != null) {
//                view_send_gift_diamond.setPackgeData(packgeModel.getList());
//            }
            updataPackgeGift();
        }
        view_send_gift_diamond.bindUserData();
        return view_send_gift_diamond;
    }

    /**
     * 返回发送金币礼物view
     *
     * @return
     */
    public BMLiveSendGiftCoinView getSendGiftViewCoin() {
//        if (view_send_gift_coin == null) {
//            view_send_gift_coin = new BMLiveSendGiftCoinView(getContext());
//            view_send_gift_coin.setCallback(new BMLiveSendGiftCoinView.SendGiftViewCallback() {
//                @Override
//                public void onClickSend(LiveGiftModel model, int num, int is_plus) {
//                    sendGift(model, num, is_plus, false);
//                }
//            });
//        }
//        if (mGiftActModel != null) {
//            view_send_gift_coin.setDataGift(mGiftActModel.getCoins_list());
//        }
//        view_send_gift_coin.bindUserData();
        return view_send_gift_coin;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mAnimVisible == null) {
            mAnimVisible = SDAnim.from(this);
            setVisibleAnimator(mAnimVisible.get());
        }
        mAnimVisible.translationY(h, 0);

        if (mAnimInvisible == null) {
            mAnimInvisible = SDAnim.from(this);
            setInvisibleAnimator(mAnimInvisible.get());
        }
        mAnimInvisible.translationY(0, h);
    }

    private void sendGift(final LiveGiftModel giftModel, int num, int is_plus, final boolean isDiamond,int is_packge,String points_json) {
        if (isShortVideo) {
            videoSendGift(giftModel, num,is_packge,points_json);
        } else {
            liveSendGift(giftModel, num, is_plus, isDiamond,is_packge,points_json);
        }
    }

    private void videoSendGift(final LiveGiftModel giftModel, int num,int is_packge,String points_json) {
        if (giftModel != null) {
            CommonInterface.requestVideoSendGift(giftModel.getId(), num, sv_id,is_packge,points_json, new AppRequestCallback<Deal_send_propActModel>() {
                @Override
                protected void onSuccess(SDResponse resp) {
                    if (actModel.isOk()) {
                        Toast.makeText(getActivity(),"主播已收到您送的礼物",Toast.LENGTH_SHORT).show();
//                        videoDetailContainerFragment.updateRoomGiftGifView(giftModel);
                        getSendGiftViewDiamond().sendGiftSuccess(giftModel);
                        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>() {
                            @Override
                            protected void onSuccess(SDResponse resp) {
                                if (actModel.getStatus() == 1) {
                                    UserModelDao.updateDiamonds(actModel.getUser().getDiamonds());
                                }
                            }
                        });
                        // 发送私聊消息给主播
                        final CustomMsgPrivateGift msg = new CustomMsgPrivateGift();
                        msg.fillData(actModel);
                        IMHelper.sendMsgC2C(actModel.getTo_user_id(), msg, new TIMValueCallBack<TIMMessage>() {
                            @Override
                            public void onError(int i, String s) {
                            }

                            @Override
                            public void onSuccess(TIMMessage timMessage) {
//                                         如果私聊界面不是每次都加载的话要post一条来刷新界面
                                IMHelper.postMsgLocal(msg, actModel.getTo_user_id());
                            }
                        });
                    }
                }
            });
        }
    }

    private void liveSendGift(final LiveGiftModel giftModel, int num, int is_plus, final boolean isDiamond,int is_packge,String points_json) {
        if (giftModel != null) {
            if (getLiveActivity().getRoomInfo() == null) {
                return;
            }
            if (getLiveActivity().getRoomInfo().getLive_in() == 0) {
                //私聊发礼物接口
                final String createrId = getLiveActivity().getCreaterId();
                if (createrId != null) {
                    CommonInterface.requestSendGiftPrivate(giftModel.getId(), num, createrId,is_packge,points_json, new AppRequestCallback<Deal_send_propActModel>() {
                        @Override
                        protected void onSuccess(SDResponse resp) {
                            if (actModel.isOk()) {
                                if (isDiamond) {
                                    getSendGiftViewDiamond().sendGiftSuccess(giftModel);
                                } else {
                                    getSendGiftViewCoin().sendGiftSuccess(giftModel);
                                }

                                // 发送私聊消息给主播
                                final CustomMsgPrivateGift msg = new CustomMsgPrivateGift();
                                msg.fillData(actModel);
                                IMHelper.sendMsgC2C(createrId, msg, new TIMValueCallBack<TIMMessage>() {
                                    @Override
                                    public void onError(int i, String s) {
                                    }

                                    @Override
                                    public void onSuccess(TIMMessage timMessage) {
//                                         如果私聊界面不是每次都加载的话要post一条来刷新界面
                                        IMHelper.postMsgLocal(msg, createrId);
                                    }
                                });
                            }else
                            SDToast.showToast(actModel.getError());
                        }
                    });
                }
            } else {
                int is_coins = isDiamond ? 0 : 1;

                AppRequestParams params = CommonInterface.requestSendGiftParams(giftModel.getId(), num, is_plus, is_coins, getLiveActivity().getRoomId(),is_packge,points_json);
                AppHttpUtil.getInstance().post(params, new AppRequestCallback<App_pop_propActModel>() {
                    @Override
                    protected void onSuccess(SDResponse resp) {
                        // 扣费
                        if (actModel.isOk()) {
                            if (isDiamond) {
                                if (actModel.getAward().getStatus() == 1) {
                                    CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>() {
                                        @Override
                                        protected void onSuccess(SDResponse resp) {
                                            if (actModel.getStatus() == 1) {
                                                UserModelDao.updateDiamonds(actModel.getUser().getDiamonds());
                                            }
                                        }
                                    });
                                }
                                getSendGiftViewDiamond().sendGiftSuccess(giftModel);
                            } else {
                                getSendGiftViewCoin().sendGiftSuccess(giftModel);
                            }
                        }
                        else{
                            SDToast.showToast(actModel.getError());
                        }
                    }

                    @Override
                    protected void onError(SDResponse resp) {
                        CommonInterface.requestMyUserInfo(null);
                    }
                });
            }
        }
    }

    @Override
    protected boolean onTouchDownOutside(MotionEvent ev) {
        getVisibilityHandler().setInvisible(true);
        return true;
    }

    @Override
    public boolean onBackPressed() {
        getVisibilityHandler().setInvisible(true);
        return true;
    }
}
