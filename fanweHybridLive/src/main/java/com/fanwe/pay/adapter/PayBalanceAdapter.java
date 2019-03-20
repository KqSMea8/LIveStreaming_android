package com.fanwe.pay.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.pay.model.LivePayContDataModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class PayBalanceAdapter extends SDSimpleAdapter<LivePayContDataModel>
{
    private String type_text = "消费秀豆";

    public void setText(String text)
    {
        this.type_text = text;
    }

    public PayBalanceAdapter(List<LivePayContDataModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_pay_balance;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, LivePayContDataModel model)
    {
        TextView tv_type_text = get(R.id.tv_type_text, convertView);

        ImageView civ_head_image = get(R.id.civ_head_image, convertView);
        ImageView civ_v_icon = get(R.id.civ_v_icon, convertView);
        TextView tv_nick_name = get(R.id.tv_nick_name, convertView);
        ImageView iv_global_male = get(R.id.iv_global_male, convertView);
        ImageView iv_rank = get(R.id.iv_rank, convertView);
        TextView tv_total_diamonds = get(R.id.tv_total_diamonds, convertView);
        LinearLayout ll_total_time= get(R.id.ll_total_time, convertView);
        TextView tv_total_time_format = get(R.id.tv_total_time_format, convertView);
        TextView tv_start_time = get(R.id.tv_start_time, convertView);

        GlideUtil.loadHeadImage(model.getHead_image()).into(civ_head_image);
        if (TextUtils.isEmpty(model.getV_icon()))
        {
            SDViewUtil.setGone(civ_v_icon);
        } else
        {
            SDViewUtil.setVisible(civ_v_icon);
            GlideUtil.loadHeadImage(model.getV_icon()).into(civ_v_icon);
        }

        if (model.getSex() > 0)
        {
            SDViewUtil.setVisible(iv_global_male);
            iv_global_male.setImageResource(model.getSexResId());
        } else
        {
            SDViewUtil.setGone(iv_global_male);
        }

        //观察时长小于0,隐藏布局
        if (model.getTotal_time() <= 0)
        {
            SDViewUtil.setGone(ll_total_time);
        } else
        {
            SDViewBinder.setTextView(tv_total_time_format, model.getTotal_time_format());
        }

        iv_rank.setImageResource(model.getLevelImageResId());
        SDViewBinder.setTextView(tv_nick_name, model.getNick_name());
        SDViewBinder.setTextView(tv_total_diamonds, model.getTotal_diamonds());
        SDViewBinder.setTextView(tv_start_time, model.getStart_time());

        tv_type_text.setText(type_text);
    }
}
