package com.fanwe.libgame.dice.view;

import android.content.Context;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.games.R;
import com.fanwe.libgame.dice.constant.DiceConstant;
import com.fanwe.libgame.view.BaseGameView;

import java.util.List;

/**
 * 包名: com.librarygames.view
 * 描述: 猜大小游戏面板主体
 * 作者: Su
 * 创建时间: 2017/5/23 10:58
 **/
public class DicePanelBodyView extends BaseGameView
{
    private DiceScoreBoardBigView mScoreBoardBig;
    private DiceScoreBoardMiddleView mScoreBoardMiddle;
    private DiceScoreBoardSmallView mScoreBoardSmall;
    private DiceAnimView mDiceAnimView;
    private DicePanelBodyCallback mDicePanelBodyCallback;


    public DicePanelBodyView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        initDicePanel(context);
    }

    public DicePanelBodyView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        initDicePanel(context);
    }

    public DicePanelBodyView(Context context)
    {
        super(context);
        initDicePanel(context);
    }

    private void initDicePanel(Context context)
    {
        setContentView(R.layout.view_dice_panel_body);

        getScoreBoardBig().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDicePanelBodyCallback().onDiceScoreBoardBigClick(getScoreBoardBig());
            }
        });

        getScoreBoardMiddle().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDicePanelBodyCallback().onDiceScoreBoardMiddleClick(getScoreBoardMiddle());
            }
        });

        getScoreBoardSmall().setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getDicePanelBodyCallback().onDiceScoreBoardSmallClick(getScoreBoardSmall());
            }
        });
    }

    private DiceScoreBoardBigView getScoreBoardBig()
    {
        if (mScoreBoardBig == null)
        {
            mScoreBoardBig = (DiceScoreBoardBigView) findViewById(R.id.view_dice_score_board_big_view_dice_panel_body);
        }
        return mScoreBoardBig;
    }

    private DiceScoreBoardMiddleView getScoreBoardMiddle()
    {
        if (mScoreBoardMiddle == null)
        {
            mScoreBoardMiddle = (DiceScoreBoardMiddleView) findViewById(R.id.view_dice_score_board_middle_view_dice_panel_body);
        }
        return mScoreBoardMiddle;
    }

    private DiceScoreBoardSmallView getScoreBoardSmall()
    {
        if (mScoreBoardSmall == null)
        {
            mScoreBoardSmall = (DiceScoreBoardSmallView) findViewById(R.id.view_dice_score_board_small_view_dice_panel_body);
        }
        return mScoreBoardSmall;
    }

    private DiceAnimView getDiceAnimView()
    {
        if (mDiceAnimView == null)
        {
            mDiceAnimView = (DiceAnimView) findViewById(R.id.view_dice_anim_view_dice_panel_body);
        }
        return mDiceAnimView;
    }

    /**
     * 设置各类型面板赔率
     *
     * @param ratio
     */
    public void setBetRatio(@NonNull List<String> ratio)
    {
        if (ratio.size() == 3)
        {
            getScoreBoardBig().setBetRatio(ratio.get(0));
            getScoreBoardMiddle().setBetRatio(ratio.get(1));
            getScoreBoardSmall().setBetRatio(ratio.get(2));
        }
    }

    /**
     * 设置投注金币显示的最小单位（每超过一定数额，则新增显示一个图标）
     *
     * @param threshold 默认10000
     */
    public void setBetsIconThreshold(int threshold)
    {
        getScoreBoardBig().setBetsIconThreshold(threshold);
        getScoreBoardMiddle().setBetsIconThreshold(threshold);
        getScoreBoardSmall().setBetsIconThreshold(threshold);
    }

    /**
     * 设置各类型面板总投注
     *
     * @param bets bets[0] 大
     *             bets[1] 中
     *             bets[2] 小
     */
    public void setBetsTotal(@NonNull List<Integer> bets)
    {
        if (bets.size() == 3)
        {
            getScoreBoardBig().setBetTotal(bets.get(0));
            getScoreBoardMiddle().setBetTotal(bets.get(1));
            getScoreBoardSmall().setBetTotal(bets.get(2));
        }
    }

    /**
     * 设置各类型面板自身总投注
     *
     * @param bets bets[0] 大
     *             bets[1] 中
     *             bets[2] 小
     */
    public void setBetsTotalSelf(@NonNull List<Integer> bets)
    {
        if (bets.size() == 3)
        {
            getScoreBoardBig().setBetTotalSelf(bets.get(0));
            getScoreBoardMiddle().setBetTotalSelf(bets.get(1));
            getScoreBoardSmall().setBetTotalSelf(bets.get(2));
        }
    }

    /**
     * 执行某个位置的金币飞行动画
     *
     * @param pos
     *          0:大
     *          1:中
     *          2:小
     * @param startView
     */
    public void addBetSelf(@IntRange(from = 0, to = 2) int pos, @NonNull View startView)
    {
        if (pos == 0)
        {
            getScoreBoardBig().addBetEmpty(startView);
        } else if (pos == 1)
        {
            getScoreBoardMiddle().addBetEmpty(startView);
        } else if (pos == 2)
        {
            getScoreBoardSmall().addBetEmpty(startView);
        }
    }

    /**
     * 执行掷出指定点数骰子动画
     *
     * @param firstPoint
     * @param secondPoint
     */
    public void throwDices(@IntRange(from = 1, to = 6) int firstPoint, @IntRange(from = 1, to = 6) int secondPoint)
    {
        setBoardBetEnable(false);

        setBoardHighLight(DiceConstant.DICE_BOARD_BIG, false, null);
        setBoardHighLight(DiceConstant.DICE_BOARD_MIDDLE, false, null);
        setBoardHighLight(DiceConstant.DICE_BOARD_SMALL, false, null);

        getDiceAnimView().throwDices(firstPoint, secondPoint, new DiceAnimView.DiceAniMCallback()
        {
            @Override
            public void onDiceAnimationFinish(final int firstPoint, final int secondPoint)
            {
                setBoardHighLight(getResultType(firstPoint, secondPoint), true, new Runnable()
                {
                    @Override
                    public void run()
                    {
                        getDicePanelBodyCallback().onDiceResultShown(firstPoint, secondPoint);
                    }
                });
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
        getScoreBoardBig().setBoardBetEnable(enable);
        getScoreBoardMiddle().setBoardBetEnable(enable);
        getScoreBoardSmall().setBoardBetEnable(enable);
    }

    /**
     * 获取当前各类型赔率
     *
     * @return ratios[0] 大
     * ratios[1] 中
     * ratios[2] 小
     */
    public String[] getBetsRatio()
    {
        String[] ratios = new String[3];

        ratios[0] = getScoreBoardBig().getBetRatio();
        ratios[1] = getScoreBoardMiddle().getBetRatio();
        ratios[2] = getScoreBoardSmall().getBetRatio();
        return ratios;
    }

    /**
     * 获取当前各类型总投注数
     *
     * @return
     */
    public int[] getBetsTotal()
    {
        int[] bets = new int[3];
        bets[0] = getScoreBoardBig().getBetTotal();
        bets[1] = getScoreBoardMiddle().getBetTotal();
        bets[2] = getScoreBoardSmall().getBetTotal();
        return bets;
    }

    /**
     * 获取当前各类型当前用户总投注数
     *
     * @return
     */
    public int[] getBetsTotalSelf()
    {
        int[] bets = new int[3];
        bets[0] = getScoreBoardBig().getBetTotalSelf();
        bets[1] = getScoreBoardMiddle().getBetTotalSelf();
        bets[2] = getScoreBoardSmall().getBetTotalSelf();
        return bets;
    }

    /**
     * 重置游戏面板状态
     */
    public void resetStates()
    {
        getScoreBoardBig().resetState();
        getScoreBoardMiddle().resetState();
        getScoreBoardSmall().resetState();
        resetAnimState();
    }

    public void resetAnimState()
    {
        getDiceAnimView().reset();
    }

    private void setBoardHighLight(@DiceConstant.DiceScoreType int type, boolean enable, @Nullable Runnable endRunnable)
    {
        if (DiceConstant.DICE_BOARD_BIG == type)
        {
            getScoreBoardBig().setBoardHighLight(enable, endRunnable);
        } else if (DiceConstant.DICE_BOARD_MIDDLE == type)
        {
            getScoreBoardMiddle().setBoardHighLight(enable, endRunnable);
        } else if (DiceConstant.DICE_BOARD_SMALL == type)
        {
            getScoreBoardSmall().setBoardHighLight(enable, endRunnable);
        }
    }

    private int getResultType(int firstPoint, int secondPoint)
    {
        int sum = firstPoint + secondPoint;

        if (7 == sum)
        {
            return DiceConstant.DICE_BOARD_MIDDLE;
        } else if (7 > sum)
        {
            return DiceConstant.DICE_BOARD_SMALL;
        }
        return DiceConstant.DICE_BOARD_BIG;
    }

    private DicePanelBodyCallback getDicePanelBodyCallback()
    {
        if (mDicePanelBodyCallback == null)
        {
            mDicePanelBodyCallback = new DicePanelBodyCallback()
            {
                @Override
                public void onDiceScoreBoardBigClick(DiceScoreBoardBigView bigView)
                {

                }

                @Override
                public void onDiceScoreBoardMiddleClick(DiceScoreBoardMiddleView middleView)
                {

                }

                @Override
                public void onDiceScoreBoardSmallClick(DiceScoreBoardSmallView smallView)
                {

                }

                @Override
                public void onDiceResultShown(int firstPoint, int secondPoint)
                {

                }
            };
        }
        return mDicePanelBodyCallback;
    }

    public void setDicePanelBodyCallback(DicePanelBodyCallback callback)
    {
        this.mDicePanelBodyCallback = callback;
    }

    public interface DicePanelBodyCallback
    {
        void onDiceScoreBoardBigClick(DiceScoreBoardBigView bigView);

        void onDiceScoreBoardMiddleClick(DiceScoreBoardMiddleView middleView);

        void onDiceScoreBoardSmallClick(DiceScoreBoardSmallView smallView);

        void onDiceResultShown(int firstPoint, int secondPoint);
    }

}
