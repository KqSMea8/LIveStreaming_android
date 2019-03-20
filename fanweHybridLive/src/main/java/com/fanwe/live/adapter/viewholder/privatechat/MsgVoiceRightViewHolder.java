package com.fanwe.live.adapter.viewholder.privatechat;

import android.view.View;

import com.fanwe.live.R;

/**
 * Created by Administrator on 2016/8/30.
 */
public class MsgVoiceRightViewHolder extends MsgVoiceLeftViewHolder
{
    public MsgVoiceRightViewHolder(View itemView)
    {
        super(itemView);
    }

    @Override
    public int getVoiceGifRes()
    {
        return R.drawable.ic_play_voice_right_gif;
    }

    @Override
    public int getVoiceStaticRes()
    {
        return R.drawable.ic_play_voice_right;
    }
}
