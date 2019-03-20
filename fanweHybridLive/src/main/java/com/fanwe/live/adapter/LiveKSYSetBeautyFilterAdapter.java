package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveKSYBeautyFilterModel;

import java.util.List;

/**
 * Created by Administrator on 2017/2/7.
 */

public class LiveKSYSetBeautyFilterAdapter extends SDSimpleAdapter<LiveKSYBeautyFilterModel>
{
    public LiveKSYSetBeautyFilterAdapter(List<LiveKSYBeautyFilterModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_ksy_set_beauty_filter;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final LiveKSYBeautyFilterModel model)
    {
        TextView tv_name = get(R.id.tv_name, convertView);
        SDViewBinder.setTextView(tv_name, model.getName());

        if (model.isSelected())
        {
            tv_name.setTextColor(SDResourcesUtil.getColor(R.color.main_color));
            SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_transparent_stroke_main_color_corner);
        } else
        {
            tv_name.setTextColor(SDResourcesUtil.getColor(R.color.gray));
            SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_transparent_stroke_gray_corner_5dp);
        }
        convertView.setOnClickListener(this);
    }
}
