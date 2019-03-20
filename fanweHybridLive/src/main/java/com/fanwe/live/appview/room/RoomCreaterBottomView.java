package com.fanwe.live.appview.room;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.fanwe.games.BankerBusiness;
import com.fanwe.games.GameBusiness;
import com.fanwe.hybrid.http.AppHttpUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.http.AppRequestParams;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.blocker.SDObjectBlocker;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.IMHelper;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_pop_msgActModel;
import com.fanwe.live.model.custommsg.CustomMsgText;
import com.tencent.TIMMessage;
import com.tencent.TIMValueCallBack;

/**
 * Created by Administrator on 2016/8/4.
 */
public class RoomCreaterBottomView extends RoomBottomView implements
        GameBusiness.GameCtrlView,
        BankerBusiness.BankerCreaterCtrlView {

    public RoomCreaterBottomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RoomCreaterBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RoomCreaterBottomView(Context context) {
        super(context);
    }

    private RoomMenuView menu_send_msg; //发消息
    private RoomMenuView menu_creater_plugin; //插件中心

    private RoomMenuView menu_private_msg; //私聊消息
    private RoomMenuView menu_share; //分享
    private RoomMenuView menu_pay_mode; //付费模式
    private RoomMenuView menu_pay_mode_upgrade; //付费模式提档
    private RoomMenuView menu_bottom_extend_switch;//显示隐藏底部扩展(游戏等)
    private RoomMenuView menu_send_gift; //礼物
    private RoomMenuView menu_start;
    private RoomMenuView menu_waiting;
    private RoomMenuView menu_close;
    private FrameLayout fl_game_banker;
    private RoomMenuView menu_open_banker;
    private RoomMenuView menu_open_banker_list;
    private RoomMenuView menu_stop_banker;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    protected int onCreateContentView() {
        return R.layout.view_room_creater_bottom;
    }

    @Override
    protected void onBaseInit() {
        super.onBaseInit();
        menu_send_msg = find(R.id.menu_send_msg);
        menu_creater_plugin = find(R.id.menu_creater_plugin);
        menu_private_msg = find(R.id.menu_private_msg);
        menu_share = find(R.id.menu_share);
        menu_pay_mode = find(R.id.menu_pay_mode);
        menu_pay_mode_upgrade = find(R.id.menu_pay_mode_upgrade);
        menu_bottom_extend_switch = find(R.id.menu_bottom_extend_switch);
        menu_start = find(R.id.menu_start);
        menu_waiting = find(R.id.menu_waiting);
        menu_close = find(R.id.menu_close);
        fl_game_banker = find(R.id.fl_game_banker);
        menu_open_banker = find(R.id.menu_open_banker);
        menu_open_banker_list = find(R.id.menu_open_banker_list);
        menu_stop_banker = find(R.id.menu_stop_banker);
        menu_send_gift = find(R.id.menu_send_gift);
        //礼物
        menu_send_gift.setImageResId(R.drawable.ic_live_bottom_gift);
        //发消息
        menu_send_msg.setImageResId(R.drawable.ic_live_bottom_open_send);
        //插件中心
        menu_creater_plugin.setImageResId(R.drawable.ic_live_bottom_plugin);
        //显示隐藏插件
        menu_bottom_extend_switch.setImageResId(R.drawable.ic_live_hide_plugin);
        //私聊消息
        menu_private_msg.setImageResId(R.drawable.ic_live_bottom_msg);
        //分享
        menu_share.setImageResId(R.drawable.ic_live_bottom_share);
        //切换付费模式
        menu_pay_mode.setImageResId(R.drawable.ic_live_bottom_switch_pay_mode);
        //付费模式提档
        menu_pay_mode_upgrade.setImageResId(R.drawable.ic_live_bottom_pay_mode_upgrade);

        menu_open_banker.setImageResId(R.drawable.ic_live_bottom_game_open_banker);
        menu_open_banker_list.setImageResId(R.drawable.ic_live_bottom_game_open_banker_list);
        menu_stop_banker.setImageResId(R.drawable.ic_live_bottom_game_stop_banker);
        menu_send_gift.setOnClickListener(this);
        menu_send_msg.setOnClickListener(this);
        menu_creater_plugin.setOnClickListener(this);
        menu_private_msg.setOnClickListener(this);
        menu_share.setOnClickListener(this);
        menu_start.setOnClickListener(this);
        menu_close.setOnClickListener(this);
        menu_pay_mode.setOnClickListener(this);
        menu_pay_mode_upgrade.setOnClickListener(this);
        menu_bottom_extend_switch.setOnClickListener(this);
        menu_open_banker.setOnClickListener(this);
        menu_open_banker_list.setOnClickListener(this);
        menu_stop_banker.setOnClickListener(this);

        setUnreadMessageModel(IMHelper.getC2CTotalUnreadMessageModel());

        SDViewUtil.setVisibleOrGone(fl_game_banker, AppRuntimeWorker.isOpenBankerModule());

    }




    @Override
    public void onClick(View v) {
        super.onClick(v);

        if (clickListener != null) {
            if (v == menu_send_msg) {
                clickListener.onClickMenuSendMsg(v);
            } else if (v == menu_creater_plugin) {
                clickListener.onClickMenuCreaterPlugin(v);
            } else if (v == menu_start) {
                clickListener.onClickMenuStart(v);
            } else if (v == menu_close) {
                clickListener.onClickMenuClose(v);
            } else if (v == menu_private_msg) {
                clickListener.onClickMenuPrivateMsg(v);
            } else if (v == menu_share) {
                clickListener.onClickMenuShare(v);
            } else if (v == menu_pay_mode) {
                clickListener.onClickMenuPayMode(v);
            } else if (v == menu_pay_mode_upgrade) {
                clickListener.onClickMenuPayModeUpagrade(v);
            } else if (v == menu_bottom_extend_switch) {
                clickListener.onClickMenuBottomExtendSwitch(v);
            } else if (v == menu_open_banker) {
                clickListener.onClickMenuOpenBanker(v);
            } else if (v == menu_stop_banker) {
                clickListener.onClickMenuStopBanker(v);
            } else if (v == menu_open_banker_list) {
                clickListener.onClickMenuBankerList(v);
            }else if (v == menu_send_gift)
            {
                clickListener.onClickMenuSendGift(v);
            }
        }
    }



    @Override
    public void showMenuShare(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_share, show);
    }

    /**
     * 显示隐藏付费模式
     *
     * @param show
     */
    public void showMenuPayMode(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_pay_mode, show);
    }

    /**
     * 显示隐藏付费模式提档
     *
     * @param show
     */
    public void showMenuPayModeUpgrade(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_pay_mode_upgrade, show);
    }

    @Override
    public void showMenuBottomExtendSwitch(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_bottom_extend_switch, show);
    }

    @Override
    public void setMenuBottomExtendSwitchStateOpen() {
        super.setMenuBottomExtendSwitchStateOpen();
        menu_bottom_extend_switch.setImageResId(R.drawable.ic_live_show_plugin);
    }

    @Override
    public void setMenuBottomExtendSwitchStateClose() {
        super.setMenuBottomExtendSwitchStateClose();
        menu_bottom_extend_switch.setImageResId(R.drawable.ic_live_hide_plugin);
    }

    @Override
    protected void onIMUnreadNumber(String numberFormat) {
        super.onIMUnreadNumber(numberFormat);
        menu_private_msg.setTextUnread(numberFormat);
    }

    @Override
    public void onGameCtrlShowStart(boolean show, int gameId) {
        menu_start.setImageResId(R.drawable.ic_bottom_start);

        SDViewUtil.setVisibleOrGone(menu_start, show);
    }

    @Override
    public void onGameCtrlShowClose(boolean show, int gameId) {
        menu_close.setImageResId(R.drawable.ic_bottom_close);

        SDViewUtil.setVisibleOrGone(menu_close, show);
    }

    @Override
    public void onGameCtrlShowWaiting(boolean show, int gameId) {
        menu_waiting.setImageResId(R.drawable.ic_bottom_waiting);

        SDViewUtil.setVisibleOrGone(menu_waiting, show);
    }

    public void showMenuOpenBankerListUnread(boolean show) {
        if (show) {
            menu_open_banker_list.setTextUnread("");
        } else {
            menu_open_banker_list.setTextUnread(null);
        }
    }

    @Override
    public void onBankerCtrlCreaterShowOpenBanker(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_open_banker, show);
    }

    @Override
    public void onBankerCtrlCreaterShowOpenBankerList(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_open_banker_list, show);
    }

    @Override
    public void onBankerCtrlCreaterShowStopBanker(boolean show) {
        SDViewUtil.setVisibleOrGone(menu_stop_banker, show);
    }

    public interface ClickListener {
        void onClickMenuSendMsg(View v);

        void onClickMenuCreaterPlugin(View v);

        void onClickMenuStart(View v);

        void onClickMenuClose(View v);

        void onClickMenuPrivateMsg(View v);

        void onClickMenuShare(View v);

        void onClickMenuPayMode(View v);

        void onClickMenuPayModeUpagrade(View v);

        void onClickMenuBottomExtendSwitch(View v);

        void onClickMenuOpenBanker(View v);

        void onClickMenuStopBanker(View v);

        void onClickMenuBankerList(View v);
        void onClickMenuSendGift(View v);
    }
}
