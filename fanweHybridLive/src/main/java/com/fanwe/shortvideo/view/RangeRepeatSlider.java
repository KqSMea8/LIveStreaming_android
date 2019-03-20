package com.fanwe.shortvideo.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import com.fanwe.live.R;


public class RangeRepeatSlider extends ViewGroup {
    private static final String TAG = "RangeRepeateSlider";
    private static final int DEFAULT_LINE_SIZE = 1;
    private static final int DEFAULT_THUMB_WIDTH = 7;
    private static final int DEFAULT_TICK_START = 0;
    private static final int DEFAULT_TICK_END = 5;
    private static final int DEFAULT_TICK_INTERVAL = 1;
    private static final int DEFAULT_MASK_BACKGROUND = 0xA0000000;
    private static final int DEFAULT_LINE_COLOR = 0x0accac;
    public static final int TYPE_LEFT = 1;
    public static final int TYPE_RIGHT = 2;
    public static final int TYPE_MID = 3;

    private final Paint mLinePaint, mBgPaint;
    private final ThumbView mLeftThumb, mRightThumb, mMidThumb;

    private int mTouchSlop;
    private int mOriginalX, mLastX;

    private int mThumbWidth;

    private int mTickStart = DEFAULT_TICK_START;
    private int mTickEnd = DEFAULT_TICK_END;
    private int mTickInterval = DEFAULT_TICK_INTERVAL;
    private int mTickCount = (mTickEnd - mTickStart) / mTickInterval;

    private float mLineSize;

    private boolean mIsDragging;
    private boolean mIsRangeEnable; //标记是否允许移动两侧的区间

    private OnRepeatChangeListener mOnRepeateChangeListener;

    public RangeRepeatSlider(Context context) {
        this(context, null);
    }

