package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2016/7/11.
 */
public class InterceptTouchEventLinearLayout extends LinearLayout
{
    public InterceptTouchEventLinearLayout(Context context)
    {
        super(context);
    }

    public InterceptTouchEventLinearLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public InterceptTouchEventLinearLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        super.onTouchEvent(event);
        return true;
    }
}
