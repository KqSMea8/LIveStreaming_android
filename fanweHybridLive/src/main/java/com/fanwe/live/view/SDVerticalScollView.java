package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.fanwe.library.gesture.SDGestureHandler;
import com.fanwe.library.listener.SDSizeChangedCallback;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewSizeListener;

public class SDVerticalScollView extends RelativeLayout
{
    private static final long DURATION_HORIZONTAL = 500;

    private static final long DURATION_VERTICAL = 300;

    private EnumFinishState finishState;

    //vertical
    private View topView;
    private View bottomView;
    private View verticalView;
    private int verticalHeight;
    private SDViewSizeListener verticalViewSizeListener;
    private int verticalIndex = 1;
    private float downY;
    private float upY;
    private boolean enableVerticalScroll = false;
    private boolean needDealVerticalOnUp = false;

    //horizontal
    private View leftView;
    private View rightView;
    private View horizontalView;
    private int horizontalWidth;
    private SDViewSizeListener horizontalViewSizeListener;
    private int horizontalIndex = 1;
    private float downX;
    private float upX;
    private boolean enableHorizontalScroll = true;
    private boolean needDealHorizontalOnUp = false;

    private boolean verticalMode = true;

    private ScrollListener listenerScroll;

    private SDGestureHandler gestureHandler;
    private OnClickListener onClickListener;

    @Override
    public void setOnClickListener(OnClickListener l)
    {
        this.onClickListener = l;
    }

