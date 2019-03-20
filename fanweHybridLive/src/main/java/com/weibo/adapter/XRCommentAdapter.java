package com.weibo.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.live.R;
import com.weibo.model.XRCommentListModel;
import com.weibo.widget.XRCommentItemView;
import com.weibo.widget.XRCommentView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class XRCommentAdapter extends SDSimpleRecyclerAdapter<XRCommentListModel.ListBean> {

    private Activity activity;
    private XRCommentView mView;

    public XRCommentAdapter(ArrayList<XRCommentListModel.ListBean> listModel, Activity activity) {
        super(listModel, activity);
        this.activity = activity;
    }

    public void setCommentView(XRCommentView view) {
        this.mView = view;
    }

    @Override
    public void onBindData(SDRecyclerViewHolder<XRCommentListModel.ListBean> holder, final int position, final XRCommentListModel.ListBean model) {
        XRCommentItemView item0 = holder.get(R.id.item_comment);
        item0.setModel(model);
        item0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(model);
            }
        });
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_comment_weibo_list;
    }

    ItemClickListener itemClickListener;

    public ItemClickListener getItemClickListener() {
        return itemClickListener;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void itemClick(XRCommentListModel.ListBean model);
    }
}
