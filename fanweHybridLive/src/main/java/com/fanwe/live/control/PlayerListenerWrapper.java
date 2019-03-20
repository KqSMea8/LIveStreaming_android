package com.fanwe.live.control;

import android.os.Bundle;

/**
 * Created by Administrator on 2017/1/19.
 */

public abstract class PlayerListenerWrapper implements LivePlayerSDK.PlayerListener
{
    private LivePlayerSDK.PlayerListener playerListener;

    public final void setPlayerListener(LivePlayerSDK.PlayerListener playerListener)
    {
        this.playerListener = playerListener;
    }

    @Override
    public void onPlayEvent(int event, Bundle param)
    {
        if (playerListener != null)
        {
            playerListener.onPlayEvent(event, param);
        }
    }

    @Override
    public void onPlayBegin(int event, Bundle param)
    {
        if (playerListener != null)
        {
            playerListener.onPlayBegin(event, param);
        }
    }

    @Override
    public void onPlayRecvFirstFrame(int event, Bundle param)
    {
        if (playerListener != null)
        {
            playerListener.onPlayRecvFirstFrame(event, param);
        }
    }

    @Override
    public void onPlayProgress(int event, Bundle param, int total, int progress)
    {
        if (playerListener != null)
        {
            playerListener.onPlayProgress(event, param, total, progress);
        }
    }

    @Override
    public void onPlayEnd(int event, Bundle param)
    {
        if (playerListener != null)
        {
            playerListener.onPlayEnd(event, param);
        }
    }

    @Override
    public void onPlayLoading(int event, Bundle param)
    {
        if (playerListener != null)
        {
            playerListener.onPlayLoading(event, param);
        }
    }

    @Override
    public void onNetStatus(Bundle param)
    {
        if (playerListener != null)
        {
            playerListener.onNetStatus(param);
        }
    }
}
