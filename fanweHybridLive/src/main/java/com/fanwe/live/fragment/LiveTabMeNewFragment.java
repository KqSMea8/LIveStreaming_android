package com.fanwe.live.fragment;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fanwe.auction.adapter.AuctionTabMeItemNewAdapter;
import com.fanwe.auction.model.AuctionTabMeItemModel;
import com.fanwe.baimei.dialog.BMLiveVerifyDialog;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDNumberUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveChatC2CActivity;
import com.fanwe.live.activity.LiveDistributionActivity;
import com.fanwe.live.activity.LiveFamilyDetailsActivity;
import com.fanwe.live.activity.LiveFollowActivity;
import com.fanwe.live.activity.LiveMyFocusActivity;
import com.fanwe.live.activity.LiveMySelfContActivity;
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.activity.LiveRechargeVipActivity;
import com.fanwe.live.activity.LiveSearchUserActivity;
import com.fanwe.live.activity.LiveShopActivity;
import com.fanwe.live.activity.LiveSociatyDetailsActivity;
import com.fanwe.live.activity.LiveUserEditActivity;
import com.fanwe.live.activity.LiveUserHomeReplayActivity;
import com.fanwe.live.activity.LiveUserPhotoActivity;
import com.fanwe.live.activity.LiveUserProfitActivity;
import com.fanwe.live.activity.LiveUserSettingActivity;
import com.fanwe.live.activity.LiveUserShareIncomeActivity;
import com.fanwe.live.activity.LiveWebViewActivity;
import com.fanwe.live.activity.UserCenterAuthentActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveAddNewFamilyDialog;
import com.fanwe.live.dialog.LiveGameExchangeDialog;
import com.fanwe.live.dialog.LiveJoinCreateSociatyDialog;
import com.fanwe.live.event.ERefreshMsgUnReaded;
import com.fanwe.live.event.EUpdateUserInfo;
import com.fanwe.live.model.App_gameExchangeRateActModel;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.TotalConversationUnreadMessageModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.FastBlur;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;
import com.fanwe.live.view.LiveUnReadNumTextView;
import com.fanwe.o2o.activity.O2OShoppingMystoreActivity;
import com.fanwe.pay.activity.PayBalanceActivity;
import com.fanwe.shop.activity.ShopMyStoreActivity;
import com.fanwe.shortvideo.activity.MyVideoListActivity;
import com.fanwei.jubaosdk.common.util.ToastUtil;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by Administrator on 2016/10/20.
 */

public class LiveTabMeNewFragment extends BaseFragment {
    public static final String TAG = "LiveTabMeFragment";

    @ViewInject(R.id.ll_search)
    private LinearLayout ll_search;// 搜索

    @ViewInject(R.id.tv_use_diamonds)
    private TextView tv_use_diamonds; // 送出

    @ViewInject(R.id.ll_chat)
    private RelativeLayout ll_chat;

    @ViewInject(R.id.tv_total_unreadnum)
    private LiveUnReadNumTextView tv_total_unreadnum;

    @ViewInject(R.id.fl_head)
    private FrameLayout fl_head;

    @ViewInject(R.id.tv_user_id)
    private TextView tv_user_id;

    @ViewInject(R.id.ll_user_id)
    private LinearLayout ll_user_id;

    @ViewInject(R.id.iv_head)
    private ImageView iv_head;// 头像

    @ViewInject(R.id.iv_level)
    private ImageView iv_level;// 等级

    @ViewInject(R.id.tv_nick_name)
    private TextView tv_nick_name; // 昵称

    @ViewInject(R.id.iv_vip)
    private ImageView iv_vip;//vip图片标识

    @ViewInject(R.id.iv_global_male)
    private ImageView iv_global_male;// 性别

    @ViewInject(R.id.iv_rank)
    private ImageView iv_rank;// 等级

    @ViewInject(R.id.iv_remark)
    private RelativeLayout iv_remark;// 编辑个人资料

    @ViewInject(R.id.ll_v_explain)
    private LinearLayout ll_v_explain; // 认证
    @ViewInject(R.id.tv_v_explain)
    private TextView tv_v_explain;

