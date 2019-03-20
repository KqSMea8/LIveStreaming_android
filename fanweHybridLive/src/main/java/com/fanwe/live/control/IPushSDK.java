package com.fanwe.live.control;

import android.view.View;

import com.fanwe.live.model.LiveQualityData;

/**
 * Created by Administrator on 2017/2/23.
 */

public interface IPushSDK
{

    int CAMERA_FRONT = 0;
    int CAMERA_BACK = 1;
    int CAMERA_NONE = -1;

    /**
     * 初始化
     *
     * @param view 推流要渲染的view，根据不同的sdk，view对象不同
     */
    void init(View view);

    /**
     * 设置默认配置
     */
    void setConfigDefault();

    /**
     * 设置连麦大主播配置
     */
    void setConfigLinkMicMain();

    /**
     * 设置连麦小主播配置
     */
    void setConfigLinkMicSub();

    /**
     * 推流是否已经启动
     *
     * @return
     */
    boolean isPushStarted();

    /**
     * 设置推流地址
     *
     * @param url
     */
    void setUrl(String url);

    /**
     * 开始推流
     */
    void startPush();

    /**
     * 暂停推流
     */
    void pausePush();

    /**
     * 恢复推流
     */
    void resumePush();

    /**
     * 停止推流
     */
    void stopPush();

    /**
     * 开启摄像头预览
     */
    void startCameraPreview();

    /**
     * 关闭摄像头预览
     */
    void stopCameraPreview();

    /**
     * 是否开启美颜
     *
     * @param enable
     */
    void enableBeauty(boolean enable);

    /**
     * 设置美颜级别
     *
     * @param progress [0-100]
     */
    void setBeautyProgress(int progress);

    /**
     * 是否开启美颜滤镜
     *
     * @param enable
     */
    void enableBeautyFilter(boolean enable);

    /**
     * 设置美颜滤镜
     *
     * @param type 根据不同的sdk实现，意义不同
     */
    void setBeautyFilter(int type);

    /**
     * 设置美白级别
     *
     * @param progress [0-100]
     */
    void setWhitenProgress(int progress);

    /**
     * 手电筒开关
     *
     * @param enable
     */
    void enableFlashLight(boolean enable);

    /**
     * 麦克风开关
     *
     * @param enable
     */
    void enableMic(boolean enable);

    /**
     * 设置麦克风音量
     *
     * @param progress [0-100]
     */
    void setMicVolume(int progress);

    /**
     * 切换摄像头
     */
    void switchCamera();

    /**
     * 是否是后置摄像头
     *
     * @return
     */
    boolean isBackCamera();

    /**
     * 获得直播质量
     *
     * @return
     */
    LiveQualityData getLiveQualityData();

    /**
     * 设置推流回调
     *
     * @param pushCallback
     */
    void setPushCallback(PushCallback pushCallback);

    // bgm

    /**
     * 设置音乐播放器回调
     *
     * @param bgmPlayerCallback
     */
    void setBgmPlayerCallback(BGMPlayerCallback bgmPlayerCallback);

    /**
     * 音乐是否正在播放
     *
     * @return
     */
    boolean isBGMPlaying();

    /**
     * 音乐是否已经启动
     *
     * @return
     */
    boolean isBGMStarted();

    /**
     * 播放音乐
     *
     * @param path 本地音乐文件路径
     * @return
     */
    boolean playBGM(String path);
    /**
     * 设置是否镜像推流
     *
     * @param mirror
     */
    void setMirror(boolean mirror);

    /**
     * 当前是否镜像推流
     *
     * @return
     */
    boolean isMirror();
    /**
     * 暂停音乐
     *
     * @return
     */
    boolean pauseBGM();

    /**
     * 恢复音乐
     *
     * @return
     */
    boolean resumeBGM();

    /**
     * 停止音乐
     *
     * @return
     */
    boolean stopBGM();

    /**
     * 设置音乐音量
     *
     * @param progress [0-100]
     */
    void setBGMVolume(int progress);

    /**
     * 获得bgm播放的当前位置
     *
     * @return
     */
    long getBGMPosition();

    /**
     * 销毁当前推流
     */
    void onDestroy();

    public static interface BGMPlayerCallback
    {
        /**
         * 音乐播放完毕
         */
        void onBGMComplete();
    }

    public static interface PushCallback
    {
        /**
         * 推流开始
         */
        void onPushStarted();
    }

}
