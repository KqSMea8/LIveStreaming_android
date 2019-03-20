package com.fanwe.baimei.fragment;


import com.fanwe.games.constant.GameParamsConstant;

/**
 * Created by yhz on 2017/5/24. 猜大小  排行榜
 */

public class BMGameRankDiceFragment extends BMGameRankCGDBaseFragment
{
    @Override
    protected void init()
    {
        this.mGameID = GameParamsConstant.GAME_ID_4;
        super.init();
    }
}