    @ViewInject(R.id.ll_video)
    private LinearLayout ll_video;//直播
    @ViewInject(R.id.tv_video_num)
    private TextView tv_video_num;

    @ViewInject(R.id.ll_my_focus)
    private LinearLayout ll_my_focus;
    @ViewInject(R.id.tv_focus_count)
    private TextView tv_focus_count;// 关注

    @ViewInject(R.id.ll_my_fans)
    private LinearLayout ll_my_fans;
    @ViewInject(R.id.tv_fans_count)
    private TextView tv_fans_count; // 粉丝

    @ViewInject(R.id.tv_introduce)
    private TextView tv_introduce;// 签名

    @ViewInject(R.id.rl_level)
    private RelativeLayout rl_level;//等级
    @ViewInject(R.id.tv_level)
    private TextView tv_level;

    @ViewInject(R.id.rl_accout)
    private RelativeLayout rl_accout;//账户
    @ViewInject(R.id.tv_accout)
    private TextView tv_accout;

    @ViewInject(R.id.rl_income)
    private RelativeLayout rl_income;//收益
    @ViewInject(R.id.tv_income)
    private TextView tv_income;

    @ViewInject(R.id.rl_share_income)
    private RelativeLayout rl_share_income;//分享收益
    @ViewInject(R.id.tv_share_income)
    private TextView tv_share_income;

    @ViewInject(R.id.ll_auction_gll_info)
    private LinearLayout ll_auction_gll_info;
    @ViewInject(R.id.auction_gll_info)
    private SDGridLinearLayout auction_gll_info;//竞拍Item

    @ViewInject(R.id.include_cont_linear)
    private View include_cont_linear;//秀豆贡献榜

    @ViewInject(R.id.rel_upgrade)
    private RelativeLayout rel_upgrade;//认证
    @ViewInject(R.id.tv_anchor)
    private TextView tv_anchor;
    @ViewInject(R.id.tv_v_type)
    private TextView tv_v_type;

    @ViewInject(R.id.rel_family)
    private RelativeLayout rel_family;//我的家族

    @ViewInject(R.id.rel_sociaty)
    private RelativeLayout rel_sociaty;//我的公会

    @ViewInject(R.id.rel_pay)
    private RelativeLayout rel_pay;//付费模式

    @ViewInject(R.id.rel_distribution)
    private RelativeLayout rel_distribution;//我的分销

    @ViewInject(R.id.ll_vip)
    private View ll_vip; //vip模块

    @ViewInject(R.id.ll_game_currency_exchange)
    private View ll_game_currency_exchange;//游戏币兑换模块

    @ViewInject(R.id.tv_game_currency)
    private TextView tv_game_currency;

    @ViewInject(R.id.tv_vip)
    private TextView tv_vip; //是否开通vip文字标识

    @ViewInject(R.id.rel_setting)
    private RelativeLayout rel_setting;//设置
    @ViewInject(R.id.iv_short_video)
    private RelativeLayout rel_short_video;//小视频
    @ViewInject(R.id.rel_shop)
    private RelativeLayout rel_shop;//小视频
    @ViewInject(R.id.rel_invitation_code)
    private RelativeLayout rel_invitation_code;//邀请码
    @ViewInject(R.id.lv_head)
    private LinearLayout lv_head;//整个头部
    @ViewInject(R.id.bg_img_head_bur)
    private ImageView bg_img_head_bur;// 高斯头像
    @ViewInject(R.id.iv_meda1)
    private ImageView iv_meda1;
    @ViewInject(R.id.iv_meda2)
    private ImageView iv_meda2;
    @ViewInject(R.id.iv_meda3)
    private ImageView iv_meda3;
    private App_userinfoActModel app_userinfoActModel;

    private AuctionTabMeItemNewAdapter adapter;
    private List<AuctionTabMeItemModel> auction_gll_info_array = new ArrayList<AuctionTabMeItemModel>();

    private LiveAddNewFamilyDialog dialogFam;
    private LiveJoinCreateSociatyDialog dialogSoc;
    private BMLiveVerifyDialog dialogVerify;

    @Override
    protected int onCreateContentView() {
        return R.layout.frag_live_new_tab_me;
    }

