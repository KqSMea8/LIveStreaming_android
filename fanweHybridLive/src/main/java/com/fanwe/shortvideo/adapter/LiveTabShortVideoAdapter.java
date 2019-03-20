package com.fanwe.shortvideo.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.SDSimpleRecyclerAdapter;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.adapter.viewholder.SDRecyclerViewHolder;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.shortvideo.activity.ShortVideoDetailActivity;
import com.fanwe.shortvideo.appview.mian.ItemShortVideoView;
import com.fanwe.shortvideo.model.ShortVideoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxy
 */
public class LiveTabShortVideoAdapter extends SDSimpleRecyclerAdapter<ShortVideoModel> {

    private Activity activity;
    private int tag = 0;

    public LiveTabShortVideoAdapter(ArrayList<ShortVideoModel> listModel, Activity activity) {
        super(listModel, activity);
        this.activity = activity;
    }

    public void setTag(int tag) {
        this.tag = tag;
        notifyDataSetChanged();

    }

    @Override
    public void onBindData(SDRecyclerViewHolder<ShortVideoModel> holder, final int position, final ShortVideoModel model) {
        ItemShortVideoView item0 = holder.get(R.id.item_short_video);
        item0.setModel(model, tag);
        item0.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemShortVideoView view = (ItemShortVideoView) v;
                ShortVideoModel model = view.getModel();

                if (model == null) {
                    SDToast.showToast("数据为空");
                    return;
                } else {
                    Intent intent = new Intent(activity, ShortVideoDetailActivity.class);
                    ArrayList<String> videoIdList = new ArrayList<>();
                    ArrayList<String> videoImgList = new ArrayList<>();
                    for (ShortVideoModel videoModel : getData()) {
                        videoIdList.add(videoModel.getSv_id());
                        videoImgList.add(videoModel.getSv_img());
                    }
                    intent.putExtra("position", position);
                    intent.putStringArrayListExtra("video_id_list", videoIdList);
                    intent.putStringArrayListExtra("video_img_list", videoImgList);
                    activity.startActivity(intent);
                }
            }
        });
        item0.setDeleteItemListener(new ItemShortVideoView.OnDeleteListener() {
            @Override
            public void onDelete() {
                showDialog(model, position);
            }
        });
    }

    private void showDialog(final ShortVideoModel model, final int position) {
        new AlertDialog.Builder(activity).setMessage("删除并重新录制？")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                getData().remove(position);
                notifyDataSetChanged();
                requestData(model.getSv_id());
            }
        }).show();
    }

    @Override
    public int getLayoutId(ViewGroup parent, int viewType) {
        return R.layout.item_live_tab_shortvideo;
    }

    protected void requestData(String sv_id) {
        CommonInterface.requestDelVideo(sv_id, new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    SDToast.showToast("删除成功");
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });

    }

}
