package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.fanwe.library.gesture.SDGestureHandler;
import com.fanwe.library.utils.SDViewUtil;

public class SDSlidingButton extends RelativeLayout
{
    private static final long DUR_MAX = 200;

    private View viewNormal;
    private View viewSelected;
    private View viewHandle;

    private SDGestureHandler gestureHandler;

    private MarginLayoutParams marginParams;
    private int defaultLeftMargin;
    private int defaultRightMargin;
    private int defaultTopMargin;
    private int defaultBottomMargin;

    private boolean onUpVelocityFling;

    private boolean selected;
    private boolean runtimeSelected;
    private SelectedChangeListener selectedChangeListener;

    public void setSelectedChangeListener(SelectedChangeListener selectedChangeListener)
    {
        this.selectedChangeListener = selectedChangeListener;
    }

    public void setOnUpVelocityFling(boolean onUpVelocityFling)
    {
        this.onUpVelocityFling = onUpVelocityFling;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        setSelected(selected, true);
    }

    public void setSelected(boolean selected, boolean smooth)
    {
        this.runtimeSelected = selected;

        if (marginParams != null)
        {
            if (selected)
            {
                scrollToRight(smooth);
            } else
            {
                scrollToLeft(smooth);
            }
        }
    }

    public SDSlidingButton(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public SDSlidingButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDSlidingButton(Context context)
    {
        super(context);
        init();
    }

    private void init()
    {

        gestureHandler = new SDGestureHandler(getContext());
        gestureHandler.setGestureListener(new SDGestureHandler.SDGestureListener()
        {
            @Override
            public boolean onDown(MotionEvent e)
            {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                setSelected(!selected);
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onScrollHorizontal(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                getParent().requestDisallowInterceptTouchEvent(true);
                float left = marginParams.leftMargin - distanceX;
                if (left < defaultLeftMargin)
                {
                    left = defaultLeftMargin;
                } else
                {
                    if (left + viewHandle.getWidth() > getWidth() - defaultRightMargin)
                    {
                        left = getWidth() - viewHandle.getWidth() - defaultRightMargin;
                    }
                }
                updateLeftMargin((int) left);

                return true;
            }

            @Override
            public void onActionUp(MotionEvent event, float velocityX, float velocityY)
            {
                getParent().requestDisallowInterceptTouchEvent(false);
                if (onUpVelocityFling)
                {
                    float minVelocity = gestureHandler.getScaledMinimumFlingVelocityCommon();
                    if (Math.abs(velocityX) > minVelocity)
                    {
                        if (velocityX > 0)
                        {
                            scrollToRight(true);
                        } else
                        {
                            scrollToLeft(true);
                        }
                    } else
                    {
                        dealScrollDirection(true);
                    }
                } else
                {
                    dealScrollDirection(true);
                }
                super.onActionUp(event, velocityX, velocityY);
            }
        });
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        if (changed)
        {
            setViewNormal(getChildAt(0));
            setViewSelected(getChildAt(1));
            setViewHandle(getChildAt(2));
            setSelected(runtimeSelected, false);
        }
    }

    public void setViewNormal(View viewNormal)
    {
        this.viewNormal = viewNormal;
    }

    public void setViewSelected(View viewSelected)
    {
        this.viewSelected = viewSelected;
    }

    public void setViewHandle(View viewHandle)
    {
        if (this.viewHandle != viewHandle)
        {
            this.viewHandle = viewHandle;
            marginParams = SDViewUtil.getMarginLayoutParams(viewHandle);
            if (marginParams != null)
            {
                defaultBottomMargin = marginParams.bottomMargin;
                defaultLeftMargin = marginParams.leftMargin;
                defaultRightMargin = marginParams.rightMargin;
                defaultTopMargin = marginParams.topMargin;
            }
        }
    }

    public int getMaxMargin()
    {
        return getWidth() - viewHandle.getWidth() - defaultRightMargin;
    }

    private void updateLeftMargin(int margin)
    {
        marginParams.leftMargin = margin;
        viewHandle.setLayoutParams(marginParams);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureHandler.onTouchEvent(event);
    }

    private void dealScrollDirection(boolean smooth)
    {
        if (marginParams.leftMargin < getMaxMargin() / 2)
        {
            // 在左半部
            scrollToLeft(smooth);
        } else
        {
            // 在右半部
            scrollToRight(smooth);
        }
    }

    private void scrollToLeft(boolean smooth)
    {
        runtimeSelected = false;
        if (smooth)
        {
            int dur = SDGestureHandler.getDurationPercent(marginParams.leftMargin - defaultLeftMargin, getMaxMargin(), DUR_MAX);
            gestureHandler.getScroller().startScrollToX(marginParams.leftMargin, defaultLeftMargin, dur);
            postInvalidate();
        } else
        {
            updateLeftMargin(defaultLeftMargin);
            updateSelected();
        }
    }

    private void scrollToRight(boolean smooth)
    {
        runtimeSelected = true;
        if (smooth)
        {
            int dur = SDGestureHandler.getDurationPercent(marginParams.leftMargin - getMaxMargin(), getMaxMargin(), DUR_MAX);
            gestureHandler.getScroller().startScrollToX(marginParams.leftMargin, getMaxMargin(), dur);
            postInvalidate();
        } else
        {
            updateLeftMargin(getMaxMargin());
            updateSelected();
        }
    }

    @Override
    public void computeScroll()
    {
        if (gestureHandler.getScroller().computeScrollOffset())
        {
            if (gestureHandler.getScroller().isFinished())
            {
                updateSelected();
            } else
            {
                updateLeftMargin(gestureHandler.getScroller().getCurrX());
            }
        }
        postInvalidate();
    }

    protected void updateSelected()
    {
        if (runtimeSelected != selected)
        {
            selected = runtimeSelected;
            notifyListener();
        }
        updateVisibility();
    }

    private void updateVisibility()
    {
        if (selected)
        {
            SDViewUtil.setVisible(viewSelected);
            SDViewUtil.setGone(viewNormal);
        } else
        {
            SDViewUtil.setVisible(viewNormal);
            SDViewUtil.setGone(viewSelected);
        }
    }

    private void notifyListener()
    {
        if (selectedChangeListener != null)
        {
            selectedChangeListener.onSelectedChange(this, selected);
        }
    }

    public interface SelectedChangeListener
    {
        void onSelectedChange(SDSlidingButton view, boolean selected);
    }

}
