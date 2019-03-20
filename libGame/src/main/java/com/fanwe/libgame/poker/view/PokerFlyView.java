package com.fanwe.libgame.poker.view;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.fanwe.games.R;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.OnEndReset;
import com.fanwe.library.animator.listener.OnEndVisible;
import com.fanwe.library.animator.listener.SDAnimatorListener;
import com.fanwe.library.utils.SDViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 发牌动画view
 */
@TargetApi(25)
public class PokerFlyView extends BaseGameView
{
    public PokerFlyView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public PokerFlyView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PokerFlyView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private ImageView iv_pokers;
    private ImageView iv_poker;

    private List<View> mListTarget = new ArrayList<>();
    private SDAnimSet mAnimSet;

    private SoundPool mSoundPool;
    private int mSoundTagFlyPoker;

    private void init()
    {
        setContentView(R.layout.view_poker_fly);
        iv_pokers = (ImageView) findViewById(R.id.iv_pokers);
        iv_poker = (ImageView) findViewById(R.id.iv_poker);

        try
        {
            mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
            mSoundTagFlyPoker = mSoundPool.load(getContext(), R.raw.dealsound, 0);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setImagePoker(int resId)
    {
        iv_poker.setImageResource(resId);
    }

    public void setImagePokers(int resId)
    {
        iv_pokers.setImageResource(resId);
    }

    public void addTarget(View view)
    {
        if (view == null)
        {
            return;
        }
        mListTarget.add(view);
    }

    public void startFly()
    {
        if (mListTarget.isEmpty())
        {
            return;
        }

        SDViewUtil.setVisible(iv_poker);
        SDViewUtil.setVisible(iv_pokers);

        mAnimSet = SDAnimSet.from(iv_poker);
        for (int i = 0; i < mListTarget.size(); i++)
        {
            final View view = mListTarget.get(i);

            mAnimSet = mAnimSet.setAlignType(SDAnim.AlignType.Center).moveToX(view).setDuration(200)
                    .withClone().moveToY(view)
                    .with().scaleX(view).setDuration(200).withClone().scaleY(view)
                    .addListener(new SDAnimatorListener()
                    {
                        @Override
                        public void onAnimationStart(Animator animation)
                        {
                            super.onAnimationStart(animation);
                            playSound();
                        }

                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            super.onAnimationEnd(animation);
                            stopSound();
                        }
                    })
                    .addListener(new OnEndReset(iv_poker))
                    .addListener(new OnEndVisible(view));

            if (i < mListTarget.size() - 1)
            {
                mAnimSet = mAnimSet.next();
            }
        }

        mAnimSet.addListener(new SDAnimatorListener()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                super.onAnimationEnd(animation);
                stopFly();
            }
        });
        mAnimSet.start();
    }

    public void stopFly()
    {
        if (mAnimSet != null)
        {
            mAnimSet.cancel();
        }

        SDViewUtil.setInvisible(iv_poker);
        SDViewUtil.setInvisible(iv_pokers);
        stopSound();
    }

    private void stopSound()
    {
        if (mSoundPool != null)
        {
            mSoundPool.autoPause();
        }
    }

    private void playSound()
    {
        if (mSoundPool != null)
        {
            mSoundPool.play(mSoundTagFlyPoker, 1.0f, 1.0f, 0, 0, 1.0f);
        }
    }

    @Override
    protected void onDetachedFromWindow()
    {
        super.onDetachedFromWindow();
        if (mSoundPool != null)
        {
            mSoundPool.autoPause();
            mSoundPool.release();
            mSoundPool = null;
        }
    }
}
