package com.fanwe.shortvideo.appview.mian;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.model.VideoCommentListModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 小视频评论列表itemView
 *
 * @author wxy
 */
public class VideoCommentItemView extends BaseAppView {

    private CircleImageView iv_head_image;
    private TextView tv_nick_name;
    private TextView tv_content;
    private TextView tv_date;
    private VideoCommentListModel.CommentItemModel model;

    public VideoCommentItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public VideoCommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoCommentItemView(Context context) {
        super(context);
        init();
    }

    protected void init() {
        setContentView(R.layout.item_video_comment_view);

        iv_head_image = find(R.id.iv_head_image);
        tv_nick_name = find(R.id.tv_nick_name);
        tv_content = find(R.id.tv_content);
        tv_date = find(R.id.tv_date);
    }

    public VideoCommentListModel.CommentItemModel getModel() {
        return model;
    }

    public void setModel(VideoCommentListModel.CommentItemModel model) {
        this.model = model;
        if (model != null) {
            SDViewUtil.setVisible(this);
            GlideUtil.load(model.head_image).into(iv_head_image);
            SDViewBinder.setTextView(tv_nick_name, model.nick_name);
            if (!model.to_user_nickname.isEmpty()) {
                SDViewBinder.setTextView(tv_content, "回复 " + model.to_user_nickname + ": " + model.com_content);
            } else {
                SDViewBinder.setTextView(tv_content, model.com_content);
            }
            Date date=new Date(new Long(model.com_time)*1000);
            SDViewBinder.setTextView(tv_date, new SimpleDateFormat("yyyy-MM-dd").format(date));

        }
    }

}
