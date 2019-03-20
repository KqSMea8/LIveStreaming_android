package com.fanwe.live.appview;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;

/**
 * 连麦view
 */
public class LiveLinkMicView extends LiveVideoExtendView
{
    private String userId;
    private Mode mode = Mode.None;

    public LiveLinkMicView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveLinkMicView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveLinkMicView(Context context)
    {
        super(context);
        init();
    }

    public void setMode(Mode mode)
    {
        if (mode == null)
        {
            mode = Mode.None;
        }

        if (this.mode != mode)
        {
            stop();
            this.mode = mode;
        }
    }

    /**
     * 开始
     */
    public void start()
    {
        switch (mode)
        {
            case Player:
                if (!TextUtils.isEmpty(getPlayer().getUrl()))
                {
                    showProgress();
                    getPlayer().startPlay();
                }
                break;
            case Pusher:
                getPusher().startPush();
                break;
            default:
                break;
        }
    }

    /**
     * 暂停
     */
    public void pause()
    {
        switch (mode)
        {
            case Player:
                getPlayer().stopPlay();
                break;
            case Pusher:
                getPusher().pausePush();
                break;
            default:
                break;
        }
    }

    /**
     * 恢复
     */
    public void resume()
    {
        switch (mode)
        {
            case Player:
                getPlayer().startPlay();
                break;
            case Pusher:
                getPusher().resumePush();
                break;
            default:
                break;
        }
    }

    /**
     * 停止
     */
    public void stop()
    {
        switch (mode)
        {
            case Player:
                getPlayer().stopPlay();
                hideProgress();
                break;
            case Pusher:
                getPusher().stopPush();
                break;
            default:
                break;
        }
    }

    /**
     * 设置要播放的链接
     *
     * @param url       视频链接
     * @param videoType 视频类型
     */
    public void setPlayer(String url, int videoType)
    {
        if (TextUtils.isEmpty(url))
        {
            return;
        }
        setMode(Mode.Player);
        getPlayer().setPlayType(videoType);
        getPlayer().setUrl(url);
    }

    /**
     * 设置要推流的链接
     *
     * @param url
     */
    public void setPusher(String url)
    {
        if (TextUtils.isEmpty(url))
        {
            return;
        }
        setMode(Mode.Pusher);
        getPusher().init(getVideoView());
        getPusher().setUrl(url);
    }

    /**
     * 设置用户id
     *
     * @param userId
     */
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    /**
     * 获得用户id
     *
     * @return
     */
    public String getUserId()
    {
        if (userId == null)
        {
            userId = "";
        }
        return userId;
    }

    /**
     * 重置用户信息
     */
    public void resetView()
    {
        switch (mode)
        {
            case Player:
                getPlayer().setUrl(null);
                getPlayer().stopPlay();
                break;
            case Pusher:
                getPusher().setUrl(null);
                getPusher().stopPush();
                break;
            default:
                break;
        }

        this.userId = null;
        this.mode = Mode.None;
    }

    public enum Mode
    {
        /**
         * 播放模式
         */
        Player,
        /**
         * 推流模式
         */
        Pusher,
        None
    }
}
