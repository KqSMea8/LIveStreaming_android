package com.fanwe.auction.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.auction.model.ImageModel;
import com.fanwe.library.adapter.SDSimpleAdapter;
import com.fanwe.live.R;
import com.fanwe.live.utils.GlideUtil;

import java.util.List;

/**
 * Created by shibx on 2016/8/8.
 */
public class ItemImgAdapter extends SDSimpleAdapter<ImageModel>
{

    private OnItemRemoveListener mListener;

    public ItemImgAdapter(List<ImageModel> listModel, Activity activity)
    {
        super(listModel, activity);
    }

    @Override
    public int getLayoutId(int position, View convertView, ViewGroup parent)
    {
        return R.layout.item_image;
    }

    @Override
    public void bindData(final int position, final View convertView, ViewGroup parent, final ImageModel model)
    {
        final ImageView iv_item_img = get(R.id.iv_item_img, convertView);
        ImageView iv_item_remove = get(R.id.iv_item_remove, convertView);
        if (model.getUri() == null)
        {
            iv_item_remove.setVisibility(View.INVISIBLE);
            convertView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    notifyItemClickCallback(position, model, v);
                }
            });
        } else
        {
            iv_item_remove.setVisibility(View.VISIBLE);
            GlideUtil.load(model.getUri()).into(iv_item_img);
            iv_item_remove.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    if (mListener != null)
                    {
                        mListener.onRemove(position, model, convertView);
                    }
                }
            });
        }
    }

    public void setOnItemRemoveListener(OnItemRemoveListener listener)
    {
        this.mListener = listener;
    }

    public interface OnItemRemoveListener
    {
        public void onRemove(int position, ImageModel item, View view);
    }
}
