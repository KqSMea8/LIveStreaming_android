package com.fanwe.live.music.effect;

import java.io.Serializable;

/**
 * Created by ldh on 16/6/24.
 */
public class PlayCtrl implements Serializable
{
    private static final long serialVersionUID = 0L;

    /**
     * 伴奏音量
     */
    public int bzVol;
    /**
     * 麦克风音量
     */
    public int micVol;
    /**
     * eq类型
     */
    public int eqModel;
    /**
     * 升降调，0-原调
     */
    public int pitchShift;

    public PlayCtrl()
    {
        bzVol = 50;
        micVol = 100;
        pitchShift = 0;
    }
}
