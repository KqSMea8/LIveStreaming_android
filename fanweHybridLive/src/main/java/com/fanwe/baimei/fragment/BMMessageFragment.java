package com.fanwe.baimei.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.baimei.appview.BMGameCenterGameGalleryView;
import com.fanwe.baimei.appview.BMGameFriendsRoomView;
import com.fanwe.baimei.common.BMCommonInterface;
import com.fanwe.baimei.dialog.BMCreateRoomSuccessDialog;
import com.fanwe.baimei.dialog.BMGameRoomListDialog;
import com.fanwe.baimei.dialog.BMNumberInputDialog;
import com.fanwe.baimei.model.BMGameCenterGameModel;
import com.fanwe.baimei.model.BMGameCenterResponseModel;
import com.fanwe.baimei.model.BMGameCreateRoomResponseModel;
import com.fanwe.baimei.model.BMGameFriendsRoomModel;
import com.fanwe.baimei.model.BMGameFriendsRoomResponseModel;
import com.fanwe.baimei.model.BMGameRoomCodeInputResponseModel;
import com.fanwe.baimei.model.BMGameRoomListModel;
import com.fanwe.baimei.util.ViewUtil;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.umeng.UmengSocialManager;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveChatC2CActivity;
import com.fanwe.live.activity.LivePrivateChatActivity;
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.activity.LiveTabMeNewActivity;
import com.fanwe.live.appview.LiveChatC2CNewView;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveGameExchangeDialog;
import com.fanwe.live.model.App_gameExchangeRateActModel;
import com.fanwe.live.model.CreateLiveData;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.JoinLiveData;
import com.fanwe.live.model.LiveConversationListModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;
import com.scottsu.stateslayout.StatesLayout;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * 包名 com.fanwe.baimei.fragment
 * 描述 游戏首页
 * 作者 Su
 * 创建时间 2017/5/15 15:46
 **/

public class BMMessageFragment extends BMBaseFragment
{
    private LinearLayout ll_content;
    public void init()
    {
        ll_content= (LinearLayout) findViewById(R.id.ll_content);
        LiveChatC2CNewView view = new LiveChatC2CNewView(getActivity());
        view.showBackView(false);
        view.setOnChatItemClickListener(new LiveChatC2CNewView.OnChatItemClickListener()
        {
            @Override
            public void onChatItemClickListener(LiveConversationListModel itemLiveChatListModel)
            {
                Intent intent = new Intent(getActivity(), LivePrivateChatActivity.class);
                intent.putExtra(LivePrivateChatActivity.EXTRA_USER_ID, itemLiveChatListModel.getPeer());
                startActivity(intent);
            }
        });
        ll_content.addView(view);
        //传入数据
        view.requestData();
    }

    public static BMMessageFragment newInstance()
    {
        return new BMMessageFragment();
    }

    @Override
    protected int getContentLayoutRes()
    {
        return R.layout.act_live_chat_c2c;
    }

    @Override
    protected void onViewFirstTimeCreated() {
        init();
    }


}