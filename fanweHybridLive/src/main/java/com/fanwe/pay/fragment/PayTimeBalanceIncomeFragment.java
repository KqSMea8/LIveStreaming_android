package com.fanwe.pay.fragment;

/**
 * Created by yhz on 2016/11/30. 按时付费收费记录
 */

public class PayTimeBalanceIncomeFragment extends PayBalanceBaseFragment
{
    @Override
    protected void init()
    {
        this.type = 1;
        this.live_pay_type = 0;
        super.init();
        adapter.setText("贡献秀豆:");
    }
}
