package com.fanwe.games;

import com.fanwe.games.constant.GameType;
import com.fanwe.games.model.GameBetDataModel;
import com.fanwe.games.model.Games_betActModel;
import com.fanwe.games.model.Games_logActModel;
import com.fanwe.games.model.custommsg.GameMsgModel;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.live.business.BaseBusiness;
import com.fanwe.live.common.CommonInterface;

import java.util.List;

/**
 * 投注类游戏业务类
 */
public abstract class BetGameBusiness extends BaseBusiness
{
    private GameBusiness mGameBusiness;

    public BetGameBusiness(GameBusiness gameBusiness)
    {
        this.mGameBusiness = gameBusiness;
    }

    private GameBusiness getGameBusiness()
    {
        return mGameBusiness;
    }

    /**
     * 处理游戏消息(需要外部调用)
     *
     * @param msg
     * @param isPush true-推送消息的数据，false-请求接口返回的数据
     */
    public void onGameMsg(GameMsgModel msg, boolean isPush)
    {
        switch (msg.getGame_id())
        {
            //以游戏id作为区分处理消息
            case GameType.GOLD_FLOWER:
            case GameType.BULL:
            case GameType.DICE:
                onBetGameMsg(msg, isPush);
                break;
            default:
                break;
        }
    }

    protected void onBetGameMsg(GameMsgModel msg, boolean isPush)
    {
        if (isPush)
        {
            //推送的数据
            switch (msg.getGame_action())
            {
                case 1:
                    onGameBegin(msg, isPush);
                    break;
                case 2:
                    onGameBet(msg);
                    break;
                case 4:
                    onGameSettlement(msg, isPush);
                    break;
                default:
                    break;
            }
        } else
        {
            //接口的数据
            if (msg.getGame_status() == 1)
            {
                onGameBegin(msg, isPush);
            } else
            {
                onGameSettlement(msg, isPush);
            }
        }
        getBetGameBusinessCallback().onBsGameBetUpdateBetCoinsOption(msg.getBet_option());
    }

    /**
     * 游戏开始
     *
     * @param msg
     * @param isPush
     */
    protected void onGameBegin(GameMsgModel msg, boolean isPush)
    {
        getGameBusiness().cancelRequestGameInfoDelay();
        getGameBusiness().setInGameRound(true);

        getBetGameBusinessCallback().onBsGameBetMsgBegin(msg, isPush);
        if (!isPush)
        {
            //如果是接口过来的数据，要更新投注信息
            updateTotalBet(msg.getGame_data().getBet());
            updateUserBet(msg.getGame_data().getUser_bet());
        }
    }

    /**
     * 游戏下注
     *
     * @param msg
     */
    protected void onGameBet(GameMsgModel msg)
    {
        //更新总投注金额
        updateTotalBet(msg.getGame_data().getBet());
    }

    /**
     * 游戏结算
     *
     * @param msg
     * @param isPush
     */
    protected void onGameSettlement(GameMsgModel msg, boolean isPush)
    {
        getGameBusiness().cancelRequestGameInfoDelay();
        getGameBusiness().setInGameRound(false);
        getGameBusiness().requestGameIncome();

        updateTotalBet(msg.getGame_data().getBet());
        updateUserBet(msg.getGame_data().getUser_bet());
    }

    /**
     * 更新总投注信息
     */
    protected final void updateTotalBet(List<Integer> listData)
    {
        getBetGameBusinessCallback().onBsGameBetUpdateTotalBet(listData);
    }

    /**
     * 更新用户投注信息
     *
     * @param listData
     */
    protected final void updateUserBet(List<Integer> listData)
    {
        getBetGameBusinessCallback().onBsGameBetUpdateUserBet(listData);
    }

    /**
     * 玩家下注
     *
     * @param betPosition 下注位置[0-2]
     * @param betCoin     下注金额
     */
    public void requestDoBet(final int betPosition, final long betCoin)
    {
        CommonInterface.requestDoBet(getGameBusiness().getGameLogId(), betPosition + 1, betCoin, new AppRequestCallback<Games_betActModel>()
        {
            @Override
            public String getCancelTag()
            {
                return getHttpCancelTag();
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.isOk())
                {
                    GameBetDataModel data = actModel.getData();

                    getGameBusiness().updateAccount(data.getAccount_diamonds(), data.getCoin());

                    updateTotalBet(data.getGame_data().getBet()); //更新总投注
                    updateUserBet(data.getGame_data().getUser_bet()); //更新用户投注

                    getBetGameBusinessCallback().onBsGameBetRequestDoBetSuccess(actModel, betPosition, betCoin);
                }
            }
        });
    }

    /**
     * 请求游戏记录接口
     */
    public void requestGameLog()
    {
        CommonInterface.requestGamesLog(getGameBusiness().getGameId(),
                getGameBusiness().getLiveActivity().getCreaterId(), new AppRequestCallback<Games_logActModel>()
                {
                    @Override
                    public String getCancelTag()
                    {
                        return getHttpCancelTag();
                    }

                    @Override
                    protected void onStart()
                    {
                        super.onStart();
                        showProgress("");
                    }

                    @Override
                    protected void onSuccess(SDResponse resp)
                    {
                        if (actModel.isOk())
                        {
                            getBetGameBusinessCallback().onBsGameBetRequestGameLogSuccess(actModel);
                        }
                    }

                    @Override
                    protected void onFinish(SDResponse resp)
                    {
                        super.onFinish(resp);
                        hideProgress();
                    }
                });
    }

    @Override
    protected BaseBusinessCallback getBaseBusinessCallback()
    {
        return getBetGameBusinessCallback();
    }

    protected abstract BetGameBusinessCallback getBetGameBusinessCallback();

    public interface BetGameBusinessCallback extends BaseBusinessCallback
    {
        /**
         * 投注游戏开始
         *
         * @param msg
         * @param isPush
         */
        void onBsGameBetMsgBegin(GameMsgModel msg, boolean isPush);

        /**
         * 更新总投注信息
         *
         * @param listData
         */
        void onBsGameBetUpdateTotalBet(List<Integer> listData);

        /**
         * 更新用户投注信息
         *
         * @param listData
         */
        void onBsGameBetUpdateUserBet(List<Integer> listData);

        /**
         * 更新可投注金额列表
         *
         * @param listData
         */
        void onBsGameBetUpdateBetCoinsOption(List<Integer> listData);

        /**
         * 请求游戏记录接口成功回调
         *
         * @param actModel
         */
        void onBsGameBetRequestGameLogSuccess(Games_logActModel actModel);

        /**
         * 请求投注成功
         *
         * @param actModel
         * @param betPosition 投注位置
         * @param betCoin     投注金额
         */
        void onBsGameBetRequestDoBetSuccess(Games_betActModel actModel, int betPosition, long betCoin);
    }
}
