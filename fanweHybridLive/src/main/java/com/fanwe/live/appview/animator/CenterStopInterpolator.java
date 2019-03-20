package com.fanwe.live.appview.animator;

import android.view.animation.Interpolator;

/**
 * Created by Administrator on 2016/8/8.
 */
public class CenterStopInterpolator implements Interpolator
{
    @Override
    public float getInterpolation(float input)
    {
        if (input <= 0.4f)
        {
            return input * input * 2;
        } else if (input > 0.4f && input < 0.6f)
        {
            return 0.4f * 0.4f * 2;
        } else
        {
            return (input - 0.2f) * (input - 0.2f) * 2;
        }
    }
}
