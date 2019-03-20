package com.fanwe.live.activity;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.live.model.App_ProfitBindingActModel;

/**
 * Created by yhz on 2017/6/22.
 */

public abstract class LiveMobileBindBaseActivity extends BaseTitleActivity
{
    protected abstract void requestMobileBind(String code);

    protected abstract void requestOnSuccess(App_ProfitBindingActModel actModel);
}
