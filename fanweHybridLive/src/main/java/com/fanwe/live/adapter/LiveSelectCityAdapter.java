package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.utils.ViewHolder;
import com.fanwe.live.R;
import com.fanwe.live.model.SelectCityModel;

import java.util.List;

/**
 * Created by HSH on 2016/7/25.
 */
public class LiveSelectCityAdapter extends SDSimpleAdapter<SelectCityModel>
{

    public LiveSelectCityAdapter(List<SelectCityModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(SDSelectManager.Mode.SINGLE_MUST_ONE_SELECTED);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_live_select_city;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, SelectCityModel model)
    {
        TextView tv_city_selected = ViewHolder.get(R.id.tv_city_selected, convertView);
        TextView tv_number = ViewHolder.get(R.id.tv_number, convertView);
        final ImageView iv_selected = ViewHolder.get(R.id.iv_selected, convertView);

        SDViewBinder.setTextView(tv_city_selected, model.getCity());
        SDViewBinder.setTextView(tv_number, model.getNumber());

        if (model.isSelected())
        {
            SDViewUtil.setVisible(iv_selected);
        } else
        {
            SDViewUtil.setInvisible(iv_selected);
        }
        convertView.setOnClickListener(this);
    }
}
