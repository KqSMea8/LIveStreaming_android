package com.weibo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.model.ImageModel;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.SelectPhotoActivity;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.ESelectImage;
import com.fanwe.live.model.App_uploadImageActModel;
import com.fanwe.live.view.SDProgressPullToRefreshRecyclerView;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.weibo.adapter.LivePickPhotosAdapter;
import com.weibo.model.URIBean;
import com.weibo.model.uploadFinish;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2018/8/24 0024.
 */
public class PhotoPublishActivity extends BaseTitleActivity implements LivePickPhotosAdapter.ClickListener, TencentLocationListener {
    @ViewInject(R.id.lv_photos)
    private SDProgressPullToRefreshRecyclerView refreshRecyclerView;
    @ViewInject(R.id.edit_video_comment)
    private EditText edit_video_comment;
    @ViewInject(R.id.iv_position_icon)
    private ImageView iv_position_icon;
    @ViewInject(R.id.tv_position_text)
    private TextView tv_position_text;
    @ViewInject(R.id.tv_publish)
    private TextView tv_publish;
    private LivePickPhotosAdapter adapter;
    protected List<ImageModel> listModel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_phototext);
        x.view().inject(this);
        init();
    }

    public void show() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    ImageModel imageModel;
    List<ImageModel> list = new ArrayList<>();

    private void init() {
        refreshRecyclerView.getRefreshableView().setGridVertical(3);
        refreshRecyclerView.setPullToRefreshOverScrollEnabled(false);
        mTitle.setMiddleTextTop("发布照片");
        imageModel = new ImageModel();
        imageModel.setSelected(true);
        list.add(imageModel);
        adapter = new LivePickPhotosAdapter(list, getActivity());
        adapter.setClickListener(this);
        refreshRecyclerView.getRefreshableView().setAdapter(adapter);
        adapter.notifyDataSetChanged();
        tv_publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadimages();
            }
        });
        iv_position_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPositionSwitch();
            }
        });
    }

    //0代表未定位
    private int isLocate = 0;

    /**
     * 设置定位开关，并改变相应图片
     */
    private void setPositionSwitch() {
        if (isLocate == 1) {
            isLocate = 0;
            iv_position_icon.setImageResource(R.drawable.create_room_position_close);
            tv_position_text.setText("不显示");
            SDTencentMapManager.getInstance().stopLocation();//停止定位
        } else {
            isLocate = 1;
            iv_position_icon.setImageResource(R.drawable.ic_user_location);
            startLocate();
        }
    }

    /**
     * 开启定位
     */
    private void startLocate() {
        tv_position_text.setText("正在定位");
        SDTencentMapManager.getInstance().startLocation(true, this);
    }

    public void onEventMainThread(ESelectImage event) {
        if (event.listImage.size() > 0) {
            list.remove(imageModel);
            list.addAll(event.listImage);
            if (list.size() >= 9) {
                has_full = true;
            } else {
                list.add(imageModel);
            }
            adapter.updateData(list);
        }
    }

    ArrayList<URIBean> list_url = new ArrayList<>();
    private void uploadimages() {
        if (list.size() > 0) {
            if(null!=list.get(0).getUri()){
                showProgressDialog("正在上传");
                uploadImage(0);
            }
        }else{
            clickPublish();
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
                            list_url.add(new URIBean(actModel.getServer_full_path(), "0"));
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
    public void onEventMainThread(uploadFinish event) {
        if(event.getStatus()>=(list.size()-1)){
            clickPublish();
        }else{
            int index=event.getStatus()+1;
            if(null!=list.get(index).getUri()){
                uploadImage(index);
            }else{
                clickPublish();
            }
        }
    }


    String latitude, longitude;

    private void clickPublish() {
        String str=null;
        if(!"定位失败".equals(tv_position_text.getText().toString())&&!"".equals(tv_position_text.getText().toString())){
            str=tv_position_text.getText().toString();
        }
        CommonInterface.requestCommentImageText(edit_video_comment.getText().toString(), latitude, longitude, str, SDJsonUtil.object2Json(list_url), new AppRequestCallback<BaseActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                if (actModel.getStatus() == 1) {
                    Toast.makeText(getActivity(), "发布成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
                dismissProgressDialog();
                finish();
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
            intent.putExtra("photo_count", 9 - list.size() + 1);
            getActivity().startActivity(intent);
        }
    }

    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        TencentLocation location = SDTencentMapManager.getInstance().getLocation();
        if (isLocate == 1) {
            if (location != null) {
                tv_position_text.setText(tencentLocation.getProvince() + tencentLocation.getCity());
                latitude = location.getLatitude() + "";
                longitude = location.getLongitude() + "";
            } else {
                tv_position_text.setText("定位失败");
            }
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }
}