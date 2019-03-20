package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.PayItemModel;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:58:06 类说明
 */
public class LiveRechargePayDialogAdapter extends SDSimpleAdapter<PayItemModel>
{
    public LiveRechargePayDialogAdapter(List<PayItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_recharge_pay_dialog;
    }

    @Override
    public void bindData(final int position, final View convertView, final ViewGroup parent, final PayItemModel model)
    {
        final TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);
        SDViewBinder.setTextView(tv_name, model.getName());
        if (model.isSelected())
        {
            SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_white_corner);
        } else
        {
            SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_white_blur_corner);
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                notifyItemClickCallback(position, model, convertView);
            }
        });
    }

}
