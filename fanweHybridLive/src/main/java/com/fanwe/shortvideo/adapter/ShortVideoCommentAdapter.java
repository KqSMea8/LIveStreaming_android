package com.fanwe.shortvideo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.R;
import com.fanwe.shortvideo.appview.mian.VideoCommentItemView;
import com.fanwe.shortvideo.appview.mian.VideoCommentView;
import com.fanwe.shortvideo.model.VideoCommentListModel;
import com.weibo.adapter.XRCommentAdapter;
import com.weibo.model.XRCommentListModel;

import java.util.ArrayList;

/**
 * @author wxy
 */
public class ShortVideoCommentAdapter extends SDSimpleRecyclerAdapter<VideoCommentListModel.CommentItemModel> {

    private Activity activity;
    private VideoCommentView mView;

    public ShortVideoCommentAdapter(ArrayList<VideoCommentListModel.CommentItemModel> listModel, Activity activity) {
        super(listModel, activity);
        this.activity = activity;
    }

    public void setCommentView(VideoCommentView view) {
        this.mView = view;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<VideoCommentListModel.CommentItemModel> holder, final int position, final VideoCommentListModel.CommentItemModel model) {
        VideoCommentItemView item0 = holder.get(R.id.item_comment);
        item0.setModel(model);
        item0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(model);
            }
        });
    }


    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_comment_list;
    }

    ShortVideoCommentAdapter.ItemClickListener itemClickListener;

    public ShortVideoCommentAdapter.ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ShortVideoCommentAdapter.ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void itemClick(VideoCommentListModel.CommentItemModel model);
    }

}
