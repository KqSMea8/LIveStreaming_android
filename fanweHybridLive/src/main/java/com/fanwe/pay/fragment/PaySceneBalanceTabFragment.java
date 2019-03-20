package com.fanwe.pay.fragment;

import com.fanwe.live.R;

/**
 * Created by Administrator on 2017/1/9.
 */

public class PaySceneBalanceTabFragment extends PayBalanceTabBaseFragment
{
    protected void click0()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PaySceneBalanceExpendFragment.class);
    }

    protected void click1()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PaySceneBalanceIncomeFragment.class);
    }
}