    @Override
    protected void init() {
        super.init();
        dialogVerify = new BMLiveVerifyDialog(getActivity());
        register();
        bindAuctionAdapter();
    }

    private void register() {
        ll_search.setOnClickListener(this);
        ll_chat.setOnClickListener(this);
        fl_head.setOnClickListener(this);
        iv_remark.setOnClickListener(this);
        SDViewUtil.setVisible(iv_remark);
        SDViewUtil.setVisible(ll_user_id);
        ll_my_focus.setOnClickListener(this);
        ll_my_fans.setOnClickListener(this);
        ll_video.setOnClickListener(this);
        rl_level.setOnClickListener(this);
        rl_accout.setOnClickListener(this);
        rl_income.setOnClickListener(this);
        rl_share_income.setOnClickListener(this);
        include_cont_linear.setOnClickListener(this);
        rel_upgrade.setOnClickListener(this);
        rel_family.setOnClickListener(this);
        rel_sociaty.setOnClickListener(this);
        rel_pay.setOnClickListener(this);
        rel_distribution.setOnClickListener(this);
        rel_setting.setOnClickListener(this);
        rel_short_video.setOnClickListener(this);
        rel_invitation_code.setOnClickListener(this);
        rel_shop.setOnClickListener(this);
        ll_vip.setOnClickListener(this);
        ll_game_currency_exchange.setOnClickListener(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            request();
            changeUI();
        }
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onResume() {
        initUnReadNum();
        request();
        changeUI();
        super.onResume();
    }

    private void changeUI() {
        int live_pay = AppRuntimeWorker.getLive_pay();
        if (live_pay == 1) {
            SDViewUtil.setVisible(rel_pay);
        } else {
            SDViewUtil.setGone(rel_pay);
        }

        int distribution = AppRuntimeWorker.getDistribution();
        if (distribution == 1) {
            SDViewUtil.setVisible(rel_distribution);
        } else {
            SDViewUtil.setGone(rel_distribution);
        }
        if (AppRuntimeWorker.isOpenVip()) {
            SDViewUtil.setVisible(ll_vip);
        } else {
            SDViewUtil.setGone(ll_vip);
        }

        if (AppRuntimeWorker.isUseGameCurrency()) {
            SDViewUtil.setVisible(ll_game_currency_exchange);
        } else {
            SDViewUtil.setGone(ll_game_currency_exchange);
        }

        if (AppRuntimeWorker.getOpen_family_module() == 1) {
            SDViewUtil.setVisible(rel_family);
        } else {
            SDViewUtil.setGone(rel_family);
        }

        if (AppRuntimeWorker.getOpen_sociaty_module() == 1) {
            SDViewUtil.setVisible(rel_sociaty);
        } else {
            SDViewUtil.setGone(rel_sociaty);
        }
    }

    /**
     * 绑定Adapter
     */
    private void bindAuctionAdapter() {
        if (adapter == null) {
            auction_gll_info.setColNumber(1);
            adapter = new AuctionTabMeItemNewAdapter(auction_gll_info_array, getActivity());
            adapter.setItemClickCallback(new SDItemClickCallback<AuctionTabMeItemModel>() {
                @Override
                public void onItemClick(int position, AuctionTabMeItemModel item, View view) {
                    if (AppRuntimeWorker.getIsOpenWebviewMain()) {
                        if (item.getStr_Tag().equals(AuctionTabMeItemModel.TabMeTag.tag5)) {
                            Intent intent = new Intent(getActivity(), O2OShoppingMystoreActivity.class);
                            startActivity(intent);
                            return;
                        }
                    }

                    if (item.getStr_Tag().equals(AuctionTabMeItemModel.TabMeTag.tag7)) {
                        Intent intent = new Intent(getActivity(), ShopMyStoreActivity.class);
                        startActivity(intent);
                        return;
                    }

                    if (!TextUtils.isEmpty(item.getUrl())) {
                        Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
                        intent.putExtra(LiveWebViewActivity.EXTRA_URL, item.getUrl());
                        startActivity(intent);
                    } else {
                        SDToast.showToast("url为空");
                    }
                }
            });
            auction_gll_info.setAdapter(adapter);
        }
    }

    private void request() {
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    app_userinfoActModel = actModel;
                    UserModelDao.insertOrUpdate(actModel.getUser());
                    dialogVerify.setTip(actModel.getUser() != null ? actModel.getUser().getNick_name() : "");
                    bindAuctionData(actModel);
                }
            }
        });
    }

    private void bindNormalData(UserModel user) {
        if (user != null) {
            SDViewBinder.setTextView(tv_user_id, user.getShowId());
            String user_diamonds = "";
//            if (user.getUse_diamonds() >= 10000) {
//                user_diamonds = (user.getUse_diamonds() / 10000) + "." + user.getUse_diamonds() % 10000 + "万";
//            } else {
                user_diamonds = user.getUse_diamonds() + "";
//            }
            SDViewBinder.setTextView(tv_use_diamonds, user_diamonds);
            GlideUtil.loadHeadImage(user.getHead_image()).into(iv_head);
            Glide.with(this).load(user.getHead_image()).bitmapTransform(new BlurTransformation(this.getActivity(), 40)).into(bg_img_head_bur);

/*
            Glide.with(this).load(user.getHead_image()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
             //    lv_head.setBackgroundDrawable(new BitmapDrawable(resource));
              //  blur(resource,lv_head,lv_head.getWidth()/2);
                    blur(resource,lv_head);
                }
            }); //方法中设置asBitmap可以设置回调类型*/
            if (!TextUtils.isEmpty(user.getV_icon())) {
                GlideUtil.load(user.getV_icon()).into(iv_level);
                SDViewUtil.setVisible(iv_level);
            } else {
                SDViewUtil.setGone(iv_level);
            }

            SDViewBinder.setTextView(tv_nick_name, user.getNick_name());
            if (user.getSexResId() > 0) {
                SDViewUtil.setVisible(iv_global_male);
                iv_global_male.setImageResource(user.getSexResId());
            } else {
                SDViewUtil.setGone(iv_global_male);
            }
            iv_rank.setImageResource(user.getLevelImageResId());

            String focus_count = user.getFocus_count() + "";
            SDViewBinder.setTextView(tv_focus_count, focus_count);

//            SDViewBinder.setTextView(tv_fans_count, LiveUtils.getFormatNumber(user.getFans_count()));
            SDViewBinder.setTextView(tv_fans_count, user.getFans_count()+"");
            SDViewBinder.setTextView(tv_introduce, user.getSignature(), "TA好像忘记写签名了");

            if (!TextUtils.isEmpty(user.getV_explain())) {
                SDViewUtil.setVisible(ll_v_explain);
                SDViewBinder.setTextView(tv_v_explain, user.getV_explain());
            } else {
                SDViewUtil.setGone(ll_v_explain);
            }

            String video_count = user.getVideo_count() + "";
            SDViewBinder.setTextView(tv_video_num, video_count);

            String user_level = user.getUser_level() + "";
            SDViewBinder.setTextView(tv_level, user_level);
            SDViewBinder.setTextView(tv_income, user.getUseable_ticket()+"");

            SDViewBinder.setTextView(tv_accout, user.getDiamonds()+"");

            int v_type = SDTypeParseUtil.getInt(user.getV_type());
            if (v_type == 0) {
                SDViewUtil.setVisible(rel_upgrade);
            } else if (v_type == 1) {
                SDViewUtil.setGone(rel_upgrade);
            } else if (v_type == 2) {
                SDViewUtil.setGone(rel_upgrade);
            }

            String anchor = SDResourcesUtil.getString(R.string.live_account_authentication);
            anchor = anchor + "认证";
            SDViewBinder.setTextView(tv_anchor, anchor);

            int is_authentication = user.getIs_authentication();
            if (is_authentication == 0) {
                tv_v_type.setText("未认证");
            } else if (is_authentication == 1) {
                tv_v_type.setText("认证待审核");
            } else if (is_authentication == 2) {
                tv_v_type.setText("已认证");
            } else if (is_authentication == 3) {
                tv_v_type.setText("认证审核不通过");
            }

            if (user.getIs_vip() == 1) {
                SDViewUtil.setVisible(iv_vip);
                tv_vip.setText("已开通");
                tv_vip.setTextColor(SDResourcesUtil.getColor(R.color.main_color));
            } else {
                SDViewUtil.setGone(iv_vip);
                tv_vip.setText(user.getVip_expire_time());
                tv_vip.setTextColor(SDResourcesUtil.getColor(R.color.user_home_text_gray));
            }
            if (user.getIs_jjr() == 1) {
                SDViewUtil.setVisible(rl_share_income);
                tv_share_income.setText("￥" + user.getJjr_money());
            } else {
                SDViewUtil.setGone(rl_share_income);
            }
//            SDViewBinder.setTextView(tv_game_currency, LiveUtils.getFormatNumber(user.getCoin()) + SDResourcesUtil.getString(R.string.game_currency));
            SDViewBinder.setTextView(tv_game_currency, user.getCoin() + SDResourcesUtil.getString(R.string.game_currency));
        }
        if(null!=user.getMedals()){
            for(int i=0;i<user.getMedals().size();i++){
                switch (i){
                    case 0:
                        iv_meda1.setVisibility(View.VISIBLE);
                        GlideUtil.load(user.getMedals().get(i)).into(iv_meda1);
                        break;
                    case 1:
                        iv_meda2.setVisibility(View.VISIBLE);
                        GlideUtil.load(user.getMedals().get(i)).into(iv_meda2);
                        break;
                    case 2:
                        iv_meda3.setVisibility(View.VISIBLE);
                        GlideUtil.load(user.getMedals().get(i)).into(iv_meda3);
                        break;
                }
            }
        }
    }


    private void bindAuctionData(App_userinfoActModel actModel) {
        auction_gll_info_array.clear();
        auction_gll_info_array.addAll(actModel.getItem());

        if (auction_gll_info_array.size() == 0) {
            SDViewUtil.setGone(ll_auction_gll_info);
        } else {
            SDViewUtil.setVisible(ll_auction_gll_info);
            auction_gll_info.notifyDataSetChanged();
        }
    }

    private void initUnReadNum() {
        TotalConversationUnreadMessageModel model = IMHelper.getC2CTotalUnreadMessageModel();
        setUnReadNumModel(model);
    }

    public void onEventMainThread(ERefreshMsgUnReaded event) {
        TotalConversationUnreadMessageModel model = event.model;
        setUnReadNumModel(model);
    }

    /**
     * @param event 接收刷新UserModel信息事件
     */
    public void onEventMainThread(EUpdateUserInfo event) {
        UserModel user = event.user;
        bindNormalData(user);
    }


    private void setUnReadNumModel(TotalConversationUnreadMessageModel model) {
        SDViewUtil.setGone(tv_total_unreadnum);
        if (model != null && model.getTotalUnreadNum() > 0) {
            SDViewUtil.setVisible(tv_total_unreadnum);
            tv_total_unreadnum.setUnReadNumText(model.getTotalUnreadNum());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_search:
                clickLLSearch();
                break;
            case R.id.ll_chat:
                clickLlChat();
                break;
            case R.id.fl_head:
                clickFlHead();
                break;
            case R.id.iv_remark:
                clickIvRemark();
                break;
            case R.id.ll_video:
                clickRlVideo();
                break;
            case R.id.ll_my_focus:
                clickLlMyFocus();
                break;
            case R.id.ll_my_fans:
                clickLlMyFans();
                break;
            case R.id.rl_level:
                clickRlLevel();
                break;
            case R.id.rl_accout:
                clickRlAccout();
                break;
            case R.id.rl_income:
                clickRlIncome();
                break;
            case R.id.rl_share_income:
                clickRlShareIncome();
                break;
            case R.id.include_cont_linear:
                clickIncludeContLinear();
                break;
            case R.id.rel_upgrade:
                clickLlUpgrade();
                break;
            case R.id.rel_family:
                clickFamily();
                break;
            case R.id.rel_pay:
                clickRelPay();
                break;
            case R.id.rel_distribution:
                clickRelDistribution();
                break;
            case R.id.rel_setting:
                clickSetting();
                break;
            case R.id.iv_short_video:
                clickShortVideo();
                break;
            case R.id.rel_invitation_code:
                clickInvatationCode();
                break;
            case R.id.ll_vip:
                clickVip();
                break;
            case R.id.rel_sociaty:
                clickSociaty();
                break;
            case R.id.ll_game_currency_exchange:
                doGameExchange();
                break;
            case R.id.rel_shop:
                clickRlShop();
                break;
            default:
                break;
        }
    }

    // 搜索
    private void clickLLSearch() {
        Intent intent = new Intent(getActivity(), LiveSearchUserActivity.class);
        startActivity(intent);
//        getActivity().onBackPressed();
    }

    //聊天
    private void clickLlChat() {
        Intent intent = new Intent(getActivity(), LiveChatC2CActivity.class);
        startActivity(intent);
    }

    // 我的头像
    private void clickFlHead() {
        if (app_userinfoActModel != null) {
            UserModel user = app_userinfoActModel.getUser();
            if (user != null) {
                Intent intent = new Intent(getActivity(), LiveUserPhotoActivity.class);
                intent.putExtra(LiveUserPhotoActivity.EXTRA_USER_IMG_URL, user.getHead_image());
                startActivity(intent);
            }
        }
    }

    //编辑
    private void clickIvRemark() {
        Intent intent = new Intent(getActivity(), LiveUserEditActivity.class);
        startActivity(intent);
    }

    // 回放列表
    private void clickRlVideo() {
        Intent intent = new Intent(getActivity(), LiveUserHomeReplayActivity.class);
        startActivity(intent);
    }

    // 我关注的人
    private void clickLlMyFocus() {
        UserModel user = UserModelDao.query();
        if (user != null) {
            String user_id = user.getUser_id();
            if (!TextUtils.isEmpty(user_id)) {
                Intent intent = new Intent(getActivity(), LiveFollowActivity.class);
                intent.putExtra(LiveFollowActivity.EXTRA_USER_ID, user_id);
                startActivity(intent);
            } else {
                SDToast.showToast("本地user_id为空");
            }
        }
    }

    // 我的粉丝
    private void clickLlMyFans() {
        Intent intent = new Intent(getActivity(), LiveMyFocusActivity.class);
        startActivity(intent);
    }

    //等级
    private void clickRlLevel() {
        Intent intent = new Intent(getActivity(), LiveWebViewActivity.class);
        intent.putExtra(LiveWebViewActivity.EXTRA_URL, AppRuntimeWorker.getUrl_my_grades());
        startActivity(intent);
    }

    //账户
    private void clickRlAccout() {
        if (app_userinfoActModel != null) {
            UserModel user = app_userinfoActModel.getUser();
            if (user != null) {
                Intent intent = new Intent(getActivity(), LiveRechargeDiamondsActivity.class);
                intent.putExtra("is_payed", app_userinfoActModel.getUser().getIs_payed());
                startActivity(intent);
            }
        }
    }

    /**
     * VIP充值页面
     */
    private void clickVip() {
        Intent intent = new Intent(getActivity(), LiveRechargeVipActivity.class);
        startActivity(intent);
    }

    private void doGameExchange() {
        showProgressDialog("");
        CommonInterface.requestGamesExchangeRate(new AppRequestCallback<App_gameExchangeRateActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    LiveGameExchangeDialog dialog = new LiveGameExchangeDialog(getActivity(), LiveGameExchangeDialog.TYPE_COIN_EXCHANGE, new LiveGameExchangeDialog.OnSuccessListener() {
                        @Override
                        public void onExchangeSuccess(long diamonds, long coins) {
                            UserModel user = UserModelDao.updateDiamondsAndCoins(diamonds, coins);
                            UserModelDao.insertOrUpdate(user);
                        }

                        @Override
                        public void onSendCurrencySuccess(Deal_send_propActModel model) {

                        }
                    });
                    dialog.setRate(actModel.getExchange_rate());
                    dialog.setCurrency(app_userinfoActModel.getUser().getDiamonds());
                    dialog.showCenter();
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    //收益
    private void clickRlIncome() {
        Intent intent = new Intent(getActivity(), LiveUserProfitActivity.class);
        startActivity(intent);
    }

    //分享收益
    private void clickRlShareIncome() {
        if (app_userinfoActModel.getUser() != null) {
            Intent intent = new Intent(getActivity(), LiveUserShareIncomeActivity.class);
            intent.putExtra("jjr_money", app_userinfoActModel.getUser().getJjr_money());
            startActivity(intent);
        }
    }
    //秀豆贡献榜
    private void clickRlShop() {
        Intent intent = new Intent(getActivity(), LiveShopActivity.class);
        startActivity(intent);
    }
    //秀豆贡献榜
    private void clickIncludeContLinear() {
        Intent intent = new Intent(getActivity(), LiveMySelfContActivity.class);
        startActivity(intent);
    }

    //认证
    private void clickLlUpgrade() {
        if (app_userinfoActModel != null) {
            Intent intent = new Intent(getActivity(), UserCenterAuthentActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 我的家族
     */
    private void clickFamily() {
        UserModel dao = UserModelDao.query();
        if (dao.getFamily_id() == 0) {
            showFamDialog();
        } else {
            //家族详情
            Intent intent = new Intent(getActivity(), LiveFamilyDetailsActivity.class);
            startActivity(intent);
        }
    }

    private void showFamDialog() {
        if (dialogFam == null) {
            dialogFam = new LiveAddNewFamilyDialog(getActivity());
        }
        dialogFam.showCenter();
    }

    /**
     * 我的公会
     */
    private void clickSociaty() {
        UserModel dao = UserModelDao.query();
        if (dao.getSociety_id() == 0) {
            showSocDialog();
        } else {
            //公会详情
            Intent intent = new Intent(getActivity(), LiveSociatyDetailsActivity.class);
            startActivity(intent);
        }
    }

    private void showSocDialog() {
        if (dialogSoc == null) {
            dialogSoc = new LiveJoinCreateSociatyDialog(getActivity());
        }
        dialogSoc.showCenter();
    }

    //付费榜
    private void clickRelPay() {
        Intent intent = new Intent(getActivity(), PayBalanceActivity.class);
        startActivity(intent);
    }

    /**
     * 我的分销
     */
    private void clickRelDistribution() {
        Intent intent = new Intent(getActivity(), LiveDistributionActivity.class);
        startActivity(intent);
    }

    /**
     * 设置
     */
    private void clickSetting() {
        Intent intent = new Intent(getActivity(), LiveUserSettingActivity.class);
        startActivity(intent);
    }

    /**
     * 我的小视频
     */
    private void clickShortVideo() {
        Intent intent = new Intent(getActivity(), MyVideoListActivity.class);
        startActivity(intent);
    }

    /**
     * 邀请码
     */
    private void clickInvatationCode() {
        if (app_userinfoActModel.getUser().getHas_edit_num() == 1) {
            dialogVerify.show();
        } else {
            ToastUtil.showToast(getActivity(), "邀请码已填写！", Toast.LENGTH_LONG);
        }
    }

    /**
     * 头部虚化
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void blur(Bitmap bkg, View view, float radius) {
        Bitmap overlay = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.drawBitmap(bkg, -view.getLeft(), -view.getTop(), null);
        RenderScript rs = RenderScript.create(this.getActivity());
        Allocation overlayAlloc = Allocation.createFromBitmap(rs, overlay);
        ScriptIntrinsicBlur blur = ScriptIntrinsicBlur.create(rs, overlayAlloc.getElement());
        blur.setInput(overlayAlloc);
        blur.setRadius(radius);
        blur.forEach(overlayAlloc);
        overlayAlloc.copyTo(overlay);
        view.setBackground(new BitmapDrawable(getResources(), overlay));
        rs.destroy();
    }

    private void blur(Bitmap bkg, View view) {
        long startMs = System.currentTimeMillis();
        float scaleFactor = 8;
        float radius = 2;

        Bitmap overlay = Bitmap.createBitmap(
                (int) (view.getMeasuredWidth() / scaleFactor),
                (int) (view.getMeasuredHeight() / scaleFactor),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.translate(-view.getLeft() / scaleFactor, -view.getTop()
                / scaleFactor);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(bkg, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        view.setBackgroundDrawable(new BitmapDrawable(getResources(), overlay));
        System.out.println(System.currentTimeMillis() - startMs + "ms");
    }
}
