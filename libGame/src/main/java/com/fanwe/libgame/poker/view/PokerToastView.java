package com.fanwe.libgame.poker.view;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanwe.games.R;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.library.model.SDDelayRunnable;

/**
 * Created by Administrator on 2017/6/13.
 */

public class PokerToastView extends BaseGameView
{
    public PokerToastView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public PokerToastView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PokerToastView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_bg;
    private ImageView iv_text;


    private void init()
    {
        setContentView(R.layout.view_poker_toast);
        iv_bg = (ImageView) findViewById(R.id.iv_bg);
        iv_text = (ImageView) findViewById(R.id.iv_text);

        SDAnimSet animSetShow = SDAnimSet.from(iv_bg).scaleX(0, 1.0f).setDuration(200).withClone().scaleY(0, 1.0f)
                .with(iv_text).scaleX(0, 1.5f, 1.0f).setDuration(400).withClone().scaleY(0, 1.5f, 1.0f)
                .addListener(new SDAnimatorListener()
                {
                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        mHideRunnable.runDelay(1500);
                    }
                });
        getVisibilityHandler().setVisibleAnimator(animSetShow.getSet());

        SDAnimSet animSetHide = SDAnimSet.from(this).scaleX(1.0f, 0).setDuration(200).withClone().scaleY(1.0f, 0);
        getVisibilityHandler().setInvisibleAnimator(animSetHide.getSet());
    }

    public void setImageTextResId(int resId)
    {
        iv_text.setImageResource(resId);
    }

    public void show()
    {
        getVisibilityHandler().setVisible(true);
    }

    public void showDelay(long delay)
    {
        mShowRunnable.runDelay(delay);
    }

    public void hide()
    {
        mShowRunnable.removeDelay();
        mHideRunnable.removeDelay();
        getVisibilityHandler().setInvisible(true);
    }

    public SDDelayRunnable mShowRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            show();
        }
    };

    private SDDelayRunnable mHideRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            hide();
        }
    };
}
