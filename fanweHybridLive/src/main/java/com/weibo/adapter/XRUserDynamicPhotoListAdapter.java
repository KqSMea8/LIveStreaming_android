package com.weibo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.live.R;
import com.weibo.adapter.viewholder.XRUserDynamicPhotoListViewHolder;
import com.weibo.model.XRCommentNetworkImageModel;

import java.util.List;

/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/19 10:04
 **/
public abstract class XRUserDynamicPhotoListAdapter extends XRBaseDisplayAdapter<XRCommentNetworkImageModel, XRUserDynamicPhotoListViewHolder>
{

    public abstract void onPhotoThumbClick(View view, XRCommentNetworkImageModel model, int position);

    public XRUserDynamicPhotoListAdapter(Context context)
    {
        super(context);
    }

    public XRUserDynamicPhotoListAdapter(Context context, List<XRCommentNetworkImageModel> dataList)
    {
        super(context, dataList);
    }

    @Override
    public void onItemClick(View itemView, XRCommentNetworkImageModel entity, int position)
    {

    }

    @Override
    protected XRUserDynamicPhotoListViewHolder createVH(ViewGroup parent, int viewType)
    {
        return new XRUserDynamicPhotoListViewHolder(parent, R.layout.xr_view_holder_photo_list_user_dynamic) ;
    }

    @Override
    protected void bindVH(XRUserDynamicPhotoListViewHolder viewHolder, XRCommentNetworkImageModel entity, int position)
    {
        viewHolder.bindData(getContext(), entity, position);
        viewHolder.setCallback(new XRUserDynamicPhotoListViewHolder.XRUserDynamicPhotoListViewHolderCallback()
        {
            @Override
            public void onPhotoThumbClick(View view, XRCommentNetworkImageModel model, int position)
            {
                XRUserDynamicPhotoListAdapter.this.onPhotoThumbClick(view, model, position);
            }
        });
    }

    @Override
    protected void onDataListChanged()
    {

    }

}
