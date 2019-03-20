package com.weibo.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDRecyclerView;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.fanwe.shortvideo.common.utils.TCConstants;
import com.fanwe.shortvideo.videorecord.TCVideoRecordActivity;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.weibo.activity.PhotoPublishActivity;
import com.weibo.adapter.XRUserDynamicsAdapter;
import com.weibo.interfaces.XRRefreshableListCallback;
import com.weibo.manager.XRActivityLauncher;
import com.weibo.model.UpdateFavorite;
import com.weibo.model.XRCommentModel;
import com.weibo.model.uploadFinish;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

/**
 * @包名 com.fanwe.xianrou.fragment
 * @描述 个人中心-动态界面
 * @作者 Su
 * @创建时间 2017/3/15 15:24
 **/
public class XRUserCenterDynamicsFragment extends BaseFragment {

    private XRUserDynamicsAdapter adapter;
    @ViewInject(R.id.lv_shop)
    SDProgressPullToRefreshRecyclerView lv_content;
    @ViewInject(R.id.iv_camera)
    ImageView iv_camera;
    @Override
    protected int onCreateContentView() {
        return R.layout.frag_my_dynamics;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        x.view().inject(getActivity());
        init();
    }
    protected void init() {
        lv_content = (SDProgressPullToRefreshRecyclerView) findViewById(R.id.lv_shop);
        lv_content.getRefreshableView().setGridVertical(1);
        lv_content.setPullToRefreshOverScrollEnabled(false);
        setAdapter();
        initPullToRefresh();
        requestData(page,false);
        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSimpleListDialog(iv_camera);
            }
        });
    }
    private AlertDialog.Builder builder;
    private void showSimpleListDialog(View view) {
        builder=new AlertDialog.Builder(getActivity());
        final String[] Items={"发布照片","发布视频"};
        builder.setItems(Items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i==0){
                    Intent intent = new Intent(getActivity(), PhotoPublishActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getActivity(), TCVideoRecordActivity.class);
                    intent.putExtra(TCConstants.PUBLISH_TYPE, 1);
                    startActivity(intent);
                }
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog=builder.create();
        dialog.show();
    }
    protected void initPullToRefresh() {
        lv_content.setMode(PullToRefreshBase.Mode.BOTH);
        lv_content.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<SDRecyclerView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase) {
                page=1;
                requestData(page,false);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<SDRecyclerView> pullToRefreshBase) {
                if(has_next==1){
                    page++;
                }else{
                    lv_content.onRefreshComplete();
                    SDToast.showToast("没有更多数据了");
                    return;
                }
                requestData(page,true);
            }
        });
    }
    public void onEventMainThread(UpdateFavorite event) {
        for(int i=0;i<adapter.getDataList().size();i++){
            if(event.getWeibo_id()==Integer.parseInt(adapter.getDataList().get(i).getWeibo_id())){
                switch (event.getType()){
                    case UpdateFavorite.FAVORITE:
                        adapter.getItemEntity(i).setDigg_count(event.getDigg_count());
                        adapter.getItemEntity(i).setHas_digg(event.getHas_digg());
                        adapter.notifyDataSetChanged();
                        break;
                    case UpdateFavorite.VIDEO_COUNT:
                        adapter.getItemEntity(i).setVideo_count(event.getVideo_count()+"");
                        break;
                }
            }
        }
    }
    public XRUserCenterDynamicsFragmentCallback getCallback() {
        if (mCallback == null) {
            mCallback = new XRUserCenterDynamicsFragmentCallback() {
                @Override
                public void onDynamicItemClick(View itemView, XRCommentModel.XRUserDynamicsModel model, int position) {
                }

                @Override
                public void onEmptyRetryClick(View view) {
                }

                @Override
                public void onErrorRetryClick(View view) {
                }

                @Override
                public void onPhotoTextPhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition, int photoPosition) {

                }

                @Override
                public void onPhotoTextSinglePhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition) {

                }


                @Override
                public void onVideoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, int position) {

                }


                @Override
                public void onUserHeadClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {

                }

                @Override
                public void onCommentClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {

                }

                @Override
                public void onFavoriteClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {

                }

                @Override
                public void onMoreClick(View view, XRCommentModel.XRUserDynamicsModel entity, int position) {

                }

                @Override
                public void onListSwipeToRefresh() {

                }

                @Override
                public void onListPullToLoadMore() {

                }
            };
        }
        return mCallback;
    }

    private XRUserCenterDynamicsFragmentCallback mCallback;

    public XRUserCenterDynamicsFragmentCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(XRUserCenterDynamicsFragmentCallback mCallback) {
        this.mCallback = mCallback;
    }

    private void setAdapter() {
        adapter = new XRUserDynamicsAdapter(getActivity()) {
            @Override
            public void onItemClick(View itemView, XRCommentModel.XRUserDynamicsModel entity, int position) {
                XRUserCenterDynamicsFragment.this.getCallback().onDynamicItemClick(itemView, entity, position);
            }
        };
        adapter.setCallback(new XRUserDynamicsAdapter.XRUserCenterDynamicsAdapterCallback() {
            @Override
            public void onMoreClick(View view, XRCommentModel.XRUserDynamicsModel entity, int position) {
            }

            @Override
            public void onUserHeadClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {
                XRActivityLauncher.launchUserCenterOthers(getActivity(), itemEntity.getUser_id(),itemEntity.getHead_image());
            }

            @Override
            public void onCommentClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {
                XRActivityLauncher.clickCommnetList(getActivity(), Integer.parseInt(itemEntity.getWeibo_id()));
            }

            @Override
            public void onFavoriteClick(View view, XRCommentModel.XRUserDynamicsModel itemEntity, int position) {
                XRActivityLauncher.clickFavorite( Integer.parseInt(itemEntity.getWeibo_id()));
            }

            @Override
            public void onVideoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, int position) {
                XRActivityLauncher.launchVideoPlay(getActivity(), model.getVideo_url(),Integer.parseInt(model.getWeibo_id()));
            }

            @Override
            public void onPhotoTextPhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition, int photoPosition) {
                ArrayList<String> list=new ArrayList<String>();
                for(XRCommentModel.XRUserDynamicsModel.ImagesBean imagesBean:model.getImages()){
                    list.add(imagesBean.getOrginal_url());
                }
                XRActivityLauncher.launchGallery(getActivity(), list);
            }

            @Override
            public void onPhotoTextSinglePhotoThumbClick(View view, XRCommentModel.XRUserDynamicsModel model, String url, int itemPosition) {
                ArrayList<String> list=new ArrayList<String>();
                for(XRCommentModel.XRUserDynamicsModel.ImagesBean imagesBean:model.getImages()){
                    list.add(imagesBean.getOrginal_url());
                }
                XRActivityLauncher.launchGallery(getActivity(), list);
            }
        });
        lv_content.getRefreshableView().setAdapter(adapter);
    }
    private int page=1;
    private int has_next=0;
    private void requestData(int page, final boolean loadmore) {
        showProgressDialog("");
        CommonInterface.requestComment(page, new AppRequestCallback<XRCommentModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                has_next=actModel.getHas_next();
                if(loadmore){
                    adapter.addDataList(actModel.getList());
                }else
                adapter.updateData(actModel.getList());
                adapter.notifyDataSetChanged();
            }

            @Override
            protected void onFinish(SDResponse resp) {
                lv_content.onRefreshComplete();
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData(1,false);
    }

    public interface XRUserCenterDynamicsFragmentCallback extends XRRefreshableListCallback, XRUserDynamicsAdapter.XRUserCenterDynamicsAdapterCallback {
        void onDynamicItemClick(View itemView, XRCommentModel.XRUserDynamicsModel model, int position);
    }
}
