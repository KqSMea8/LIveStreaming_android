package com.fanwe.libgame.view;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.games.R;
import com.fanwe.libgame.adapter.GameBetCoinsOptionAdapter;
import com.fanwe.libgame.model.GameBetCoinsOptionModel;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.SDRecyclerView;

import java.util.List;

/**
 * 游戏底部view（充值，投注）
 */
public class GameBottomView extends BaseGameView implements View.OnClickListener
{
    public GameBottomView(@NonNull Context context)
    {
        super(context);
        init();
    }

    public GameBottomView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public GameBottomView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    protected View ll_recharge;
    protected ImageView iv_bot_coins;
    protected TextView tv_coins; //用户游戏币
    protected SDRecyclerView rv_coins;
    protected View iv_game_log;
    protected ImageView iv_auto_start_mode;

    public GameBetCoinsOptionAdapter mAdapter;
    private long mUserCoins;

    protected GameBottomViewCallback mCallback;

    protected void initLayout()
    {
        setContentView(R.layout.view_game_bottom);
    }

    protected void init()
    {
        initLayout();
        initView();
        initListener();
        initAdapter();

        setHasAutoStartMode(false);
        rv_coins.setLinearHorizontal();
    }

    protected void initView()
    {
        ll_recharge = findViewById(R.id.ll_recharge);
        iv_bot_coins = (ImageView) findViewById(R.id.iv_bot_coins);
        tv_coins = (TextView) findViewById(R.id.tv_coins);
        rv_coins = (SDRecyclerView) findViewById(R.id.rv_coins);
        iv_game_log = findViewById(R.id.iv_game_log);
        iv_auto_start_mode = (ImageView) findViewById(R.id.iv_auto_start_mode);
    }

    protected void initListener()
    {
        ll_recharge.setOnClickListener(this);
        iv_game_log.setOnClickListener(this);
        iv_auto_start_mode.setOnClickListener(this);
    }

    protected void initAdapter()
    {
        mAdapter = new GameBetCoinsOptionAdapter(null, (Activity) getContext());
        mAdapter.setItemClickCallback(new SDItemClickCallback<GameBetCoinsOptionModel>()
        {
            @Override
            public void onItemClick(int position, GameBetCoinsOptionModel item, View view)
            {
                performSelect(position);
            }
        });
        rv_coins.setAdapter(mAdapter);
    }

    public void setCallback(GameBottomViewCallback callback)
    {
        mCallback = callback;
    }

    /**
     * 选中第几个投注金额
     *
     * @param position
     */
    protected void performSelect(int position)
    {
        if (!mAdapter.isPositionLegal(position))
        {
            return;
        }
        GameBetCoinsOptionModel model = mAdapter.getData(position);
        if (mUserCoins < model.getCoins())
        {
            return;
        }
        mAdapter.getSelectManager().performClick(position);
    }

    /**
     * 获得选中的投注面额
     *
     * @return
     */
    public long getSelectedBetCoins()
    {
        GameBetCoinsOptionModel model = mAdapter.getSelectManager().getSelectedItem();
        if (model != null)
        {
            return model.getCoins();
        } else
        {
            return 0;
        }
    }

    /**
     * 获得投注游戏币对应的view
     *
     * @param betCoins 投注的游戏币
     * @return
     */
    public View getBetCoinsView(long betCoins)
    {
        if (mAdapter.getData().isEmpty())
        {
            return null;
        }

        int position = -1;
        for (int i = 0; i < mAdapter.getDataCount(); i++)
        {
            if (mAdapter.getData(i).getCoins() == betCoins)
            {
                position = i;
                break;
            }
        }
        if (position < 0)
        {
            return null;
        }
        if (position >= rv_coins.getChildCount())
        {
            return null;
        }

        return rv_coins.getChildAt(position);
    }

    /**
     * 设置可投注的金额数据
     *
     * @param listModel
     */
    public void setData(List<GameBetCoinsOptionModel> listModel)
    {
        mAdapter.updateData(listModel);
        performSelect(0);
    }

    /**
     * 设置用户的可用游戏币
     *
     * @param userCoins
     */
    public void setUserCoins(long userCoins)
    {
        mUserCoins = userCoins;
        tv_coins.setText(String.valueOf(userCoins));
        mAdapter.setUserCoins(userCoins);

        dealSelectIfNeed();
    }

    private void dealSelectIfNeed()
    {
        int oldPosition = mAdapter.getSelectManager().getSelectedIndex();
        if (mAdapter.isPositionLegal(oldPosition))
        {
            GameBetCoinsOptionModel model = mAdapter.getSelectManager().getSelectedItem();
            if (mUserCoins < model.getCoins())
            {
                // 如果用户余额变少，不够选择之前选中的金额，则遍历实体选中够投注的金额
                boolean hasAvailableItem = false;
                for (int i = oldPosition; i >= 0; i--)
                {
                    GameBetCoinsOptionModel item = mAdapter.getData(i);
                    if (mUserCoins >= item.getCoins())
                    {
                        performSelect(i);
                        hasAvailableItem = true;
                        break;
                    }
                }
                if (!hasAvailableItem)
                {
                    mAdapter.getSelectManager().clearSelected();
                }
            }
        } else
        {
            performSelect(0);
        }
    }

    /**
     * 设置是否可以投注
     *
     * @param canBet
     */
    public void setCanBet(boolean canBet)
    {
        SDViewUtil.setVisibleOrGone(rv_coins, canBet);
    }

    /**
     * 设置当前是否自动开始模式
     *
     * @param isAutoStartMode
     */
    public void setAutoStartMode(boolean isAutoStartMode)
    {
        if (isAutoStartMode)
        {
            iv_auto_start_mode.setImageResource(R.drawable.ic_game_auto_start_mode);
        } else
        {
            iv_auto_start_mode.setImageResource(R.drawable.ic_game_manual_start_mode);
        }
    }

    /**
     * 是否有自动开始的功能
     *
     * @param hasAutoStartGame
     */
    public void setHasAutoStartMode(boolean hasAutoStartGame)
    {
        SDViewUtil.setVisibleOrGone(iv_auto_start_mode, hasAutoStartGame);
    }

    /**
     * 底部金币图标
     *
     * @param imageRes
     */
    public void setIvBotCoinsRes(int imageRes)
    {
        iv_bot_coins.setImageResource(imageRes);
    }

    @Override
    public void onClick(View v)
    {
        if (v == ll_recharge)
        {
            mCallback.onClickRecharge();
        } else if (v == iv_game_log)
        {
            mCallback.onClickGameLog();
        } else if (v == iv_auto_start_mode)
        {
            mCallback.onClickChangeAutoStartMode();
        }
    }

    public interface GameBottomViewCallback
    {
        /**
         * 点击充值
         */
        void onClickRecharge();

        /**
         * 点击游戏历史记录
         */
        void onClickGameLog();

        /**
         * 点击自动开始游戏按钮
         */
        void onClickChangeAutoStartMode();
    }
}
