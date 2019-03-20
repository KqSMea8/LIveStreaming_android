package com.weibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewBinder;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.library.view.CircleImageView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.utils.GlideUtil;
import com.weibo.model.XRCommentListModel;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class XRCommentItemView extends BaseAppView {

    private CircleImageView iv_head_image;
    private TextView tv_nick_name;
    private TextView tv_content;
    private TextView tv_date;
    private XRCommentListModel.ListBean model;

    public XRCommentItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XRCommentItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XRCommentItemView(Context context) {
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

    public XRCommentListModel.ListBean getModel() {
        return model;
    }

    public void setModel(XRCommentListModel.ListBean model) {
        this.model = model;
        if (model != null) {
            SDViewUtil.setVisible(this);
            GlideUtil.load(model.getHead_image()).into(iv_head_image);
            SDViewBinder.setTextView(tv_nick_name, model.getNick_name());
            if (!model.getTo_nick_name().isEmpty()) {
                SDViewBinder.setTextView(tv_content, "回复 " + model.getTo_nick_name() + ": " + model.getContent());
            } else {
                SDViewBinder.setTextView(tv_content, model.getContent());
            }
            SDViewBinder.setTextView(tv_date,model.getLeft_time());
        }
    }
}