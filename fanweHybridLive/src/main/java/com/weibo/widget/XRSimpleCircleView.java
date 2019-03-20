package com.weibo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.live.R;

/**
 * @包名 com.fanwe.xianrou.view
 * @描述
 * @作者 Su
 * @创建时间 2017/3/16 13:57
 **/
public class XRSimpleCircleView extends View
{
    private static final int BACKGROUND_COLOR_DEFAULT = Color.RED;
    private int circleRadius;
    private int circleBackgroundColor;
    private Paint paint;


    public XRSimpleCircleView(Context context)
    {
        this(context, null);
    }

    public XRSimpleCircleView(Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public XRSimpleCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        initCircleView(context, attrs);
    }

    private void initCircleView(Context context, @Nullable AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XRSimpleCircleView);

        if (typedArray != null)
        {
            circleBackgroundColor = typedArray.getColor(R.styleable.XRSimpleCircleView_bg_color, BACKGROUND_COLOR_DEFAULT);
        }

        typedArray.recycle();

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(circleBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = 0, height = 0;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST)
        {
            width = 0;
        } else if (widthMode == MeasureSpec.EXACTLY)
        {
            width = widthSize;
        }

        if (heightMode == MeasureSpec.AT_MOST)
        {
            height = 0;
        } else if (heightMode == MeasureSpec.EXACTLY)
        {
            height = heightSize;
        }

        circleRadius = Math.min(width, height) / 2;
        setMeasuredDimension(circleRadius * 2, circleRadius * 2);
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);

        canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleRadius, paint);
    }

    public int getCircleBackgroundColor()
    {
        return circleBackgroundColor;
    }

    public void setCircleBackgroundColor(int circleBackgroundColor)
    {
        this.circleBackgroundColor = circleBackgroundColor;
        postInvalidate();
    }

}
