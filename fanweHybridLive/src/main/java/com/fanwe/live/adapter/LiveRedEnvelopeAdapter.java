package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.App_user_red_envelopeModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-28 下午3:23:32 类说明
 */
public class LiveRedEnvelopeAdapter extends SDSimpleAdapter<App_user_red_envelopeModel>
{

    public LiveRedEnvelopeAdapter(List<App_user_red_envelopeModel> listModel, Activity activity)
    {
        super(listModel, activity);
        // TODO Auto-generated constructor stub
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        // TODO Auto-generated method stub
        return R.layout.item_live_red_envelope;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, App_user_red_envelopeModel model)
    {
        ImageView iv_head = get(R.id.iv_head, convertView);
        TextView tv_nick_name = get(R.id.tv_nick_name, convertView);
        tv_nick_name.setMaxWidth(SDViewUtil.dp2px(135));
        TextView tv_diamonds = get(R.id.tv_diamonds, convertView);
        ImageView iv_global_male = get(R.id.iv_global_male, convertView);
        LinearLayout ll_best = get(R.id.ll_best, convertView);

        ImageView iv_rank = get(R.id.iv_rank, convertView);
        iv_rank.setVisibility(View.GONE);

        GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head);
        SDViewBinder.setTextView(tv_nick_name, model.getNick_name());
        SDViewBinder.setTextView(tv_diamonds, model.getDiamonds() + "");

        SDViewUtil.setVisible(iv_global_male);
        if (model.getSex() == 2) {

            iv_global_male.setImageResource(R.drawable.ic_global_female);
        } else if (model.getSex() == 1) {
            iv_global_male.setImageResource(R.drawable.ic_global_male);
        } else {
            SDViewUtil.setGone(iv_global_male);
        }

        if (position > 0)
        {
            // 隐藏手气最佳
            SDViewUtil.setGone(ll_best);
        } else
        {
            SDViewUtil.setVisible(ll_best);
        }
    }

}
