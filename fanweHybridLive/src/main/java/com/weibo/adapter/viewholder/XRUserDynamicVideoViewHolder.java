package com.weibo.adapter.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.live.R;
import com.weibo.constant.XRConstant;
import com.weibo.interfaces.XRCommonDynamicItemCallback;
import com.weibo.manager.ImageLoader;
import com.weibo.model.XRCommentModel;
import com.weibo.util.ViewUtil;
import com.weibo.widget.varunest.sparkbutton.SparkButton;
import com.weibo.model.XRUserDynamicsModel;

/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/19 9:06
 **/
public class XRUserDynamicVideoViewHolder extends XRBaseViewHolder<XRCommentModel.XRUserDynamicsModel>
{
    private ImageView stickTopImageView, userHeadImageView, userAuthenticationImageView, videoThumbImageView,commentImageView;
    private TextView userNameTextView, publishTimeTextView, contentTextView, favoriteNumTextView,
            commentNumTextView, watchNumTextView,placeTextView;
    private SparkButton favoriteButton;
    private XRUserDynamicVideoViewHolderCallback callback;

    public XRUserDynamicVideoViewHolder(ViewGroup parent, @LayoutRes int layout)
    {
        super(parent, layout);
        commentImageView= (ImageView) findViewById(R.id.iv_comment_xr_view_holder_user_dynamic_video);
        stickTopImageView = (ImageView) findViewById(R.id.iv_stick_top_xr_view_holder_user_dynamic_video);
        userHeadImageView = (ImageView) findViewById(R.id.iv_user_head_xr_view_holder_user_dynamic_video);
        userAuthenticationImageView = (ImageView) findViewById(R.id.iv_user_authentication_xr_view_holder_user_dynamic_video);
        videoThumbImageView = (ImageView) findViewById(R.id.iv_thumb_xr_view_holder_user_dynamic_video);
        favoriteButton = (SparkButton) findViewById(R.id.spark_button_favorite_xr_view_holder_user_dynamic_video);
        userNameTextView = (TextView) findViewById(R.id.tv_user_name_xr_view_holder_user_dynamic_video);
        publishTimeTextView = (TextView) findViewById(R.id.tv_publish_time_xr_view_holder_user_dynamic_video);
        contentTextView = (TextView) findViewById(R.id.tv_content_xr_view_holder_user_dynamic_video);
        favoriteNumTextView = (TextView) findViewById(R.id.tv_number_favorite_xr_view_holder_user_dynamic_video);
        commentNumTextView = (TextView) findViewById(R.id.tv_number_comment_xr_view_holder_user_dynamic_video);
        watchNumTextView = (TextView) findViewById(R.id.tv_number_video_watch_xr_view_holder_user_dynamic_video);
        placeTextView = (TextView) itemView.findViewById(R.id.tv_publish_place_xr_view_holder_user_dynamic_video);
        userHeadImageView.setOnClickListener(this);
        userNameTextView.setOnClickListener(this);
        favoriteNumTextView.setOnClickListener(this);
        commentImageView.setOnClickListener(this);
        watchNumTextView.setOnClickListener(this);
        videoThumbImageView.setOnClickListener(this);
        favoriteButton.setOnClickListener(this);
    }

    @Override
    public void bindData(Context context, XRCommentModel.XRUserDynamicsModel entity, int position)
    {
        setHolderEntity(entity);
        setHolderEntityPosition(position);

        if (ViewUtil.setViewVisibleOrGone(userAuthenticationImageView,
                entity.getIs_authentication().equals(XRConstant.UserAuthenticationStatus.AUTHENTICATED)))
        {
            ImageLoader.load(context, entity.getV_icon(), userAuthenticationImageView);
        }

        if (entity.getIs_show_top() == 1)
        {
            ViewUtil.setViewVisibleOrGone(stickTopImageView, "1".equals(entity.getIs_top()));
        } else
        {
            ViewUtil.setViewGone(stickTopImageView);
        }

        ImageLoader.load(context,
                entity.getHead_image(),
                userHeadImageView,
                R.drawable.xr_user_head_default_user_center,
                R.drawable.xr_user_head_default_user_center);

        ImageLoader.load(context,
                entity.getPhoto_image(),
                videoThumbImageView);


        ViewUtil.setText(userNameTextView, entity.getNick_name());
        ViewUtil.setText(publishTimeTextView, entity.getLeft_time());
        ViewUtil.setText(contentTextView, entity.getContent());
        ViewUtil.setText(favoriteNumTextView, entity.getDigg_count()+"","0");
        ViewUtil.setText(commentNumTextView, entity.getComment_count(),"0");
        ViewUtil.setText(watchNumTextView, entity.getVideo_count(),"0");

        favoriteButton.setChecked(entity.getHas_digg() == 1);
        if (ViewUtil.setViewVisibleOrGone(placeTextView, !entity.getWeibo_place().isEmpty()))
        {
            ViewUtil.setText(placeTextView, entity.getWeibo_place());
        }
    }

    @Override
    public void onClick(View view)
    {
        super.onClick(view);

        if (callback == null)
        {
            return;
        }

        if (view == userHeadImageView)
        {
            callback.onUserHeadClick(view, getHolderEntity(), getHolderEntityPosition());
        } else if (view == commentImageView)
        {
            callback.onCommentClick(view, getHolderEntity(), getHolderEntityPosition());
        } else if (view == favoriteNumTextView)
        {
            callback.onFavoriteClick(view, getHolderEntity(), getHolderEntityPosition());
        } else if (view == favoriteButton)
        {
            boolean newState = !favoriteButton.isChecked();
            favoriteButton.setChecked(newState);
            if (newState)
            {
                favoriteButton.playAnimation();
            }
            callback.onFavoriteClick(view, getHolderEntity(), getHolderEntityPosition());
        } else if (view == videoThumbImageView)
        {
            callback.onVideoThumbClick(view, getHolderEntity(), getHolderEntityPosition());
        }

    }

    public void setCallback(XRUserDynamicVideoViewHolderCallback callback)
    {
        this.callback = callback;
    }

    public interface XRUserDynamicVideoViewHolderCallback extends XRCommonDynamicItemCallback<XRCommentModel.XRUserDynamicsModel>
    {
        void onVideoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, int position);
    }


}
