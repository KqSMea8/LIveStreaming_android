package com.fanwe.live.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.fanwe.library.utils.SDViewSizeLocker;
import com.fanwe.library.utils.SDViewUtil;

/**
 * Created by Administrator on 2016/9/18.
 */
public class SDDragActivityView extends FrameLayout
{

    private SDViewSizeLocker sizeLocker;
    private float scale = 1.0f;

    public SDDragActivityView(Context context)
    {
        super(context);
        init();
    }

    public SDDragActivityView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDDragActivityView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected void init()
    {
        sizeLocker = new SDViewSizeLocker(this);
    }

    protected Activity getActivity()
    {
        if (getContext() instanceof Activity)
        {
            return (Activity) getContext();
        } else
        {
            return null;
        }
    }

    protected WindowManager.LayoutParams getAttributes()
    {
        Activity activity = getActivity();
        if (activity != null)
        {
            return activity.getWindow().getAttributes();
        } else
        {
            return null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (isScaleMode())
        {
            super.onTouchEvent(event);
            return true;
        } else
        {
            return super.onTouchEvent(event);
        }
    }

    public boolean isScaleMode()
    {
        return scale != 1.0f;
    }

    public void scaleWindow(float scale)
    {
        this.scale = scale;

        if (isScaleMode())
        {
            float scaleWidth = SDViewUtil.getWidth(this) * scale;
            float scaleHeight = SDViewUtil.getHeight(this) * scale;

            sizeLocker.lockWidth((int) scaleWidth);
            sizeLocker.lockHeight((int) scaleHeight);
        } else
        {
            sizeLocker.unlockWidth();
            sizeLocker.unlockHeight();
        }
    }

}
