package com.weibo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.fanwe.live.R;
import com.headerfooter.songhang.library.SmartRecyclerAdapter;
import com.weibo.adapter.viewholder.XRUserDynamicPhotoTextViewHolder;
import com.weibo.adapter.viewholder.XRUserDynamicVideoViewHolder;
import com.weibo.model.XRCommentModel;
import com.weibo.model.XRUserDynamicsModel;

import java.util.List;


/**
 * @包名 com.fanwe.xianrou.adapter
 * @描述
 * @作者 Su
 * @创建时间 2017/3/15 15:13
 **/
public abstract class XRUserDynamicsAdapter extends XRBaseDisplayAdapter<XRCommentModel.XRUserDynamicsModel, RecyclerView.ViewHolder>
{
    private static final int VIEW_TYPE_DYNAMIC_PHOTO_TEXT = -10001;
    private static final int VIEW_TYPE_DYNAMIC_VIDEO = -10101;

    private XRUserCenterDynamicsAdapterCallback callback;


    public XRUserDynamicsAdapter(Context context)
    {
        super(context);
    }

    @Override
    public int getItemViewType(int position)
    {
        XRCommentModel.XRUserDynamicsModel model = getItemEntity(position);

        if ("imagetext".equals(model.getType()))
        {
            return VIEW_TYPE_DYNAMIC_PHOTO_TEXT;
        }

        if ("video".equals(model.getType()))
        {
            return VIEW_TYPE_DYNAMIC_VIDEO;
        }

        return VIEW_TYPE_DYNAMIC_PHOTO_TEXT;
    }

    @Override
    protected RecyclerView.ViewHolder createVH(ViewGroup parent, int viewType)
    {
        if (viewType == VIEW_TYPE_DYNAMIC_PHOTO_TEXT)
        {
            return createPhotoTextViewHolder(parent);
        }


        if (viewType == VIEW_TYPE_DYNAMIC_VIDEO)
        {
            return createVideoViewHolder(parent);
        }
        return createPhotoTextViewHolder(parent);
    }

    private XRUserDynamicVideoViewHolder createVideoViewHolder(ViewGroup parent)
    {
        XRUserDynamicVideoViewHolder videoViewHolder = new XRUserDynamicVideoViewHolder(parent, R.layout.xr_view_holder_user_dynamic_video);
        videoViewHolder.setCallback(new XRUserDynamicVideoViewHolder.XRUserDynamicVideoViewHolderCallback()
        {
            @Override
            public void onVideoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, int position)
            {
                getCallback().onVideoThumbClick(view, model, position);
            }

            @Override
            public void onUserHeadClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position)
            {
                getCallback().onUserHeadClick(view, itemEntity, position);
            }

            @Override
            public void onCommentClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {
                getCallback().onCommentClick(view, itemEntity, position);
            }

            @Override
            public void onFavoriteClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position)
            {
                getCallback().onFavoriteClick(view, itemEntity, position);
            }

            @Override
            public void onMoreClick(View view, XRCommentModel.XRUserDynamicsModel entity, int position)
            {
                getCallback().onMoreClick(view, entity, position);
            }
        });
        return videoViewHolder;
    }


    private XRUserDynamicPhotoTextViewHolder createPhotoTextViewHolder(ViewGroup parent)
    {
        XRUserDynamicPhotoTextViewHolder photoTextViewHolder = new XRUserDynamicPhotoTextViewHolder(parent, R.layout.xr_view_holder_user_dynamic_photo_text);
        photoTextViewHolder.setCallback(new XRUserDynamicPhotoTextViewHolder.XRUserDynamicPhotoTextViewHolderCallback()
        {
            @Override
            public void onPhotoTextPhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition, int photoPosition)
            {
                getCallback().onPhotoTextPhotoThumbClick(view, model, url, itemPosition, photoPosition);
            }

            @Override
            public void onPhotoTextSinglePhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition)
            {
                getCallback().onPhotoTextSinglePhotoThumbClick(view, model, url, itemPosition);
            }

            @Override
            public void onUserHeadClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position)
            {
                getCallback().onUserHeadClick(view, itemEntity, position);
            }

            @Override
            public void onCommentClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {
                getCallback().onCommentClick(view, itemEntity, position);
            }

            @Override
            public void onFavoriteClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position)
            {
                getCallback().onFavoriteClick(view, itemEntity, position);
            }

            @Override
            public void onMoreClick(View view, XRCommentModel.XRUserDynamicsModel entity, int position)
            {
                getCallback().onMoreClick(view, entity, position);
            }
        });
        return photoTextViewHolder;
    }
    public void updateData(List<XRCommentModel.XRUserDynamicsModel> list){
        clear();
        addDataList(list);
    }
    @Override
    protected void bindVH(RecyclerView.ViewHolder viewHolder, XRCommentModel.XRUserDynamicsModel entity, int position)
    {
        int viewType = getItemViewType(position);

        if (viewType == VIEW_TYPE_DYNAMIC_PHOTO_TEXT)
        {
            XRUserDynamicPhotoTextViewHolder photoTextViewHolder = (XRUserDynamicPhotoTextViewHolder) viewHolder;
            photoTextViewHolder.bindData(getContext(), entity, position);
        }else if (viewType == VIEW_TYPE_DYNAMIC_VIDEO)
        {
            XRUserDynamicVideoViewHolder videoViewHolder = (XRUserDynamicVideoViewHolder) viewHolder;
            videoViewHolder.bindData(getContext(), entity, position);
        }
    }

    @Override
    protected void onDataListChanged()
    {

    }

    public void setCallback(XRUserCenterDynamicsAdapterCallback callback)
    {
        this.callback = callback;
    }

    public XRUserCenterDynamicsAdapterCallback getCallback()
    {
        if (callback == null)
        {
            callback = new XRUserCenterDynamicsAdapterCallback()
            {
                @Override
                public void onPhotoTextPhotoThumbClick(View view,XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition, int photoPosition)
                {

                }

                @Override
                public void onPhotoTextSinglePhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition)
                {

                }

                @Override
                public void onVideoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, int position)
                {

                }
                @Override
                public void onUserHeadClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position)
                {

                }

                @Override
                public void onCommentClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {

                }

                @Override
                public void onFavoriteClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position)
                {

                }

                @Override
                public void onMoreClick(View view, XRCommentModel.XRUserDynamicsModel entity, int position)
                {

                }
            };
        }
        return callback;
    }

    public interface XRUserCenterDynamicsAdapterCallback extends XRUserDynamicPhotoTextViewHolder.XRUserDynamicPhotoTextViewHolderCallback,
            XRUserDynamicVideoViewHolder.XRUserDynamicVideoViewHolderCallback
    {
    }
}
