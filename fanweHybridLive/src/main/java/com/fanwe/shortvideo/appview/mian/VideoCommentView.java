package com.fanwe.shortvideo.appview.mian;

import android.content.Context;
import android.util.AttributeSet;
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
import com.fanwe.shortvideo.adapter.ShortVideoCommentAdapter;
import com.fanwe.shortvideo.adapter.ShortVideoCommentAdapter.ItemClickListener;
import com.fanwe.shortvideo.model.VideoCommentListModel;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.weibo.model.XRCommentFavorite;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by Administrator on 2016/12/9.
 */

public class VideoCommentView extends BaseAppView implements ShortVideoCommentAdapter.ItemClickListener{
    @ViewInject(R.id.tv_send)
    private TextView tv_send;
    @ViewInject(R.id.et_content)
    private EditText et_content;
    @ViewInject(R.id.lv_comment)
    private SDProgressPullToRefreshRecyclerView lv_comment;
    private int page = 0;
    private boolean has_next = true;
    private String sv_id;
    private ShortVideoCommentAdapter adapter;
    private List<VideoCommentListModel.CommentItemModel> listModel = new ArrayList<>();

    public VideoCommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public VideoCommentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VideoCommentView(Context context) {
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
        adapter = new ShortVideoCommentAdapter(new ArrayList<VideoCommentListModel.CommentItemModel>(), getActivity());
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
                requestData(sv_id);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> refreshView) {
                if (has_next) {
                    page++;
                    requestData(sv_id);
                } else {
                    SDToast.showToast("没有更多数据了");
                    lv_comment.onRefreshComplete();
                }
            }
        });
    }

    public void requestData(String sv_id) {
        this.sv_id=sv_id;
        CommonInterface.requestCommentList(page, sv_id, new AppRequestCallback<VideoCommentListModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
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
        CommonInterface.requestDynamicFavorite(1, Integer.parseInt(sv_id),to_comment_id,et_content.getText().toString(), new AppRequestCallback<XRCommentFavorite>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.getStatus() == 1) {
                    Toast.makeText(getActivity(),"评论成功！",Toast.LENGTH_SHORT).show();
                    et_content.setText("");
                    to_comment_id = 0;
                    et_content.setHint("请输入评论内容");
                    tv_send.setText("发布");
                    requestData(sv_id);
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
    public void itemClick(VideoCommentListModel.CommentItemModel model) {
        to_comment_id = Integer.parseInt(model.getComment_id());
        et_content.setText("");
        et_content.setHint("回复：" + model.getNick_name());
        tv_send.setText("回复");
    }
}
