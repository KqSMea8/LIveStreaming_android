package com.fanwe.libgame.dice.view;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.fanwe.games.R;
import com.fanwe.libgame.view.BaseGameView;

import java.io.IOException;

import pl.droidsonroids.gif.AnimationListener;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * 包名:
 * 描述: 骰子投掷动画界面
 * 作者: Su
 * 创建时间: 2017/5/23 15:18
 **/
public class DiceAnimView extends BaseGameView
{
    private static final float SPEED = 5.0f;

    private static final int[][] GIF_RES = new int[][]
            {
                    {R.drawable.dice_1_1, R.drawable.dice_1_2, R.drawable.dice_1_3, R.drawable.dice_1_4, R.drawable.dice_1_5, R.drawable.dice_1_5},
                    {R.drawable.dice_2_1, R.drawable.dice_2_2, R.drawable.dice_2_3, R.drawable.dice_2_4, R.drawable.dice_2_5, R.drawable.dice_2_5},
                    {R.drawable.dice_3_1, R.drawable.dice_3_2, R.drawable.dice_3_3, R.drawable.dice_3_4, R.drawable.dice_3_5, R.drawable.dice_3_5},
                    {R.drawable.dice_4_1, R.drawable.dice_4_2, R.drawable.dice_4_3, R.drawable.dice_4_4, R.drawable.dice_4_5, R.drawable.dice_4_5},
                    {R.drawable.dice_5_1, R.drawable.dice_5_2, R.drawable.dice_5_3, R.drawable.dice_5_4, R.drawable.dice_5_5, R.drawable.dice_5_5},
                    {R.drawable.dice_6_1, R.drawable.dice_6_2, R.drawable.dice_6_3, R.drawable.dice_6_4, R.drawable.dice_6_5, R.drawable.dice_6_5}
            };

    private GifImageView mGifImageView;
    private GifDrawable mGifDrawable;

    public DiceAnimView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initDiceAnimView(context);
    }

    public DiceAnimView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initDiceAnimView(context);
    }

    public DiceAnimView(Context context)
    {
        super(context);

        initDiceAnimView(context);
    }

    private void initDiceAnimView(Context context)
    {
        setContentView(R.layout.view_dice_anim);

    }

    private GifImageView getGifImageView()
    {
        if (mGifImageView == null)
        {
            mGifImageView = (GifImageView) findViewById(R.id.iv_dice_gif_view_dice_anim);
        }
        return mGifImageView;
    }

    public void throwDices(@IntRange(from = 1, to = 6) final int firstPoint,
                           @IntRange(from = 1, to = 6) final int secondPoint,
                           @Nullable final DiceAniMCallback callback)
    {
        mGifDrawable = generateGifDrawable(firstPoint, secondPoint);
        getGifImageView().setVisibility(VISIBLE);
        getGifImageView().setImageDrawable(mGifDrawable);
        mGifDrawable.addAnimationListener(new AnimationListener()
        {
            @Override
            public void onAnimationCompleted(int loopNumber)
            {
                if (callback != null)
                {
                    callback.onDiceAnimationFinish(firstPoint, secondPoint);
                }
            }
        });
        mGifDrawable.start();
    }

    private GifDrawable generateGifDrawable(@IntRange(from = 1, to = 6) int firstPoint, @IntRange(from = 1, to = 6) int secondPoint)
    {
        GifDrawable gifDrawable = null;
        try
        {
            gifDrawable = new GifDrawable(getResources(), GIF_RES[firstPoint - 1][secondPoint - 1]);
            gifDrawable.setSpeed(SPEED);
            gifDrawable.setLoopCount(1);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return gifDrawable;
    }

    public void reset()
    {
        getGifImageView().setVisibility(INVISIBLE);
        if (mGifDrawable != null)
        {
            mGifDrawable.recycle();
        }
    }

    public interface DiceAniMCallback
    {
        void onDiceAnimationFinish(int firstPoint, int secondPoint);
    }


}
