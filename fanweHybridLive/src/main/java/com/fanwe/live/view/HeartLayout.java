package com.fanwe.live.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanwe.library.utils.SDRandom;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HeartLayout extends RelativeLayout
{

    private static final int HEART_WIDTH = SDViewUtil.dp2px(25);

    private List<Interpolator> listInterpolator = new ArrayList<Interpolator>();
    private List<String> listDrawableName = new ArrayList<String>();
    private Map<String, Drawable> mapNameDrawable = new HashMap<String, Drawable>();

    private int height;
    private int width;
    private LayoutParams heartLayoutParams;
    private Random random = new SDRandom();
    private int drawableHeight;
    private int drawableWidth;

    public HeartLayout(Context context)
    {
        super(context);
        init();
    }

    public HeartLayout(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public HeartLayout(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getHeartCount()
    {
        return getChildCount();
    }

    private void init()
    {

        listDrawableName.clear();
        listDrawableName.add("heart0");
        listDrawableName.add("heart1");
        listDrawableName.add("heart2");
        listDrawableName.add("heart3");
        listDrawableName.add("heart4");
        listDrawableName.add("heart5");
        listDrawableName.add("heart6");

        mapNameDrawable.clear();
        Drawable drawable = null;
        for (String item : listDrawableName)
        {
            int id = SDResourcesUtil.getIdentifierDrawable(item);
            if (id != 0)
            {
                drawable = getResources().getDrawable(id);
                if (drawable != null)
                {
                    mapNameDrawable.put(item, drawable);
                }
            }
        }

        drawableHeight = drawable.getIntrinsicHeight();
        drawableWidth = drawable.getIntrinsicWidth();

        createHeartLayoutParams();

        listInterpolator.add(new LinearInterpolator());
        listInterpolator.add(new AccelerateInterpolator());
        listInterpolator.add(new DecelerateInterpolator());
        listInterpolator.add(new AccelerateDecelerateInterpolator());

    }

    private void createHeartLayoutParams()
    {
        heartLayoutParams = new LayoutParams(HEART_WIDTH, HEART_WIDTH);
        heartLayoutParams.addRule(CENTER_HORIZONTAL, TRUE);// 这里的TRUE 要注意 不是true
        heartLayoutParams.addRule(ALIGN_PARENT_BOTTOM, TRUE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    public String randomImageName()
    {
        int index = random.nextInt(listDrawableName.size());
        String name = listDrawableName.get(index);
        return name;
    }

    public void addHeart(String imageName)
    {
        Drawable drawable = mapNameDrawable.get(imageName);

        if (drawable != null)
        {
            ImageView imageView = new ImageView(getContext());
            imageView.setImageDrawable(drawable);
            imageView.setLayoutParams(heartLayoutParams);

            addView(imageView);

            Animator set = getAnimator(imageView);
            set.addListener(new AnimEndListener(imageView));
            set.start();
        }
    }

    private Animator getAnimator(View target)
    {
        AnimatorSet set = getEnterAnimtor(target);

        ValueAnimator bezierValueAnimator = getBezierValueAnimator(target);

        AnimatorSet finalSet = new AnimatorSet();
        finalSet.playSequentially(set);
        finalSet.playSequentially(set, bezierValueAnimator);
        finalSet.setInterpolator(listInterpolator.get(random.nextInt(listInterpolator.size())));
        finalSet.setTarget(target);
        return finalSet;
    }

    private AnimatorSet getEnterAnimtor(final View target)
    {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(target, View.ALPHA, 0.2f, 1f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, View.SCALE_X, 0.2f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, View.SCALE_Y, 0.2f, 1f);
        AnimatorSet enter = new AnimatorSet();
        enter.setDuration(500);
        enter.setInterpolator(new LinearInterpolator());
        enter.playTogether(alpha, scaleX, scaleY);
        enter.setTarget(target);
        return enter;
    }

    private ValueAnimator getBezierValueAnimator(View target)
    {

        // 初始化一个贝塞尔计算器- - 传入
        BezierEvaluator evaluator = new BezierEvaluator(getPointF(2), getPointF(1));

        // 这里最好画个图 理解一下 传入了起点 和 终点
        ValueAnimator animator = ValueAnimator.ofObject(evaluator, new PointF((width - drawableWidth) / 2, height - drawableHeight), new PointF(
                random.nextInt(getWidth()), 0));
        animator.addUpdateListener(new BezierListenr(target));
        animator.setTarget(target);
        animator.setDuration(3000);
        return animator;
    }

    /**
     * 获取中间的两个 点
     *
     * @param scale
     */
    private PointF getPointF(int scale)
    {
        PointF pointF = new PointF();
        try
        {
            pointF.x = random.nextInt((width - 100));// 减去100 是为了控制 x轴活动范围,看效果 随意~~
            // 再Y轴上 为了确保第二个点 在第一个点之上,我把Y分成了上下两半 这样动画效果好一些 也可以用其他方法
            pointF.y = random.nextInt((height - 100)) / scale;
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return pointF;
    }

    private class BezierListenr implements ValueAnimator.AnimatorUpdateListener
    {

        private View target;

        public BezierListenr(View target)
        {
            this.target = target;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation)
        {
            // 这里获取到贝塞尔曲线计算出来的的x y值 赋值给view 这样就能让爱心随着曲线走啦
            PointF pointF = (PointF) animation.getAnimatedValue();
            target.setX(pointF.x);
            target.setY(pointF.y);
            // 这里顺便做一个alpha动画
            target.setAlpha(1 - animation.getAnimatedFraction());
        }
    }

    private class AnimEndListener extends AnimatorListenerAdapter
    {
        private View target;

        public AnimEndListener(View target)
        {
            this.target = target;
        }

        @Override
        public void onAnimationEnd(Animator animation)
        {
            super.onAnimationEnd(animation);
            // 因为不停的add 导致子view数量只增不减,所以在view动画结束后remove掉
            removeView((target));
        }
    }
}
