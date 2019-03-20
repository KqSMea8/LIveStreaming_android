package com.fanwe.libgame.poker.view;

import com.fanwe.libgame.poker.model.PokerGroupResultData;

/**
 * Created by Administrator on 2017/6/10.
 */

public interface IPokerGroupView
{
    /**
     * 获得牌的张数
     *
     * @return
     */
    int getPokerCount();

    /**
     * 获得第几张牌
     *
     * @param position
     * @return
     */
    PokerView getPokerView(int position);

    /**
     * 设置牌面信息
     *
     * @param data
     */
    void setResultData(PokerGroupResultData data);

    /**
     * 隐藏所有牌
     */
    void hideAllPoker();

    /**
     * 显示所有牌的正面
     */
    void showAllPokerFront();

    /**
     * 显示所有牌的背面
     */
    void showAllPokerBack();

}
