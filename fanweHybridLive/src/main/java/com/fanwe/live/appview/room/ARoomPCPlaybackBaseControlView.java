package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.SeekBar;

/**
 * Created by shibx on 2017/2/15.
 * PC端回放 控制视图基类
 * {@link RoomPCPlaybackLandControlView}回放竖屏控制层
 * {@link RoomPCPlaybackPortControlView}回放横屏控制层
 *
 */

public abstract class ARoomPCPlaybackBaseControlView extends ARoomPCBaseControlView {

    protected RoomPCPlaybackSeekbarView seekbar;

    public ARoomPCPlaybackBaseControlView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ARoomPCPlaybackBaseControlView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ARoomPCPlaybackBaseControlView(Context context) {
        super(context);
    }

    public abstract void setSeekBarListener(SeekBar.OnSeekBarChangeListener listener);
    public abstract void setPlaybackBottonListener(RoomPCPlaybackSeekbarView.PlaybackBottonListener listener);
    public abstract void updateProgress(long progress, long total);
    public abstract void setSeekBarMax(int maxValue);
    public abstract void setSeekBarProgress(int progressValue);
    public abstract void updateBotton(boolean isPlaying);
}