    public SDVerticalScollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();

    }

    public SDVerticalScollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public SDVerticalScollView(Context context)
    {
        super(context);
        init();

    }

    public void setListenerScroll(ScrollListener listenerScroll)
    {
        this.listenerScroll = listenerScroll;
    }

    public void setTopView(View topView)
    {
        this.topView = topView;
        resetTopView();
    }

    public void setVerticalView(View verticalView)
    {
        this.verticalView = verticalView;
        verticalViewSizeListener.listen(verticalView, new SDSizeChangedCallback<View>()
        {
            @Override
            public void onWidthChanged(int newWidth, int oldWidth, int differ, View target)
            {
            }

            @Override
            public void onHeightChanged(int newHeight, int oldHeight, int differ, View target)
            {
                initVertical();
            }
        });
        resetVerticalView();
    }

    public void setBottomView(View bottomView)
    {
        this.bottomView = bottomView;
        resetBottomView();
    }

    public void setLeftView(View leftView)
    {
        this.leftView = leftView;
        resetLeftView();
    }

    public void setHorizontalView(View horizontalView)
    {
        this.horizontalView = horizontalView;
        horizontalViewSizeListener.listen(horizontalView, new SDSizeChangedCallback<View>()
        {
            @Override
            public void onWidthChanged(int newWidth, int oldWidth, int differ, View target)
            {
                initHorizontal();
            }

            @Override
            public void onHeightChanged(int newHeight, int oldHeight, int differ, View target)
            {
            }
        });
        resetHorizontalView();
    }

    public void setRightView(View rightView)
    {
        this.rightView = rightView;
        resetRightView();
    }

    private void resetTopView()
    {
        if (topView != null)
        {
            topView.scrollTo(0, verticalHeight);
        }
    }

    private void resetVerticalView()
    {
        if (verticalView != null)
        {
            verticalView.scrollTo(0, 0);
        }
    }

    private void resetBottomView()
    {
        if (bottomView != null)
        {
            bottomView.scrollTo(0, -verticalHeight);
        }
    }

    private void resetLeftView()
    {
        if (leftView != null)
        {
            leftView.scrollTo(0, horizontalWidth);
        }
    }

    private void resetHorizontalView()
    {
        if (horizontalView != null)
        {
            horizontalView.scrollTo(0, 0);
        }
    }

    private void resetRightView()
    {
        if (rightView != null)
        {
            rightView.scrollTo(0, -horizontalWidth);
        }
    }

    public void resetVerticalViews()
    {
        resetTopView();
        resetVerticalView();
        resetBottomView();
        verticalIndex = 1;
    }

    public void resetHorizontalViews()
    {
        resetLeftView();
        resetHorizontalView();
        resetRightView();
        horizontalIndex = 1;
    }

    private void init()
    {
        horizontalViewSizeListener = new SDViewSizeListener();
        verticalViewSizeListener = new SDViewSizeListener();

        gestureHandler = new SDGestureHandler(getContext());
        gestureHandler.setGestureListener(new SDGestureHandler.SDGestureListener()
        {
            @Override
            public boolean onScrollVertical(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                if (canScrollVertical(distanceY))
                {
                    needDealVerticalOnUp = true;
                    if (topView != null)
                    {
                        topView.scrollBy(0, (int) distanceY);
                    }
                    if (bottomView != null)
                    {
                        bottomView.scrollBy(0, (int) distanceY);
                    }
                    verticalView.scrollBy(0, (int) distanceY);

                    if (listenerScroll != null)
                    {
                        listenerScroll.onVerticalScroll(e1, e2, distanceX, distanceY);
                    }
                    return true;
                } else
                {
                    downY = e2.getRawY();
                    return false;
                }
            }

            @Override
            public boolean onScrollHorizontal(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY)
            {
                if (canScrollHorizontal(distanceX))
                {
                    needDealHorizontalOnUp = true;
                    if (leftView != null)
                    {
                        leftView.scrollBy((int) distanceX, 0);
                    }
                    if (rightView != null)
                    {
                        rightView.scrollBy((int) distanceX, 0);
                    }
                    horizontalView.scrollBy((int) distanceX, 0);

                    if (listenerScroll != null)
                    {
                        listenerScroll.onHorizontalScroll(e1, e2, distanceX, distanceY);
                    }
                    return true;
                } else
                {
                    downX = e2.getRawX();
                    return false;
                }
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e)
            {
                if (onClickListener != null)
                {
                    onClickListener.onClick(SDVerticalScollView.this);
                }
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDown(MotionEvent e)
            {
                downX = e.getRawX();
                downY = e.getRawY();
                return true;
            }

            @Override
            public void onActionUp(MotionEvent event, float velocityX, float velocityY)
            {
                upX = event.getRawX();
                upY = event.getRawY();
                if (gestureHandler.isScrollVertical())
                {
                    if (needDealVerticalOnUp)
                    {
                        verticalMode = true;
                        if (Math.abs(velocityY) > gestureHandler.getScaledMinimumFlingVelocityCommon())
                        {
                            if (velocityY > 0)
                            {
                                if (topView != null)
                                {
                                    scrollToBottom();
                                } else
                                {
                                    scrollToCenterVertical();
                                }
                            } else
                            {
                                if (bottomView != null)
                                {
                                    scrollToTop();
                                } else
                                {
                                    scrollToCenterVertical();
                                }
                            }
                        } else
                        {
                            dealActionUpVertical();
                        }
                    }
                } else if (gestureHandler.isScrollHorizontal())
                {
                    if (needDealHorizontalOnUp)
                    {
                        verticalMode = false;
                        if (Math.abs(velocityX) > gestureHandler.getScaledMinimumFlingVelocityCommon())
                        {
                            if (velocityX > 0)
                            {
                                if (leftView != null)
                                {
                                    scrollToRight();
                                } else
                                {
                                    scrollToCenterHorizontal();
                                }
                            } else
                            {
                                if (rightView != null)
                                {
                                    scrollToLeft();
                                } else
                                {
                                    scrollToCenterHorizontal();
                                }
                            }
                        } else
                        {
                            dealActionUpHorizontal();
                        }
                    }
                }
                needDealVerticalOnUp = false;
                needDealHorizontalOnUp = false;
            }
        });
    }

    /**
     * 是否可以竖直方向滚动
     *
     * @param distanceY
     * @return
     */
    private boolean canScrollVertical(float distanceY)
    {
        if (!enableVerticalScroll)
        {
            return false;
        }

        if (verticalView == null || (topView == null && bottomView == null))
        {
            return false;
        }

        int scrollY = verticalView.getScrollY();

        int bottomValue = 0;
        if (bottomView == null)
        {
            bottomValue = 0;
        } else
        {
            bottomValue = verticalHeight;
        }

        int topValue = 0;
        if (topView == null)
        {
            topValue = 0;
        } else
        {
            topValue = -verticalHeight;
        }

        if (scrollY >= bottomValue)
        {
            //在最底部页面
            if (distanceY > 0)
            {
                //上滑动
                return false;
            }
        } else if (scrollY <= topValue)
        {
            //在最顶部页面
            if (distanceY < 0)
            {
                //下滑动
                return false;
            }
        }
        return true;
    }

    /**
     * 是否可以横向滚动
     *
     * @param distanceX
     * @return
     */
    private boolean canScrollHorizontal(float distanceX)
    {
        if (!enableHorizontalScroll)
        {
            return false;
        }

        if (horizontalView == null || (leftView == null && rightView == null))
        {
            return false;
        }

        int scrollX = horizontalView.getScrollX();

        int rightValue = 0;
        if (rightView == null)
        {
            rightValue = 0;
        } else
        {
            rightValue = horizontalWidth;
        }

        int leftValue = 0;
        if (leftView == null)
        {
            leftValue = 0;
        } else
        {
            leftValue = -horizontalWidth;
        }

        if (scrollX >= rightValue)
        {
            //在最右边页面
            if (distanceX > 0)
            {
                //左滑动
                return false;
            }
        } else if (scrollX <= leftValue)
        {
            //在最左边页面
            if (distanceX < 0)
            {
                //右滑动
                return false;
            }
        }
        return true;
    }

    private void initVertical()
    {
        if (verticalView != null)
        {
            int newHeight = verticalView.getHeight();
            if (newHeight != verticalHeight)
            {
                verticalHeight = newHeight;
                resetVerticalViews();
            }
        }
    }

    private void initHorizontal()
    {
        if (horizontalView != null)
        {
            int newWidth = horizontalView.getWidth();
            if (newWidth != horizontalWidth)
            {
                horizontalWidth = newWidth;
                resetHorizontalViews();
            }
        }
    }

    @Override
    public void computeScroll()
    {
        if (gestureHandler.getScroller().computeScrollOffset())
        {
            if (gestureHandler.getScroller().isFinished())
            {
                switch (finishState)
                {
                    case top:
                        verticalIndex = 0;
                        if (listenerScroll != null)
                        {
                            listenerScroll.onFinishTop();
                        }
                        break;
                    case centerVertical:
                        verticalIndex = 1;
                        if (listenerScroll != null)
                        {
                            listenerScroll.onFinishCenter();
                        }
                        break;
                    case bottom:
                        verticalIndex = 2;
                        if (listenerScroll != null)
                        {
                            listenerScroll.onFinishBottom();
                        }
                        break;
                    case left:
                        horizontalIndex = 0;
                        break;
                    case centerHorizontal:
                        horizontalIndex = 1;
                        break;
                    case right:
                        horizontalIndex = 2;
                        break;

                    default:
                        break;
                }
            } else
            {
                if (verticalMode)
                {
                    int currX = gestureHandler.getScroller().getCurrX();
                    int currY = gestureHandler.getScroller().getCurrY();

                    if (topView != null)
                    {
                        topView.scrollTo(currX, currY + verticalHeight);
                    }
                    verticalView.scrollTo(currX, currY);
                    if (bottomView != null)
                    {
                        bottomView.scrollTo(currX, currY - verticalHeight);
                    }
                } else
                {
                    int currX = gestureHandler.getScroller().getCurrX();
                    int currY = gestureHandler.getScroller().getCurrY();

                    if (leftView != null)
                    {
                        leftView.scrollTo(currX + horizontalWidth, currY);
                    }
                    horizontalView.scrollTo(currX, currY);
                    if (rightView != null)
                    {
                        rightView.scrollTo(currX - horizontalWidth, currY);
                    }
                }
            }
        }
        postInvalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return gestureHandler.onTouchEvent(event);
    }

    private float getDistanceX()
    {
        return Math.abs(downX - upX);
    }


    private float getDistanceY()
    {
        return Math.abs(downY - upY);
    }

    /**
     * 手指向左滑动
     *
     * @return
     */
    private boolean isSlidLeftOnUp()
    {
        return downX - upX > 0;
    }

    /**
     * 手指向上滑动
     *
     * @return
     */
    private boolean isSlideTopOnUp()
    {
        return downY - upY > 0;
    }

    private void dealActionUpHorizontal()
    {
        if (horizontalView != null)
        {
            if (getDistanceX() > horizontalWidth / 2)
            {
                switch (horizontalIndex)
                {
                    case 0:
                        if (isSlidLeftOnUp())
                        {
                            scrollToCenterHorizontal();
                        }
                        break;
                    case 1:
                        if (isSlidLeftOnUp())
                        {
                            scrollToLeft();
                        } else
                        {
                            scrollToRight();
                        }
                        break;
                    case 2:
                        if (isSlidLeftOnUp())
                        {

                        } else
                        {
                            scrollToCenterHorizontal();
                        }
                        break;
                    default:
                        break;
                }
            } else
            {
                switch (horizontalIndex)
                {
                    case 0:
                        scrollToRight();
                        break;
                    case 1:
                        scrollToCenterHorizontal();
                        break;
                    case 2:
                        scrollToLeft();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 界面向左滑动
     */
    private void scrollToLeft()
    {
        int dur = SDGestureHandler.getDurationPercent(horizontalView.getScrollX() - horizontalWidth, horizontalWidth, DURATION_HORIZONTAL);
        LogUtil.i("time:" + dur);
        gestureHandler.getScroller().startScrollToX(horizontalView.getScrollX(), horizontalWidth, dur);
        finishState = EnumFinishState.right;
        postInvalidate();
    }

    /**
     * 界面向右
     */
    private void scrollToRight()
    {
        int dur = SDGestureHandler.getDurationPercent(horizontalView.getScrollX() - (-horizontalWidth), horizontalWidth, DURATION_HORIZONTAL);
        LogUtil.i("time:" + dur);
        gestureHandler.getScroller().startScrollToX(horizontalView.getScrollX(), -horizontalWidth, dur);
        finishState = EnumFinishState.left;
        postInvalidate();
    }

    private void scrollToCenterHorizontal()
    {
        int dur = SDGestureHandler.getDurationPercent(horizontalView.getScrollX(), horizontalWidth, DURATION_HORIZONTAL);
        LogUtil.i("time:" + dur);
        gestureHandler.getScroller().startScrollToX(horizontalView.getScrollX(), 0, dur);
        finishState = EnumFinishState.centerHorizontal;
        postInvalidate();
    }

    private void dealActionUpVertical()
    {
        if (verticalView != null)
        {
            if (getDistanceY() > verticalHeight / 2)
            {
                switch (verticalIndex)
                {
                    case 0:
                        if (isSlideTopOnUp())
                        {
                            scrollToCenterVertical();
                        }
                        break;
                    case 1:
                        if (isSlideTopOnUp())
                        {
                            scrollToTop();
                        } else
                        {
                            scrollToBottom();
                        }
                        break;
                    case 2:
                        if (isSlideTopOnUp())
                        {

                        } else
                        {
                            scrollToCenterVertical();
                        }
                        break;
                    default:
                        break;
                }
            } else
            {
                switch (horizontalIndex)
                {
                    case 0:
                        scrollToBottom();
                        break;
                    case 1:
                        scrollToCenterVertical();
                        break;
                    case 2:
                        scrollToTop();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 界面向上滚动
     */
    private void scrollToTop()
    {
        int dur = SDGestureHandler.getDurationPercent(verticalView.getScrollY() - verticalHeight, verticalHeight, DURATION_VERTICAL);
        LogUtil.i("time:" + dur);
        gestureHandler.getScroller().startScrollToY(verticalView.getScrollY(), verticalHeight, dur);
        finishState = EnumFinishState.bottom;
        postInvalidate();
    }

    /**
     * 界面向下滚动
     */
    private void scrollToBottom()
    {
        int dur = SDGestureHandler.getDurationPercent(verticalView.getScrollY() - (-verticalHeight), verticalHeight, DURATION_VERTICAL);
        LogUtil.i("time:" + dur);
        gestureHandler.getScroller().startScrollToY(verticalView.getScrollY(), -verticalHeight, dur);
        finishState = EnumFinishState.top;
        postInvalidate();
    }

    private void scrollToCenterVertical()
    {
        int dur = SDGestureHandler.getDurationPercent(verticalView.getScrollY(), verticalHeight, DURATION_VERTICAL);
        LogUtil.i("time:" + dur);
        gestureHandler.getScroller().startScrollToY(verticalView.getScrollY(), 0, dur);
        finishState = EnumFinishState.centerVertical;
        postInvalidate();
    }

    public void setEnableVerticalScroll(boolean enableVerticalScroll)
    {
        this.enableVerticalScroll = enableVerticalScroll;
    }

    public void setEnableHorizontalScroll(boolean enableHorizontalScroll)
    {
        this.enableHorizontalScroll = enableHorizontalScroll;
    }

    public enum EnumFinishState
    {
        top, centerVertical, bottom, left, centerHorizontal, right;
    }

    public interface ScrollListener
    {
        void onFinishTop();

        void onFinishCenter();

        void onFinishBottom();

        void onVerticalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);

        void onHorizontalScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY);
    }

}
