package com.fanwe.baimei.fragment;

import android.content.Intent;
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
import com.fanwe.live.activity.LiveRechargeDiamondsActivity;
import com.fanwe.live.activity.LiveTabMeNewActivity;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.dialog.LiveGameExchangeDialog;
import com.fanwe.live.model.App_gameExchangeRateActModel;
import com.fanwe.live.model.CreateLiveData;
import com.fanwe.live.model.Deal_send_propActModel;
import com.fanwe.live.model.JoinLiveData;
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

public class BMGameCenterFragment extends BMBaseFragment
{
    private boolean mFirstTimeRequest = true;
    private BMGameCenterResponseModel mGameCenterResponseModel;
    private StatesLayout mStatesLayout;
    private LinearLayout mContentLinearLayout;
    private View mAvatarLayout, mDiamondsLayout, mCoinsLayout, mCreateRoomButton, mEnterRoomButton, mRefreshRoomButton;
    private ImageView mAvatarImageView, mCreateRoomImageView;
    private TextView mDiamondsTextView, mCoinsTextView, mOnlineCountTextView;
    private FrameLayout mGameGalleryContainer, mGameFriendsRoomContainer;
    private BMGameCenterGameGalleryView mGameGalleryView;   //游戏横向列表
    private BMGameFriendsRoomView mGameFriendsRoomView;     //关注好友房间列表
    private BMNumberInputDialog mRoomCodeInputDialog;     //数字输入弹窗
    private BMCreateRoomSuccessDialog mCreateRoomSuccessDialog;   //房间创建成功弹窗
    private BMGameRoomListDialog mGameRoomListDialog;     //游戏房间列表弹窗

    public static BMGameCenterFragment newInstance()
    {
        return new BMGameCenterFragment();
    }

    @Override
    protected int getContentLayoutRes()
    {
        return R.layout.bm_frag_game_center;
    }

    @Override
    protected void onViewFirstTimeCreated()
    {
        getGameGalleryContainer().addView(getGameGalleryView());
        getGameFriendsRoomContainer().addView(getGameFriendsRoomView());

        initListener();
    }

    @Override
    public void onResume()
    {
        super.onResume();

        requestMainData(mFirstTimeRequest);

        if (mFirstTimeRequest)
        {
            mFirstTimeRequest = false;
        }
    }

    private void requestMainData(boolean firstTime)
    {
        requestGameCenter(firstTime);
        requestGameFriendsRoom();
    }

    private void initListener()
    {
        getAvatarLayout().setOnClickListener(this);
        getDiamondsLayout().setOnClickListener(this);
        getCoinsLayout().setOnClickListener(this);
        getRefreshRoomButton().setOnClickListener(this);
        getCreateRoomButton().setOnClickListener(this);
        getEnterRoomButton().setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);

