package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.RuleItemModel;

import java.util.List;

/**
 * vip商品列表适配器
 * Created by shibx on 2017/0/16.
 */
public class LiveRechrgeVipPaymentRuleAdapter extends SDSimpleAdapter<RuleItemModel>
{
    public LiveRechrgeVipPaymentRuleAdapter(List<RuleItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_recharge_vip_payment_rule;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final RuleItemModel model)
    {
        TextView tv_vip_day_num = get(R.id.tv_vip_day_num, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        TextView tv_money = get(R.id.tv_money, convertView);

        SDViewBinder.setTextView(tv_vip_day_num, model.getDay_num());
        SDViewBinder.setTextView(tv_name, model.getName());
        SDViewBinder.setTextView(tv_money, model.getMoneyFormat());

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, v);
            }
        });
    }
}
