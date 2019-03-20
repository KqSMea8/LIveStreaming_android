package com.fanwe.pay.appview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.pay.model.App_live_live_pay_deductActModel;

/**
 * Created by Administrator on 2016/11/21.
 */

public class PayUserBalanceContentView extends BaseAppView
{
    private TextView tv_balance;
    private TextView tv_info;
    private App_live_live_pay_deductActModel data;

    public App_live_live_pay_deductActModel getData()
    {
        return data;
    }

    public PayUserBalanceContentView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public PayUserBalanceContentView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public PayUserBalanceContentView(Context context)
    {
        super(context);
        init();
    }

    protected void init()
    {
        setContentView(R.layout.dialog_pay_user_balance_content);
        register();
    }

    private void register()
    {
        tv_balance = find(R.id.tv_balance);
        tv_info = find(R.id.tv_info);
    }

    public void bindData(App_live_live_pay_deductActModel data)
    {
        this.data = data;
        if (data != null)
        {
            int diamonds = data.getDiamonds();
            SDViewBinder.setTextView(tv_balance, Integer.toString(diamonds));
            SDViewBinder.setTextView(tv_info, data.getMsg());
        }
    }
}
