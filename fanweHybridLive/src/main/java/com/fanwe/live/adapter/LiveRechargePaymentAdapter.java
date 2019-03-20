package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.PayItemModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6.
 */
public class LiveRechargePaymentAdapter extends SDSimpleAdapter<PayItemModel>
{
    public LiveRechargePaymentAdapter(List<PayItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_recharge_payment;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final PayItemModel model)
    {
        ImageView iv_image = get(R.id.iv_image, convertView);
        TextView tv_name = get(R.id.tv_name, convertView);
        ImageView iv_selected = get(R.id.iv_selected, convertView);

        GlideUtil.load(model.getLogo()).into(iv_image);
        SDViewBinder.setTextView(tv_name, model.getName());
        if (model.isSelected())
        {
            iv_selected.setImageResource(R.drawable.ic_me_following);
        } else
        {
            iv_selected.setImageResource(R.drawable.ic_live_pop_choose_off);
        }

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
