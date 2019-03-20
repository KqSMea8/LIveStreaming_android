package com.fanwe.live.activity.room;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SeekBar;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveVideoView;
import com.fanwe.live.appview.room.RoomPlayControlView;
import com.fanwe.live.model.App_get_videoActModel;
import com.fanwe.live.view.SDVerticalScollView;
import com.tencent.TIMCallBack;

/**
 * Created by Administrator on 2016/8/8.
 */
public class LivePlaybackActivity extends LivePlayActivity
{
    private RoomPlayControlView mRoomPlayControlView;
    private FrameLayout fl_live_play_control;

    private int mSeekValue;
    private boolean mHasVideoControl;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_playback_new;
    }

    @Override
    protected void initLayout(View view)
    {
        super.initLayout(view);

        fl_live_play_control = (FrameLayout) view.findViewById(R.id.fl_live_play_control);
        addLivePlayControl();
    }

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);

        setVideoView((LiveVideoView) findViewById(R.id.view_video));
        requestRoomInfo();
    }

    private void addLivePlayControl()
    {
        mRoomPlayControlView = new RoomPlayControlView(this);
        mRoomPlayControlView.setClickListener(controlClickListener);
        mRoomPlayControlView.setOnSeekBarChangeListener(controlSeekBarListener);
        replaceView(fl_live_play_control, mRoomPlayControlView);
        SDViewUtil.setGone(mRoomPlayControlView);

        updatePlayButton();
        updateDuration(0, 0);
    }

    @Override
    protected void onShowSendGiftView(View view)
    {
        if (mHasVideoControl)
        {
            SDViewUtil.setInvisible(mRoomPlayControlView);
        }
        super.onShowSendGiftView(view);
    }

    @Override
    protected void onHideSendGiftView(View view)
    {
        if (mHasVideoControl)
        {
            SDViewUtil.setVisible(mRoomPlayControlView);
        }
        super.onHideSendGiftView(view);
    }

    @Override
    protected void initIM()
    {
        super.initIM();

        final String groupId = getGroupId();
        getViewerIM().joinGroup(groupId, new TIMCallBack()
        {
            @Override
            public void onError(int i, String s)
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
    protected void destroyIM()
    {
        super.destroyIM();
        getViewerIM().destroyIM();
    }

    @Override
    protected void requestRoomInfo()
    {
        getLiveBusiness().requestRoomInfo(mStrPrivateKey);
    }

    @Override
    public void onBsRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        super.onBsRequestRoomInfoSuccess(actModel);
        dealRequestRoomInfoSuccess(actModel);
    }

    private void dealRequestRoomInfoSuccess(App_get_videoActModel actModel)
    {
        mHasVideoControl = actModel.getHas_video_control() == 1;
        if (mHasVideoControl)
        {
            SDViewUtil.setVisible(mRoomPlayControlView);
        } else
        {
            SDViewUtil.setGone(mRoomPlayControlView);
        }

        switchVideoViewMode();

        if (actModel.getIs_del_vod() == 1)
        {
            SDToast.showToast("视频已删除");
            getViewerBusiness().exitRoom(true);
            return;
        } else
        {
            initIM();
            String playUrl = actModel.getPlay_url();
            if (!TextUtils.isEmpty(playUrl))
            {
                prePlay(playUrl);
            } else
            {
                SDToast.showToast("视频已删除.");
                getViewerBusiness().exitRoom(true);
            }
        }
    }

    @Override
    public void onBsRequestRoomInfoError(App_get_videoActModel actModel)
    {
        LogUtil.i("onLiveRequestRoomInfoError");
        if (!actModel.canJoinRoom())
        {
            super.onBsRequestRoomInfoError(actModel);
            return;
        }
        super.onBsRequestRoomInfoError(actModel);
        getViewerBusiness().exitRoom(true);
    }

    private void switchVideoViewMode()
    {
        if (getVideoView() == null)
        {
            return;
        }
        if (getLiveBusiness().isPCCreate())
        {
            float height = 0.618f * SDViewUtil.getScreenWidth();

            SDViewUtil.setHeight(getVideoView(), (int) height);
            SDViewUtil.setMarginTop(getVideoView(), SDViewUtil.dp2px(80));
        } else
        {
            SDViewUtil.setHeight(getVideoView(), ViewGroup.LayoutParams.MATCH_PARENT);
            SDViewUtil.setMarginTop(getVideoView(), 0);
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

    private void prePlay(String url)
    {
        if (getPlayer().setVodUrl(url))
        {
            clickPlayVideo();
        } else
        {
            SDToast.showToast("播放地址不合法");
        }
    }


    private boolean seekPlayerIfNeed()
    {
        if (mSeekValue > 0 && mSeekValue < getPlayer().getTotal())
        {
            getPlayer().seek(mSeekValue);
            mSeekValue = 0;
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

        mRoomPlayControlView.setMax(total);
        mRoomPlayControlView.setProgress(progress);
    }

    @Override
    public void onPlayEnd(int event, Bundle param)
    {
        super.onPlayEnd(event, param);
        updateDuration(getPlayer().getTotal(), getPlayer().getProgress());
        updatePlayButton();
    }

    private SeekBar.OnSeekBarChangeListener controlSeekBarListener = new SeekBar.OnSeekBarChangeListener()
    {
        private boolean needResume = false;

        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            if (getPlayer().getTotal() > 0)
            {
                mSeekValue = seekBar.getProgress();
                if (needResume)
                {
                    needResume = false;
                    getPlayer().resume();
                }
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
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            if (getPlayer().getTotal() > 0)
            {
                updateDuration(seekBar.getMax(), progress);
            }
        }
    };

    private RoomPlayControlView.ClickListener controlClickListener = new RoomPlayControlView.ClickListener()
    {
        @Override
        public void onClickPlayVideo(View v)
        {
            clickPlayVideo();
        }
    };

    protected void clickPlayVideo()
    {
        getPlayer().performPlay();
        updatePlayButton();

        setPauseMode(!isPlaying());
    }

    private void updatePlayButton()
    {
        if (isPlaying())
        {
            mRoomPlayControlView.setImagePlayVideo(R.drawable.ic_live_bottom_pause_video);
        } else
        {
            mRoomPlayControlView.setImagePlayVideo(R.drawable.ic_live_bottom_play_video);
        }
    }

    private void updateDuration(int total, int progress)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(SDDateUtil.formatDuring2mmss(progress * 1000)).append("/").append(SDDateUtil.formatDuring2mmss(total * 1000));

        mRoomPlayControlView.setTextDuration(sb.toString());
    }

    @Override
    protected void initSDVerticalScollView(SDVerticalScollView scollView)
    {
        super.initSDVerticalScollView(scollView);
        scollView.setEnableVerticalScroll(false);
    }

    @Override
    protected void onClickCloseRoom(View v)
    {
        getViewerBusiness().exitRoom(true);
    }

    @Override
    public void onBackPressed()
    {
        getViewerBusiness().exitRoom(true);
    }

    @Override
    public void onBsViewerExitRoom(boolean needFinishActivity)
    {
        super.onBsViewerExitRoom(needFinishActivity);
        destroyIM();
        finish();
    }

}
