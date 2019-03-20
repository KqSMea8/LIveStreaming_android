package com.weibo.adapter.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fanwe.live.R;
import com.weibo.manager.ImageLoader;
import com.weibo.model.XRCommentNetworkImageModel;

/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/19 9:54
 **/
public class XRUserDynamicPhotoListViewHolder extends XRBaseViewHolder<XRCommentNetworkImageModel>
{
    private ImageView thumbImageView;
    private XRUserDynamicPhotoListViewHolderCallback callback;

    public XRUserDynamicPhotoListViewHolder(ViewGroup parent, @LayoutRes int layout)
    {
        super(parent, layout);

        thumbImageView = (ImageView) itemView.findViewById(R.id.ic_thumb_xr_view_holder_photo_list_user_dynamic);
    }

    @Override
    public void bindData(Context context, final XRCommentNetworkImageModel entity, final int position)
    {
        setHolderEntity(entity);
        setHolderEntityPosition(position);

//        if (entity.isBlur())
//        {
//            ImageLoader.loadBlur(context, entity.getImgPath(), thumbImageView);
//        } else
//        {
        ImageLoader.load(context, entity.getImgPath(), thumbImageView);
//        }

        thumbImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                callback.onPhotoThumbClick(thumbImageView, entity, position);
            }
        });
    }

    public void setCallback(XRUserDynamicPhotoListViewHolderCallback callback)
    {
        this.callback = callback;
    }

    public interface XRUserDynamicPhotoListViewHolderCallback
    {
        void onPhotoThumbClick(View view, XRCommentNetworkImageModel model, int position);
    }
}
