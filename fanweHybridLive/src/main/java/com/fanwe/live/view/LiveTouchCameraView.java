package com.fanwe.live.view;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.OnScaleGestureListener;
import android.widget.LinearLayout;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDOtherUtil;

public class LiveTouchCameraView extends LinearLayout implements AutoFocusCallback
{

    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;

    private float max = 1000f;
    private float min = 1f;
    private float current = min;

    public LiveTouchCameraView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public LiveTouchCameraView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTouchCameraView(Context context)
    {
        super(context);
        init();
    }

    public Camera getCamera()
    {
        return null;
    }

    private void init()
    {
        gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener()
        {
            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                if (getCamera() != null)
                {
                    getCamera().cancelAutoFocus();
                    getCamera().autoFocus(LiveTouchCameraView.this);
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDown(MotionEvent e)
            {
                return true;
            }
        });

        scaleGestureDetector = new ScaleGestureDetector(getContext(), new OnScaleGestureListener()
        {

            @Override
            public void onScaleEnd(ScaleGestureDetector detector)
            {

            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector)
            {
                if (getCamera() != null)
                {
                    return getCamera().getParameters().isZoomSupported();
                }
                return false;
            }

            @Override
            public boolean onScale(ScaleGestureDetector detector)
            {
                float maxZoom = getCamera().getParameters().getMaxZoom();

                float scale = detector.getScaleFactor();
                float scaleDelta = Math.abs(((float) (1.0f - scale)));

                if (scale < 1)
                {
                    current = current - max * scaleDelta;
                } else if (scale == 1)
                {

                } else
                {
                    current = current + max * scaleDelta;
                }

                current = current * scale;

                if (current < min)
                {
                    current = min;
                } else if (current > max)
                {
                    current = max;
                }

                float zoomPercent = ((float) (current - 1)) / max;
                int zoom = (int) (zoomPercent * maxZoom);

                LogUtil.i(SDOtherUtil.build(current, zoom));

                Parameters params = getCamera().getParameters();
                params.setZoom((int) zoom);
                getCamera().setParameters(params);
                return true;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        try
        {
            boolean scale = scaleGestureDetector.onTouchEvent(event);
            boolean normal = gestureDetector.onTouchEvent(event);

            if (scale || normal)
            {
                return true;
            } else
            {
                return super.onTouchEvent(event);
            }
        } catch (Exception ex)
        {
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onAutoFocus(boolean success, Camera camera)
    {

    }

}
