package com.fanwe.live.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RadioGroup;
import android.widget.TextView;

/**
 * @author wangxiaoya
 * @description com.example.wangxiaoya.myapplication
 * @date 2018/3/15
 */
public class WinCoinTextView extends android.support.v7.widget.AppCompatTextView {
    private Context mContext;

    public WinCoinTextView(Context context) {
        this(context,null);
    }

    public WinCoinTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public WinCoinTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        setLayoutParams(new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setTextSize(24);
        setTextColor(Color.YELLOW);
        TextPaint tp = getPaint();
        tp.setFakeBoldText(true);
    }
    public void setPoint(Point point) {
        setX(point.x);
        setY(point.y);
        invalidate();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }


    public void startAnimation() {
        ScaleAnimation scaleAnimation = new ScaleAnimation(
                1.0f, 2.0f, 1.0f, 2.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        scaleAnimation.setDuration(1500);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(1500);
        AnimationSet set = new AnimationSet(true);
        set.addAnimation(scaleAnimation);
        set.addAnimation(alphaAnimation);
        set.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup viewGroup = (ViewGroup) getParent();
                viewGroup.removeView(WinCoinTextView.this);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        startAnimation(set);
    }

}
