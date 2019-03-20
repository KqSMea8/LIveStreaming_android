package com.weibo.adapter.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.live.R;
import com.weibo.constant.XRConstant;
import com.weibo.manager.ImageLoader;
import com.weibo.model.XRUserDynamicCommentModel;
import com.weibo.util.ViewUtil;

/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/24 17:52
 **/
public class XRUserDynamicCommentNormalViewHolder extends XRBaseViewHolder<XRUserDynamicCommentModel>
{
    private ImageView userHeadImageView, userAuthenticationImageView;
    private TextView userNameTextView, commentTimeTextView, commentContentTextView;
    private XRUserDynamicCommentNormalViewHolderCallback callback;

    public XRUserDynamicCommentNormalViewHolder(ViewGroup parent, @LayoutRes int layout)
    {
        super(parent, layout);

        userHeadImageView = (ImageView) itemView.findViewById(R.id.iv_user_head_xr_view_holder_user_dynamic_comment_normal);
        userAuthenticationImageView = (ImageView) itemView.findViewById(R.id.iv_user_authentication_xr_view_holder_user_dynamic_comment_normal);
        userNameTextView = (TextView) itemView.findViewById(R.id.tv_user_name_xr_view_holder_user_dynamic_comment_normal);
        commentTimeTextView = (TextView) itemView.findViewById(R.id.tv_time_xr_view_holder_user_dynamic_comment_normal);
        commentContentTextView = (TextView) itemView.findViewById(R.id.tv_content_xr_view_holder_user_dynamic_comment_normal);
    }

    @Override
    public void bindData(Context context, final XRUserDynamicCommentModel entity, final int position)
    {
        setHolderEntity(entity);
        setHolderEntityPosition(position);

        ImageLoader.load(context,entity.getHead_image(),userHeadImageView);

        ViewUtil.setText(userNameTextView, entity.getNick_name());
        ViewUtil.setText(commentTimeTextView, entity.getLeft_time());
        ViewUtil.setText(commentContentTextView, entity.getContent());

        if (ViewUtil.setViewVisibleOrGone(userAuthenticationImageView,
                entity.getIs_authentication().equals(XRConstant.UserAuthenticationStatus.AUTHENTICATED)))
        {
            ImageLoader.load(context, entity.getV_icon(), userAuthenticationImageView);
        }

        userHeadImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getCallback().onCommentUserHeadClick(userHeadImageView, entity, position);
            }
        });
    }

    private XRUserDynamicCommentNormalViewHolderCallback getCallback()
    {
        if (callback == null)
        {
            callback = new XRUserDynamicCommentNormalViewHolderCallback()
            {
                @Override
                public void onCommentUserHeadClick(View view, XRUserDynamicCommentModel model, int position)
                {

                }
            };
        }
        return callback;
    }

    public void setCallback(XRUserDynamicCommentNormalViewHolderCallback callback)
    {
        this.callback = callback;
    }

    public interface XRUserDynamicCommentNormalViewHolderCallback
    {
        void onCommentUserHeadClick(View view, XRUserDynamicCommentModel model, int position);
    }

}
