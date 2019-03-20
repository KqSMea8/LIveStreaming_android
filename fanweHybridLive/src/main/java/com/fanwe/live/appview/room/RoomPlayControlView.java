package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;

/**
 * Created by Administrator on 2016/8/8.
 */
public class RoomPlayControlView extends BaseAppView
{

    public RoomPlayControlView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public RoomPlayControlView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public RoomPlayControlView(Context context)
    {
        super(context);
    }

    private View rl_play_video;
    private ImageView iv_play_video;
    private SeekBar sb_play_video;
    private TextView tv_duration;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }


    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_play_control;
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        rl_play_video = find(R.id.rl_play_video);
        iv_play_video = find(R.id.iv_play_video);
        sb_play_video = find(R.id.sb_play_video);
        tv_duration = find(R.id.tv_duration);


        rl_play_video.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == rl_play_video)
        {
            if (clickListener != null)
            {
                clickListener.onClickPlayVideo(v);
            }
        }
    }

    public void setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener listener)
    {
        sb_play_video.setOnSeekBarChangeListener(listener);
    }

    public void setTextDuration(String text)
    {
        tv_duration.setText(text);
    }

    public void setImagePlayVideo(int resId)
    {
        iv_play_video.setImageResource(resId);
    }

    public void setMax(int max)
    {
        sb_play_video.setMax(max);
    }

    public void setProgress(int progress)
    {
        sb_play_video.setProgress(progress);
    }

    public interface ClickListener
    {
        void onClickPlayVideo(View v);
    }

}
