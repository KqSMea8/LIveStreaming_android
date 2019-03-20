package com.fanwe.live.dialog;

import android.app.Activity;
import android.view.View;
import android.widget.SeekBar;

import com.fanwe.library.utils.SDCache;
import com.fanwe.live.R;
import com.fanwe.live.music.effect.MusicEffectDialog;
import com.fanwe.live.music.effect.PlayCtrl;
import com.fanwe.live.view.VerticalSeekBar;

/**
 * 推流直播的时候音效设置窗口
 */
public class PushMusicEffectDialog extends LiveBaseDialog
{

    private View iv_reset;
    private View iv_close;
    private VerticalSeekBar sb_bz;
    private VerticalSeekBar sb_mic;
    private PlayCtrl playCtrl;
    private MusicEffectDialog.PlayCtrlListener playCtrlListener;

    public PushMusicEffectDialog(Activity activity)
    {
        super(activity);
        setContentView(R.layout.dialog_push_music_effect);
        setCanceledOnTouchOutside(true);
        paddings(0);

        iv_reset = findViewById(R.id.iv_reset);
        iv_close = findViewById(R.id.iv_close);
        sb_bz = (VerticalSeekBar) findViewById(R.id.sb_bz);
        sb_mic = (VerticalSeekBar) findViewById(R.id.sb_mic);

        iv_reset.setOnClickListener(this);
        iv_close.setOnClickListener(this);

        playCtrl = SDCache.getObject(PlayCtrl.class);
        if (playCtrl == null)
        {
            playCtrl = new PlayCtrl();
        }

        sb_bz.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                playCtrl.bzVol = progress;
                notifyListener();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });

        sb_mic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                playCtrl.micVol = progress;
                notifyListener();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar)
            {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
            }
        });
        updateViewState();
    }

    public void setPlayCtrlListener(MusicEffectDialog.PlayCtrlListener playCtrlListener)
    {
        this.playCtrlListener = playCtrlListener;
    }

    private void updateViewState()
    {
        sb_bz.setProgress(playCtrl.bzVol);
        sb_mic.setProgress(playCtrl.micVol);
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (v == iv_close)
        {
            dismiss();
        } else if (v == iv_reset)
        {
            playCtrl = new PlayCtrl();
            updateViewState();
        }
    }

    @Override
    public void dismiss()
    {
        SDCache.setObject(playCtrl);
        super.dismiss();
    }

    protected void notifyListener()
    {
        if (playCtrlListener != null)
        {
            playCtrlListener.onPlayCtrl(playCtrl);
        }
    }
}
