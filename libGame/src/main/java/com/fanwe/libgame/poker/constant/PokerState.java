package com.fanwe.libgame.poker.constant;

/**
 * Created by Administrator on 2017/6/10.
 */

public interface PokerState
{
    /**
     * 准备完毕，可以开始开始游戏
     */
    int PREPARED = 0;
    /**
     * 下注中
     */
    int BET = 1;
    /**
     * 结算
     */
    int SETTLEMENT = 2;
}
