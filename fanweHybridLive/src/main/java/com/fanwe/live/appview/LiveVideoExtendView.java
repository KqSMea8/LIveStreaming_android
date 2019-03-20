package com.fanwe.live.appview;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.control.LivePlayerSDK;
import com.fanwe.live.control.LivePushSDK;
import com.fanwe.live.control.PlayerListenerWrapper;

/**
 * 视频播放扩展view
 */
public class LiveVideoExtendView extends BaseAppView
{
    private LiveVideoView view_video;
    private ProgressBar pgb_progress;
    private boolean isSmallMode;

    public LiveVideoExtendView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveVideoExtendView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveVideoExtendView(Context context)
    {
        super(context);
        init();
    }

    /**
     * 设置播放状态监听
     *
     * @param playerListener
     */
    public void setPlayerListener(LivePlayerSDK.PlayerListener playerListener)
    {
        playerListenerWrapper.setPlayerListener(playerListener);
    }

    protected void init()
    {
        setContentView(R.layout.view_live_video_extend);

        pgb_progress = find(R.id.pgb_progress);
        view_video = find(R.id.view_video);
        view_video.setPlayerListener(playerListenerWrapper);
    }

    /**
     * 设置小窗口显示模式，
     *
     * @param smallMode true-view会以parent的1/4大小显示
     */
    public void setSmallMode(boolean smallMode)
    {
        isSmallMode = smallMode;
    }

    /**
     * 获得腾讯sdk需要的view对象
     *
     * @return
     */
    public LiveVideoView getVideoView()
    {
        return view_video;
    }

    /**
     * 获得封装的播放sdk对象
     *
     * @return
     */
    public LivePlayerSDK getPlayer()
    {
        return view_video.getPlayer();
    }

    /**
     * 获得封装的推流sdk对象
     *
     * @return
     */
    public LivePushSDK getPusher()
    {
        return view_video.getPusher();
    }

    /**
     * 显示加载进度圈
     */
    public void showProgress()
    {
        SDViewUtil.setVisible(pgb_progress);
    }

    /**
     * 隐藏加载进度圈
     */
    public void hideProgress()
    {
        SDViewUtil.setGone(pgb_progress);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (isSmallMode)
        {
            if (changed)
            {
                View parent = (View) getParent();
                if (parent != null)
                {
                    int totalHeight = parent.getHeight();
                    if (totalHeight > 0)
                    {
                        int height = totalHeight / 4;
                        int width = SDViewUtil.getScaleWidth(3, 4, height);
                        SDViewUtil.setSize(this, width, height);
                    }
                }
            }
        }
    }

    private PlayerListenerWrapper playerListenerWrapper = new PlayerListenerWrapper()
    {
        @Override
        public void onPlayRecvFirstFrame(int event, Bundle param)
        {
            hideProgress();
            super.onPlayRecvFirstFrame(event, param);
        }

        @Override
        public void onPlayProgress(int event, Bundle param, int total, int progress)
        {
            hideProgress();
            super.onPlayProgress(event, param, total, progress);
        }

        @Override
        public void onPlayLoading(int event, Bundle param)
        {
            showProgress();
            super.onPlayLoading(event, param);
        }
    };

    public void onDestroy()
    {
        view_video.destroy();
    }
}
