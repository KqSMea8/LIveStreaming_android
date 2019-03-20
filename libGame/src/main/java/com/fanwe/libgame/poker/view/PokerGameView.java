package com.fanwe.libgame.poker.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.fanwe.games.R;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.libgame.poker.PokerManager;
import com.fanwe.libgame.poker.model.PokerGroupResultData;
import com.fanwe.libgame.view.BaseGameView;
import com.fanwe.libgame.view.GameBottomView;
import com.fanwe.library.animator.SDAnim;
import com.fanwe.library.animator.SDAnimSet;
import com.fanwe.library.animator.listener.OnEndRemoveView;
import com.fanwe.library.utils.SDCollectionUtil;
import com.fanwe.library.utils.SDViewUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/6/10.
 */
public class PokerGameView extends BaseGameView implements
        PokerManager.PokerManagerCallback,
        View.OnClickListener
{
    public PokerGameView(@NonNull Context context)
    {
        super(context);
    }

    public PokerGameView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public PokerGameView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    private static final float BLUR_LOST = 0.4f;

    private PokerManager mManager;

    private View view_poker_group_container_0, view_poker_group_container_1, view_poker_group_container_2;
    protected PokerGroupView view_poker_group_0, view_poker_group_1, view_poker_group_2;
    protected PokerResultView view_poker_result_0, view_poker_result_1, view_poker_result_2;

    private View view_bet_container_0, view_bet_container_1, view_bet_container_2;
    protected PokerBetView view_poker_bet_0, view_poker_bet_1, view_poker_bet_2;

    private GameBottomView view_poker_bottom;

    private PokerClockView view_clock; //开牌倒计时view
    private PokerFlyView view_poker_fly;
    private PokerToastView view_poker_toast;

    private PokerGameViewCallback mCallback;

    public PokerManager getManager()
    {
        if (mManager == null)
        {
            mManager = new PokerManager();
            mManager.setCallback(this);
        }
        return mManager;
    }

    public void setCallback(PokerGameViewCallback callback)
    {
        mCallback = callback;
    }

    @Override
    public void setContentView(int layoutId)
    {
        super.setContentView(layoutId);
        init();
    }

    private void init()
    {
        view_poker_group_container_0 = findViewById(R.id.view_poker_group_container_0);
        view_poker_group_container_1 = findViewById(R.id.view_poker_group_container_1);
        view_poker_group_container_2 = findViewById(R.id.view_poker_group_container_2);
        view_poker_group_0 = (PokerGroupView) findViewById(R.id.view_poker_group_0);
        view_poker_group_1 = (PokerGroupView) findViewById(R.id.view_poker_group_1);
        view_poker_group_2 = (PokerGroupView) findViewById(R.id.view_poker_group_2);
        view_poker_result_0 = (PokerResultView) findViewById(R.id.view_poker_result_0);
        view_poker_result_1 = (PokerResultView) findViewById(R.id.view_poker_result_1);
        view_poker_result_2 = (PokerResultView) findViewById(R.id.view_poker_result_2);
        view_bet_container_0 = findViewById(R.id.view_bet_container_0);
        view_bet_container_1 = findViewById(R.id.view_bet_container_1);
        view_bet_container_2 = findViewById(R.id.view_bet_container_2);
        view_poker_bet_0 = (PokerBetView) findViewById(R.id.view_poker_bet_0);
        view_poker_bet_1 = (PokerBetView) findViewById(R.id.view_poker_bet_1);
        view_poker_bet_2 = (PokerBetView) findViewById(R.id.view_poker_bet_2);
        view_clock = (PokerClockView) findViewById(R.id.view_clock);
        view_poker_fly = (PokerFlyView) findViewById(R.id.view_poker_fly);
        view_poker_bottom = (GameBottomView) findViewById(R.id.view_poker_bottom);
        view_poker_toast = (PokerToastView) findViewById(R.id.view_poker_toast);

        view_poker_bet_0.setOnClickListener(this);
        view_poker_bet_1.setOnClickListener(this);
        view_poker_bet_2.setOnClickListener(this);

        initPokerFlyView(view_poker_fly);
        initGameBottomView();
        resetGame();
    }

    private void initGameBottomView()
    {
        view_poker_bottom.setCallback(new GameBottomView.GameBottomViewCallback()
        {
            @Override
            public void onClickRecharge()
            {
                mCallback.onClickRecharge();
            }

            @Override
            public void onClickGameLog()
            {
                mCallback.onClickGameLog();
            }

            @Override
            public void onClickChangeAutoStartMode()
            {
                mCallback.onClickChangeAutoStartMode();
            }
        });
    }

    /**
     * 初始化发牌动画view
     *
     * @param pokerFlyView
     */
    protected void initPokerFlyView(PokerFlyView pokerFlyView)
    {

    }

    @Override
    public void onStart(long countTime)
    {
        resetGame();
    }

    @Override
    public void onStartDealPoker(boolean anim)
    {
        if (anim)
        {
            startFlyPoker();
            if (!getManager().isCreater())
            {
                //观众显示请选择幸运区域
                view_poker_toast.setImageTextResId(R.drawable.ic_poker_text_xzxyqy);
                view_poker_toast.showDelay(1000);
            }
        } else
        {
            view_poker_group_0.showAllPokerBack();
            view_poker_group_1.showAllPokerBack();
            view_poker_group_2.showAllPokerBack();
        }
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
        showClockView(show);
    }

    @Override
    public void onResultData(List<PokerGroupResultData> listData)
    {
        view_poker_group_0.setResultData(SDCollectionUtil.get(listData, 0));
        view_poker_group_1.setResultData(SDCollectionUtil.get(listData, 1));
        view_poker_group_2.setResultData(SDCollectionUtil.get(listData, 2));
    }

    @Override
    public void onShowResult(int position)
    {
        switch (position)
        {
            case 0:
                view_poker_group_0.showAllPokerFront();
                view_poker_result_0.show();
                break;
            case 1:
                view_poker_group_1.showAllPokerFront();
                view_poker_result_1.show();
                break;
            case 2:
                view_poker_group_2.showAllPokerFront();
                view_poker_result_2.show();
                break;
            default:
                break;
        }
    }

    @Override
    public void onStartShowResult(boolean sequentially)
    {
        if (sequentially)
        {
            //比牌开始
            view_poker_toast.setImageTextResId(R.drawable.ic_poker_text_bpks);
            view_poker_toast.show();
        }
    }

    @Override
    public void onMarkWinPosition(int winPosition)
    {
        resetWinPosition(); //为确保结果正确，先重置
        switch (winPosition)
        {
            case 0:
                view_poker_group_container_1.setAlpha(BLUR_LOST);
                view_poker_group_container_2.setAlpha(BLUR_LOST);
                view_bet_container_1.setAlpha(BLUR_LOST);
                view_bet_container_2.setAlpha(BLUR_LOST);
                break;
            case 1:
                view_poker_group_container_0.setAlpha(BLUR_LOST);
                view_poker_group_container_2.setAlpha(BLUR_LOST);
                view_bet_container_0.setAlpha(BLUR_LOST);
                view_bet_container_2.setAlpha(BLUR_LOST);
                break;
            case 2:
                view_poker_group_container_0.setAlpha(BLUR_LOST);
                view_poker_group_container_1.setAlpha(BLUR_LOST);
                view_bet_container_0.setAlpha(BLUR_LOST);
                view_bet_container_1.setAlpha(BLUR_LOST);
                break;
            default:
                resetWinPosition();
                break;
        }
    }

    @Override
    public void onUpdateBetMultiple(List<String> listData)
    {
        String data0 = SDCollectionUtil.get(listData, 0);
        view_poker_bet_0.setTextBetMultiple(data0);

        String data1 = SDCollectionUtil.get(listData, 1);
        view_poker_bet_1.setTextBetMultiple(data1);

        String data2 = SDCollectionUtil.get(listData, 2);
        view_poker_bet_2.setTextBetMultiple(data2);
    }

    @Override
    public void onUpdateTotalBet(List<Integer> listData)
    {
        Integer data0 = SDCollectionUtil.get(listData, 0);
        if (data0 != null)
        {
            view_poker_bet_0.setTextTotalBet(data0);
        }

        Integer data1 = SDCollectionUtil.get(listData, 1);
        if (data1 != null)
        {
            view_poker_bet_1.setTextTotalBet(data1);
        }

        Integer data2 = SDCollectionUtil.get(listData, 2);
        if (data2 != null)
        {
            view_poker_bet_2.setTextTotalBet(data2);
        }
    }

    @Override
    public void onUpdateUserBet(List<Integer> listData)
    {
        Integer data0 = SDCollectionUtil.get(listData, 0);
        if (data0 != null)
        {
            view_poker_bet_0.setTextUserBet(data0);
        }

        Integer data1 = SDCollectionUtil.get(listData, 1);
        if (data1 != null)
        {
            view_poker_bet_1.setTextUserBet(data1);
        }

        Integer data2 = SDCollectionUtil.get(listData, 2);
        if (data2 != null)
        {
            view_poker_bet_2.setTextUserBet(data2);
        }
    }

    @Override
    public void onUpdateBetCoinsOption(List<GameBetCoinsOptionModel> listData)
    {
        view_poker_bottom.setData(listData);
    }

    @Override
    public void onUpdateUserCoins(long userCoins)
    {
        view_poker_bottom.setUserCoins(userCoins);
    }

    @Override
    public void onGameStateChanged(int oldState, int newState)
    {

    }

    @Override
    public void onHasAutoStartMode(boolean hasAutoStartMode)
    {
        view_poker_bottom.setHasAutoStartMode(hasAutoStartMode);
    }

    @Override
    public void onAutoStartModeChanged(boolean isAutoStartMode)
    {
        view_poker_bottom.setAutoStartMode(isAutoStartMode);
    }

    @Override
    public void onUserCoinsImageRes(int res)
    {
        view_poker_bottom.setIvBotCoinsRes(res);
    }

    @Override
    public void onCanBetChanged(boolean canBet)
    {
        view_poker_bottom.setCanBet(canBet);
    }

    @Override
    public void onBetSuccess(int betPosition, long betCoins)
    {
        View coinsView = view_poker_bottom.getBetCoinsView(betCoins);
        if (coinsView == null)
        {
            return;
        }
        View betView = null;
        if (betPosition == 0)
        {
            betView = view_bet_container_0;
        } else if (betPosition == 1)
        {
            betView = view_bet_container_1;
        } else if (betPosition == 2)
        {
            betView = view_bet_container_2;
        }
        startFlyCoins(coinsView, betView);
    }

    /**
     * 开始发牌动画
     */
    private void startFlyPoker()
    {
        postLayoutRunnable(mFlyPokerRunnable);
    }

    private Runnable mFlyPokerRunnable = new Runnable()
    {
        @Override
        public void run()
        {
            view_poker_fly.startFly();
        }
    };

    /**
     * 停止发牌动画
     */
    private void stopFlyPoker()
    {
        view_poker_fly.stopFly();
    }

    /**
     * 隐藏提示toast
     */
    private void hidePokerToast()
    {
        view_poker_toast.hide();
    }

    /**
     * 显示隐藏倒计时view
     *
     * @param show
     */
    private void showClockView(boolean show)
    {
        SDViewUtil.setVisibleOrInvisible(view_clock, show);
    }

    /**
     * 重置游戏
     */
    private void resetGame()
    {
        stopFlyPoker();
        showClockView(false);
        hidePokerToast();
        resetPokerGroups();
        resetPokerBet();
        resetWinPosition();
    }

    /**
     * 重置牌组
     */
    private void resetPokerGroups()
    {
        view_poker_group_0.showAllPokerBack();
        view_poker_group_1.showAllPokerBack();
        view_poker_group_2.showAllPokerBack();
        view_poker_group_0.hideAllPoker();
        view_poker_group_1.hideAllPoker();
        view_poker_group_2.hideAllPoker();

        //重置牌组的牌面结果
        view_poker_result_0.hide();
        view_poker_result_1.hide();
        view_poker_result_2.hide();
    }

    /**
     * 重置投注区域数据
     */
    private void resetPokerBet()
    {
        view_poker_bet_0.resetView();
        view_poker_bet_1.resetView();
        view_poker_bet_2.resetView();
    }

    /**
     * 重置赢牌标注
     */
    private void resetWinPosition()
    {
        SDViewUtil.resetView(view_poker_group_container_0);
        SDViewUtil.resetView(view_poker_group_container_1);
        SDViewUtil.resetView(view_poker_group_container_2);

        SDViewUtil.resetView(view_bet_container_0);
        SDViewUtil.resetView(view_bet_container_1);
        SDViewUtil.resetView(view_bet_container_2);
    }

    @Override
    public void onClick(View v)
    {
        if (v == view_poker_bet_0)
        {
            if (getManager().isCanBet())
            {
                mCallback.onClickBetView(0, view_poker_bottom.getSelectedBetCoins());
            }
        } else if (v == view_poker_bet_1)
        {
            if (getManager().isCanBet())
            {
                mCallback.onClickBetView(1, view_poker_bottom.getSelectedBetCoins());
            }
        } else if (v == view_poker_bet_2)
        {
            if (getManager().isCanBet())
            {
                mCallback.onClickBetView(2, view_poker_bottom.getSelectedBetCoins());
            }
        }
    }

    /**
     * 投注金额飞行动画
     *
     * @param coinsView 游戏币view
     * @param betView   下注区域view
     */
    private void startFlyCoins(View coinsView, View betView)
    {
        if (coinsView == null || betView == null)
        {
            return;
        }
        SDAnimSet.from(coinsView).setAlignType(SDAnim.AlignType.Center)
                .moveToX(betView).setDuration(500).withClone().moveToY(betView)
                .with().alpha(1.0f, 0.6f).setDuration(500)
                .with().scaleX(1.0f, 0.6f).setDuration(500).withClone().scaleY(1.0f, 0.6f)
                .addListener(new OnEndRemoveView())
                .startAsPop();
    }

    public interface PokerGameViewCallback extends GameBottomView.GameBottomViewCallback
    {
        /**
         * 投注区域view被点击
         *
         * @param betPosition 投注位置[0-2]
         * @param betCoin     投注金额
         */
        void onClickBetView(int betPosition, long betCoin);

        /**
         * 倒计时结束
         */
        void onClockFinish();
    }
}
