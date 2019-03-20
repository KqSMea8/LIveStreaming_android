package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.DistributionItemModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by yhz on 2017/1/3.
 */

public class LiveDistributionAdatper extends SDSimpleAdapter<DistributionItemModel>
{
    public LiveDistributionAdatper(List<DistributionItemModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_distridution;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, DistributionItemModel model)
    {
        ImageView civ_v_icon = (ImageView) convertView.findViewById(R.id.civ_v_icon);
        ImageView iv_global_male = (ImageView) convertView.findViewById(R.id.iv_global_male);
        ImageView iv_rank = (ImageView) convertView.findViewById(R.id.iv_rank);

        SDViewUtil.setGone(civ_v_icon);
        SDViewUtil.setGone(iv_global_male);
        SDViewUtil.setGone(iv_rank);

        ImageView civ_head_image = (ImageView) convertView.findViewById(R.id.civ_head_image);
        TextView tv_nick_name = (TextView) convertView.findViewById(R.id.tv_nick_name);
        TextView tv_name_text = (TextView) convertView.findViewById(R.id.tv_name_text);
        TextView tv_ticket = (TextView) convertView.findViewById(R.id.tv_ticket);

        GlideUtil.load(model.getHead_image()).into(civ_head_image);
        SDViewBinder.setTextView(tv_nick_name, model.getUser_name());
        SDViewBinder.setTextView(tv_name_text, AppRuntimeWorker.getTicketName() + ":");
        SDViewBinder.setTextView(tv_ticket, model.getTicket());
    }
}
