package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.live.R;
import com.fanwe.live.music.lrc.LrcView;

/**
 * 推流类型的直播间播放音乐view
 */
public class LivePlayMusicView extends BaseAppView
{
    private View btn_effect;
    private View btn_close;
    private ImageView iv_music_control;
    private LrcView lrc_view;
    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public LivePlayMusicView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LivePlayMusicView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LivePlayMusicView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_live_play_music);
        btn_effect = find(R.id.btn_effect);
        btn_close = find(R.id.btn_close);
        iv_music_control = find(R.id.iv_music_control);
        lrc_view = find(R.id.lrc_view);

        btn_effect.setOnClickListener(this);
        btn_close.setOnClickListener(this);
        iv_music_control.setOnClickListener(this);
    }

    /**
     * 显示播放按钮
     */
    public void setImageMusicControlPlay()
    {
        iv_music_control.setImageResource(R.drawable.ic_live_play_music);
    }

    /**
     * 显示暂停按钮
     */
    public void setImageMusicControlPause()
    {
        iv_music_control.setImageResource(R.drawable.ic_live_pause_music);
    }

    public LrcView getLrc_view()
    {
        return lrc_view;
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == btn_effect)
        {
            if (clickListener != null)
            {
                clickListener.onClickEffect(v);
            }
        } else if (v == btn_close)
        {
            if (clickListener != null)
            {
                clickListener.onClickClose(v);
            }
        } else if (v == iv_music_control)
        {
            if (clickListener != null)
            {
                clickListener.onClickMusicControl(v);
            }
        }
    }

    public interface ClickListener
    {
        void onClickEffect(View v);

        void onClickClose(View v);

        void onClickMusicControl(View v);
    }
}
