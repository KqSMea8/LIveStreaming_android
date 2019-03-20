package com.fanwe.shortvideo.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.dialog.SDDialogProgress;
import com.fanwe.live.R;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.shortvideo.common.utils.FileUtils;
import com.fanwe.shortvideo.common.utils.TCConstants;
import com.fanwe.shortvideo.editor.TCVideoPreprocessActivity;
import com.fanwe.shortvideo.model.SignModel;
import com.fanwe.shortvideo.videoupload.TXUGCPublish;
import com.fanwe.shortvideo.videoupload.TXUGCPublishTypeDef;
import com.fanwei.jubaosdk.common.util.ToastUtil;
import com.sina.weibo.sdk.component.view.LoadingBar;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXVodPlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.util.Locale;


public class UpLoadVideoActivity extends BaseActivity implements View.OnClickListener,ITXLivePlayListener,TencentLocationListener {
    private static String TAG = "UpLoadVideoActivity";
    @ViewInject(R.id.tv_position_text)
    private TextView tv_position_text;
    @ViewInject(R.id.iv_position_icon)
    private ImageView iv_position_icon;
    @ViewInject(R.id.bg_video_view)
    private TXCloudVideoView bg_video_view;
    @ViewInject(R.id.img_back)
    private ImageView img_back;
    @ViewInject(R.id.img_edit)
    private ImageView img_edit;
    @ViewInject(R.id.edit_video_comment)
    private EditText edit_video_comment;
    @ViewInject(R.id.img_video_cover)
    private ImageView img_video_cover;
    @ViewInject(R.id.tv_save_local)
    private TextView tv_save_local;
    @ViewInject(R.id.tv_upload_video)
    private TextView tv_upload_video;
    private int record_type;
    private String videoPath;
    private String coverPath;
    private String resolution;
    private long duration;
    private TXVodPlayer mVodPlayer;

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_back:
                clickBack();
                break;
            case R.id.img_edit:
                startEditVideo();
                break;
            case R.id.tv_save_local:
                downloadRecord();
                break;
            case R.id.tv_upload_video:
                requestSignData();
                break;
            case R.id.iv_position_icon:
                setPositionSwitch();
                break;
        }
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
            iv_position_icon.setImageResource(R.drawable.create_room_position_open);
            startLocate();
        }
    }
    String latitude,longitude;
    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        TencentLocation location = SDTencentMapManager.getInstance().getLocation();
        if (isLocate == 1) {
            if (location != null) {
                tv_position_text.setText(tencentLocation.getProvince() + tencentLocation.getCity());
                latitude=location.getLatitude()+"";
                longitude=location.getLongitude()+"";
            } else {
                tv_position_text.setText("定位失败");
            }
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    /**
     * 开启定位
     */
    private void startLocate() {
        tv_position_text.setText("正在定位");
        SDTencentMapManager.getInstance().startLocation(true, this);
    }
    private void clickBack() {
        finish();
//        new AlertDialog.Builder(this).setMessage("删除并重新录制？")
//                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//
//                    }
//                }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                deleteVideo();
//            }
//        }).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_video);
        x.view().inject(this);
        getIntentData();
        initView();
        initListener();
    }
    int type;
    private void getIntentData() {
        record_type = getIntent().getIntExtra(TCConstants.VIDEO_RECORD_TYPE, 0);
        videoPath = getIntent().getStringExtra(TCConstants.VIDEO_EDITER_PATH);
        coverPath = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_COVERPATH);
        resolution = getIntent().getStringExtra(TCConstants.VIDEO_RECORD_RESOLUTION);
        duration = getIntent().getLongExtra(TCConstants.VIDEO_RECORD_DURATION, 0);
        type=getIntent().getIntExtra(TCConstants.PUBLISH_TYPE,0);
    }
    private void initView() {
        File file = new File(coverPath);
        GlideUtil.load(file).into(img_video_cover);
        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        mVodPlayer.setPlayListener(this);
        //关键player对象与界面view
        mVodPlayer.setPlayerView(bg_video_view);
        mVodPlayer.startPlay(videoPath);
    }

    private void initListener() {
        img_back.setOnClickListener(this);
        img_edit.setOnClickListener(this);
        tv_save_local.setOnClickListener(this);
        tv_upload_video.setOnClickListener(this);
        iv_position_icon.setOnClickListener(this);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mVodPlayer.resume();
        bg_video_view.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        bg_video_view.onDestroy();
    }

    private void loadVideo(String videoUrl) {

        mVodPlayer.stopPlay(true); // true代表清除最后一帧画面
        //创建player对象
        mVodPlayer = new TXVodPlayer(getActivity());
        mVodPlayer.setPlayListener(this);
        //关键player对象与界面view
        mVodPlayer.setPlayerView(bg_video_view);
        mVodPlayer.startPlay(videoUrl);
    }

    private void deleteVideo() {
        //删除文件
        FileUtils.deleteFile(videoPath);
        FileUtils.deleteFile(coverPath);
        deleteMediaStore(coverPath);
        finish();
    }
    private void deleteMediaStore(String path){
        String where = MediaStore.Video.Media.DATA + " = '" + path+"'";
        this.getContentResolver().delete(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,where,null);

    }
    private void startEditVideo() {
        Intent intent = new Intent(this, TCVideoPreprocessActivity.class);
        intent.putExtra(TCConstants.VIDEO_RECORD_TYPE, TCConstants.VIDEO_RECORD_TYPE_EDIT);
        intent.putExtra(TCConstants.VIDEO_EDITER_PATH, videoPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_COVERPATH, coverPath);
        intent.putExtra(TCConstants.VIDEO_RECORD_RESOLUTION, resolution);
        startActivity(intent);
        finish();
    }

    private void downloadRecord() {
        File file = new File(videoPath);
        if (file.exists()) {
            try {
                File newFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + File.separator + file.getName());
//                if (!newFile.exists()) {
//                    newFile = new File(getExternalFilesDir(Environment.DIRECTORY_DCIM).getPath() + File.separator + file.getName());
//                }

                file.renameTo(newFile);
                videoPath = newFile.getAbsolutePath();

                ContentValues values = initCommonContentValues(newFile);
                values.put(MediaStore.Video.VideoColumns.DATE_TAKEN, System.currentTimeMillis());
                values.put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Video.VideoColumns.DURATION, duration);//时长
                this.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);

                insertVideoThumb(newFile.getPath(), coverPath);
            } catch (Exception e) {
                e.printStackTrace();
            }
            finish();
        }
    }

    /**
     * 插入视频缩略图
     *
     * @param videoPath
     * @param coverPath
     */
    private void insertVideoThumb(String videoPath, String coverPath) {
        //以下是查询上面插入的数据库Video的id（用于绑定缩略图）
        //根据路径查询
        Cursor cursor = getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Thumbnails._ID},//返回id列表
                String.format("%s = ?", MediaStore.Video.Thumbnails.DATA), //根据路径查询数据库
                new String[]{videoPath}, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                String videoId = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Thumbnails._ID));
                //查询到了Video的id
                ContentValues thumbValues = new ContentValues();
                thumbValues.put(MediaStore.Video.Thumbnails.DATA, coverPath);//缩略图路径
                thumbValues.put(MediaStore.Video.Thumbnails.VIDEO_ID, videoId);//video的id 用于绑定
                //Video的kind一般为1
                thumbValues.put(MediaStore.Video.Thumbnails.KIND,
                        MediaStore.Video.Thumbnails.MINI_KIND);
                //只返回图片大小信息，不返回图片具体内容
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                Bitmap bitmap = BitmapFactory.decodeFile(coverPath, options);
                if (bitmap != null) {
                    thumbValues.put(MediaStore.Video.Thumbnails.WIDTH, bitmap.getWidth());//缩略图宽度
                    thumbValues.put(MediaStore.Video.Thumbnails.HEIGHT, bitmap.getHeight());//缩略图高度
                    if (!bitmap.isRecycled()) {
                        bitmap.recycle();
                    }
                }
                this.getContentResolver().insert(
                        MediaStore.Video.Thumbnails.EXTERNAL_CONTENT_URI, //缩略图数据库
                        thumbValues);
            }
            cursor.close();
        }
    }

    private static ContentValues initCommonContentValues(File saveFile) {
        ContentValues values = new ContentValues();
        long currentTimeInSeconds = System.currentTimeMillis();
        values.put(MediaStore.MediaColumns.TITLE, saveFile.getName());
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, saveFile.getName());
        values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATE_ADDED, currentTimeInSeconds);
        values.put(MediaStore.MediaColumns.DATA, saveFile.getAbsolutePath());
        values.put(MediaStore.MediaColumns.SIZE, saveFile.length());

        return values;
    }

    private void publish(String sign) {
        TXUGCPublish txugcPublish = new TXUGCPublish(this.getApplicationContext(), "customID");
        txugcPublish.setListener(new TXUGCPublishTypeDef.ITXVideoPublishListener() {
            @Override
            public void onPublishProgress(long uploadBytes, long totalBytes) {

            }

            @Override
            public void onPublishComplete(TXUGCPublishTypeDef.TXPublishResult result) {
                requestData(result.videoURL,result.coverURL);
            }
        });

        TXUGCPublishTypeDef.TXPublishParam param = new TXUGCPublishTypeDef.TXPublishParam();
        // signature计算规则可参考 https://www.qcloud.com/document/product/266/9221
        param.signature = sign;
        param.videoPath = videoPath;
        param.coverPath = coverPath;
        txugcPublish.publishVideo(param);
    }
    SDDialogProgress progress;
    protected void requestSignData() {
        if(edit_video_comment.getText().toString().trim().length()==0){
            ToastUtil.showToast(UpLoadVideoActivity.this,"描述不能为空！", Toast.LENGTH_LONG);
            return;
        }
        if(null==progress){
            progress=new SDDialogProgress(getActivity());
            progress.setCanceledOnTouchOutside(false);
            progress.setTextMsg("发布视频中！");
            progress.show();
        }
        CommonInterface.requestUpLoadSign(new AppRequestCallback<SignModel>() {
            @Override
            protected void onSuccess(SDResponse sdResponse) {
                if (actModel.isOk()) {
                    publish(actModel.sign);
                    return;
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
                progress.dismiss();
            }

            @Override
            protected void onFinish(SDResponse resp) {
                super.onFinish(resp);
            }
        });

    }

    protected void requestData(String videoPath,String coverPath) {
        //1 发布小视频动态
        if(type==1){
            CommonInterface.requestCommentVideo(edit_video_comment.getText().toString(),latitude,longitude,coverPath,videoPath, new AppRequestCallback<BaseActModel>() {
                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if (actModel.isOk()) {
                        ToastUtil.showToast(UpLoadVideoActivity.this,"发布成功", Toast.LENGTH_LONG);
                        progress.dismiss();
                        finish();
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    super.onError(resp);
                    progress.dismiss();
                }

                @Override
                protected void onFinish(SDResponse resp) {
                    super.onFinish(resp);
                }
            });
        }
        else{
            CommonInterface.requestUpLoadVideo(videoPath, coverPath, edit_video_comment.getText().toString(), new AppRequestCallback<BaseActModel>() {
                @Override
                protected void onSuccess(SDResponse sdResponse) {
                    if (actModel.isOk()) {
                        ToastUtil.showToast(UpLoadVideoActivity.this,"发布成功", Toast.LENGTH_LONG);
                        progress.dismiss();
                        finish();
                    }
                }

                @Override
                protected void onError(SDResponse resp) {
                    super.onError(resp);
                    progress.dismiss();
                }

                @Override
                protected void onFinish(SDResponse resp) {
                    super.onFinish(resp);
                }
            });
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBack();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }

    @Override
    public void onPlayEvent(int i, Bundle bundle) {
        if (i == TXLiveConstants.PLAY_EVT_PLAY_END) {
           loadVideo(videoPath);
        }
    }

    @Override
    public void onNetStatus(Bundle bundle) {

    }
}
