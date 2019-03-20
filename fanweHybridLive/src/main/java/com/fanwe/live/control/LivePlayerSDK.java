package com.fanwe.live.control;

import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.hybrid.app.App;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.model.LiveQualityData;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * Created by Administrator on 2016/7/25.
 */
public class LivePlayerSDK implements ITXLivePlayListener
{
    private TXLivePlayer mPlayer;
    private TXCloudVideoView mVideoView;

    /**
     * 播放链接
     */
    private String mUrl;
    /**
     * 播放类型
     */
    private int mPlayType;
    /**
     * 总的播放时间(秒)
     */
    private int mTotal;
    /**
     * 当前播放的进度(秒)
     */
    private int mProgress;
    /**
     * 是否已经开始播放
     */
    private boolean mIsPlayerStarted = false;
    /**
     * 是否暂停
     */
    private boolean mIsPaused = false;

    private PlayerListener mPlayerListener;
    private LiveQualityData mLiveQualityData;


    public LivePlayerSDK()
    {
        mLiveQualityData = new LiveQualityData();
        mPlayer = new TXLivePlayer(App.getApplication());
    }

    public LiveQualityData getLiveQualityData()
    {
        return mLiveQualityData;
    }

    public boolean isPaused()
    {
        return mIsPaused;
    }

    /**
     * 初始化
     *
     * @param videoView
     */
    public void init(TXCloudVideoView videoView)
    {
        LogUtil.i("init player:" + videoView);
        this.mVideoView = videoView;
        mPlayer.setPlayerView(videoView);

        setRenderModeFill();
        setRenderRotationPortrait();
        enableHardwareDecode(true);
    }

    /**
     * 是否正在播放中
     */
    public boolean isPlaying()
    {
        return mIsPlayerStarted && !mIsPaused;
    }

    /**
     * 是否已经开始播放
     */
    public boolean isPlayerStarted()
    {
        return mIsPlayerStarted;
    }

    public boolean enableHardwareDecode(boolean enable)
    {
        return mPlayer.enableHardwareDecode(enable);
    }

    /**
     * 全屏渲染
     */
    public void setRenderModeFill()
    {
        mPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
    }

    /**
     * 按分辨率渲染
     */
    public void setRenderModeAdjustResolution()
    {
        mPlayer.setRenderMode(TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION);
    }

    /**
     * 竖屏
     */
    public void setRenderRotationPortrait()
    {
        mPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
    }

