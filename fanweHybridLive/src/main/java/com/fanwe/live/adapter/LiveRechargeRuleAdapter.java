package com.fanwe.live.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.RuleItemModel;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:58:35 类说明
 */
public class LiveRechargeRuleAdapter extends SDSimpleAdapter<RuleItemModel>
{

    private OnRuleViewClickListener mListener;

    public LiveRechargeRuleAdapter(List<RuleItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getItemViewType(int position)
    {
        if (getItem(position) != null && getItem(position).is_other_money())
        {
            return 1;
        } else
        {
            return 0;
        }

    }

    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        int layout = 0;
        switch (getItemViewType(position))
        {
            case 0:
                layout = R.layout.item_live_recharge_rule;
                break;
            case 1:
                layout = R.layout.item_live_recharge_rule_other;
                break;

        }
        return layout;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, RuleItemModel model)
    {
        if (getItemViewType(position) == 1)
        {
            bindRuleOtherData(convertView);
        } else
        {
            bindRuleData(convertView, model);
        }
    }

    private void bindRuleData(View convertView, final RuleItemModel model)
    {

        TextView tv_diamonds = ViewHolder.get(R.id.tv_diamonds, convertView);
        TextView tv_money = ViewHolder.get(R.id.tv_money, convertView);
        TextView tv_gift_coins_dec = ViewHolder.get(R.id.tv_gift_coins_dec, convertView);
        SDViewBinder.setTextView(tv_diamonds, model.getDiamonds() + "");
        SDViewBinder.setTextView(tv_money, model.getMoneyFormat());

        if (TextUtils.isEmpty(model.getGift_coins_des()))
        {
            SDViewUtil.setGone(tv_gift_coins_dec);
        } else
        {
            SDViewUtil.setVisible(tv_gift_coins_dec);
            SDViewBinder.setTextView(tv_gift_coins_dec, model.getGift_coins_des());
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                mListener.onClickRuleView(model);
            }
        });
    }

    private void bindRuleOtherData(View convertView)
    {
        convertView.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                mListener.onClickOtherView();
            }
        });
    }


    public void setOnRuleViewClickListener(OnRuleViewClickListener listener)
    {
        this.mListener = listener;
    }

    public interface OnRuleViewClickListener
    {
        void onClickOtherView();

        void onClickRuleView(RuleItemModel model);
    }
}
