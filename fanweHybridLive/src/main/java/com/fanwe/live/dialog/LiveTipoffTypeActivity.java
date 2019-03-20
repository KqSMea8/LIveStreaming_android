package com.fanwe.live.dialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.view.annotation.ViewInject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.model.ImageModel;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.SelectPhotoActivity;
import com.fanwe.live.adapter.LiveTipoffTypeAdapter;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.ESelectImage;
import com.fanwe.live.model.App_tipoff_typeActModel;
import com.fanwe.live.model.App_tipoff_typeModel;
import com.fanwe.live.model.App_uploadImageActModel;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.weibo.adapter.LivePickPhotosAdapter;
import com.weibo.model.URIBean;
import com.weibo.model.uploadFinish;

import de.greenrobot.event.EventBus;

/**
 * @author 作者 E-mail:
 * @version 创建时间：2016-5-26 下午6:16:49 类说明
 */
public class LiveTipoffTypeActivity extends BaseActivity implements LivePickPhotosAdapter.ClickListener {

    @ViewInject(R.id.btn_confim)
    private Button btn_confim;
    @ViewInject(R.id.et_beizhu)
    private EditText et_beizhu;
    @ViewInject(R.id.lv_types)
    private GridView lv_types;

    @ViewInject(R.id.iv_close)
    private ImageView iv_close;

    @ViewInject(R.id.lv_photos)
    private SDProgressPullToRefreshRecyclerView refreshRecyclerView;
    private LiveTipoffTypeAdapter adapter_type;
    private LivePickPhotosAdapter adapter;
    private List<ImageModel> listModel = new ArrayList<>();
    private String to_user_id;
    private int roomid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_tipoff_type);
        x.view().inject(this);
        to_user_id=getIntent().getStringExtra("to_user_id");
        roomid=getIntent().getIntExtra("roomid",0);
        requestTipoff_type();
        register();
    }

    ImageModel imageModel;
    List<ImageModel> list = new ArrayList<>();

    private void register() {
        setAdapter();
        initPullToRefresh();
        btn_confim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(adapter_type.getSelectedId()==-1){
                    SDToast.showToast("请选择举报类型！");
                    return;
                }else
                uploadimages();
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    ArrayList<String> list_url = new ArrayList<>();
    private void uploadimages() {
        if (list.size() > 0) {
            if(null!=list.get(0).getUri()){
                showProgressDialog("正在上传");
                uploadImage(0);
            }
        }else{
            requestTipoff();
        }
    }
    public void onEventMainThread(uploadFinish event) {
        if(event.getStatus()>=(list.size()-1)){
            requestTipoff();
        }else{
            int index=event.getStatus()+1;
            if(null!=list.get(index).getUri()){
                uploadImage(index);
            }else{
                requestTipoff();
            }
        }
    }
    private void uploadImage(final int position) {
        try {
            File file_s = new File(list.get(position).getUri());
            String filePath = file_s.getAbsolutePath();
            String[] dataStr = filePath.split("/");
            String fileTruePath = "/sdcard";
            for (int i = 4; i < dataStr.length; i++) {
                fileTruePath = fileTruePath + "/" + dataStr[i];
            }
            File file = new File(fileTruePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            CommonInterface.requestUploadImage(file, new AppRequestCallback<App_uploadImageActModel>() {
                @Override
                public void onStart() {
                }

                @Override
                protected void onSuccess(SDResponse resp) {
                    if (actModel.getStatus() == 1) {
                        if (!TextUtils.isEmpty(actModel.getServer_full_path())) {
                            list_url.add(actModel.getServer_full_path());
                        } else {
                            SDToast.showToast("图片地址为空");
                        }
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    super.onError(resp);
                    SDToast.showToast("上传失败");
                }

                @Override
                protected void onFinish(SDResponse resp) {
                    super.onFinish(resp);
                    EventBus.getDefault().post(new uploadFinish(position));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    protected void initPullToRefresh() {
        refreshRecyclerView.setMode(PullToRefreshBase.Mode.DISABLED);
    }
    private void setAdapter() {
        refreshRecyclerView.getRefreshableView().setGridVertical(3);
        refreshRecyclerView.setPullToRefreshOverScrollEnabled(false);
        imageModel = new ImageModel();
        imageModel.setSelected(true);
        list.add(imageModel);
        adapter = new LivePickPhotosAdapter(list, getActivity());
        adapter.setClickListener(this);
        refreshRecyclerView.getRefreshableView().setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    // 举报类型列表
    List<App_tipoff_typeModel> types = new ArrayList<>();
    private void requestTipoff_type() {
        CommonInterface.requestTipoff_type(new AppRequestCallback<App_tipoff_typeActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    types.addAll(actModel.getList());
                    adapter_type=new LiveTipoffTypeAdapter(getApplicationContext(),types);
                    lv_types.setNumColumns(2);
                    lv_types.setAdapter(adapter_type);
                } else {
                    SDToast.showToast("举报列表为空");
                }
            }
        });
    }
    public void onEventMainThread(ESelectImage event) {
        if (event.listImage.size() > 0) {
            list.remove(imageModel);
            list.addAll(event.listImage);
            if (list.size() >= 3) {
                has_full = true;
            } else {
                list.add(imageModel);
            }
            adapter.updateData(list);
        }
    }

    private void requestTipoff() {
        CommonInterface.requestTipoff(roomid, to_user_id, adapter_type.getSelectedId(), et_beizhu.getText().toString(), SDJsonUtil.object2Json(list_url), new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    SDToast.showToast("已收到举报消息,我们将尽快落实处理");
                    finish();
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    boolean has_full;

    @Override
    public void clearClick(int position) {
        list.remove(position);
        if (has_full) {
            list.add(imageModel);
            has_full = false;
        }
        adapter.updateData(list);
    }

    @Override
    public void addClick(int position) {
        if (!has_full && list.get(position).isSelected()) {
            Intent intent = new Intent(getActivity(), SelectPhotoActivity.class);
            intent.putExtra("photo_count", 3 - list.size() + 1);
            startActivity(intent);
        }
    }
}
