package com.fanwe.live.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 游戏币兑换比例
 */
public class App_gameExchangeRateActModel extends BaseActModel
{
    private float exchange_rate;

    public float getExchange_rate() {
        return exchange_rate;
    }

    public void setExchange_rate(float exchange_rate) {
        this.exchange_rate = exchange_rate;
    }
}
