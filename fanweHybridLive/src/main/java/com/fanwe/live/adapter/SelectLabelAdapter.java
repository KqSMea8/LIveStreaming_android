package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.live.R;
import com.fanwe.live.model.SelectLabelModel;

import java.util.List;

/**
 * Created by shibx on 2016/7/15.
 */
public class SelectLabelAdapter extends SDSimpleAdapter<SelectLabelModel> {

    public SelectLabelAdapter(List<SelectLabelModel> listModel, Activity activity) {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent) {
        return R.layout.item_label;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final SelectLabelModel model) {
        TextView tv_label = get(R.id.tv_label,convertView);
        tv_label.setText(model.getLabel());
        tv_label.setBackgroundResource(R.drawable.selector_gray_blue_corner);
        tv_label.setTextColor(SDDrawable.getStateListColor(getActivity().getResources().getColor(R.color.black),getActivity().getResources().getColor(R.color.white),getActivity().getResources().getColor(R.color.white),getActivity().getResources().getColor(R.color.white)));
        if(model.isSelected()) {
            tv_label.setSelected(true);
        } else {
            tv_label.setSelected(false);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemClickCallback(position, model, v);
            }
        });
    }

}
