package com.fanwe.live.appview.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.model.custommsg.CustomMsgGift;

/**
 * Created by Administrator on 2016/8/8.
 */
public abstract class GiftAnimatorView extends BaseAppView
{
    private CustomMsgGift msg;
    private boolean isPlaying;
    private boolean isCreate;
    private boolean needPlay;
    private AnimatorSet animatorSet;
    private Animator.AnimatorListener animatorListener;

    protected TextView tv_gift_desc;

    public GiftAnimatorView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public GiftAnimatorView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GiftAnimatorView(Context context)
    {
        super(context);
    }

    @Override
    protected void onBaseInit()
    {
        super.onBaseInit();
        animatorSet = new AnimatorSet();
    }

    public void setAnimatorSet(AnimatorSet animatorSet)
    {
        this.animatorSet = animatorSet;
    }

    public AnimatorSet getAnimatorSet()
    {
        return animatorSet;
    }

    public void setAnimatorListener(Animator.AnimatorListener animatorListener)
    {
        this.animatorListener = animatorListener;
    }

    public void setMsg(CustomMsgGift msg)
    {
        this.msg = msg;
        if (tv_gift_desc != null)
        {
            SDViewBinder.setTextView(tv_gift_desc, msg.getTop_title());
        }
    }

    public CustomMsgGift getMsg()
    {
        return msg;
    }

    protected void setPlaying(boolean playing)
    {
        this.isPlaying = playing;
    }

    public final boolean isPlaying()
    {
        return isPlaying;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        super.onLayout(changed, l, t, r, b);
        tryCreateAnimator();
    }

    private void tryCreateAnimator()
    {
        if (!isCreate)
        {
            isCreate = true;
            createAnimator();
            if (needPlay)
            {
                needPlay = false;
                getAnimatorSet().start();
            }
        }
    }

    protected abstract void createAnimator();

    protected abstract void resetView();

    public void play()
    {
        if (!isPlaying)
        {
            isPlaying = true;
            if (isCreate)
            {
                getAnimatorSet().start();
            } else
            {
                needPlay = true;
            }
        }
    }

    protected void notifyAnimationStart(Animator animation)
    {
        LogUtil.i("notifyAnimationStart:" + getClass().getSimpleName());
        setPlaying(true);
        if (animatorListener != null)
        {
            animatorListener.onAnimationStart(animation);
        }
    }

    protected void notifyAnimationEnd(Animator animation)
    {
        LogUtil.i("notifyAnimationEnd:" + getClass().getSimpleName());
        setPlaying(false);
        resetView();
        if (animatorListener != null)
        {
            animatorListener.onAnimationEnd(animation);
        }
    }

    protected void notifyAnimationCancel(Animator animation)
    {
        LogUtil.i("notifyAnimationCancel:" + getClass().getSimpleName());
        setPlaying(false);
        if (animatorListener != null)
        {
            animatorListener.onAnimationCancel(animation);
        }
    }

    protected void notifyAnimationRepeat(Animator animation)
    {
        LogUtil.i("notifyAnimationRepeat:" + getClass().getSimpleName());
        if (animatorListener != null)
        {
            animatorListener.onAnimationRepeat(animation);
        }
    }

    public static int getXLeftOut(View view)
    {
        return -SDViewUtil.getWidth(view);
    }

    public static int getXCenterCenter(View view)
    {
        return SDViewUtil.getScreenWidth() / 2 - SDViewUtil.getWidth(view) / 2;
    }

    public static int getXRightOut(View view)
    {
        return SDViewUtil.getScreenWidth();
    }

    public static int getYTopOut(View view)
    {
        return -SDViewUtil.getHeight(view);
    }

    public static int getYCenterCenter(View view)
    {
        return SDViewUtil.getScreenHeight() / 2 - SDViewUtil.getHeight(view) / 2;
    }

    public static int getYBottomOut(View view)
    {
        return SDViewUtil.getScreenHeight();
    }

    @Override
    protected void onDetachedFromWindow()
    {
        if (getAnimatorSet().isRunning())
        {
            getAnimatorSet().cancel();
        }
        LogUtil.i("onDetachedFromWindow:" + getClass().getSimpleName());
        super.onDetachedFromWindow();
    }
}
