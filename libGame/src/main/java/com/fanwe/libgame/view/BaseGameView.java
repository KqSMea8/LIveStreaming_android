package com.fanwe.libgame.view;

import android.content.Context;
import android.util.AttributeSet;

import com.fanwe.library.view.SDAppView;

/**
 * Created by Administrator on 2017/6/17.
 */

public class BaseGameView extends SDAppView
{
    public BaseGameView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public BaseGameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public BaseGameView(Context context)
    {
        super(context);
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();

        setNeedRegisterEventBus(false);
        setNeedRegisterActivityEvent(false);
    }
}
