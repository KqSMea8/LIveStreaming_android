package com.fanwe.libgame.dice.view;

import android.content.Context;
import android.view.View;

import com.fanwe.games.R;
import com.fanwe.libgame.dice.DiceManager;
import com.fanwe.libgame.dice.view.base.DiceScoreBaseBoardView;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.libgame.view.GameBottomView;
import com.fanwe.library.utils.SDViewUtil;

import java.util.List;

/**
 * Created by yhz on 2017/5/16. 骰子面板
 */

public class DiceGameView extends BaseGameView
{
    private DicePanelBodyView view_dice_panel_body; //骰子游戏主体界面
    private DiceClockView view_clock;//倒计时
    private DiceBottomView view_game_bottom;//底部菜单

    private DiceGameViewCallback mCallback;
    private DiceManager mManager;

    public void setCallback(DiceGameViewCallback callback)
    {
        mCallback = callback;
    }

    public DiceGameView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.view_dice_game);
        view_clock = (DiceClockView) findViewById(R.id.view_clock);
        view_game_bottom = (DiceBottomView) findViewById(R.id.view_game_bottom);
        view_dice_panel_body = (DicePanelBodyView) findViewById(R.id.view_dice_panel_body);

        initListener();
        resetAllState();
    }

    public DiceManager getManager()
    {
        if (mManager == null)
        {
            mManager = new DiceManager();
            mManager.setCallback(mDiceManagerCallback);
        }
        return mManager;
    }

    private void initListener()
    {
        view_dice_panel_body.setDicePanelBodyCallback(new DicePanelBodyView.DicePanelBodyCallback()
        {
            @Override
            public void onDiceScoreBoardBigClick(DiceScoreBoardBigView bigView)
            {
                if (!getManager().isCanBet())
                {
                    return;
                }
                if (mCallback != null)
                {
                    mCallback.onClickBetView(bigView, 0, view_game_bottom.getSelectedBetCoins());
                }
            }

            @Override
            public void onDiceScoreBoardMiddleClick(DiceScoreBoardMiddleView middleView)
            {
                if (!getManager().isCanBet())
                {
                    return;
                }
                if (mCallback != null)
                {
                    mCallback.onClickBetView(middleView, 1, view_game_bottom.getSelectedBetCoins());
                }
            }

            @Override
            public void onDiceScoreBoardSmallClick(DiceScoreBoardSmallView smallView)
            {
                if (!getManager().isCanBet())
                {
                    return;
                }
                if (mCallback != null)
                {
                    mCallback.onClickBetView(smallView, 2, view_game_bottom.getSelectedBetCoins());
                }
            }

            @Override
            public void onDiceResultShown(int firstPoint, int secondPoint)
            {
            }
        });

        //底部点击回调
        view_game_bottom.setCallback(new DiceBottomView.GameBottomViewCallback()
        {
            @Override
            public void onClickRecharge()
            {
                if (mCallback != null)
                {
                    mCallback.onClickRecharge();
                }
            }

            @Override
            public void onClickGameLog()
            {
                if (mCallback != null)
                {
                    mCallback.onClickGameLog();
                }
            }

            @Override
            public void onClickChangeAutoStartMode()
            {
                if (mCallback != null)
                {
                    mCallback.onClickChangeAutoStartMode();
                }
            }
        });
    }

    private DiceManager.DiceManagerCallback mDiceManagerCallback = new DiceManager.DiceManagerCallback()
    {
        @Override
        public void onStart(long countTime)
        {
            resetAllState();
        }

        @Override
        public void onCountDownTick(long leftTime)
        {
            view_clock.setTextLeftTime(String.valueOf(leftTime / 1000));
        }

        @Override
        public void onCountDownFinish()
        {
            view_clock.setTextLeftTime("0");
            mCallback.onClockFinish();
        }

        @Override
        public void onShowCountDown(boolean show)
        {
            SDViewUtil.setVisibleOrInvisible(view_clock, show);
        }

        @Override
        public void onMarkWinPosition(int winPosition)
        {
            //TODO 标注赢的位置
        }

        @Override
        public void onUpdateBetMultiple(List<String> listData)
        {
            view_dice_panel_body.setBetRatio(listData);
        }

        @Override
        public void onUpdateTotalBet(List<Integer> listData)
        {
            view_dice_panel_body.setBetsTotal(listData);
        }

        @Override
        public void onUpdateUserBet(List<Integer> listData)
        {
            view_dice_panel_body.setBetsTotalSelf(listData);
        }

        @Override
        public void onUpdateBetCoinsOption(List<GameBetCoinsOptionModel> listData)
        {
            view_game_bottom.setData(listData);
        }

        @Override
        public void onCanBetChanged(boolean canBet)
        {
            view_game_bottom.setCanBet(canBet);
        }

        @Override
        public void onBetSuccess(int betPosition, long betCoins)
        {
            View coinsView = view_game_bottom.getBetCoinsView(betCoins);
            if (coinsView == null)
            {
                return;
            }
            view_dice_panel_body.addBetSelf(betPosition, coinsView);
        }

        @Override
        public void onUpdateUserCoins(long userCoins)
        {
            view_game_bottom.setUserCoins(userCoins);
        }

        @Override
        public void onGameStateChanged(int oldState, int newState)
        {

        }

        @Override
        public void onHasAutoStartMode(boolean hasAutoStartMode)
        {
            view_game_bottom.setHasAutoStartMode(hasAutoStartMode);
        }

        @Override
        public void onAutoStartModeChanged(boolean isAutoStartMode)
        {
            view_game_bottom.setAutoStartMode(isAutoStartMode);
        }

        @Override
        public void onUserCoinsImageRes(int res)
        {
            view_game_bottom.setIvBotCoinsRes(res);
        }

        @Override
        public void onShowResult(List<Integer> listData)
        {
            view_dice_panel_body.throwDices(listData.get(0), listData.get(1));
        }
    };

    /**
     * 重置所有状态
     */
    public void resetAllState()
    {
        view_dice_panel_body.resetStates();
        SDViewUtil.setInvisible(view_clock);
    }

    public interface DiceGameViewCallback extends GameBottomView.GameBottomViewCallback
    {
        /**
         * 投注区域view被点击
         *
         * @param view
         * @param betPosition 投注位置[0-2]
         * @param betCoin     投注金额
         */
        void onClickBetView(DiceScoreBaseBoardView view, int betPosition, long betCoin);

        void onClockFinish();
    }

}
