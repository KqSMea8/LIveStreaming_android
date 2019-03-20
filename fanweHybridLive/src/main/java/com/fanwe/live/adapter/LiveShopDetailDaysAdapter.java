package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.model.PayShouhuModel;
import com.fanwe.live.model.ShopModel;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:58:35 类说明
 */
public class LiveShopDetailDaysAdapter extends SDSimpleAdapter<ShopModel.MountRuleListBean.RulesBean>
{

    private OnDaysViewClickListener mListener;

    public LiveShopDetailDaysAdapter(List<ShopModel.MountRuleListBean.RulesBean> listModel, Activity activity)
    {
        super(listModel, activity);
    }


    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_recharge_shouhu_days;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, ShopModel.MountRuleListBean.RulesBean model)
    {
            bindDaysData(convertView, model);
    }
    private void bindDaysData(View convertView, final ShopModel.MountRuleListBean.RulesBean model)
    {
        RelativeLayout rl_days= (RelativeLayout) convertView.findViewById(R.id.rl_days);
        TextView tv_days = (TextView) convertView.findViewById(R.id.tv_days);
        ImageView iv_corner = (ImageView) convertView.findViewById(R.id.iv_corner);
        TextView tv_plugin_price = (TextView) convertView.findViewById(R.id.tv_plugin_price);
        tv_plugin_price.setText(model.getDiamonds());
        SDViewBinder.setTextView(tv_days, model.getDay_length()+"天");
        if(model.ispk()){
            rl_days.setBackgroundResource(R.drawable.layer_shouhu_type_checked);
            iv_corner.setImageResource(R.drawable.corner_checked);
            tv_days.setTextColor(getActivity().getResources().getColor(R.color.edit_green));
        }else{
            rl_days.setBackgroundResource(R.drawable.layer_shouhu_type_normal);
            iv_corner.setImageResource(R.drawable.corner_normal);
            tv_days.setTextColor(getActivity().getResources().getColor(R.color.gray));
        }
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onClickDaysView(model);
            }
        });
    }


    public void setOnDaysViewClickListener(OnDaysViewClickListener listener)
    {
        this.mListener = listener;
    }

    public interface OnDaysViewClickListener
    {
        void onClickDaysView(ShopModel.MountRuleListBean.RulesBean model);
    }
}
