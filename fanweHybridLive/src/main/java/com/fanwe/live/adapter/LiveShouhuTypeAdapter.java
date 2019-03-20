package com.fanwe.live.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.PayShouhuModel;
import com.fanwe.live.model.RuleItemModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-7 上午11:58:35 类说明
 */
public class LiveShouhuTypeAdapter extends SDSimpleAdapter<PayShouhuModel.GuardRuleListBean>
{

    private OnTypeViewClickListener mListener;

    public LiveShouhuTypeAdapter(List<PayShouhuModel.GuardRuleListBean> listModel, Activity activity)
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
        return R.layout.item_live_recharge_shouhu_type;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, PayShouhuModel.GuardRuleListBean model)
    {
            bindTypeData(convertView, model);
    }

    private void bindTypeData(View convertView, final PayShouhuModel.GuardRuleListBean model)
    {

        RelativeLayout rl_type= (RelativeLayout) convertView.findViewById(R.id.rl_type);
        ImageView iv_type_icon = (ImageView) convertView.findViewById(R.id.iv_type_icon);
        ImageView iv_corner = (ImageView) convertView.findViewById(R.id.iv_corner);
        TextView tv_type_name = (TextView) convertView.findViewById(R.id.tv_type_name);
        SDViewBinder.setTextView(tv_type_name, model.getName());
        GlideUtil.load(model.getIcon()).into(iv_type_icon);
        if(model.isChecked()){
            rl_type.setBackgroundResource(R.drawable.layer_shouhu_type_checked);
            iv_corner.setImageResource(R.drawable.corner_checked);
            tv_type_name.setTextColor(getActivity().getResources().getColor(R.color.edit_green));
        }else{
            rl_type.setBackgroundResource(R.drawable.layer_shouhu_type_normal);
            iv_corner.setImageResource(R.drawable.corner_normal);
            tv_type_name.setTextColor(getActivity().getResources().getColor(R.color.gray));
        }
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mListener.onClickTypeView(model);
            }
        });
    }


    public void setOnTypeViewClickListener(OnTypeViewClickListener listener)
    {
        this.mListener = listener;
    }

    public interface OnTypeViewClickListener
    {

        void onClickTypeView(PayShouhuModel.GuardRuleListBean model);
    }
}
