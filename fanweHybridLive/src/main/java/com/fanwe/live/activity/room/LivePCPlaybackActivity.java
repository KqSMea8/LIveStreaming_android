package com.fanwe.live.activity.room;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.fanwe.library.adapter.SDPagerAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.customview.SDViewPager;
import com.fanwe.library.listener.SDViewVisibilityCallback;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDTabUnderline;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveVideoView;
import com.fanwe.live.appview.room.ARoomPCBaseControlView;
import com.fanwe.live.appview.room.ARoomPCPlaybackBaseControlView;
import com.fanwe.live.appview.room.RoomContributionView;
import com.fanwe.live.appview.room.RoomPCLiveView;
import com.fanwe.live.appview.room.RoomPCMessageView;
import com.fanwe.live.appview.room.RoomPCPlaybackSeekbarView;
import com.fanwe.live.appview.room.RoomSendGiftView;
import com.fanwe.live.appview.room.RoomView;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.custommsg.CustomMsgViewerJoin;
import com.tencent.TIMCallBack;
import com.tencent.rtmp.TXLivePlayer;

import java.util.ArrayList;
import java.util.List;

import master.flame.danmaku.controller.DrawHandler;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.DanmakuTimer;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.DanmakuContext;
import master.flame.danmaku.danmaku.model.android.Danmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.ui.widget.DanmakuView;

/**
 * 推流直播间观众界面 ---PC端直播
 */
