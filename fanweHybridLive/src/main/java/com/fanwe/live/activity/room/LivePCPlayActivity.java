package com.fanwe.live.activity.room;

import android.os.Bundle;
import com.fanwe.live.appview.LiveVideoView;
import com.fanwe.live.control.LivePlayerSDK;
import com.fanwe.live.model.LiveQualityData;

/**
 * pc端直播--播放业务
 * {@link #setVideoView} 首先实例化PalyView
 * {@link LivePCViewerActivity} 直播页面
 * {@link LivePCPlaybackActivity} 回放页面
 * Created by shibx on 2017/01/18.
 */
public class LivePCPlayActivity extends LivePCLayoutActivity implements LivePlayerSDK.PlayerListener
{

    private LiveVideoView playView;
    private LivePlayerSDK player;
    private boolean isPauseMode = true;

    public void setVideoView(LiveVideoView videoView) {
        this.playView = videoView;

        player = playView.getPlayer();
        player.setPlayerListener(this);
    }

    public LivePlayerSDK getPlayer()
    {
        return player;
    }

    public LiveVideoView getPlayView()
    {
        return playView;
    }

    public void setPauseMode(boolean pauseMode)
    {
        isPauseMode = pauseMode;
    }

    @Override
    public void onPlayEvent(int event, Bundle param) {

    }

    @Override
    public void onPlayBegin(int event, Bundle param) {

    }

    @Override
    public void onPlayRecvFirstFrame(int event, Bundle param) {

    }

    @Override
    public void onPlayProgress(int event, Bundle param, int total, int progress) {

    }

    @Override
    public void onPlayEnd(int event, Bundle param) {

    }

    @Override
    public void onPlayLoading(int event, Bundle param) {

    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }

    protected void setVideoQuality() {

    }

    @Override
    public LiveQualityData onBsGetLiveQualityData() {
        return player.getLiveQualityData();
    }

    public boolean isPlaying() {
        return player != null && player.isPlaying();
    }

    @Override
    protected void onResume() {

        if (isPauseMode) {
            //pc需处理暂停模式
//            player.pause();
        } else {
            player.resume();
        }
        super.onResume();
    }
}
