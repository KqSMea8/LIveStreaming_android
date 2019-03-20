package com.fanwe.live.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.App_user_homeActModel;
import com.fanwe.live.model.ItemApp_user_reviewModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-6-13 下午5:34:09 类说明
 */
public class LiveUserHomeRightAdapter extends SDSimpleAdapter<ItemApp_user_reviewModel>
{
    private UserModel user;

    public LiveUserHomeRightAdapter(List<ItemApp_user_reviewModel> listModel, Activity activity, App_user_homeActModel app_user_homeActModel)
    {
        super(listModel, activity);
        if (app_user_homeActModel != null)
        {
            user = app_user_homeActModel.getUser();
        }
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_frag_user_home_right_ios;
    }

    @Override
    public void bindData(int position, View convertView, ViewGroup parent, ItemApp_user_reviewModel model)
    {
        bindIOS(position, convertView, parent, model);
    }

    private void bindIOS(int position, View convertView, ViewGroup parent, ItemApp_user_reviewModel model)
    {
        TextView tv_title = ViewHolder.get(R.id.tv_title, convertView);
        TextView tv_begin_time_format = ViewHolder.get(R.id.tv_begin_time_format, convertView);
        TextView tv_watch_number_format = ViewHolder.get(R.id.tv_watch_number_format, convertView);

        SDViewBinder.setTextView(tv_title, model.getTitle());
        SDViewBinder.setTextView(tv_begin_time_format, model.getBegin_time_format());
        SDViewBinder.setTextView(tv_watch_number_format, model.getWatch_number_format());
    }

    private void bindAndroid(int position, View convertView, ViewGroup parent, ItemApp_user_reviewModel model)
    {
        View view_color = ViewHolder.get(R.id.view_color, convertView);
        TextView tv_title = ViewHolder.get(R.id.tv_title, convertView);
        TextView tv_begin_time_format = ViewHolder.get(R.id.tv_begin_time_format, convertView);
        TextView tv_watch_number_format = ViewHolder.get(R.id.tv_watch_number_format, convertView);

        ImageView iv_head = ViewHolder.get(R.id.iv_head, convertView);
        ImageView iv_level = ViewHolder.get(R.id.iv_level, convertView);
        TextView tv_name = ViewHolder.get(R.id.tv_name, convertView);

        view_color.setBackgroundColor(model.getColor(position));
        SDViewBinder.setTextView(tv_title, model.getTitle());
        SDViewBinder.setTextView(tv_begin_time_format, model.getBegin_time_format());
        SDViewBinder.setTextView(tv_watch_number_format, model.getWatch_number_format());

        if (user != null)
        {
            SDViewBinder.setTextView(tv_name, user.getNick_name());
            GlideUtil.loadHeadImage(user.getHead_image()).into(iv_head);
            if (!TextUtils.isEmpty(user.getV_icon()))
            {
                GlideUtil.load(user.getV_icon()).into(iv_level);
            } else
            {
                SDViewUtil.setGone(iv_level);
            }
        }

    }
}