    /**
     * 横屏
     */
    public void setRenderRotationLandscape()
    {
        mPlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_LANDSCAPE);
    }

    /**
     * 设置静音
     *
     * @param mute true-静音
     */
    public void setMute(boolean mute)
    {
        mPlayer.setMute(mute);
    }

    /**
     * 获得总的播放时间(秒)
     *
     * @return
     */
    public int getTotal()
    {
        return mTotal;
    }

    /**
     * 获得当前播放的进度(秒)
     *
     * @return
     */
    public int getProgress()
    {
        return mProgress;
    }

    public void setPlayerListener(PlayerListener playerListener)
    {
        this.mPlayerListener = playerListener;
    }

    /**
     * 设置播放地址
     */
    public void setUrl(String url)
    {
        this.mUrl = url;
    }

    /**
     * 设置点播地址
     *
     * @param url
     * @return
     */
    public boolean setVodUrl(String url)
    {
        if (TextUtils.isEmpty(url))
        {
            return false;
        }

        if (url.startsWith("http://") || url.startsWith("https://"))
        {
            if (url.contains(".flv"))
            {
                setPlayType(TXLivePlayer.PLAY_TYPE_VOD_FLV);
            } else if (url.contains(".m3u8"))
            {
                setPlayType(TXLivePlayer.PLAY_TYPE_VOD_HLS);
            } else if (url.contains(".mp4"))
            {
                setPlayType(TXLivePlayer.PLAY_TYPE_VOD_MP4);
            } else
            {
                return false;
            }
        } else
        {
            return false;
        }

        setUrl(url);
        return true;
    }

    public String getUrl()
    {
        return mUrl;
    }

    /**
     * 设置播放类型 flv,mp4等。。。
     *
     * @param playType TXLivePlayer.PLAY_TYPE_XXXXXXX
     */
    public void setPlayType(int playType)
    {
        this.mPlayType = playType;
    }

    public void restartPlay()
    {
        stopPlay();
        startPlay();
    }

    private SDDelayRunnable mPlayRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            LogUtil.i("playRunnable run delayed...");
            startPlay();
        }
    };

    public void performPlay()
    {
        if (mIsPlayerStarted)
        {
            if (!mIsPaused)
            {
                pause();
            } else
            {
                resume();
            }
        } else
        {
            startPlay();
        }
    }

    private boolean canPlay()
    {
        if (TextUtils.isEmpty(mUrl))
        {
            return false;
        }
        return true;
    }

    /**
     * 开始播放
     */
    public void startPlay()
    {
        if (!canPlay())
        {
            return;
        }
        if (mIsPlayerStarted)
        {
            return;
        }

        mPlayer.setPlayListener(this);
        mPlayer.startPlay(mUrl, mPlayType);
        mIsPlayerStarted = true;
        LogUtil.i("startPlay (playType:" + mPlayType + ") url:" + mUrl);
    }

    /**
     * 暂停（直播拉流不要使用此方法，用startPlay和stopPlay实现暂停和恢复）
     */
    public void pause()
    {
        if (mIsPlayerStarted)
        {
            if (!mIsPaused)
            {
                if (mVideoView != null)
                {
                    mVideoView.onPause();
                }
                mPlayer.pause();

                mIsPaused = true;
                LogUtil.i("pausePlay:" + mUrl);
            }
        }
    }

    /**
     * 恢复播放（直播拉流不要使用此方法，用startPlay和stopPlay实现暂停和恢复）
     */
    public void resume()
    {
        if (mIsPlayerStarted)
        {
            if (mIsPaused)
            {
                if (mVideoView != null)
                {
                    mVideoView.onResume();
                }
                mPlayer.resume();

                mIsPaused = false;
                LogUtil.i("resumePlay:" + mUrl);
            }
        }
    }

    /**
     * 停止播放
     */
    public void stopPlay()
    {
        stopPlay(false);
    }

    /**
     * 停止播放
     *
     * @param clearLastFrame true-清除最后一帧视频
     */
    public void stopPlay(boolean clearLastFrame)
    {
        stopPlayInternale(clearLastFrame);
        mPlayRunnable.removeDelay();
    }

    private void stopPlayInternale(boolean clearLastFrame)
    {
        if (mIsPlayerStarted)
        {
            mPlayer.setPlayListener(null);
            mPlayer.stopPlay(clearLastFrame);
            resetState();
            LogUtil.i("stopPlay:" + mUrl);
        }
    }

    /**
     * 重置状态
     */
    private void resetState()
    {
        mIsPlayerStarted = false;
        mIsPaused = false;
    }

    public void seek(int time)
    {
        mPlayer.seek(time);
    }

    @Override
    public void onPlayEvent(int event, Bundle param)
    {
        if (mPlayerListener != null)
        {
            mPlayerListener.onPlayEvent(event, param);
        }

        switch (event)
        {
            case TXLiveConstants.PLAY_EVT_PLAY_BEGIN:
                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_EVT_PLAY_BEGIN");
                if (mPlayerListener != null)
                {
                    mPlayerListener.onPlayBegin(event, param);
                }
                break;
            case TXLiveConstants.PLAY_EVT_RCV_FIRST_I_FRAME:
                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_EVT_RCV_FIRST_I_FRAME");
                if (mPlayerListener != null)
                {
                    mPlayerListener.onPlayRecvFirstFrame(event, param);
                }
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_PROGRESS:
                mTotal = param.getInt(TXLiveConstants.EVT_PLAY_DURATION);
                mProgress = param.getInt(TXLiveConstants.EVT_PLAY_PROGRESS);

                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_EVT_PLAY_PROGRESS:" + mProgress + "," + mTotal);
                if (mPlayerListener != null)
                {
                    mPlayerListener.onPlayProgress(event, param, mTotal, mProgress);
                }
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_END:
                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_EVT_PLAY_END");
                stopPlay();
                if (mPlayerListener != null)
                {
                    mPlayerListener.onPlayEnd(event, param);
                }
                break;
            case TXLiveConstants.PLAY_EVT_PLAY_LOADING:
                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_EVT_PLAY_LOADING");
                if (mPlayerListener != null)
                {
                    mPlayerListener.onPlayLoading(event, param);
                }
                break;

            case TXLiveConstants.PLAY_ERR_NET_DISCONNECT:
                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_ERR_NET_DISCONNECT");
                stopPlayInternale(false);
                mPlayRunnable.runDelay(3000);
                break;
            case TXLiveConstants.PLAY_ERR_GET_RTMP_ACC_URL_FAIL:
                LogUtil.i("----------PLAY_EVENT:" + event + " PLAY_ERR_GET_RTMP_ACC_URL_FAIL");
                stopPlayInternale(false);
                mPlayRunnable.runDelay(3000);
                break;
            default:
                LogUtil.i("----------PLAY_EVENT:" + event);
                break;
        }
    }

    @Override
    public void onNetStatus(Bundle bundle)
    {
        this.mLiveQualityData.parseBundle(bundle, false);
        if (mPlayerListener != null)
        {
            mPlayerListener.onNetStatus(bundle);
        }
    }

    /**
     * 销毁当前播放
     */
    public void onDestroy()
    {
        LogUtil.i("onDestroy player:" + mVideoView);
        mPlayRunnable.removeDelay();
        stopPlay(true);

        if (mVideoView != null)
        {
            mVideoView.onDestroy();
            mVideoView = null;
        }

        if (mPlayer != null)
        {
            mPlayer.setPlayerView(null);
            mPlayer = null;
        }

        mPlayerListener = null;
    }

    public interface PlayerListener
    {
        void onPlayEvent(int event, Bundle param);

        void onPlayBegin(int event, Bundle param);

        void onPlayRecvFirstFrame(int event, Bundle param);

        void onPlayProgress(int event, Bundle param, int total, int progress);

        void onPlayEnd(int event, Bundle param);

        void onPlayLoading(int event, Bundle param);

        void onNetStatus(Bundle param);
    }
}
