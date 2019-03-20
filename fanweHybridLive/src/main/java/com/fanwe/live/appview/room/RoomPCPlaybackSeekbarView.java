package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fanwe.library.utils.SDDateUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

/**
 * Created by shibx on 2017/2/15.
 * PC端回放 播放暂停进度条控制视图
 */

public class RoomPCPlaybackSeekbarView extends RoomView
{

    private ImageView iv_play_pause;
    private ImageView iv_playback_fullscreen;
    private SeekBar seekBar;

    private TextView tv_playback_progress;
    private TextView tv_playback_total;

    private boolean isPlaying;

    private PlaybackBottonListener mListener;

    public RoomPCPlaybackSeekbarView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomPCPlaybackSeekbarView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomPCPlaybackSeekbarView(Context context)
    {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_pc_playback_seekbar;
    }

    protected void init()
    {
        iv_play_pause = find(R.id.iv_play_pause);
        iv_playback_fullscreen = find(R.id.iv_playback_fullscreen);
        seekBar = find(R.id.sb_playback);
        tv_playback_progress = find(R.id.tv_playback_progress);
        tv_playback_total = find(R.id.tv_playback_total);
        iv_play_pause.setOnClickListener(this);
        iv_playback_fullscreen.setOnClickListener(this);
    }

    public void setSeekBarOnChangeListener(SeekBar.OnSeekBarChangeListener listener)
    {
        seekBar.setOnSeekBarChangeListener(listener);
    }

    public void setSeekBarMax(int maxValue)
    {
        seekBar.setMax(maxValue);
    }

    public void setSeekBarProgress(int value)
    {
        seekBar.setProgress(value);
    }

    public void updateProgress(long progress, long total)
    {
        tv_playback_progress.setText(SDDateUtil.mil2mmss(progress * 1000));
        tv_playback_total.setText(SDDateUtil.mil2mmss(total * 1000));
    }

    public void updateControlBotton(boolean isPlaying)
    {
        this.isPlaying = isPlaying;
        if (isPlaying)
        {
            GlideUtil.load(R.drawable.ic_pc_playback_pause).into(iv_play_pause);
        } else
        {
            GlideUtil.load(R.drawable.ic_pc_playback_play).into(iv_play_pause);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.iv_play_pause:
                mListener.onClickPlayOrPause(isPlaying);
                break;
            case R.id.iv_playback_fullscreen:
                mListener.onClickFullScreen();
                break;
            default:
                break;
        }
    }

    public void setPlaybackBottonListener(PlaybackBottonListener listener)
    {
        this.mListener = listener;
    }

    public interface PlaybackBottonListener
    {
        void onClickPlayOrPause(boolean isPlaying);

        void onClickFullScreen();
    }

}
