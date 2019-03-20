package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.ShouhuListModel;
import com.fanwe.live.model.UserShouhuModel;
import com.fanwe.live.utils.GlideUtil;
import java.util.List;

/**
 * Created by yhz on 2017/1/3.
 */

public class LiveShouhuAdatper extends SDSimpleAdapter<ShouhuListModel.ListBean>
{
    public LiveShouhuAdatper(List<ShouhuListModel.ListBean> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_shouhu;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent,final ShouhuListModel.ListBean model)
    {
        TextView tv_num = (TextView) convertView.findViewById(R.id.tv_num);
        TextView tv_status = (TextView) convertView.findViewById(R.id.tv_status);
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView tv_ticket = (TextView) convertView.findViewById(R.id.tv_ticket);
        TextView tv_shouhu_days = (TextView) convertView.findViewById(R.id.tv_shouhu_days);
        ImageView civ_head_image = (ImageView) convertView.findViewById(R.id.civ_head_image);
        ImageView civ_head_type = (ImageView) convertView.findViewById(R.id.civ_v_icon);
        ImageView star_level = (ImageView) convertView.findViewById(R.id.iv_shouhu_level);
        GlideUtil.load(model.getHead_image()).into(civ_head_image);
        GlideUtil.load(model.getIcon()).into(civ_head_type);
        GlideUtil.load(model.getLevel_icon()).into(star_level);
        if(model.getIn_room()==1){
            tv_status.setText("在座");
            tv_status.setTextColor(getActivity().getResources().getColor(R.color.shou_state_online));
        }else{
            tv_status.setText("离开");
            tv_status.setTextColor(getActivity().getResources().getColor(R.color.shou_state_away));
        }
        SDViewBinder.setTextView(tv_name, model.getNick_name());
        SDViewBinder.setTextView(tv_num, (position+1)+"");
        SDViewBinder.setTextView(tv_ticket, model.getTicket()+"票");
        SDViewBinder.setTextView(tv_shouhu_days, "已守护:"+model.getDay()+"天");
        convertView.setOnClickListener(this);
    }
}
