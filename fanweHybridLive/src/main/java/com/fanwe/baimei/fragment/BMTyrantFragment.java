package com.fanwe.baimei.fragment;

import com.fanwe.live.R;

/**
 * Created by yhz on 2017/5/24. 土豪榜
 */

public class BMTyrantFragment extends BMPTBaseFragment
{
    @Override
    void clickTabDay()
    {
        getSDFragmentManager().toggle(R.id.fl_content_ptbase, null, BMTyrantDayFragment.class);
    }

    @Override
    void clickTabWeek() {
        getSDFragmentManager().toggle(R.id.fl_content_ptbase, null, BMTyrantWeekFragment.class);
    }

    @Override
    void clickTabMonth()
    {
        getSDFragmentManager().toggle(R.id.fl_content_ptbase, null, BMTyrantMonthFragment.class);
    }

    @Override
    void clickTabTotal()
    {
        getSDFragmentManager().toggle(R.id.fl_content_ptbase, null, BMTyrantAllFragment.class);
    }
}
