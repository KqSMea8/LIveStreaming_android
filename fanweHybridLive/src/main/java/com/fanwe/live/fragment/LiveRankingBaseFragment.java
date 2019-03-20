package com.fanwe.live.fragment;

import com.fanwe.hybrid.fragment.BaseFragment;

/**
 * 排行榜baseFragment
 *
 * @author luodong
 * @date 2016-10-15
 */
public class LiveRankingBaseFragment extends BaseFragment
{
    protected String rankingType;

    public String getRankingType() {
        return rankingType;
    }
    /**
     * 设置选择的tab选项
     */
    public void setRankingType(String rankingType) {
        this.rankingType = rankingType;
    }
}
