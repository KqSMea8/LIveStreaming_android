package com.fanwe.games.model;

import com.fanwe.hybrid.model.BaseActModel;

/**
 * 申请上庄列表
 * Created by shibx on 2017/02/24.
 */

public class App_banker_applyActModel extends BaseActModel
{
   private long coin;//扣除申请上庄之后的游戏币余额

    public long getCoin() {
        return coin;
    }

    public void setCoin(long coin) {
        this.coin = coin;
    }
}
