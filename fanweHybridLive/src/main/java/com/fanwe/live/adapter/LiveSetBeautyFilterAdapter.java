package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveBeautyFilterModel;

import java.util.List;

/**
 * 美颜滤镜适配器
 */
public class LiveSetBeautyFilterAdapter extends SDSimpleAdapter<LiveBeautyFilterModel>
{
    public LiveSetBeautyFilterAdapter(List<LiveBeautyFilterModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_set_beauty_filter;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final LiveBeautyFilterModel model)
    {
        TextView tv_name = get(R.id.tv_name, convertView);

        SDViewBinder.setTextView(tv_name, model.getName());
        if (model.isSelected())
        {
            SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_transparent_stroke_main_color_corner);
            SDViewUtil.setTextViewColorResId(tv_name, R.color.main_color);
        } else
        {
            SDViewUtil.setBackgroundResource(tv_name, R.drawable.layer_transparent_stroke_gray_corner_5dp);
            SDViewUtil.setTextViewColorResId(tv_name, R.color.gray);
        }
        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                getSelectManager().setSelected(position, true);
                notifyItemClickCallback(position, model, v);
            }
        });
    }
}
