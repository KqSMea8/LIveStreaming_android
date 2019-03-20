package com.fanwe.live.control;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.fanwe.hybrid.app.App;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LiveBeautyConfig;
import com.fanwe.live.model.LiveQualityData;
import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;

/**
 * 腾讯推流sdk封装
 */
public class LivePushSDK implements
        IPushSDK,
        ITXLivePushListener,
        TXLivePusher.OnBGMNotify
{

    private static LivePushSDK sInstance;

    private TXLivePusher mPusher;
    private TXLivePushConfig mConfig;
    private TXCloudVideoView mVideoView;
    /**
     * 推流是否已经启动
     */
    private boolean mIsPushStarted;
    /**
     * 推流地址
     */
    private String mUrl;
    /**
     * 当前摄像头id
     */
    private int mCameraId = CAMERA_FRONT;
    /**
     * 美颜值
     */
    private int mBeautyValue;
    /**
     * 美白值
     */
    private int mWhitenValue;
    /**
     * 是否镜像
     */
    private boolean mIsMirror = false;
    /**
     * 音乐是否正在播放
     */
    private boolean mIsBGMPlaying;
    /**
     * 音乐是否已经开始
     */
    private boolean mIsBGMStarted;
    private boolean mIsMicEnable = true;
    /**
     * 视频质量
     */
    private LiveQualityData mLiveQualityData;
    /**
     * 音乐当前播放位置
     */
    private long mBGMPosition;

    private PushCallback mPushCallback;
    private BGMPlayerCallback mBgmPlayerCallback;

    private LivePushSDK()
    {
        mLiveQualityData = new LiveQualityData();
        mConfig = new TXLivePushConfig();
    }

    public static LivePushSDK getInstance()
    {
        if (sInstance == null)
        {
            synchronized (LivePushSDK.class)
            {
                if (sInstance == null)
                {
                    sInstance = new LivePushSDK();
                }
            }
        }
        return sInstance;
    }

    //----------IPushSDK implements start----------

    /**
     * 初始化推流
     */
    @Override
    public void init(View view)
    {
        if (!(view instanceof TXCloudVideoView))
        {
            throw new IllegalArgumentException("view should be instanceof TXCloudVideoView");
        }
        this.mVideoView = (TXCloudVideoView) view;

        mPusher = new TXLivePusher(App.getApplication());
        updatePusherConfig();

        mConfig.setTouchFocus(false);
        mConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_AUDIO | TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO);
        mConfig.setAudioSampleRate(48000);
        mConfig.setAudioChannels(1);
        mConfig.setFrontCamera(true);
        Bitmap bitmap = BitmapFactory.decodeResource(App.getApplication().getResources(), R.drawable.bg_creater_leave);
        mConfig.setPauseImg(bitmap);
        mConfig.setPauseImg(3600, 10);
        setConfigDefault();
    }

    @Override
    public void setConfigDefault()
    {
        if (mPusher == null)
        {
            return;
        }
        int resolutionType = AppRuntimeWorker.getVideoResolutionType();
        switch (resolutionType)
        {
            case LiveConstant.VideoQualityType.VIDEO_QUALITY_STANDARD:
                mPusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION,true,true);
                mConfig.setVideoBitrate(700); //初始码率
                mConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE); //软硬件加速
                mConfig.setAutoAdjustBitrate(false); //码率自适应
                break;
            case LiveConstant.VideoQualityType.VIDEO_QUALITY_HIGH:
                mPusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION,true,true);
                mConfig.setVideoBitrate(1000);
                mConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
                mConfig.setAutoAdjustBitrate(false);
                break;
            case LiveConstant.VideoQualityType.VIDEO_QUALITY_SUPER:
                mPusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION,true,true);
                mConfig.setVideoBitrate(1500);
                mConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_AUTO);
                mConfig.setAutoAdjustBitrate(false);
                break;
            default:
                mPusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION,true,true);
                mConfig.setVideoBitrate(700);
                mConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_SOFTWARE);
                mConfig.setAutoAdjustBitrate(false);
                break;
        }

        updatePusherConfig();
        LogUtil.i("setConfigDefault:" + resolutionType);
    }

    @Override
    public void setConfigLinkMicMain()
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_LINKMIC_MAIN_PUBLISHER,true,true);
        LogUtil.i("setConfigLinkMicMain");
    }

    @Override
    public void setConfigLinkMicSub()
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.setVideoQuality(TXLiveConstants.VIDEO_QUALITY_LINKMIC_SUB_PUBLISHER,true,true);
        mConfig.setVideoBitrate(200);
        updatePusherConfig();
        LogUtil.i("setConfigLinkMicSub");
    }

    @Override
    public boolean isPushStarted()
    {
        return mIsPushStarted;
    }

    @Override
    public void setUrl(String url)
    {
        this.mUrl = url;
    }

    @Override
    public void startPush()
    {
        if (mPusher == null)
        {
            return;
        }
        if (TextUtils.isEmpty(mUrl))
        {
            return;
        }
        if (mIsPushStarted)
        {
            return;
        }

        updatePusherConfig();

        mPusher.setPushListener(this);
        mPusher.startPusher(mUrl);
        startCameraPreview();
        if(!isBackCamera()){
            setMirror(true);
        }

        mCameraId = CAMERA_FRONT;
        mIsPushStarted = true;
        LogUtil.i("startPush:" + mUrl);
    }

    @Override
    public void pausePush()
    {
        if (mPusher == null)
        {
            return;
        }
        if (mVideoView != null)
        {
            mVideoView.onPause();
        }

        if (mIsPushStarted)
        {
            stopCameraPreview();
            mPusher.pausePusher();
            LogUtil.i("pausePush");
        }
    }

    @Override
    public void resumePush()
    {
        if (mPusher == null)
        {
            return;
        }
        if (mVideoView != null)
        {
            mVideoView.onResume();
        }

        if (mIsPushStarted)
        {
            startCameraPreview();
            mPusher.resumePusher();
            LogUtil.i("resumePush");
        }
    }

    @Override
    public void stopPush()
    {
        if (mPusher == null)
        {
            return;
        }
        if (!mIsPushStarted)
        {
            return;
        }

        stopCameraPreview();
        mPusher.setPushListener(null);
        mPusher.stopPusher();

        mCameraId = CAMERA_NONE;
        mIsPushStarted = false;
        LogUtil.i("stopPush");
    }

    @Override
    public void startCameraPreview()
    {
        if (mPusher == null)
        {
            return;
        }
        if (mVideoView != null)
        {
            mPusher.startCameraPreview(mVideoView);
        }
    }

    @Override
    public void stopCameraPreview()
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.stopCameraPreview(false);
    }

    @Override
    public boolean isMirror()
    {
        return mIsMirror;
    }
    @Override
    public void enableBeauty(boolean enable)
    {
        LiveBeautyConfig beautyConfig = LiveBeautyConfig.get();

        int beautyProgress = 0;
        int whitenProgress = 0;
        if (enable)
        {
            beautyProgress = beautyConfig.getBeautyProgress();
            whitenProgress = beautyConfig.getWhitenProgress();
        } else
        {
            beautyProgress = 0;
            whitenProgress = 0;
        }
        setBeautyProgress(beautyProgress);
        setWhitenProgress(whitenProgress);
    }

    @Override
    public void setBeautyProgress(int progress)
    {
        if (mPusher == null)
        {
            return;
        }
        this.mBeautyValue = getRealValue(progress);
        mPusher.setBeautyFilter(TXLiveConstants.BEAUTY_STYLE_SMOOTH,mBeautyValue, mWhitenValue,mWhitenValue);
    }

    @Override
    public void enableBeautyFilter(boolean enable)
    {
        int beautyFilter = 0;
        if (enable)
        {
            beautyFilter = LiveBeautyConfig.get().getBeautyFilter();
        } else
        {
            beautyFilter = 0;
        }
        setBeautyFilter(beautyFilter);
    }

    @Override
    public void setBeautyFilter(int imgResId)
    {
        if (mPusher == null)
        {
            return;
        }

        if (imgResId == 0)
        {
            mPusher.setFilter(null);
        } else
        {
            Resources resources = App.getApplication().getResources();
            TypedValue value = new TypedValue();
            resources.openRawResource(imgResId, value);
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inTargetDensity = value.density;
            Bitmap bmp = BitmapFactory.decodeResource(resources, imgResId, opts);
            mPusher.setFilter(bmp);
        }
    }

    @Override
    public void setWhitenProgress(int progress)
    {
        if (mPusher == null)
        {
            return;
        }
        this.mWhitenValue = getRealValue(progress);
        mPusher.setBeautyFilter(TXLiveConstants.BEAUTY_STYLE_SMOOTH,mBeautyValue, mWhitenValue,mWhitenValue);
    }

    @Override
    public void enableFlashLight(boolean enable)
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.turnOnFlashLight(enable);
    }

    @Override
    public void enableMic(boolean enable)
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.setMute(!enable);
        mIsMicEnable = enable;
    }

    @Override
    public void setMicVolume(int progress)
    {
        if (mPusher == null)
        {
            return;
        }
        float realValue = progress / 50.0f;
        mPusher.setMicVolume(realValue);
    }

    @Override
    public void switchCamera()
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.switchCamera();
        if (mCameraId == CAMERA_FRONT)
        {
            mCameraId = CAMERA_BACK;
        } else if (mCameraId == CAMERA_BACK)
        {
            mCameraId = CAMERA_FRONT;
        }
    }

    @Override
    public boolean isBackCamera()
    {
        return mCameraId == CAMERA_BACK;
    }

    @Override
    public LiveQualityData getLiveQualityData()
    {
        return this.mLiveQualityData;
    }

    @Override
    public void setPushCallback(PushCallback pushCallback)
    {
        mPushCallback = pushCallback;
    }

    @Override
    public void setBgmPlayerCallback(BGMPlayerCallback bgmPlayerCallback)
    {
        mBgmPlayerCallback = bgmPlayerCallback;
    }

    @Override
    public boolean isBGMPlaying()
    {
        return mIsBGMPlaying;
    }

    @Override
    public boolean isBGMStarted()
    {
        return mIsBGMStarted;
    }

    @Override
    public boolean playBGM(String path)
    {
        if (mPusher == null)
        {
            return false;
        }
        boolean result = mPusher.playBGM(path);
        mPusher.setBGMNofify(this);
        mIsBGMPlaying = result;
        mIsBGMStarted = result;
        return result;
    }

    @Override
    public boolean pauseBGM()
    {
        if (mPusher == null)
        {
            return false;
        }
        boolean result = mPusher.pauseBGM();
        if (result)
        {
            mIsBGMPlaying = false;
        }
        return result;
    }

    @Override
    public boolean resumeBGM()
    {
        if (mPusher == null)
        {
            return false;
        }
        boolean result = mPusher.resumeBGM();
        if (result)
        {
            mIsBGMPlaying = true;
        }
        return result;
    }

    @Override
    public boolean stopBGM()
    {
        if (mPusher == null)
        {
            return false;
        }
        boolean result = mPusher.stopBGM();
        mPusher.setBGMNofify(null);
        mIsBGMPlaying = !result;
        mIsBGMStarted = !result;
        return result;
    }

    @Override
    public void setBGMVolume(int progress)
    {
        if (mPusher == null)
        {
            return;
        }
        float realValue = progress / 50.0f;
        mPusher.setBGMVolume(realValue);
    }

    @Override
    public long getBGMPosition()
    {
        return mBGMPosition;
    }

    @Override
    public void onDestroy()
    {
        if (mVideoView != null)
        {
            mVideoView.onDestroy();
            mVideoView = null;
        }
        mPushRunnable.removeDelay();
        stopPush();
        stopBGM();
        mUrl = null;
        mConfig.setPauseImg(null);
        mPushCallback = null;
        mBgmPlayerCallback = null;
        mPusher = null;
    }

    //----------IPushSDK implements end----------

    /**
     * 重新给推流对象设置config
     */
    private void updatePusherConfig()
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.setConfig(mConfig);
    }

    /**
     * 重新开始推流
     */
    public void restartPush()
    {
        stopPush();
        startPush();
    }

    /**
     * 设置镜像
     *
     * @param mirror
     */
    public void setMirror(boolean mirror)
    {
        this.mIsMirror = mirror;
        mPusher.setMirror(mIsMirror);
    }

    /**
     * 真实值转换
     *
     * @param progress [0-100]
     * @return [0-10]
     */
    public static int getRealValue(int progress)
    {
        float value = ((float) progress / 100) * 10;
        return (int) value;
    }

    private SDDelayRunnable mPushRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            LogUtil.i("startpush run delayed...");
            startPush();
        }
    };

    @Override
    public void onPushEvent(int event, Bundle bundle)
    {
        LogUtil.i("onPushEvent:" + event);
        switch (event)
        {
            case TXLiveConstants.PUSH_ERR_NET_DISCONNECT:
                stopPush();
                mPushRunnable.runDelay(3000);
                break;
            case TXLiveConstants.PUSH_EVT_PUSH_BEGIN:
                mPusher.setMirror(mIsMirror);
                if (mPushCallback != null)
                {
                    mPushCallback.onPushStarted();
                }
                break;

            default:
                break;
        }

        if (event == TXLiveConstants.PUSH_EVT_OPEN_CAMERA_SUCC)
        {
            enableMic(mIsMicEnable);
        }

    }

    @Override
    public void onNetStatus(Bundle bundle)
    {
        this.mLiveQualityData.parseBundle(bundle, true);
    }

    @Override
    public void onBGMStart()
    {

    }

    @Override
    public void onBGMProgress(long current, long total)
    {
        mBGMPosition = current;
    }

    @Override
    public void onBGMComplete(int i)
    {
        SDHandlerManager.post(new Runnable()
        {
            @Override
            public void run()
            {
                if (mBgmPlayerCallback != null)
                {
                    mBgmPlayerCallback.onBGMComplete();
                }
            }
        });
    }
}
