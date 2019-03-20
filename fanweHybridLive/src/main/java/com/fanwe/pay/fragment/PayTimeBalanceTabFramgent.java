package com.fanwe.pay.fragment;

import com.fanwe.live.R;

/**
 * Created by Administrator on 2017/1/9.
 */

public class PayTimeBalanceTabFramgent extends PayBalanceTabBaseFragment
{
    protected void click0()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PayTimeBalanceExpendFragemt.class);
    }

    protected void click1()
    {
        getSDFragmentManager().toggle(R.id.ll_content, null, PayTimeBalanceIncomeFragment.class);
    }
}
