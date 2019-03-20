package com.weibo.adapter.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.live.R;
import com.weibo.adapter.XRUserDynamicPhotoListAdapter;
import com.weibo.constant.XRConstant;
import com.weibo.manager.ImageLoader;
import com.weibo.model.XRCommentModel;
import com.weibo.model.XRCommentNetworkImageModel;
import com.weibo.model.XRDynamicImagesBean;
import com.weibo.util.ViewUtil;
import com.weibo.widget.varunest.sparkbutton.SparkButton;
import com.weibo.interfaces.XRCommonDynamicItemCallback;
import com.weibo.model.XRUserDynamicsModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/17 15:53
 **/
public class XRUserDynamicPhotoTextViewHolder extends XRBaseViewHolder<XRCommentModel.XRUserDynamicsModel> {
    private ImageView stickTopImageView, userHeadImageView, userAuthenticationImageView, singlePhotoImageView, commentImageView, iv_location;
    private TextView userNameTextView, publishTimeTextView, contentTextView, favoriteNumTextView,
            commentNumTextView, placeTextView;
    private RecyclerView photosRecyclerView;
    private SparkButton favoriteButton;
    private XRUserDynamicPhotoTextViewHolderCallback callback;

    public XRUserDynamicPhotoTextViewHolder(ViewGroup parent, @LayoutRes int layout) {
        super(parent, layout);
        iv_location = (ImageView) itemView.findViewById(R.id.lv_location);
        commentImageView = (ImageView) itemView.findViewById(R.id.iv_comment_xr_view_holder_user_dynamic_photo_text);
        stickTopImageView = (ImageView) itemView.findViewById(R.id.iv_stick_top_xr_view_holder_user_dynamic_photo_text);
        userHeadImageView = (ImageView) itemView.findViewById(R.id.iv_user_head_xr_view_holder_user_dynamic_photo_text);
        userAuthenticationImageView = (ImageView) itemView.findViewById(R.id.iv_user_authentication_xr_view_holder_user_dynamic_photo_text);
        singlePhotoImageView = (ImageView) itemView.findViewById(R.id.iv_photo_single_xr_view_holder_user_dynamic_photo_text);
        favoriteButton = (SparkButton) itemView.findViewById(R.id.spark_button_favorite_xr_view_holder_user_dynamic_photo_text);
        userNameTextView = (TextView) itemView.findViewById(R.id.tv_user_name_xr_view_holder_user_dynamic_photo_text);
        publishTimeTextView = (TextView) itemView.findViewById(R.id.tv_publish_time_xr_view_holder_user_dynamic_photo_text);
        contentTextView = (TextView) itemView.findViewById(R.id.tv_content_xr_view_holder_user_dynamic_photo_text);
        favoriteNumTextView = (TextView) itemView.findViewById(R.id.tv_number_favorite_xr_view_holder_user_dynamic_photo_text);
        commentNumTextView = (TextView) itemView.findViewById(R.id.tv_number_comment_xr_view_holder_user_dynamic_photo_text);
        photosRecyclerView = (RecyclerView) itemView.findViewById(R.id.recycler_view_photos_xr_view_holder_user_dynamic_photo_text);
        placeTextView = (TextView) itemView.findViewById(R.id.tv_publish_place_xr_view_holder_user_dynamic_photo_text);
        userHeadImageView.setOnClickListener(this);
        singlePhotoImageView.setOnClickListener(this);
        userNameTextView.setOnClickListener(this);
        favoriteNumTextView.setOnClickListener(this);
        commentImageView.setOnClickListener(this);
        favoriteButton.setOnClickListener(this);
    }

