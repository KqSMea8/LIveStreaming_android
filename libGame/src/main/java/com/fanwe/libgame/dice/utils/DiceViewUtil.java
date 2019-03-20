package com.fanwe.libgame.dice.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 包名: com.librarygames.utils
 * 描述:
 * 作者: Su
 * 创建时间: 2017/5/26 10:29
 **/
public class DiceViewUtil
{
    /**
     * 获取控件在屏幕的X坐标
     *
     * @param view
     * @return
     */
    public static int getViewXOnScreen(View view)
    {

        return getViewLocationOnScreen(view)[0];
    }

    /**
     * 获取控件在屏幕的Y坐标
     *
     * @param view
     * @return
     */
    public static int getViewYOnScreen(View view)
    {

        return getViewLocationOnScreen(view)[1];
    }

    /**
     * 获取控件在屏幕的坐标
     *
     * @param view
     * @return
     */
    public static int[] getViewLocationOnScreen(View view)
    {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        return location;
    }

    /**
     * 拷贝置顶view当前缓存视图至指定ImageView
     *
     * @param source
     * @param target
     */
    public static void copyView(@NonNull View source, @NonNull ImageView target)
    {
        source.setDrawingCacheEnabled(true);

        //拷贝Bitmap对象，否则调用setImageBitmap()将报错:
        // java.lang.RuntimeException:
        // Canvas: trying to use a recycled bitmap android.graphics.Bitmap@acad581

        if (source.getDrawingCache() == null)
        {
            throw new IllegalArgumentException("Can not copy from an empty view.");
        }

        final ViewGroup.LayoutParams sourceParams = source.getLayoutParams();

        if (sourceParams == null)
        {
            throw new IllegalArgumentException("Can not copy from an unspecific view.");
        }

        Bitmap copy = Bitmap.createBitmap(source.getDrawingCache());

        //设置false清空缓存，否则图片改变时获取到的仍为旧的bitmap.
        source.setDrawingCacheEnabled(false);

        target.setImageBitmap(copy);

        target.setLayoutParams(new ViewGroup.LayoutParams(source.getWidth(), source.getHeight()));
    }

    public static void moveTo(@NonNull Activity activity, @NonNull final View starter,
                              @NonNull final View target, long duration)
    {
        moveTo(activity, starter, target, duration, true, true);
    }

    /**
     * 创建Activity下2个视图间的位移动画
     *
     * @param activity
     * @param starter
     * @param target
     * @param duration
     * @param autoHideStart
     * @param autoShowEnd
     * @param extraAnimators
     */
    public static void moveTo(@NonNull Activity activity,
                              @NonNull final View starter,
                              @NonNull final View target,
                              long duration,
                              final boolean autoHideStart,
                              final boolean autoShowEnd,
                              @Nullable ObjectAnimator... extraAnimators)
    {
        final FrameLayout decorView = (FrameLayout) activity.getWindow().getDecorView();

        final FrameLayout layout = new FrameLayout(activity);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setClickable(true);

        final ImageView v = new ImageView(target.getContext());

        copyView(target, v);

        layout.addView(v);
        decorView.addView(layout);

        //定位至起点View位置
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) v.getLayoutParams();
        layoutParams.leftMargin = DiceViewUtil.getViewXOnScreen(starter);
        layoutParams.topMargin = DiceViewUtil.getViewYOnScreen(starter);

        float translationX0 = target.getTranslationX();
        float translationY0 = target.getTranslationY();

        float translationX1 = DiceViewUtil.getViewXOnScreen(target) - layoutParams.leftMargin; //注意要减去margin
        float translationY1 = DiceViewUtil.getViewYOnScreen(target) - layoutParams.topMargin;

        AnimatorSet set = new AnimatorSet();

        ObjectAnimator translationX = ObjectAnimator.ofFloat(v, "translationX", translationX0, translationX1);
        ObjectAnimator translationY = ObjectAnimator.ofFloat(v, "translationY", translationY0, translationY1);

        translationY.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation)
            {
                super.onAnimationStart(animation);
                if (autoHideStart)
                {
                    target.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);

                if (autoShowEnd)
                {
                    target.setVisibility(View.VISIBLE);
                }

                layout.removeAllViews();
                decorView.removeView(layout);
            }
        });

        Animator[] animators;
        if (extraAnimators != null && extraAnimators.length > 0)
        {
            int n = extraAnimators.length;
            animators = new Animator[n + 2];

            for (int i = 0; i < n; i++)
            {
                extraAnimators[i].setTarget(v);
                animators[i] = extraAnimators[i];
            }
            animators[n] = translationX;
            animators[n + 1] = translationY;
        } else
        {
            animators = new Animator[2];
            animators[0] = translationX;
            animators[1] = translationY;
        }

        set.playTogether(animators);
        set.setDuration(duration);
        set.setInterpolator(new FastOutSlowInInterpolator());
        set.start();
    }

}