        if (getAvatarLayout() == v)
        {
            goToUserPage();
        } else if (getDiamondsLayout() == v)
        {
            goToDiamondsRecharge();
        } else if (getCoinsLayout() == v)
        {
            exchangeCoins();
        } else if (getRefreshRoomButton() == v)
        {
            requestGameFriendsRoom();
        } else if (getCreateRoomButton() == v)
        {
            requestGameCreateRoom();
        } else if (getEnterRoomButton() == v)
        {
            getRoomCodeInputDialog().show();
        }
    }

    /**
     * 进入用户信息界面
     */
    private void goToUserPage()
    {
        Intent intent = new Intent(getActivity(), LiveTabMeNewActivity.class);
        startActivity(intent);
    }

    private void goToDiamondsRecharge()
    {
        startActivity(new Intent(getActivity(), LiveRechargeDiamondsActivity.class));
    }

    private void exchangeCoins()
    {
        showProgressDialog("");
        CommonInterface.requestGamesExchangeRate(new AppRequestCallback<App_gameExchangeRateActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    LiveGameExchangeDialog dialog = new LiveGameExchangeDialog(getActivity(), LiveGameExchangeDialog.TYPE_COIN_EXCHANGE, new LiveGameExchangeDialog.OnSuccessListener()
                    {
                        @Override
                        public void onExchangeSuccess(long diamonds, long coins)
                        {
                            UserModel user = UserModelDao.updateDiamondsAndCoins(diamonds, coins);
                            UserModelDao.insertOrUpdate(user);

                            //手动更新
                            getDiamondsTextView().setText(String.valueOf(diamonds));
                            getCoinsTextView().setText(String.valueOf(coins));
                        }

                        @Override
                        public void onSendCurrencySuccess(Deal_send_propActModel model)
                        {

                        }
                    });
                    dialog.setRate(actModel.getExchange_rate());
                    dialog.setCurrency(Long.valueOf(mGameCenterResponseModel.getDiamonds()));
                    dialog.showCenter();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }


    private LinearLayout getContentLinearLayout()
    {
        if (mContentLinearLayout == null)
        {
            mContentLinearLayout = (LinearLayout) findViewById(R.id.ll_content_bm_frag_game_center);
        }
        return mContentLinearLayout;
    }

    private FrameLayout getGameFriendsRoomContainer()
    {
        if (mGameFriendsRoomContainer == null)
        {
            mGameFriendsRoomContainer = (FrameLayout) findViewById(R.id.fl_container_friends_room_bm_frag_game_center);
        }
        return mGameFriendsRoomContainer;
    }

    private StatesLayout getStatesLayout()
    {
        if (mStatesLayout == null)
        {
            mStatesLayout = (StatesLayout) findViewById(R.id.states_layout_bm_frag_game_center);
            mStatesLayout.setCallback(new StatesLayout.StatesLayoutCallback()
            {
                @Override
                public void onEmptyClick(View view)
                {
                    requestGameCenter(true);
                }

                @Override
                public void onErrorClick(View view)
                {
                    requestGameCenter(true);
                }
            });
        }
        return mStatesLayout;
    }

    private View getAvatarLayout()
    {
        if (mAvatarLayout == null)
        {
            mAvatarLayout = findViewById(R.id.fl_avatar);
        }
        return mAvatarLayout;
    }

    private ImageView getAvatarImageView()
    {
        if (mAvatarImageView == null)
        {
            mAvatarImageView = (ImageView) findViewById(R.id.iv_avatar);
        }
        return mAvatarImageView;
    }

    private View getDiamondsLayout()
    {
        if (mDiamondsLayout == null)
        {
            mDiamondsLayout = findViewById(R.id.fl_user_diamonds);
        }
        return mDiamondsLayout;
    }

    private View getCoinsLayout()
    {
        if (mCoinsLayout == null)
        {
            mCoinsLayout = findViewById(R.id.fl_user_coins);
        }
        return mCoinsLayout;
    }

    private TextView getDiamondsTextView()
    {
        if (mDiamondsTextView == null)
        {
            mDiamondsTextView = (TextView) findViewById(R.id.tv_user_diamonds);
        }
        return mDiamondsTextView;
    }

    private TextView getCoinsTextView()
    {
        if (mCoinsTextView == null)
        {
            mCoinsTextView = (TextView) findViewById(R.id.tv_user_coins);
        }
        return mCoinsTextView;
    }

    private View getCreateRoomButton()
    {
        if (mCreateRoomButton == null)
        {
            mCreateRoomButton = findViewById(R.id.fl_create_room);
        }
        return mCreateRoomButton;
    }

    private View getEnterRoomButton()
    {
        if (mEnterRoomButton == null)
        {
            mEnterRoomButton = findViewById(R.id.fl_enter_room);
        }
        return mEnterRoomButton;
    }

    private ImageView getCreateRoomImageView()
    {
        if (mCreateRoomImageView == null)
        {
            mCreateRoomImageView = (ImageView) findViewById(R.id.iv_create_room);
        }
        return mCreateRoomImageView;
    }

    private View getRefreshRoomButton()
    {
        if (mRefreshRoomButton == null)
        {
            mRefreshRoomButton = findViewById(R.id.iv_refresh_room);
        }
        return mRefreshRoomButton;
    }

    private TextView getOnlineCountTextView()
    {
        if (mOnlineCountTextView == null)
        {
            mOnlineCountTextView = (TextView) findViewById(R.id.tv_online_count);
        }
        return mOnlineCountTextView;
    }

    private FrameLayout getGameGalleryContainer()
    {
        if (mGameGalleryContainer == null)
        {
            mGameGalleryContainer = (FrameLayout) findViewById(R.id.fl_container_game_gallery);
        }
        return mGameGalleryContainer;
    }

    private BMGameCenterGameGalleryView getGameGalleryView()
    {
        if (mGameGalleryView == null)
        {
            mGameGalleryView = new BMGameCenterGameGalleryView(getActivity());
            mGameGalleryView.setCallback(new BMGameCenterGameGalleryView.BMGameCenterGameGalleryViewCallback()
            {

                @Override
                public void onGamePageClick(View view, BMGameCenterGameModel model, int modelPosition)
                {
                    getGameRoomListDialog().setTitleText(model.getName());
                    getGameRoomListDialog().setGameId(model.getChild_id());
                    getGameRoomListDialog().show();
                }
            });
        }
        return mGameGalleryView;
    }

    private BMNumberInputDialog getRoomCodeInputDialog()
    {
        if (mRoomCodeInputDialog == null)
        {
            mRoomCodeInputDialog = new BMNumberInputDialog(getActivity());
            mRoomCodeInputDialog.setTitleText(getString(R.string.input_room_code));
            mRoomCodeInputDialog.setCallback(new BMNumberInputDialog.BMNumberInputDialogCallback()
            {
                @Override
                public void onNumberInput(int num, String output)
                {
                }

                @Override
                public void onNumberFilled(String output)
                {
                    requestGameRoomCodeInput(output);
                }

                @Override
                public void onNumberDeleted(int num, String output)
                {
                }
            });
        }
        return mRoomCodeInputDialog;
    }

    private BMCreateRoomSuccessDialog getCreateRoomSuccessDialog()
    {
        if (mCreateRoomSuccessDialog == null)
        {
            mCreateRoomSuccessDialog = new BMCreateRoomSuccessDialog(getActivity());
            mCreateRoomSuccessDialog.setCallback(new BMCreateRoomSuccessDialog.BMCreateRoomSuccessDialogCallback()
            {
                @Override
                public void onEnterRoomClick(View view)
                {
                    mCreateRoomSuccessDialog.dismiss();
                    enterRoomSelf(mCreateRoomSuccessDialog.getModel().getRoom_id());
                }

                @Override
                public void onInviteClick(View view)
                {
                    BMGameCreateRoomResponseModel.ShareBean model = mCreateRoomSuccessDialog.getModel().getShare();

                    if (model != null)
                    {
                        UmengSocialManager.shareWeixin(model.getShare_title(),
                                model.getShare_content(),
                                model.getShare_imageUrl(),
                                model.getShare_url(),
                                getActivity(),
                                new UMShareListener()
                                {
                                    @Override
                                    public void onStart(SHARE_MEDIA share_media)
                                    {

                                    }

                                    @Override
                                    public void onResult(SHARE_MEDIA share_media)
                                    {
                                        mCreateRoomSuccessDialog.dismiss();
                                    }

                                    @Override
                                    public void onError(SHARE_MEDIA share_media, Throwable throwable)
                                    {

                                    }

                                    @Override
                                    public void onCancel(SHARE_MEDIA share_media)
                                    {

                                    }
                                });
                    }
                }
            });
        }
        return mCreateRoomSuccessDialog;
    }

    private BMGameRoomListDialog getGameRoomListDialog()
    {
        if (mGameRoomListDialog == null)
        {
            mGameRoomListDialog = new BMGameRoomListDialog(getActivity());
            mGameRoomListDialog.setBMGameRoomListDialogCallback(new BMGameRoomListDialog.BMGameRoomListDialogCallback()
            {
                @Override
                public void onEnterClick(View view, BMGameRoomListModel model, int position)
                {
                    mGameRoomListDialog.dismiss();

                    enterRoomOthers(model.getUser_id(), model.getGroup_id(), model.getLive_image(),
                            model.getRoom_id(), 0, model.getCreate_type());
                }
            });
        }
        return mGameRoomListDialog;
    }

    private BMGameFriendsRoomView getGameFriendsRoomView()
    {
        if (mGameFriendsRoomView == null)
        {
            mGameFriendsRoomView = new BMGameFriendsRoomView(getContext());
            mGameFriendsRoomView.setBMGameFriendsRoomViewCallback(new BMGameFriendsRoomView.BMGameFriendsRoomViewCallback()
            {
                @Override
                public void onItemClick(View view, int position, BMGameFriendsRoomModel model)
                {
                    enterRoomOthers(model.getUser_id(), model.getGroup_id(), model.getLive_image(),
                            model.getRoom_id(), 0, model.getCreate_type());
                }
            });
        }
        return mGameFriendsRoomView;
    }

    private void setCreateRoomButtonState(boolean hasRoomCreated)
    {
        getCreateRoomImageView().setImageResource(hasRoomCreated ? R.drawable.bm_bg_btn_my_room
                : R.drawable.bm_bg_btn_create_room);
    }

    /**
     * 请求游戏主页数据
     *
     * @param firstTime
     */
    private void requestGameCenter(final boolean firstTime)
    {
        BMCommonInterface.requestGameHome(new AppRequestCallback<BMGameCenterResponseModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                if (firstTime)
                {
                    getStatesLayout().showLoading();
                }
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    mGameCenterResponseModel = actModel;
                    setCreateRoomButtonState(actModel.getHas_room() > 0);
                    getGameGalleryView().setGameModels(actModel.getList());
                    GlideUtil.loadHeadImage(actModel.getHead_image()).into(getAvatarImageView());
                    SDViewBinder.setTextView(getDiamondsTextView(), actModel.getDiamonds());
                    SDViewBinder.setTextView(getCoinsTextView(), actModel.getCoins());
                    SDViewBinder.setTextView(getOnlineCountTextView(), actModel.getOnline_user() + "");

                    getStatesLayout().showContent();
                } else
                {
                    getStatesLayout().showError();
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                getStatesLayout().showError();
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                Log.e("===>GameCenter", resp.getDecryptedResult());

                if (null != actModel)
                {
                    ViewUtil.setViewVisibleOrGone(getContentLinearLayout(), actModel.getStatus() == 1);
                }
            }
        });
    }

    /**
     * 请求创建私密房间
     */
    private void requestGameCreateRoom()
    {
        BMCommonInterface.requestGameCreateRoom(new AppRequestCallback<BMGameCreateRoomResponseModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    setCreateRoomButtonState(true);
                    if (actModel.getHas_room() == 1)
                    {
                        enterRoomSelf(actModel.getRoom_id());
                    } else
                    {
                        getCreateRoomSuccessDialog()
                                .setModel(actModel)
                                .show();
                    }
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
                Log.e("===>CreateRoom", resp.getDecryptedResult());
            }
        });
    }

    /**
     * 请求关注用户房间列表
     */
    private void requestGameFriendsRoom()
    {
        BMCommonInterface.requestGameFriendsRoomList(new AppRequestCallback<BMGameFriendsRoomResponseModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    getGameFriendsRoomView().setData(actModel.getList());
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                Log.e("===>GameFriendsRoom", resp.getDecryptedResult());
            }
        });
    }

    /**
     * 请求输入房间密码
     *
     * @param code
     */
    private void requestGameRoomCodeInput(final String code)
    {
        BMCommonInterface.requestGameRoomCodeInput(code, new AppRequestCallback<BMGameRoomCodeInputResponseModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    getRoomCodeInputDialog().dismiss();

                    BMGameRoomCodeInputResponseModel.RoomBean model = actModel.getRoom();

                    enterRoomOthers(model.getUser_id(), model.getGroup_id(), model.getLive_image(),
                            model.getRoom_id(), model.getSdk_type(), model.getCreate_type());
                } else
                {
                    getRoomCodeInputDialog().animError();
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
                Log.e("===>RoomCodeInput", resp.getDecryptedResult());
            }
        });
    }

    /**
     * 根据房间信息，进入其他人创建的房间
     *
     * @param userId
     * @param group_id
     * @param liveImage
     * @param roomId
     * @param sdkType
     * @param createType
     */
    private void enterRoomOthers(String userId, String group_id, String liveImage, String roomId, int sdkType, String createType)
    {
        JoinLiveData data = new JoinLiveData();

        data.setCreaterId(userId);
        data.setGroupId(group_id);
        data.setLoadingVideoImageUrl(liveImage);
        data.setRoomId(Integer.valueOf(roomId));
        data.setSdkType(sdkType);
        data.setCreate_type(Integer.valueOf(createType));

        AppRuntimeWorker.joinLive(data, getActivity());
    }

    /**
     * 进入自己创建的房间
     *
     * @param roomId
     */
    private void enterRoomSelf(int roomId)
    {
        if (roomId <= 0)
        {
            SDToast.showToast("roomId<=0");
            return;
        }

        CreateLiveData data = new CreateLiveData();
        data.setRoomId(roomId);
        AppRuntimeWorker.createLive(data, getActivity());
    }


}