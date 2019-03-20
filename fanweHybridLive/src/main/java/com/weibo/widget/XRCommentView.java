package com.weibo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.appview.BaseAppView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.weibo.adapter.XRCommentAdapter;
import com.weibo.model.XRCommentFavorite;
import com.weibo.model.XRCommentListModel;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/29 0029.
 */

public class XRCommentView extends BaseAppView implements XRCommentAdapter.ItemClickListener {
    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.lv_comment)
    private SDProgressPullToRefreshRecyclerView lv_comment;
    private int page = 0;
    private boolean has_next = true;
    private int weibo_id;
    private XRCommentAdapter adapter;
    private List<XRCommentListModel.ListBean> listModel = new ArrayList<>();

    public XRCommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public XRCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XRCommentView(Context context) {
        super(context);
        init();
    }

    protected void init() {
        setContentView(R.layout.view_short_video_comment);
        lv_comment.getRefreshableView().setGridVertical(1);
        setAdapter();
        initPullToRefresh();
        tv_send.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestSendComment();
            }
        });
    }


    private void setAdapter() {
        adapter = new XRCommentAdapter(new ArrayList<XRCommentListModel.ListBean>(), getActivity());
        adapter.setCommentView(this);
        adapter.setItemClickListener(this);
        lv_comment.getRefreshableView().setAdapter(adapter);
    }

    private void initPullToRefresh() {
        lv_comment.setMode(PullToRefreshBase.Mode.BOTH);
        lv_comment.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {

            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                page = 0;
                requestData(weibo_id);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                if (has_next) {
                    page++;
                    requestData(weibo_id);
                } else {
                    SDToast.showToast("没有更多数据了");
                    lv_comment.onRefreshComplete();
                }
            }
        });
    }

    public void requestData(int weibo_id) {
        this.weibo_id = weibo_id;
        CommonInterface.requestCommentList(weibo_id, new AppRequestCallback<XRCommentListModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus() == 1) {
                    listModel = actModel.getList();
                    if (page == 0) {
                        adapter.updateData(listModel);
                    } else {
                        adapter.appendData(listModel);
                    }
                    has_next = listModel.size() < 20 ? false : true;
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                lv_comment.onRefreshComplete();
                super.onFinish(resp);
            }
        });
    }

    public void requestSendComment() {
        CommonInterface.requestDynamicFavorite(1, weibo_id,to_comment_id,et_content.getText().toString(), new AppRequestCallback<XRCommentFavorite>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus() == 1) {
                    Toast.makeText(getActivity(),"评论成功！",Toast.LENGTH_SHORT).show();
                    et_content.setText("");
                    tv_send.setText("发布");
                    requestData(weibo_id);
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                lv_comment.onRefreshComplete();
                super.onFinish(resp);
            }
        });
    }

    int to_comment_id = 0;

    @Override
    public void itemClick(XRCommentListModel.ListBean model) {
        to_comment_id = Integer.parseInt(model.getComment_id());
        et_content.setText("");
        et_content.setHint("回复：" + model.getNick_name());
        tv_send.setText("回复");
    }
}
