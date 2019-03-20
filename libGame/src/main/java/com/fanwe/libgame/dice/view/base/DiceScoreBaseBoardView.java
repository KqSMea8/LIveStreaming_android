package com.fanwe.libgame.dice.view.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.dice.constant.DiceConstant;
import com.fanwe.libgame.dice.utils.DiceViewUtil;
import com.fanwe.libgame.view.BaseGameView;

import java.util.Random;

/**
 * 包名: com.librarygames.view
 * 描述:  摇色子比大小结果面板基类
 * 作者: Su
 * 创建时间: 2017/5/23 10:07
 **/
public abstract class DiceScoreBaseBoardView extends BaseGameView
{
    private View mMaskView;
    private RelativeLayout mBetsContainer;
    private ImageView mBetAnimationImageView;
    private TextView mBetTotalTextView;
    private TextView mBetSelfTextView;
    private TextView mRatioTextView;

    private int mBetsIconThreshold = 10000;
    private int mBetTotal;  //总投注
    private int mBetTotalSelf;   //自身投注
    private String mBetRatio;    //赔率

    private boolean mBetEnable = true;


    public DiceScoreBaseBoardView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initDiceScoreBaseBoard(context);
    }

    public DiceScoreBaseBoardView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initDiceScoreBaseBoard(context);
    }

    public DiceScoreBaseBoardView(Context context)
    {
        super(context);
        initDiceScoreBaseBoard(context);
    }

    @DiceConstant.DiceScoreType
    protected abstract int getDiceScoreType();

    private void initDiceScoreBaseBoard(Context context)
    {
        int layoutRes = getDiceScoreType() == DiceConstant.DICE_BOARD_BIG ? R.layout.view_dice_score_board_big
                : (getDiceScoreType() == DiceConstant.DICE_BOARD_SMALL ? R.layout.view_dice_score_board_small
                : R.layout.view_dice_score_board_middle);

        setContentView(layoutRes);
        getMaskView().setAlpha(0);
    }

    private View getMaskView()
    {
        if (mMaskView == null)
        {
            mMaskView = findViewById(R.id.mask_view_dice_score_board);
        }
        return mMaskView;
    }

    private RelativeLayout getBetsContainer()
    {
        if (mBetsContainer == null)
        {
            mBetsContainer = (RelativeLayout) findViewById(R.id.rl_container_bets_view_dice_score_board);
        }
        return mBetsContainer;
    }

    private ImageView getBetAnimationImageView()
    {
        if (mBetAnimationImageView == null)
        {
            mBetAnimationImageView = (ImageView) findViewById(R.id.iv_bets_view_dice_score_board);
        }
        return mBetAnimationImageView;
    }

    private TextView getBetTotalTextView()
    {
        if (mBetTotalTextView == null)
        {
            mBetTotalTextView = (TextView) findViewById(R.id.tv_bet_total_view_dice_score_board);
        }
        return mBetTotalTextView;
    }

    private TextView getBetSelfTextView()
    {
        if (mBetSelfTextView == null)
        {
            mBetSelfTextView = (TextView) findViewById(R.id.tv_bet_self_view_dice_score_board);
        }
        return mBetSelfTextView;
    }

    private TextView getRatioTextView()
    {
        if (mRatioTextView == null)
        {
            mRatioTextView = (TextView) findViewById(R.id.tv_ratio_view_dice_score_board);
        }
        return mRatioTextView;
    }

    /**
     * 设置投注金币显示的最小单位（每超过一定数额，则新增显示一个图标）
     *
     * @param threshold
     */
    public void setBetsIconThreshold(int threshold)
    {
        if (threshold <= 0)
        {
            return;
        }
        mBetsIconThreshold = threshold;
    }

    /**
     * 增加当前类型赔率
     *
     * @param ratio
     */
    public void setBetRatio(String ratio)
    {
        mBetRatio = ratio;
        getRatioTextView().setVisibility(VISIBLE);
        getRatioTextView().setText("x" + ratio);
    }

    /**
     * 设置当前类型总投注数
     *
     * @param betTotal
     */
    public void setBetTotal(int betTotal)
    {
        if (betTotal <= 0)
        {
            return;
        }

        //顺序错误
        if (betTotal <= mBetTotal)
        {
            return;
        }

        mBetTotal = betTotal;

        getBetTotalTextView().setVisibility(VISIBLE);
        getBetTotalTextView().setText(mBetTotal + "");

        final int FinalBetTotal = mBetTotal;

        postLayoutRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                addBetIconIfNeed(FinalBetTotal);
            }
        });
    }

    /**
     * 增加当前类型自身总投注数
     *
     * @param bet
     */
    public void setBetTotalSelf(int bet)
    {
        if (bet <= 0)
        {
            return;
        }

        //顺序错误
        if (bet <= mBetTotalSelf)
        {
            return;
        }

        mBetTotalSelf = bet;

        getBetSelfTextView().setVisibility(VISIBLE);
        getBetSelfTextView().setText(mBetTotalSelf + "");
    }

    /**
     * 随机添加投注图标
     *
     * @param betTotal
     */
    private void addBetIconIfNeed(int betTotal)
    {
        if (betTotal < 0)
        {
            return;
        }

        int betsIcons = betTotal / mBetsIconThreshold;

        if (betsIcons < 1)
        {
            betsIcons = 1;
        }

        int currentIcons = getBetsContainer().getChildCount();
        int iconsToAdd = betsIcons - currentIcons;

        if (iconsToAdd <=0)
        {
            return;
        }

        for (int i = 0; i < iconsToAdd; i++)
        {
            View view = generateBetIcon();
            setBetRandomPosition(view, getBetsContainer());
            getBetsContainer().addView(view);
        }

    }

    private void setBetRandomPosition(View view, RelativeLayout container)
    {
        int[] pos = generateRandomPosition(view, container);

        view.setTranslationX(pos[0]);
        view.setTranslationY(pos[1]);
    }

    /**
     * 生成投注图标
     *
     * @return
     */
    private View generateBetIcon()
    {
        ImageView imageView = new ImageView(getActivity());
        DiceViewUtil.copyView(getBetAnimationImageView(), imageView);
        return imageView;
    }

    /**
     * 随机生成坐标
     *
     * @param container
     * @return
     */
    private int[] generateRandomPosition(View target, View container)
    {
        int[] pos = new int[2];
        Random random = new Random();

        pos[0] = random.nextInt((container.getWidth() - target.getLayoutParams().width));
        pos[1] = random.nextInt((container.getHeight() - target.getLayoutParams().height));
        return pos;
    }

    /**
     * 追加空投注（只执行金币飞行动画）
     *
     * @param animStarter 金币飞行起点View
     */
    public void addBetEmpty(@NonNull final View animStarter)
    {
        if (!mBetEnable)
        {
            return;
        }

        postLayoutRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                startBetsAnimation(animStarter, getBetAnimationImageView());
            }
        });
    }

    /**
     * 设置是否可投注
     *
     * @param enable
     */
    public void setBoardBetEnable(boolean enable)
    {
        mBetEnable = enable;
    }

    /**
     * 设置结果高亮
     *
     * @param highLight
     * @param endRunnable
     */
    public void setBoardHighLight(final boolean highLight, @Nullable final Runnable endRunnable)
    {
        getMaskView().animate()
                .alpha(highLight ? 0.0f : 1.0f)
                .setInterpolator(highLight?new FastOutSlowInInterpolator():new DecelerateInterpolator())
                .setDuration(800)
                .setListener(new AnimatorListenerAdapter()
                {
                    @Override
                    public void onAnimationStart(Animator animation)
                    {
                        super.onAnimationStart(animation);
                        getMaskView().setVisibility(VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation)
                    {
                        super.onAnimationEnd(animation);
                        if (endRunnable != null)
                        {
                            endRunnable.run();
                        }

                        if (highLight)
                        {
                            getMaskView().setVisibility(GONE);
                        }
                    }
                })
                .start();
    }

    /**
     * 获取当前类型总投注数
     *
     * @return
     */
    public int getBetTotal()
    {
        return mBetTotal;
    }

    /**
     * 获取当前类型用户总投注数
     *
     * @return
     */
    public int getBetTotalSelf()
    {
        return mBetTotalSelf;
    }

    /**
     * 获取当前类型赔率
     *
     * @return
     */
    public String getBetRatio()
    {
        return mBetRatio;
    }

    /**
     * 重置状态（清空投注、结果高亮等）
     */
    public void resetState()
    {
        mBetTotal = 0;
        mBetTotalSelf = 0;
        mBetEnable = true;

        setBoardHighLight(true, null);

        getBetTotalTextView().setVisibility(INVISIBLE);
        getBetSelfTextView().setVisibility(INVISIBLE);

        clearBetIcons();
    }

    /**
     * 清除投注图标
     */
    private void clearBetIcons()
    {
        int n = getBetsContainer().getChildCount();
        if (n > 0)
        {

            for (int i = 0; i < n; i++)
            {
                View view = getBetsContainer().getChildAt(i);

                ViewPropertyAnimator animator = view.animate()
                        .scaleX(0)
                        .scaleY(0)
                        .setDuration(300)
                        .setInterpolator(new DecelerateInterpolator());

                if(i==n-1){
                    animator.setListener(new AnimatorListenerAdapter()
                    {
                        @Override
                        public void onAnimationEnd(Animator animation)
                        {
                            super.onAnimationEnd(animation);
                            getBetsContainer().removeAllViews();
                        }
                    });
                }

                animator.start();
            }
        }
    }

    /**
     * 开始投注动画
     *
     * @param animStarter
     * @param animEndView
     */
    private void startBetsAnimation(@NonNull View animStarter, @NonNull View animEndView)
    {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(animEndView, "scaleX", 1.5f, 0.8f, 0.8f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(animEndView, "scaleY", 1.5f, 0.8f, 0.8f, 1.0f);

        DiceViewUtil.moveTo(getActivity(), animStarter, animEndView, 500, true, false, scaleX, scaleY);
    }


}
