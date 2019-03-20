package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2016/12/21.
 */

public class SlideToBottomScrollView extends ScrollView
{
    private OnScrollToBottomListener onScrollToBottomListener;

    public void setOnScrollToBottomListener(OnScrollToBottomListener onScrollToBottomListener)
    {
        this.onScrollToBottomListener = onScrollToBottomListener;
    }

    public SlideToBottomScrollView(Context context)
    {
        super(context);
        init();
    }

    public SlideToBottomScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SlideToBottomScrollView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        setOnTouchListener();
    }

    private void setOnTouchListener()
    {
        this.setOnTouchListener(new TouchListenerImpl());
    }

    private class TouchListenerImpl implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY = view.getScrollY();
                    int height = view.getHeight();
                    int scrollViewMeasuredHeight = getChildAt(0).getMeasuredHeight();
                    if (scrollY == 0)
                    {
                        System.out.println("滑动到了顶端 view.getScrollY()=" + scrollY);
                    }
                    if ((scrollY + height) == scrollViewMeasuredHeight)
                    {
                        System.out.println("滑动到了底部 scrollY=" + scrollY);
                        System.out.println("滑动到了底部 height=" + height);
                        System.out.println("滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                        if (onScrollToBottomListener != null)
                        {
                            onScrollToBottomListener.onScrollToBottom();
                        }
                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    }

    public interface OnScrollToBottomListener
    {
        void onScrollToBottom();
    }
}
