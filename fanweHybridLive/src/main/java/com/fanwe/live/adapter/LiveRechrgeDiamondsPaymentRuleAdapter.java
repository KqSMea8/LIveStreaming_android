package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.RuleItemModel;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LiveRechrgeDiamondsPaymentRuleAdapter extends SDSimpleAdapter<RuleItemModel> {
    private int is_payed;
    public LiveRechrgeDiamondsPaymentRuleAdapter(List<RuleItemModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_live_recharge_diamonds_payment_rule;
    }
    public void setIs_payed(int is_payed){
        this.is_payed=is_payed;
    }
    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final RuleItemModel model) {
        TextView tv_diamonds = get(R.id.tv_diamonds, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        TextView tv_money = get(R.id.tv_money, convertView);
        TextView tv_first_recharge = get(R.id.tv_first_recharge, convertView);
        String desc="";
        if(null!=model.getGift_diamonds()){
            if(!"0".equals(model.getGift_diamonds())){
                desc="赠送"+model.getGift_diamonds();
            }
        }
        //没有享受过首充
        if(0==model.getIs_payed()){
            if(null!=model.getFirst_gift_diamonds()){
                if(desc.trim().length()>0){
                    desc+=",";
                }
                desc=desc+"首充赠送"+model.getFirst_gift_diamonds();
            }
        }
        if(desc.trim().length()>0){
            desc="("+desc+")";
        }
        SDViewBinder.setTextView(tv_first_recharge,desc);
        SDViewBinder.setTextView(tv_diamonds, String.valueOf(model.getDiamonds()));
        SDViewBinder.setTextView(tv_name, model.getName());
        SDViewBinder.setTextView(tv_money, model.getMoneyFormat());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClickCallback(position, model, v);
            }
        });
    }
}
