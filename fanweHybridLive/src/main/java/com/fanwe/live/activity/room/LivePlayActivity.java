package com.fanwe.live.activity.room;

import android.os.Bundle;

import com.fanwe.live.LiveInformation;
import com.fanwe.live.appview.LiveVideoView;
import com.fanwe.live.control.LivePlayerSDK;
import com.fanwe.live.model.LiveQualityData;

/**
 * Created by Administrator on 2016/8/7.
 */
public class LivePlayActivity extends LiveLayoutViewerExtendActivity implements LivePlayerSDK.PlayerListener
{

    private LiveVideoView mVideoView;
    private boolean mIsPauseMode = false;

    @Override
    protected void init(Bundle savedInstanceState)
    {
        super.init(savedInstanceState);
        LiveInformation.getInstance().setPlayback(true);
    }

    public void setVideoView(LiveVideoView videoView)
    {
        this.mVideoView = videoView;

        this.mVideoView.setPlayerListener(this);
    }

    public LivePlayerSDK getPlayer()
    {
        if (mVideoView != null)
        {
            return mVideoView.getPlayer();
        }
        return null;
    }

    public LiveVideoView getVideoView()
    {
        return mVideoView;
    }

    public void setPauseMode(boolean pauseMode)
    {
        mIsPauseMode = pauseMode;
    }

    @Override
    public void onPlayEvent(int event, Bundle param)
    {

    }

    @Override
    public void onPlayBegin(int event, Bundle param)
    {

    }

    @Override
    public void onPlayRecvFirstFrame(int event, Bundle param)
    {

    }

    @Override
    public void onPlayProgress(int event, Bundle param, int total, int progress)
    {

    }

    @Override
    public void onPlayEnd(int event, Bundle param)
    {

    }

    @Override
    public void onPlayLoading(int event, Bundle param)
    {

    }

    @Override
    public void onNetStatus(Bundle param)
    {

    }

    @Override
    public LiveQualityData onBsGetLiveQualityData()
    {
        return getPlayer().getLiveQualityData();
    }

    public boolean isPlaying()
    {
        if (getPlayer() != null)
        {
            return getPlayer().isPlaying();
        }
        return false;
    }

    @Override
    protected void onResume()
    {
        if (mIsPauseMode)
        {
            //暂停模式不处理
        } else
        {
            getPlayer().resume();
        }
        super.onResume();
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        getPlayer().pause();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        getPlayer().onDestroy();
    }
}
