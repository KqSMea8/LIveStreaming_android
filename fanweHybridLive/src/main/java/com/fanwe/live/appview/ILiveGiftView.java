package com.fanwe.live.appview;

/**
 * Created by Administrator on 2016/9/3.
 */
public interface ILiveGiftView<T>
{

    /**
     * 获得消息
     *
     * @return
     */
    T getMsg();

    /**
     * 设置消息
     *
     * @param msg
     */
    void setMsg(T msg,int type);

    /**
     * 是否包含这条消息
     *
     * @param msg
     * @return
     */
    boolean containsMsg(T msg);

    boolean playMsg(T msg);

    /**
     * 绑定数据
     *
     * @param msg
     */
    void bindData(T msg);

    /**
     * 是否正在播放
     *
     * @return
     */
    boolean isPlaying();

    /**
     * 是否可以播放
     *
     * @return
     */
    boolean canPlay();

    /**
     * 播放进入动画
     */
    void playIn();

    /**
     * 播放数字
     */
    void playNumber();

    /**
     * 播放退出动画
     */
    void playOut();

    /**
     * 停止动画
     */
    void stopAnimator();

}
