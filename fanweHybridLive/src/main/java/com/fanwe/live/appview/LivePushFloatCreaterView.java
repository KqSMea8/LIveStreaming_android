package com.fanwe.live.appview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.fanwe.hybrid.app.App;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.common.SDWindowManager;
import com.fanwe.library.gesture.SDGestureHandler;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.activity.room.LivePushCreaterActivity;
import com.fanwe.live.event.ELiveFloatCreaterViewClick;
import com.fanwe.live.event.ELiveFloatViewClose;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Administrator on 2017/1/18.
 */

public class LivePushFloatCreaterView extends BaseAppView
{
    private View ll_touch;
    private View iv_close;
    private static WindowManager.LayoutParams params;

    private SDGestureHandler gestureHandler;

    private float downX;
    private float downY;
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;

    private LivePushFloatCreaterView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    private LivePushFloatCreaterView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    private LivePushFloatCreaterView(Context context)
    {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_live_push_float;
    }

    protected void init()
    {
        ll_touch = find(R.id.ll_touch);
        iv_close = find(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                click_close();
            }
        });

        initLayoutParams();
        initGestureHandler();

    }

    private void initLayoutParams()
    {
        if (params == null)
        {
            params = SDWindowManager.getInstance().newLayoutParams();
            params.width = SDViewUtil.getScreenWidthPercent(0.3f);
            params.height = SDViewUtil.getScreenHeightPercent(0.3f);
        }

        minX = 0;
        minY = 0;
        maxX = SDViewUtil.getScreenWidth() - params.width;
        maxY = SDViewUtil.getScreenHeight() - params.height;
    }

    private void initGestureHandler()
    {
        gestureHandler = new SDGestureHandler(getContext());
        gestureHandler.setGestureListener(new SDGestureHandler.SDGestureListener()
        {
            @Override
            public boolean onDown(MotionEvent e)
            {
                downX = params.x;
                downY = params.y;
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {

                float disX = e2.getRawX() - e1.getRawX();
                float disY = e2.getRawY() - e1.getRawY();

                float x = downX + disX;
                float y = downY + disY;

                if (x < minX)
                {
                    x = minX;
                } else if (x > maxX)
                {
                    x = maxX;
                }

                if (y < minY)
                {
                    y = minY;
                } else if (y > maxY)
                {
                    y = maxY;
                }

                params.x = (int) (x);
                params.y = (int) (y);
                SDWindowManager.getInstance().updateViewLayout(LivePushFloatCreaterView.this, params);

                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                switchToBigScreenEvent();
                return true;
            }
        });

        ll_touch.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                return gestureHandler.onTouchEvent(event);
            }
        });
    }

    @Override
    public Activity getActivity()
    {
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity != null)
        {
            return activity;
        } else
        {
            return super.getActivity();
        }
    }

    public static void addToFloatView()
    {
        LivePushFloatCreaterView viewerView = (LivePushFloatCreaterView) SDWindowManager.getInstance().getFirstView(LivePushFloatCreaterView.class);
        if (viewerView == null)
        {
            viewerView = new LivePushFloatCreaterView(App.getApplication());
        }
        viewerView.switchToSmall();
    }

    public void switchToSmall()
    {
        addToWindow();
    }

    /*切换成大屏*/
    public void switchToBigScreenEvent()
    {
        Intent intent = new Intent(getActivity(), LivePushCreaterActivity.class);
        getActivity().startActivity(intent);

        ELiveFloatCreaterViewClick event = new ELiveFloatCreaterViewClick();
        SDEventManager.post(event);
    }

    private void click_close()
    {
        removeFromWindow();
        Intent intent = new Intent(getActivity(), LivePushCreaterActivity.class);
        getActivity().startActivity(intent);
        exitRoomEvent();
    }


    private void exitRoomEvent()
    {
        ELiveFloatViewClose event = new ELiveFloatViewClose();
        SDEventManager.post(event);
    }

    /**
     * 显示悬浮窗
     */
    private void addToWindow()
    {
        if (!SDWindowManager.getInstance().hasView(getClass()))
        {
            SDWindowManager.getInstance().addView(this, params);
        }
    }

    /**
     * 移除悬浮窗
     */
    private void removeFromWindow()
    {
        SDWindowManager.getInstance().removeView(this);
    }
}
