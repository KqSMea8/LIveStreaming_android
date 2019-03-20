package com.fanwe.live.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.model.LiveCreaterMenuModel;

import java.util.List;

public class LiveCreaterMenuAdapter extends SDSimpleAdapter<LiveCreaterMenuModel>
{

    public LiveCreaterMenuAdapter(List<LiveCreaterMenuModel> listModel, Activity activity)
    {
        super(listModel, activity);
        getSelectManager().setMode(Mode.MULTI);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_creater_menu;
    }

    @Override
    public void bindData(final int position, View convertView, ViewGroup parent, final LiveCreaterMenuModel model)
    {
        ImageView iv_image = get(R.id.iv_image, convertView);
        TextView tv_content = get(R.id.tv_content, convertView);

        if (model.isSelected())
        {
            iv_image.setImageResource(model.getImageResIdSelected());
            SDViewBinder.setTextView(tv_content, model.getTextSelected());
            SDViewUtil.setTextViewColorResId(tv_content, model.getTextColorResIdSelected());
        } else
        {
            iv_image.setImageResource(model.getImageResIdNormal());
            SDViewBinder.setTextView(tv_content, model.getTextNormal());
            SDViewUtil.setTextViewColorResId(tv_content, model.getTextColorResIdNormal());
        }

        if (model.isVisible())
        {
            SDViewUtil.setVisible(convertView);
        } else
        {
            SDViewUtil.setGone(convertView);
        }

        convertView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                notifyItemClickCallback(position, model, view);
            }
        });

    }

}
