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
import com.fanwe.live.model.App_user_adminModel;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.LiveUtils;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-31 下午2:15:21 类说明
 */
public class LiveAdminAdapter extends SDSimpleAdapter<App_user_adminModel>
{

    public LiveAdminAdapter(List<App_user_adminModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_user_admin;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, final App_user_adminModel model)
    {
        ImageView iv_head_image = get(R.id.iv_head_image, convertView);
        TextView tv_nick_name = get(R.id.tv_nick_name, convertView);
        ImageView iv_sex = get(R.id.iv_sex, convertView);
        ImageView iv_rank = get(R.id.iv_rank, convertView);

        GlideUtil.loadHeadImage(model.getHead_image()).into(iv_head_image);
        SDViewBinder.setTextView(tv_nick_name, model.getNick_name());

        if (model.getSex() == 2)
        {
            iv_sex.setImageResource(R.drawable.ic_global_female);
        } else if (model.getSex() == 1)
        {
            iv_sex.setImageResource(R.drawable.ic_global_male);
        } else
        {
            SDViewUtil.setGone(iv_sex);
        }
        iv_rank.setImageResource(LiveUtils.getLevelImageResId(model.getUser_level()));
    }

}
