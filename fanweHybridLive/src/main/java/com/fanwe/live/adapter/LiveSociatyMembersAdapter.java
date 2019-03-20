package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * 家族成员adapter
 * Created by Administrator on 2016/9/28.
 */

public class LiveSociatyMembersAdapter extends SDSimpleAdapter<UserModel>
{
    private SDItemClickCallback<UserModel> clickDelListener;
    private UserModel dao = UserModelDao.query();

    public void setClickDelListener(SDItemClickCallback<UserModel> clickDelListener)
    {
        this.clickDelListener = clickDelListener;
    }

    public LiveSociatyMembersAdapter(List<UserModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_family_members;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final UserModel model)
    {
        ImageView iv_head_img = get(R.id.civ_head_image, convertView);
        ImageView civ_v_icon = get(R.id.civ_v_icon, convertView);
        ImageView iv_family = get(R.id.iv_family, convertView);
        TextView tv_nick_name = get(R.id.tv_nick_name, convertView);
        ImageView iv_global_male = get(R.id.iv_global_male, convertView);
        ImageView iv_rank = get(R.id.iv_rank, convertView);
        TextView tv_signature = get(R.id.tv_signature, convertView);
        TextView txv_del = get(R.id.txv_del, convertView);//踢出公会

        iv_family.setImageResource(R.drawable.ic_sociaty);
        SDViewBinder.setTextView(txv_del, "踢出公会");

        GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head_img);
        GlideUtil.load(model.getV_icon()).into(civ_v_icon);
        if (model.getSex() > 0)
        {
            SDViewUtil.setVisible(iv_global_male);
            iv_global_male.setImageResource(model.getSexResId());
        } else
        {
            SDViewUtil.setGone(iv_global_male);
        }
        iv_rank.setImageResource(model.getLevelImageResId());
        tv_nick_name.setText(model.getNick_name());
        tv_signature.setText(model.getSignature());
        if (dao.getSociety_chieftain() == 1)
        {
            if (model.getSociety_chieftain() == 1)//是否公会长；0：否、1：是
            {
                txv_del.setVisibility(View.GONE);
                iv_family.setVisibility(View.VISIBLE);
            } else
            {
                txv_del.setVisibility(View.VISIBLE);
                iv_family.setVisibility(View.GONE);
            }
        } else
        {
            txv_del.setVisibility(View.GONE);
            if (model.getSociety_chieftain() == 1)//是否公会长；0：否、1：是
                iv_family.setVisibility(View.VISIBLE);
            else
                iv_family.setVisibility(View.GONE);
        }

        /**
         * 用户详情
         */
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemClickCallback(position, model, view);
            }
        });

        /**
         * 踢出公会
         */
        txv_del.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (clickDelListener != null)
                {
                    clickDelListener.onItemClick(position, model, view);
                }
            }
        });
    }
}
