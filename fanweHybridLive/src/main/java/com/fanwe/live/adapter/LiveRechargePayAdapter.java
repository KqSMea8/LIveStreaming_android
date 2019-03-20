package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.PayItemModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:58:06 类说明
 */
public class LiveRechargePayAdapter extends SDSimpleAdapter<PayItemModel>
{
    public LiveRechargePayAdapter(List<PayItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_recharge_pay;
    }

    @Override
    public void bindData(final int position, final View convertView, final ViewGroup parent, PayItemModel model)
    {
        ImageView iv_logo = ViewHolder.get(R.id.iv_logo, convertView);
        TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
        GlideUtil.load(model.getLogo()).into(iv_logo);
        SDViewBinder.setTextView(tv_name, model.getName());
    }


}
