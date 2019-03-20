package com.fanwe.live.control;

import android.opengl.GLSurfaceView;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.hybrid.app.App;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.receiver.SDHeadsetPlugReceiver;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDCache;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.live.LiveConstant;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LiveKSYBeautyConfig;
import com.fanwe.live.model.LiveQualityData;
import com.fanwe.live.music.effect.PlayCtrl;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyDenoiseFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyIllusionFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautyProFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySkinWhitenFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySmoothFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySoftExtFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySoftFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgBeautySoftSharpenFilter;
import com.ksyun.media.streamer.filter.imgtex.ImgFilterBase;
import com.ksyun.media.streamer.filter.imgtex.ImgTexFilterMgt;
import com.ksyun.media.streamer.kit.KSYStreamer;
import com.ksyun.media.streamer.kit.RecorderConstants;
import com.ksyun.media.streamer.kit.StreamerConstants;
import com.ksyun.media.streamer.util.audio.KSYBgmPlayer;

/**
 * Created by Administrator on 2017/2/7.
 */

public class KSYPushSDK implements IPushSDK, SDHeadsetPlugReceiver.HeadsetPlugCallback
{

    private static KSYPushSDK sInstance;

    private KSYStreamer mPusher;
    private ImgFilterBase mImgFilter;
    private GLSurfaceView mVideoView;
    private String mUrl;

    private boolean mIsPushStarted;

    private boolean mIsBGMPlaying;
    private boolean mIsBGMStarted;

    private boolean mIsMicEnable = true;
    private boolean mIsMicEnableWhenPause = true;

    private int mCameraId = CAMERA_FRONT;
    private LiveQualityData mLiveQualityData;

    private PushCallback mPushCallback;
    private BGMPlayerCallback mBgmPlayerCallback;