public class LivePCPlaybackActivity extends LivePCPlayActivity implements ARoomPCBaseControlView.PCControlViewClickListener,
        RoomPCMessageView.RoomMessageViewListener
{

    private View mViewPortrait;//竖屏view

    private View mViewLandscape;//横屏view

    private LiveVideoView mViewVideo;

    private SDTabUnderline tab_room_chat;//聊天页面

    private SDTabUnderline tab_room_con;//贡献榜页面

    private SDTabUnderline tab_room_live;//直播列表页面

    private SDViewPager vpg_content;//fragment页面容器

    private RelativeLayout view_touch_scroll;

    private FrameLayout fl_container_gift;

    private FrameLayout fl_container_video;

    private FrameLayout.LayoutParams mParamsVideo;

    private RoomSendGiftView mViewGift;

    private DanmakuView danmaku_view;

    private DanmakuCallBack mCallBackDanmaku;

    private SDSelectViewManager<SDTabUnderline> selectViewManager = new SDSelectViewManager<>();

    private ViewPager.OnPageChangeListener mListenerPageChange;

    private SDTabUnderline[] items = new SDTabUnderline[3];

    private ARoomPCPlaybackBaseControlView mViewControl;

    private boolean showDanmaku;

    private DanmakuContext mContextDanmaku;

    /**
     * 直播的类型，仅用于观众时候需要传入0-热门;1-最新;2-关注(int)
     */
    public static final String EXTRA_LIVE_TYPE = "extra_live_type";
    /**
     * 私密直播的key(String)
     */
    public static final String EXTRA_PRIVATE_KEY = "extra_private_key";
    /**
     * 性别0-全部，1-男，2-女(int)
     */
    public static final String EXTRA_SEX = "extra_sex";
    /**
     * 话题id(int)
     */
    public static final String EXTRA_CATE_ID = "extra_cate_id";
    /**
     * 城市(String)
     */
    public static final String EXTRA_CITY = "extra_city";

    /**
     * 直播的类型0-热门;1-最新
     */
    protected int type;
    /**
     * 私密直播的key
     */
    protected String strPrivateKey;
    /**
     * 性别0-全部，1-男，2-女
     */
    protected int sex;
    /**
     * 话题id
     */
    protected int cate_id;
    /**
     * 城市
     */
    protected String city;

    private int mValueSeek;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_pc_playback_port;
    }

    @Override
    public void setContentView(View view)
    {
        if (isOrientationPortrait())
        {
            mViewPortrait = view;
        } else
        {
            mViewLandscape = view;
        }
        super.setContentView(view);
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        type = getIntent().getIntExtra(EXTRA_LIVE_TYPE, 0);
        strPrivateKey = getIntent().getStringExtra(EXTRA_PRIVATE_KEY);
        sex = getIntent().getIntExtra(EXTRA_SEX, 0);
        city = getIntent().getStringExtra(EXTRA_CITY);
        requestRoomInfo();
        mCallBackDanmaku = new DanmakuCallBack();
        initCommonView();
        initSDViewPager();
        initTabs();
    }

    /**
     * 横竖屏切换需要调用该方法重新实例化各组件
     */
    private void initCommonView()
    {
        if (mViewVideo == null)
        {
            initVideoView();
            initVideoParams();
        }
        fl_container_video = find(R.id.fl_container_video);
        fl_container_video.addView(mViewVideo, mParamsVideo);
        startPlayVideo();
        view_touch_scroll = find(R.id.view_touch_scroll);
        danmaku_view = find(R.id.danmaku_view);
        initOrientationView();
        initDanmakuView();
    }

    private void initVideoView()
    {
        mViewVideo = new LiveVideoView(this);
        mViewVideo.setBackgroundColor(Color.WHITE);
        setVideoView(mViewVideo);
        getPlayer().setRenderModeAdjustResolution();
    }

    private void initVideoParams()
    {
        mParamsVideo = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }

    private void startPlayVideo()
    {
        if (!getPlayer().isPlayerStarted())
        {
            getPlayer().startPlay();
        } else
        {
            getPlayer().resume();
        }
    }

    private void initOrientationView()
    {
        if (isOrientationPortrait())
        {
            setPlayerSize();
            initPortraitView();
        } else
        {
            initLandscapeView();
        }
    }

    private void setPlayerSize()
    {
        SDViewUtil.setHeight(view_touch_scroll, SDViewUtil.getScreenHeight() / 3);
    }

    private void initDanmakuView()
    {
        danmaku_view.enableDanmakuDrawingCache(true);
        danmaku_view.setCallback(mCallBackDanmaku);
        mContextDanmaku = DanmakuContext.create();
        danmaku_view.prepare(parser, mContextDanmaku);
        danmaku_view.clear();
        danmaku_view.removeAllDanmakus(true);
        danmaku_view.resume();
    }

    private BaseDanmakuParser parser = new BaseDanmakuParser()
    {
        @Override
        protected IDanmakus parse()
        {
            return new Danmakus();
        }
    };

    @Override
    public void onClickSwitchScreenOrientation(View view)
    {
        switchScreenOrientation();
    }

    @Override
    public void onClickPause(View view)
    {
        addDanmaku(System.currentTimeMillis() + "", false);
    }

    @Override
    public void onClickClose(View view)
    {
        exitRoom();
    }

    @Override
    public void onClickRefresh(View view)
    {
        //刷新，重载视频
    }

    @Override
    public void onClickLives(View view)
    {
        //房间列表
        //首先隐藏控制视图
        //右边滑出房间列表
        addDanmaku(System.currentTimeMillis() + "", true);
    }

    @Override
    public void onClickLock(View view)
    {
        //锁定控制层
        addDanmaku(System.currentTimeMillis() + "", false);
    }

    @Override
    public void onClickGift(View view)
    {
        //横屏显示礼物框
        addDanmaku(System.currentTimeMillis() + "", false);
    }

    @Override
    public void onClickDanmakuSwitch(View view)
    {
        //弹幕开关
        addDanmaku(System.currentTimeMillis() + "", false);
    }

    @Override
    public void showGiftViewPort(View view)
    {
        //竖屏显示礼物框
        SDKeyboardUtil.hideKeyboard(view);
        addSendGiftView();
        mViewGift.getVisibilityHandler().setVisible(true);
    }

    private void addSendGiftView()
    {
        if (mViewGift == null)
        {
            mViewGift = new RoomSendGiftView(this);
            mViewGift.addVisibilityCallback(new SDViewVisibilityCallback()
            {

                @Override
                public void onViewVisibilityChanged(View view, int visibility)
                {
                    if (View.VISIBLE != visibility)
                    {
                        removeView(mViewGift);
                    }
                }
            });
        }
        mViewGift.bindData();
        replaceView(fl_container_gift, mViewGift);
    }

    @Override
    protected void onHideSendGiftView(View view)
    {
        super.onHideSendGiftView(view);
        if (mViewGift != null)
        {
            SDViewUtil.setInvisible(mViewGift);
        }
    }

    @Override
    protected boolean isSendGiftViewVisible()
    {
        return mViewGift != null && mViewGift.isVisible();
    }

    private class DanmakuCallBack implements DrawHandler.Callback
    {

        @Override
        public void prepared()
        {
            showDanmaku = true;
            danmaku_view.start();
        }

        @Override
        public void updateTimer(DanmakuTimer timer)
        {

        }

        @Override
        public void danmakuShown(BaseDanmaku danmaku)
        {

        }

        @Override
        public void drawingFinished()
        {

        }
    }

    /**
     * 向弹幕View中添加一条弹幕
     *
     * @param content    弹幕的具体内容
     * @param withBorder 弹幕是否有边框
     */
    private void addDanmaku(String content, boolean withBorder)
    {
        BaseDanmaku danmaku = mContextDanmaku.mDanmakuFactory.createDanmaku(BaseDanmaku.TYPE_SCROLL_RL, mContextDanmaku);
        danmaku.text = content;
        danmaku.padding = 5;
        danmaku.textSize = 30;
        danmaku.textColor = Color.BLUE;
        danmaku.setTime(danmaku_view.getCurrentTime());
        if (withBorder)
        {
            danmaku.borderColor = Color.CYAN;
        }
        danmaku_view.addDanmaku(danmaku);
    }

    private void initPortraitView()
    {
        fl_container_gift = find(R.id.fl_container_gift);
        tab_room_chat = find(R.id.tab_room_chat);
        tab_room_con = find(R.id.tab_room_con);
        tab_room_live = find(R.id.tab_room_live);
        vpg_content = find(R.id.vpg_content);
        mViewControl = find(R.id.view_control_port);
        mViewControl.setPCControlViewClickListener(this);
        mViewControl.setSeekBarListener(mListenerSeekBar);
        mViewControl.setPlaybackBottonListener(mListenerPlaybackBotton);
        mViewControl.updateProgress(0, 0);
        mViewControl.showControlView();
    }

    private void initLandscapeView()
    {
        mViewControl = find(R.id.view_control_land);
        mViewControl.setPCControlViewClickListener(this);
        mViewControl.setSeekBarListener(mListenerSeekBar);
        mViewControl.setPlaybackBottonListener(mListenerPlaybackBotton);
        mViewControl.updateProgress(0, 0);
        mViewControl.showControlView();
    }

    private void switchScreenOrientation()
    {
        if (isOrientationLandscape())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        else if (isOrientationPortrait())
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getPlayer().pause();
        fl_container_video.removeAllViews();
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.iv_pc_close:
                //关闭直播间
                exitRoom();
                break;
            case R.id.iv_pc_switch_fullscreen:
                switchScreenOrientation();
                break;
            case R.id.iv_pc_return:
                switchScreenOrientation();
                break;
            case R.id.iv_pc_pause:
                addDanmaku(System.currentTimeMillis() + "", false);
                break;
            default:
                break;
        }
    }

    private void initSDViewPager()
    {
        vpg_content.setOffscreenPageLimit(2);
        List<String> listModel = new ArrayList<>();
        listModel.add("");
        listModel.add("");
        listModel.add("");

        if (mListenerPageChange == null)
        {
            mListenerPageChange = new ViewPager.OnPageChangeListener()
            {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
                {

                }

                @Override
                public void onPageSelected(int position)
                {
                    if (selectViewManager.getSelectedIndex() != position)
                    {
                        selectViewManager.setSelected(position, true);
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state)
                {

                }
            };
        }
        RoomTabAdapter adapterTab = new RoomTabAdapter(listModel, this);
        vpg_content.addOnPageChangeListener(mListenerPageChange);
        vpg_content.setAdapter(adapterTab);
    }

    private void initTabs()
    {
        // 聊天
        String room_pc_tab_chat_text = getString(R.string.room_pc_tab_chat_text);
        tab_room_chat.setTextTitle(room_pc_tab_chat_text);
        Log.i("test", tab_room_chat.toString());
        tab_room_chat.getViewConfig(tab_room_chat.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT)
                .setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_room_chat.getViewConfig(tab_room_chat.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.white)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color));

        // 贡献榜
        String room_pc_tab_con_text = getString(R.string.room_pc_tab_con_text);
        tab_room_con.setTextTitle(room_pc_tab_con_text);
        tab_room_con.getViewConfig(tab_room_con.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_room_con.getViewConfig(tab_room_con.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.white)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color));

        // 直播
        String room_pc_tab_live_text = getString(R.string.room_pc_tab_live_text);
        tab_room_live.setTextTitle(room_pc_tab_live_text);
        tab_room_live.getViewConfig(tab_room_live.mIvUnderline).setBackgroundColorNormal(Color.TRANSPARENT).setBackgroundColorSelected(SDResourcesUtil.getColor(R.color.main_color));
        tab_room_live.getViewConfig(tab_room_live.mTvTitle).setTextColorNormal(SDResourcesUtil.getColor(R.color.white)).setTextColorSelected(SDResourcesUtil.getColor(R.color.main_color));

        items[0] = tab_room_chat;
        items[1] = tab_room_con;
        items[2] = tab_room_live;

        selectViewManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabUnderline>()
        {

            @Override
            public void onNormal(int index, SDTabUnderline item)
            {
            }

            @Override
            public void onSelected(int index, SDTabUnderline item)
            {
                switch (index)
                {
                    case 0:
                        vpg_content.setCurrentItem(0);
                        break;
                    case 1:
                        vpg_content.setCurrentItem(1);
                        break;
                    case 2:
                        vpg_content.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });
        selectViewManager.setItems(items);
        selectViewManager.setSelected(0, true);//默认选中聊天页面
    }

    private class RoomTabAdapter extends SDPagerAdapter<String>
    {

        RoomView view = null;

        RoomTabAdapter(List<String> listModel, Activity activity)
        {
            super(listModel, activity);
        }

        @Override
        public View getView(ViewGroup container, int position)
        {
            switch (position)
            {
                case 0:
                    view = getRoomPCMessageView();
                    break;
                case 1:
                    view = new RoomContributionView(getActivity());
                    break;
                case 2:
                    view = new RoomPCLiveView(getActivity());
                    break;
                default:
                    break;
            }
            return view;
        }
    }

    private RoomPCMessageView getRoomPCMessageView()
    {
        return new RoomPCMessageView(this, this);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
        if (isOrientationPortrait())
        {
            if (mViewPortrait == null)
            {
                setContentView(R.layout.act_live_pc_playback_port);
            } else
            {
                setContentView(mViewPortrait);
            }
            setFullScreen(false);
        } else
        {

            if (mViewLandscape == null)
            {
                setContentView(R.layout.act_live_pc_playback_land);
            } else
            {
                setContentView(mViewLandscape);
            }
            setFullScreen(true);
        }
        initCommonView();
    }

    @Override
    protected void initIM()
    {
        super.initIM();

        final String groupId = getGroupId();
        getViewerIM().joinGroup(groupId, new TIMCallBack()
        {
            @Override
            public void onError(int code, String desc)
            {
            }

            @Override
            public void onSuccess()
            {
                sendViewerJoinMsg();
            }
        });
    }

    @Override
    protected void onResume()
    {
        if (danmaku_view != null && danmaku_view.isPrepared() && danmaku_view.isPaused())
        {
            danmaku_view.resume();
        }

        if (getPlayer().isPlayerStarted() && getPlayer().isPaused())
        {
            getPlayer().resume();
            //暂停模式不处理
        }
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (danmaku_view != null && danmaku_view.isPrepared())
        {
            danmaku_view.pause();
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        showDanmaku = false;
        if (danmaku_view != null)
        {
            danmaku_view.release();
            danmaku_view = null;
        }
        getPlayer().onDestroy();
    }

    /**
     * 发送观众加入消息
     */
    public void sendViewerJoinMsg()
    {
        if (!getViewerIM().isCanSendViewerJoinMsg())
        {
            return;
        }
        App_get_videoActModel actModel = getRoomInfo();
        if (actModel == null)
        {
            return;
        }
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            return;
        }

        boolean sendViewerJoinMsg = true;
        if (!user.isProUser() && actModel.getJoin_room_prompt() == 0)
        {
            sendViewerJoinMsg = false;
        }

        if (sendViewerJoinMsg)
        {
            CustomMsgViewerJoin joinMsg = new CustomMsgViewerJoin();
            joinMsg.setSortNumber(actModel.getSort_num());

            getViewerIM().sendViewerJoinMsg(joinMsg, null);
        }
    }

    /**
     * 退出房间
     */
    protected void exitRoom()
    {

        destroyIM();
        finish();
        getPlayer().stopPlay();
    }

    @Override
    protected void destroyIM()
    {
        super.destroyIM();
        getViewerIM().destroyIM();
    }

    @Override
    protected void requestRoomInfo()
    {
        getLiveBusiness().requestRoomInfo(strPrivateKey);
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        dealRequestRoomInfoSuccess(actModel);
    }

    private void dealRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {

        if (actModel.getIs_del_vod() == 1)
        {
            SDToast.showToast("视频已删除");
            exitRoom();
            return;
        } else
        {
            initIM();
            String playUrl = actModel.getPlay_url();
            if (!TextUtils.isEmpty(playUrl))
            {
                prePlay(playUrl, TXLivePlayer.PLAY_TYPE_VOD_MP4);
            } else
            {
                SDToast.showToast("视频已删除.");
            }
        }
    }

    @Override
    public void onBsViewerStartJoinRoom()
    {
        super.onBsViewerStartJoinRoom();
        if (getRoomInfo() == null)
        {
            return;
        }
        String playUrl = getRoomInfo().getPlay_url();
        if (TextUtils.isEmpty(playUrl))
        {
            requestRoomInfo();
        } else
        {
            dealRequestRoomInfoSuccess(getRoomInfo());
        }
    }

    @Override
    public void onBackPressed()
    {
        if (isOrientationLandscape())
        {
            switchScreenOrientation();
        } else
            exitRoom();
    }

    @Override
    protected void onClickCloseRoom(View v)
    {
        exitRoom();
    }

    private void prePlay(String url, int playType)
    {
        if (isEmpty(url))
        {
            SDToast.showToast("url为空");
            return;
        }

        getPlayer().setUrl(url);
        getPlayer().setPlayType(playType);

        clickPlayVideo();
    }

    protected void clickPlayVideo()
    {
        getPlayer().performPlay();
        updatePlayButton();
        setPauseMode(!isPlaying());
    }

    private boolean seekPlayerIfNeed()
    {
        if (mValueSeek > 0 && mValueSeek < getPlayer().getTotal())
        {
            getPlayer().seek(mValueSeek);
            mValueSeek = 0;
            return true;
        } else
        {
            return false;
        }
    }

    @Override
    public void onPlayBegin(int event, Bundle param)
    {
        super.onPlayBegin(event, param);
        updatePlayButton();
    }

    @Override
    public void onPlayProgress(int event, Bundle param, int total, int progress)
    {
        super.onPlayProgress(event, param, total, progress);
        if (seekPlayerIfNeed())
        {
            return;
        }
        mViewControl.setSeekBarMax(total);
        mViewControl.setSeekBarProgress(progress);
    }

    @Override
    public void onPlayEnd(int event, Bundle param)
    {
        super.onPlayEnd(event, param);
        mViewControl.updateProgress(getPlayer().getTotal(), getPlayer().getProgress());
        updatePlayButton();
    }

    private void updatePlayButton()
    {
        mViewControl.updateBotton(isPlaying());
    }

    private RoomPCPlaybackSeekbarView.PlaybackBottonListener mListenerPlaybackBotton = new RoomPCPlaybackSeekbarView.PlaybackBottonListener()
    {

        @Override
        public void onClickPlayOrPause(boolean isPlaying)
        {
            clickPlayVideo();
        }

        @Override
        public void onClickFullScreen()
        {
            switchScreenOrientation();
        }
    };

    private SeekBar.OnSeekBarChangeListener mListenerSeekBar = new SeekBar.OnSeekBarChangeListener()
    {

        private boolean needResume = false;

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {

            if (getPlayer().getTotal() > 0)
            {
                mViewControl.updateProgress(progress, seekBar.getMax());
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {

            if (getPlayer().getTotal() > 0)
            {
                if (getPlayer().isPlaying())
                {
                    getPlayer().pause();
                    needResume = true;
                }
            }
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {

            if (getPlayer().getTotal() > 0)
            {
                mValueSeek = seekBar.getProgress();
                if (needResume)
                {
                    needResume = false;
                    getPlayer().resume();
                }
            }
        }
    };

}
