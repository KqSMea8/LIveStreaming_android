package com.weibo.adapter.viewholder;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.span.builder.SDSpannableStringBuilder;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.live.R;
import com.fanwe.live.view.LivePrivateChatSpanTextView;
import com.weibo.constant.XRConstant;
import com.weibo.manager.ImageLoader;
import com.weibo.model.XRUserDynamicCommentModel;
import com.weibo.util.ViewUtil;

/**
 * @包名 com.fanwe.xianrou.adapter.viewholder
 * @描述
 * @作者 Su
 * @创建时间 2017/3/26 10:32
 **/
public class XRUserDynamicCommentReplyViewHolder extends XRBaseViewHolder<XRUserDynamicCommentModel>
{
    private ImageView userHeadImageView, userAuthenticationImageView;
    private TextView userNameTextView, commentTimeTextView;
    private LivePrivateChatSpanTextView commentContentTextView;
    private XRUserDynamicCommentReplyViewHolderCallback callback;


    public XRUserDynamicCommentReplyViewHolder(ViewGroup parent, @LayoutRes int layout)
    {
        super(parent, layout);

        userHeadImageView = (ImageView) itemView.findViewById(R.id.iv_user_head_xr_view_holder_user_dynamic_comment_reply);
        userAuthenticationImageView = (ImageView) itemView.findViewById(R.id.iv_user_authentication_xr_view_holder_user_dynamic_comment_reply);
        userNameTextView = (TextView) itemView.findViewById(R.id.tv_user_name_xr_view_holder_user_dynamic_comment_reply);
        commentTimeTextView = (TextView) itemView.findViewById(R.id.tv_time_xr_view_holder_user_dynamic_comment_reply);
        commentContentTextView = (LivePrivateChatSpanTextView) itemView.findViewById(R.id.tv_content_xr_view_holder_user_dynamic_comment_reply);
    }

    @Override
    public void bindData(Context context, final XRUserDynamicCommentModel entity, final int position)
    {
        setHolderEntity(entity);
        setHolderEntityPosition(position);

        SDSpannableStringBuilder sb = new SDSpannableStringBuilder();

        ImageLoader.load(context, entity.getHead_image(), userHeadImageView);

        ViewUtil.setText(userNameTextView, entity.getNick_name());
        ViewUtil.setText(commentTimeTextView, entity.getLeft_time());

        if (ViewUtil.setViewVisibleOrGone(userAuthenticationImageView,
                entity.getIs_authentication().equals(XRConstant.UserAuthenticationStatus.AUTHENTICATED)))
        {
            ImageLoader.load(context, entity.getV_icon(), userAuthenticationImageView);
        }

        appendContent(sb, context.getString(R.string.reply));
        appendUserName(sb, entity.getTo_nick_name());
        appendContent(sb, ": ");
        appendContent(sb, entity.getContent());
        SDViewBinder.setTextView(commentContentTextView, sb);

        userHeadImageView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                getCallback().onCommentUserHeadClick(view, entity, position);
            }
        });
    }

    private XRUserDynamicCommentReplyViewHolderCallback getCallback()
    {
        if (callback == null)
        {
            callback = new XRUserDynamicCommentReplyViewHolderCallback()
            {
                @Override
                public void onCommentUserHeadClick(View view, XRUserDynamicCommentModel model, int position)
                {

                }
            };
        }
        return callback;
    }

    /**
     * 添加用户名
     *
     * @param name
     */
    protected void appendUserName(SDSpannableStringBuilder sb, String name)
    {
        if (!TextUtils.isEmpty(name))
        {
            int textColor = SDResourcesUtil.getColor(R.color.main);
            ForegroundColorSpan textSpan = new ForegroundColorSpan(textColor);
            sb.appendSpan(textSpan, name);
        }
    }

    /**
     * 添加内容
     *
     * @param content
     */
    protected void appendContent(SDSpannableStringBuilder sb, String content)
    {
        if (!TextUtils.isEmpty(content))
        {
            int textColor = SDResourcesUtil.getColor(R.color.textColorSecondary);
            ForegroundColorSpan textSpan = new ForegroundColorSpan(textColor);
            sb.appendSpan(textSpan, content);
        }
    }


    public void setCallback(XRUserDynamicCommentReplyViewHolderCallback callback)
    {
        this.callback = callback;
    }

    public interface XRUserDynamicCommentReplyViewHolderCallback
    {
        void onCommentUserHeadClick(View view, XRUserDynamicCommentModel model, int position);
    }
}