    public static KSYPushSDK getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new KSYPushSDK();
        }
        return sInstance;
    }

    //----------IPushSDK implements start----------

    @Override
    public void init(View view)
    {
        if (!(view instanceof GLSurfaceView))
        {
            throw new IllegalArgumentException("view should be instanceof GLSurfaceView");
        }
        this.mVideoView = (GLSurfaceView) view;
        SDHeadsetPlugReceiver.addCallback(this);

        initPusher();

        setConfigDefault();
    }

    @Override
    public void setConfigDefault()
    {
        mPusher.setEnableAudioMix(true);
        mPusher.setPreviewFps(15); //预览帧率
        mPusher.setTargetFps(15); //编码帧率

        int resolutionType = AppRuntimeWorker.getVideoResolutionType();
        switch (resolutionType)
        {
            case LiveConstant.VideoQualityType.VIDEO_QUALITY_STANDARD:
                mPusher.setTargetResolution(RecorderConstants.VIDEO_RESOLUTION_360P);
                mPusher.setVideoKBitrate(400, 400, 200); //初始码率，最大码率，最小码率
                break;
            case LiveConstant.VideoQualityType.VIDEO_QUALITY_HIGH:
                mPusher.setTargetResolution(RecorderConstants.VIDEO_RESOLUTION_540P);
                mPusher.setVideoKBitrate(700, 700, 350);
                break;
            case LiveConstant.VideoQualityType.VIDEO_QUALITY_SUPER:
                mPusher.setTargetResolution(RecorderConstants.VIDEO_RESOLUTION_720P);
                mPusher.setVideoKBitrate(1000, 1000, 500);
                break;
            default:
                break;
        }
    }

    @Override
    public void setConfigLinkMicMain()
    {

    }

    @Override
    public void setConfigLinkMicSub()
    {

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

        dealHeadsetPlug(SDOtherUtil.isHeadsetPlug(App.getApplication()));
        startCameraPreview();

        mPusher.setUrl(mUrl);
        mPusher.startStream();

        mCameraId = CAMERA_FRONT;
        mIsPushStarted = true;
    }

    @Override
    public void pausePush()
    {
        if (mPusher == null)
        {
            return;
        }

        stopCameraPreview();
        mPusher.onPause();

        mIsMicEnableWhenPause = mIsMicEnable;
        enableMic(false);
        LogUtil.i("pausePush");
    }

    @Override
    public void resumePush()
    {
        if (mPusher == null)
        {
            return;
        }

        startCameraPreview();
        mPusher.onResume();
        if (mIsMicEnableWhenPause)
        {
            enableMic(true);
        }
        LogUtil.i("resumePush");
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

        mPusher.stopStream();
        //避免内部重连的时候造成预览闪烁，所以不停止摄像头预览，请在需要的地方手动调用

        mCameraId = CAMERA_NONE;
        mIsPushStarted = false;
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
            mVideoView.setVisibility(View.VISIBLE);
        }
        mPusher.startCameraPreview();
    }

    @Override
    public void stopCameraPreview()
    {
        if (mPusher == null)
        {
            return;
        }
        if (mVideoView != null)
        {
            mVideoView.setVisibility(View.INVISIBLE);
        }
        mPusher.stopCameraPreview();
    }

    @Override
    public void enableBeauty(boolean enable)
    {
        enableBeautyFilter(enable);
    }

    @Override
    public void setBeautyProgress(int progress)
    {
        setGrindProgress(progress);
    }

    @Override
    public void enableBeautyFilter(boolean enable)
    {
        int beautyFilter = LiveKSYBeautyConfig.get().getBeautyFilter();
        if (enable)
        {
            if (beautyFilter == ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE)
            {
                beautyFilter = ImgTexFilterMgt.KSY_FILTER_BEAUTY_SMOOTH;
            }
        } else
        {
            beautyFilter = ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE;
        }
        setBeautyFilter(beautyFilter);
    }

    @Override
    public void setBeautyFilter(int type)
    {
        switch (type)
        {
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_DISABLE:
                mImgFilter = null;
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT:
                mImgFilter = new ImgBeautySoftFilter(mPusher.getGLRender());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SKINWHITEN:
                mImgFilter = new ImgBeautySkinWhitenFilter(mPusher.getGLRender());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_ILLUSION:
                mImgFilter = new ImgBeautyIllusionFilter(mPusher.getGLRender());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_DENOISE:
                mImgFilter = new ImgBeautyDenoiseFilter(mPusher.getGLRender());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SMOOTH:
                mImgFilter = new ImgBeautySmoothFilter(mPusher.getGLRender(), App.getApplication());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_EXT:
                mImgFilter = new ImgBeautySoftExtFilter(mPusher.getGLRender());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_SOFT_SHARPEN:
                mImgFilter = new ImgBeautySoftSharpenFilter(mPusher.getGLRender());
                break;
            case ImgTexFilterMgt.KSY_FILTER_BEAUTY_PRO:
                mImgFilter = new ImgBeautyProFilter(mPusher.getGLRender(), App.getApplication());
                break;
            default:
                mImgFilter = null;
                break;
        }
        mPusher.getImgTexFilterMgt().setFilter(mImgFilter);
    }

    @Override
    public void setWhitenProgress(int progress)
    {
        if (mImgFilter == null)
        {
            return;
        }
        float realValue = progress / 100.0f;
        mImgFilter.setWhitenRatio(realValue);
    }

    @Override
    public void enableFlashLight(boolean enable)
    {
        if (mPusher == null)
        {
            return;
        }
        mPusher.toggleTorch(enable);
    }

    @Override
    public void enableMic(boolean enable)
    {
        if (mPusher == null)
        {
            return;
        }
        if (enable)
        {
            if (!mIsMicEnable)
            {
                mIsMicEnable = true;
                restoreMicVolume();
            }
        } else
        {
            if (mIsMicEnable)
            {
                setMicVolume(0);
                mIsMicEnable = false;
            }
        }
    }

    @Override
    public void setMicVolume(int progress)
    {
        if (mPusher == null)
        {
            return;
        }
        if (!mIsMicEnable)
        {
            return;
        }
        float realValue = progress / 100.0f;
        LogUtil.i("setMicVolume:" + realValue);
        mPusher.setVoiceVolume(realValue);
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
        if (mLiveQualityData == null)
        {
            mLiveQualityData = new LiveQualityData();
        }
        if (mPusher != null)
        {
            int kbpsSpeed = mPusher.getCurrentUploadKBitrate();
            double kBpsSpeed = mLiveQualityData.formatSpeed(kbpsSpeed);
            mLiveQualityData.setSendKBps(kBpsSpeed);
        }
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
        if (TextUtils.isEmpty(path))
        {
            return false;
        }
        if (mIsBGMStarted)
        {
            return false;
        }
        mPusher.startBgm(path, false);
        mIsBGMPlaying = true;
        mIsBGMStarted = true;
        return true;
    }

    @Override
    public void setMirror(boolean mirror) {

    }

    @Override
    public boolean isMirror() {
        return false;
    }

    @Override
    public boolean pauseBGM()
    {
        if (mPusher == null)
        {
            return false;
        }
        if (!mIsBGMStarted)
        {
            return false;
        }
        mPusher.getAudioPlayerCapture().getBgmPlayer().pause();
        mIsBGMPlaying = false;
        return true;
    }

    @Override
    public boolean resumeBGM()
    {
        if (mPusher == null)
        {
            return false;
        }
        if (!mIsBGMStarted)
        {
            return false;
        }
        mPusher.getAudioPlayerCapture().getBgmPlayer().resume();
        mIsBGMPlaying = true;
        return true;
    }

    @Override
    public boolean stopBGM()
    {
        if (mPusher == null)
        {
            return false;
        }
        if (!mIsBGMStarted)
        {
            return false;
        }
        mPusher.stopBgm();
        mIsBGMPlaying = false;
        mIsBGMStarted = false;
        return true;
    }

    @Override
    public void setBGMVolume(int progress)
    {
        if (mPusher == null)
        {
            return;
        }
        float realValue = progress / 100.0f;
        LogUtil.i("setBGMVolume:" + realValue);
        mPusher.getAudioPlayerCapture().getBgmPlayer().setVolume(realValue);
    }

    @Override
    public long getBGMPosition()
    {
        if (mPusher == null)
        {
            return 0;
        }
        return mPusher.getAudioPlayerCapture().getBgmPlayer().getPosition();
    }

    @Override
    public void onDestroy()
    {
        SDHeadsetPlugReceiver.removeCallback(this);
        stopBGM();
        stopCameraPreview();
        stopPushDelayRunnable();
        stopPush();
        mImgFilter = null;
        mVideoView = null;
        mIsMicEnable = true;
        mBgmPlayerCallback = null;
        mPushCallback = null;
        if (mPusher != null)
        {
            mPusher.release();
            mPusher = null;
        }
    }

    //----------IPushSDK implements end----------

    protected KSYStreamer createPusher()
    {
        return new KSYStreamer(App.getApplication());
    }

    private void initPusher()
    {
        mPusher = createPusher();
        if (mVideoView != null)
        {
            mPusher.setDisplayPreview(mVideoView);
        }
        LogUtil.i("sdkVersion:" + mPusher.getVersion());
        mPusher.setOnErrorListener(new KSYStreamer.OnErrorListener()
        {
            @Override
            public void onError(int event, int msg1, int msg2)
            {
                LogUtil.e(event + "," + msg1 + "," + msg2);
                switch (event)
                {
                    case StreamerConstants.KSY_STREAMER_ERROR_CONNECT_BREAKED:
                    case StreamerConstants.KSY_STREAMER_ERROR_DNS_PARSE_FAILED:
                    case StreamerConstants.KSY_STREAMER_ERROR_CONNECT_FAILED:
                    case StreamerConstants.KSY_STREAMER_ERROR_PUBLISH_FAILED:
                    case StreamerConstants.KSY_STREAMER_ERROR_AV_ASYNC:
                        // 重连
                        stopPush();
                        startPushDelayRunnable();
                        break;
                    case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNSUPPORTED:
                    case StreamerConstants.KSY_STREAMER_VIDEO_ENCODER_ERROR_UNKNOWN:
                        // 切换到软编兼容模式
                        mPusher.setEncodeMethod(StreamerConstants.ENCODE_METHOD_SOFTWARE_COMPAT);
                        break;
                    default:
                        break;
                }
            }
        });

        mPusher.getAudioPlayerCapture().getBgmPlayer().setOnBgmPlayerListener(defaultBgmPlayerListener);
    }

    private void startPushDelayRunnable()
    {
        mPushRunnable.runDelay(5000);
    }

    private void stopPushDelayRunnable()
    {
        mPushRunnable.removeDelay();
    }

    private SDDelayRunnable mPushRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            startPush();
        }
    };

    private void restoreMicVolume()
    {
        if (mIsBGMStarted)
        {
            PlayCtrl playCtrl = SDCache.getObject(PlayCtrl.class);
            if (playCtrl == null)
            {
                setMicVolume(100);
            } else
            {
                setMicVolume(playCtrl.micVol);
            }
        } else
        {
            setMicVolume(100);
        }
    }

    /**
     * 当前美颜滤镜是否支持磨皮
     *
     * @return
     */
    public boolean isGrindSupported()
    {
        if (mImgFilter == null)
        {
            return false;
        }
        return mImgFilter.isGrindRatioSupported();
    }

    /**
     * 设置磨皮百分比
     *
     * @param progress
     */
    public void setGrindProgress(int progress)
    {
        if (mImgFilter == null)
        {
            return;
        }
        float realValue = progress / 100.0f;
        mImgFilter.setGrindRatio(realValue);
    }

    /**
     * 获得磨皮百分比
     *
     * @return
     */
    public int getGrindProgress()
    {
        if (mImgFilter == null)
        {
            return 0;
        }
        float realValue = mImgFilter.getGrindRatio();
        return (int) (realValue * 100);
    }

    /**
     * 当前滤镜是否支持美白
     *
     * @return
     */
    public boolean isWhitenSupported()
    {
        if (mImgFilter == null)
        {
            return false;
        }
        return mImgFilter.isWhitenRatioSupported();
    }

    /**
     * 获得美白百分比
     *
     * @return
     */
    public int getWhitenProgress()
    {
        if (mImgFilter == null)
        {
            return 0;
        }
        float realValue = mImgFilter.getWhitenRatio();
        return (int) (realValue * 100);
    }

    /**
     * 当前滤镜是否支持红润
     *
     * @return
     */
    public boolean isRuddySupported()
    {
        if (mImgFilter == null)
        {
            return false;
        }
        return mImgFilter.isRuddyRatioSupported();
    }

    /**
     * 设置红润百分比
     *
     * @param progress
     */
    public void setRuddyProgress(int progress)
    {
        if (mImgFilter == null)
        {
            return;
        }
        float realValue = progress / 100.0f;
        mImgFilter.setRuddyRatio(realValue);
    }

    /**
     * 获得红润百分比
     *
     * @return
     */
    public int getRuddyProgress()
    {
        if (mImgFilter == null)
        {
            return 0;
        }
        float realValue = mImgFilter.getRuddyRatio();
        return (int) (realValue * 100);
    }

    private KSYBgmPlayer.OnBgmPlayerListener defaultBgmPlayerListener = new KSYBgmPlayer.OnBgmPlayerListener()
    {
        @Override
        public void onCompleted()
        {
            if (mBgmPlayerCallback != null)
            {
                mBgmPlayerCallback.onBGMComplete();
            }
        }

        @Override
        public void onError(int i)
        {

        }
    };

    @Override
    public void onHeadsetPlugChange(boolean plug)
    {
        dealHeadsetPlug(plug);
    }

    private void dealHeadsetPlug(boolean plug)
    {
        LogUtil.i("dealHeadsetPlug:" + plug);
        if (mPusher == null)
        {
            return;
        }
        mPusher.setEnableAudioPreview(plug);
    }

}