    @Override
    public void bindData(Context context, XRCommentModel.XRUserDynamicsModel entity, int position) {
        setHolderEntity(entity);
        setHolderEntityPosition(position);
        Log.e("bmbmbmbmbm",entity.getHas_digg()+"has_digg");
        if (ViewUtil.setViewVisibleOrGone(userAuthenticationImageView,
                entity.getIs_authentication().equals("1"))) {
            ImageLoader.load(context, entity.getV_icon(), userAuthenticationImageView);
        }


        if (entity.getIs_show_top() == 1) {
            ViewUtil.setViewVisibleOrGone(stickTopImageView, entity.getIs_show_top() == 1);
        } else {
            ViewUtil.setViewGone(stickTopImageView);
        }

        ImageLoader.load(context,
                entity.getHead_image(),
                userHeadImageView,
                R.drawable.xr_user_head_default_user_center,
                R.drawable.xr_user_head_default_user_center);

        ViewUtil.setText(userNameTextView, entity.getNick_name());
        ViewUtil.setText(publishTimeTextView, entity.getLeft_time());
        ViewUtil.setText(contentTextView, entity.getContent());
        ViewUtil.setText(favoriteNumTextView, entity.getDigg_count()+"", "0");
        ViewUtil.setText(commentNumTextView, entity.getComment_count(), "0");
        favoriteButton.setChecked(entity.getHas_digg() == 1);
        if (entity.getImages() != null) {
            if (entity.getImages_count() == 1) {
                //单张照片，显示大图
                ViewUtil.setViewVisible(singlePhotoImageView);
                ViewUtil.setViewGone(photosRecyclerView);

                ImageLoader.load(context,
                        entity.getImages().get(0).getOrginal_url(),
                        singlePhotoImageView);

            } else if (entity.getImages_count() > 1) {
                //多张（>=2）照片，显示网格小图
                ViewUtil.setViewVisible(photosRecyclerView);
                ViewUtil.setViewGone(singlePhotoImageView);
                photosRecyclerView.setLayoutManager(new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false));
                XRUserDynamicPhotoListAdapter photoListAdapter = new XRUserDynamicPhotoListAdapter(context, convertPhotoModelList(entity.getImages())) {
                    @Override
                    public void onPhotoThumbClick(View view, XRCommentNetworkImageModel model, int position) {
                        if (callback == null) {
                            return;
                        }
                        callback.onPhotoTextPhotoThumbClick(view, getHolderEntity(), model.getImgPath(), getHolderEntityPosition(), position);
                    }
                };
                photosRecyclerView.setAdapter(photoListAdapter);
            }
        }

        if (ViewUtil.setViewVisibleOrGone(placeTextView, !entity.getWeibo_place().isEmpty())) {
            ViewUtil.setText(placeTextView, entity.getWeibo_place());
            iv_location.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        if (callback == null) {
            return;
        }
        if (view == userHeadImageView) {
            callback.onUserHeadClick(view, getHolderEntity(), getHolderEntityPosition());
        } else if (view == singlePhotoImageView) {
            callback.onPhotoTextSinglePhotoThumbClick(view, getHolderEntity(), getHolderEntity().getImages().get(0).getOrginal_url(), getHolderEntityPosition());
        } else if (view == commentImageView) {
            callback.onCommentClick(view, getHolderEntity(), getHolderEntityPosition());
        } else if (view == favoriteButton) {
            boolean newState = !favoriteButton.isChecked();
            favoriteButton.setChecked(newState);
            if (newState) {
                favoriteButton.playAnimation();
            }
            callback.onFavoriteClick(view, getHolderEntity(), getHolderEntityPosition());
        }

    }

    public void setCallback(XRUserDynamicPhotoTextViewHolderCallback callback) {
        this.callback = callback;
    }

    private List<XRCommentNetworkImageModel> convertPhotoModelList(List<XRCommentModel.XRUserDynamicsModel.ImagesBean> images) {
        List<XRCommentNetworkImageModel> models = new ArrayList<>();

        for (XRCommentModel.XRUserDynamicsModel.ImagesBean imagesBean : images) {
            XRCommentNetworkImageModel model = new XRCommentNetworkImageModel(imagesBean.getOrginal_url());
            models.add(model);
        }
        return models;
    }

    public interface XRUserDynamicPhotoTextViewHolderCallback extends XRCommonDynamicItemCallback<XRCommentModel.XRUserDynamicsModel> {
        void onPhotoTextPhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition, int photoPosition);

        void onPhotoTextSinglePhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition);
    }

}
