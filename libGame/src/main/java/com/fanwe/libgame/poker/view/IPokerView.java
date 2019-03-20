package com.fanwe.libgame.poker.view;

import com.fanwe.libgame.poker.model.PokerData;

/**
 * 扑克牌view
 */
public interface IPokerView
{
    /**
     * 显示牌的正面
     */
    void showPokerFront();

    /**
     * 显示牌的背面
     */
    void showPokerBack();

    /**
     * 设置牌的数据
     *
     * @param data {@link PokerData}
     */
    void setPokerData(PokerData data);

    /**
     * 牌的正面是否处于显示状态
     *
     * @return
     */
    boolean isPokerFrontShowing();
}