    public RangeRepeatSlider(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RangeRepeatSlider(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RangeSlider, 0, 0);
        mThumbWidth = array.getDimensionPixelOffset(R.styleable.RangeSlider_thumbWidth, DEFAULT_THUMB_WIDTH);
        mLineSize = array.getDimensionPixelOffset(R.styleable.RangeSlider_lineHeight, DEFAULT_LINE_SIZE);
        mBgPaint = new Paint();
        mBgPaint.setColor(array.getColor(R.styleable.RangeSlider_maskColor, DEFAULT_MASK_BACKGROUND));

        mLinePaint = new Paint();
        mLinePaint.setColor(array.getColor(R.styleable.RangeSlider_lineColor, DEFAULT_LINE_COLOR));

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        Drawable lDrawable = array.getDrawable(R.styleable.RangeSlider_leftThumbDrawable);
        Drawable rDrawable = array.getDrawable(R.styleable.RangeSlider_rightThumbDrawable);
        Drawable midDrawable = array.getDrawable(R.styleable.RangeSlider_midThumbDrawable);

        mLeftThumb = new ThumbView(context, mThumbWidth, lDrawable == null ? new ColorDrawable(DEFAULT_LINE_COLOR) : lDrawable);
        mRightThumb = new ThumbView(context, mThumbWidth, rDrawable == null ? new ColorDrawable(DEFAULT_LINE_COLOR) : rDrawable);
        mMidThumb = new ThumbView(context, mThumbWidth, midDrawable == null ? new ColorDrawable(DEFAULT_LINE_COLOR) : midDrawable);
        setTickCount(array.getInteger(R.styleable.RangeSlider_tickCount, DEFAULT_TICK_END));
        moveThumbByIndex(mMidThumb, mTickCount / 2);
        setRangeIndex(array.getInteger(R.styleable.RangeSlider_leftThumbIndex, DEFAULT_TICK_START),
                array.getInteger(R.styleable.RangeSlider_rightThumbIndex, mTickCount));
        array.recycle();

        addView(mLeftThumb);
        addView(mMidThumb);
        addView(mRightThumb);

        setWillNotDraw(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mLeftThumb.measure(widthMeasureSpec, heightMeasureSpec);
        mMidThumb.measure(widthMeasureSpec, heightMeasureSpec);
        mRightThumb.measure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int lThumbWidth = mLeftThumb.getMeasuredWidth();
        final int lThumbHeight = mLeftThumb.getMeasuredHeight();
        mLeftThumb.layout(0, 0, lThumbWidth, lThumbHeight);
        mMidThumb.layout(0, 0, lThumbWidth, lThumbHeight);
        mRightThumb.layout(0, 0, lThumbWidth, lThumbHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        moveThumbByIndex(mLeftThumb, mLeftThumb.getRangeIndex());
        moveThumbByIndex(mMidThumb, mMidThumb.getRangeIndex());
        moveThumbByIndex(mRightThumb, mRightThumb.getRangeIndex());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        final int width = getMeasuredWidth();
        final int height = getMeasuredHeight();

        final int lThumbWidth = mLeftThumb.getMeasuredWidth();
        final float lThumbOffset = mLeftThumb.getX();
        final float rThumbOffset = mRightThumb.getX();

        final float lineTop = mLineSize;
        final float lineBottom = height - mLineSize;

        // top line
        canvas.drawRect(lThumbWidth + lThumbOffset, 0, rThumbOffset, lineTop, mLinePaint);
        // bottom line
        canvas.drawRect(lThumbWidth + lThumbOffset, lineBottom, rThumbOffset, height, mLinePaint);

        if (lThumbOffset > mThumbWidth) {
            canvas.drawRect(0, 0, lThumbOffset + mThumbWidth, height, mBgPaint);
        }
        if (rThumbOffset < width - mThumbWidth) {
            canvas.drawRect(rThumbOffset, 0, width, height, mBgPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return false;
        }

        boolean handle = false;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();

                mLastX = mOriginalX = x;
                mIsDragging = false;
                if (!mLeftThumb.isPressed() && mLeftThumb.inInTarget(x, y)) {
                    mLeftThumb.setPressed(true);
                    handle = true;
                    if (mOnRepeateChangeListener != null) {
                        mOnRepeateChangeListener.onKeyDown(TYPE_LEFT);
                    }
                } else if (!mMidThumb.isPressed() && mMidThumb.inInTarget(x, y)) {
                    mMidThumb.setPressed(true);
                    handle = true;
                    if (mOnRepeateChangeListener != null) {
                        mOnRepeateChangeListener.onKeyDown(TYPE_MID);
                    }
                } else if (!mRightThumb.isPressed() && mRightThumb.inInTarget(x, y)) {
                    mRightThumb.setPressed(true);
                    handle = true;
                    if (mOnRepeateChangeListener != null) {
                        mOnRepeateChangeListener.onKeyDown(TYPE_RIGHT);
                    }
                }
                break;

            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mIsDragging = false;
                mOriginalX = mLastX = 0;
                getParent().requestDisallowInterceptTouchEvent(false);
                if (mLeftThumb.isPressed()) {
                    releaseLeftThumb();
                    invalidate();
                    handle = true;
                    if (mOnRepeateChangeListener != null) {
                        mOnRepeateChangeListener.onKeyUp(TYPE_LEFT, mLeftThumb.getRangeIndex(), mRightThumb.getRangeIndex());
                    }
                } else if (mMidThumb.isPressed()) {
                    releaseMidThumb();
                    invalidate();
                    handle = true;
                    if (mOnRepeateChangeListener != null) {
                        mOnRepeateChangeListener.onKeyUp(TYPE_MID, mMidThumb.getRangeIndex(), mMidThumb.getRangeIndex());
                    }
                } else if (mRightThumb.isPressed()) {
                    releaseRightThumb();
                    invalidate();
                    handle = true;
                    if (mOnRepeateChangeListener != null) {
                        mOnRepeateChangeListener.onKeyUp(TYPE_RIGHT, mLeftThumb.getRangeIndex(), mRightThumb.getRangeIndex());
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) event.getX();

                if (!mIsDragging && Math.abs(x - mOriginalX) > mTouchSlop) {
                    mIsDragging = true;
                }
                if (mIsDragging) {
                    int moveX = x - mLastX;
                    if (mLeftThumb.isPressed() && mIsRangeEnable) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        moveLeftThumbByPixel(moveX);
                        handle = true;
                        invalidate();
                    } else if (mMidThumb.isPressed()) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        moveMidThumbByPixel(moveX);
                        handle = true;
                        invalidate();
                    } else if (mRightThumb.isPressed() && mIsRangeEnable) {
                        getParent().requestDisallowInterceptTouchEvent(true);
                        moveRightThumbByPixel(moveX);
                        handle = true;
                        invalidate();
                    }
                }

                mLastX = x;
                break;
        }

        return handle;
    }

    public void setRangeEnable(boolean isEnable) {
        mIsRangeEnable = isEnable;
    }

    private boolean isValidTickCount(int tickCount) {
        return (tickCount > 1);
    }

    private boolean indexOutOfRange(int leftThumbIndex, int rightThumbIndex) {
        return (leftThumbIndex < 0 || leftThumbIndex > mTickCount
                || rightThumbIndex < 0
                || rightThumbIndex > mTickCount);
    }

    private float getRangeLength() {
        int width = getMeasuredWidth();
        if (width < mThumbWidth) {
            return 0;
        }
        return width - mThumbWidth;
    }

    private float getIntervalLength() {
        return getRangeLength() / mTickCount;
    }

    public int getNearestIndex(float x) {
        return Math.round(x / getIntervalLength());
    }

    private void notifyRangeChange(int type) {
        if (mOnRepeateChangeListener != null) {
        }
    }

    public void setOnRepeatChangeListener(OnRepeatChangeListener onRepeateChangeListener) {
        mOnRepeateChangeListener = onRepeateChangeListener;
    }

    /**
     * Sets the tick count in the RangeSlider.
     *
     * @param count Integer specifying the number of ticks.
     */
    public void setTickCount(int count) {
        int tickCount = (count - mTickStart) / mTickInterval;
        if (isValidTickCount(tickCount)) {
            mTickEnd = count;
            mTickCount = tickCount;
            mRightThumb.setTickIndex(mTickCount);
        } else {
            throw new IllegalArgumentException("tickCount less than 2; invalid tickCount.");
        }
    }

    /**
     * The location of the thumbs according by the supplied index.
     * Numbered from 0 to mTickCount - 1 from the left.
     *
     * @param leftIndex  Integer specifying the index of the left thumb
     * @param rightIndex Integer specifying the index of the right thumb
     */
    public void setRangeIndex(int leftIndex, int rightIndex) {
        if (indexOutOfRange(leftIndex, rightIndex)) {
            throw new IllegalArgumentException(
                    "Thumb index left " + leftIndex + ", or right " + rightIndex
                            + " is out of bounds. Check that it is greater than the minimum ("
                            + mTickStart + ") and less than the maximum value ("
                            + mTickEnd + ")");
        } else {
            if (mLeftThumb.getRangeIndex() != leftIndex) {
                mLeftThumb.setTickIndex(leftIndex);
            }
            if (mRightThumb.getRangeIndex() != rightIndex) {
                mRightThumb.setTickIndex(rightIndex);
            }
        }
    }

    private boolean moveThumbByIndex(ThumbView view, int index) {
        view.setX(index * getIntervalLength());
        if (view.getRangeIndex() != index) {
            view.setTickIndex(index);
            return true;
        }
        return false;
    }

    private void moveLeftThumbByPixel(int pixel) {
        float x = mLeftThumb.getX() + pixel;
        float interval = getIntervalLength();
        float start = mTickStart / mTickInterval * interval;
        float end = mTickEnd / mTickInterval * interval;

        if (x > start && x < end && x < mMidThumb.getX() - mThumbWidth) {
            mLeftThumb.setX(x);
            int index = getNearestIndex(x);
            if (mLeftThumb.getRangeIndex() != index) {
                mLeftThumb.setTickIndex(index);
                notifyRangeChange(TYPE_LEFT);
            }
        }
    }

    private void moveRightThumbByPixel(int pixel) {
        float x = mRightThumb.getX() + pixel;
        float interval = getIntervalLength();
        float start = mTickStart / mTickInterval * interval;
        float end = mTickEnd / mTickInterval * interval;

        if (x > start && x < end && x > mMidThumb.getX() + mThumbWidth) {
            mRightThumb.setX(x);
            int index = getNearestIndex(x);
            if (mRightThumb.getRangeIndex() != index) {
                mRightThumb.setTickIndex(index);
                notifyRangeChange(TYPE_RIGHT);
            }
        }
    }

    private void moveMidThumbByPixel(int pixel) {
        float x = mMidThumb.getX() + pixel;
        float interval = getIntervalLength();
        float start = mTickStart / mTickInterval * interval;
        float end = mTickEnd / mTickInterval * interval;

        if (x > start && x < end && x < mRightThumb.getX() - mThumbWidth && x > mLeftThumb.getX() + mThumbWidth) {
            mMidThumb.setX(x);
            int index = getNearestIndex(x);
            if (mMidThumb.getRangeIndex() != index) {
                mMidThumb.setTickIndex(index);
                notifyRangeChange(TYPE_MID);
            }
        }
    }

    private void releaseLeftThumb() {
        int index = getNearestIndex(mLeftThumb.getX());
        int endIndex = mMidThumb.getRangeIndex();
        if (index >= endIndex) {
            index = endIndex - 1;
        }
        if (moveThumbByIndex(mLeftThumb, index)) {
            notifyRangeChange(TYPE_LEFT);
        }
        mLeftThumb.setPressed(false);
    }

    private void releaseRightThumb() {
        int index = getNearestIndex(mRightThumb.getX());
        int endIndex = mMidThumb.getRangeIndex();
        if (index <= endIndex) {
            index = endIndex + 1;
        }
        if (moveThumbByIndex(mRightThumb, index)) {
            notifyRangeChange(TYPE_RIGHT);
        }
        mRightThumb.setPressed(false);
    }

    private void releaseMidThumb() {
        int index = getNearestIndex(mMidThumb.getX());
        int endIndex = mMidThumb.getRangeIndex();
        if (index >= endIndex) {
            index = endIndex;
        }
        if (moveThumbByIndex(mMidThumb, index)) {
            notifyRangeChange(TYPE_MID);
        }
        mMidThumb.setPressed(false);
    }

    public void setCutRange(int leftTick, int rightTick) {
        if (mLeftThumb.getRangeIndex() != leftTick) {
            moveThumbByIndex(mLeftThumb, leftTick);
        }
        if (mRightThumb.getRangeIndex() != rightTick) {
            moveThumbByIndex(mRightThumb, rightTick);
        }
        if (mMidThumb.getRangeIndex() > rightTick) {
            moveThumbByIndex(mMidThumb, rightTick - 5);
        }
        if (mMidThumb.getRangeIndex() < leftTick) {
            moveThumbByIndex(mMidThumb, leftTick + 5);
        }
        invalidate();
    }

    public interface OnRepeatChangeListener {
        void onKeyDown(int type);

        void onKeyUp(int type, int leftPinIndex, int rightPinIndex);
    }

}
