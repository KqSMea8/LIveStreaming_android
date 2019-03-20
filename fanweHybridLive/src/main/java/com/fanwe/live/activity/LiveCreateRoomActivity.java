package com.fanwe.live.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.map.tencent.SDTencentMapManager;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDHandlerManager;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.common.SDSelectManager.Mode;
import com.fanwe.library.drawable.SDDrawable;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.span.model.MatcherInfo;
import com.fanwe.library.span.utils.SDPatternUtil;
import com.fanwe.library.utils.SDKeyboardUtil;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.common.ImageCropManage;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.ECreateLiveSuccess;
import com.fanwe.live.event.EUpLoadImageComplete;
import com.fanwe.live.model.App_userinfoActModel;
import com.fanwe.live.model.CreateLiveData;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.model.Video_add_videoActModel;
import com.fanwe.live.pop.LiveCreateRoomShareTipsPop;
import com.fanwe.live.utils.GlideUtil;
import com.fanwe.live.utils.PhotoBotShowUtils;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;

import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.fanwe.hybrid.umeng.UmengSocialManager.isQQEnable;
import static com.fanwe.hybrid.umeng.UmengSocialManager.isSinaEnable;
import static com.fanwe.hybrid.umeng.UmengSocialManager.isWeixinEnable;

public class LiveCreateRoomActivity extends BaseActivity implements TextWatcher, TextView.OnEditorActionListener, TencentLocationListener, SurfaceHolder.Callback {

    @ViewInject(R.id.view_close)
    private View view_close;
    @ViewInject(R.id.ll_position_switch)
    private View ll_position_switch;
    @ViewInject(R.id.tv_start_show)
    private TextView tv_start_show;
    @ViewInject(R.id.tv_private_state)
    private TextView tv_private_state;
    @ViewInject(R.id.ll_add_topic)
    private LinearLayout ll_add_topic;
    @ViewInject(R.id.ll_private_show)
    private LinearLayout ll_private_show;
    @ViewInject(R.id.iv_private_lock)
    private ImageView iv_private_lock;
    @ViewInject(R.id.ll_share_layout)
    private LinearLayout ll_share_layout;
    @ViewInject(R.id.et_content_topic)
    private EditText mEditText;
    @ViewInject(R.id.tv_position_text)
    private TextView tv_position_text;
    @ViewInject(R.id.iv_position_icon)
    private ImageView iv_position_icon;
    @ViewInject(R.id.ck_open_pk_room)
    private CheckBox ck_open_pk_room;
    @ViewInject(R.id.iv_share_weibo)
    private ImageView iv_share_weibo;
    @ViewInject(R.id.iv_share_timeline)
    private ImageView iv_share_timeline;
    @ViewInject(R.id.iv_share_wechat)
    private ImageView iv_share_wechat;
    @ViewInject(R.id.iv_share_qq)
    private ImageView iv_share_qq;
    @ViewInject(R.id.iv_share_qqzone)
    private ImageView iv_share_qqzone;
    @ViewInject(R.id.rl_room_image)
    private RelativeLayout rl_room_image;
    @ViewInject(R.id.iv_room_image)
    private ImageView iv_room_image;
    private String image_path;
    private PhotoHandler mHandler;

    private SDSelectManager<ImageView> mManagerSelect;

    private LiveCreateRoomShareTipsPop mPopTips;

    private boolean isFirstTime = true;

    private int isPrivate = 0;
    private int isLocate = 1;

    private String mTopic = "";
    private int mCateId;
    private int mLengthTopic;

    private static final int DEFAULT_TOPIC_LENGTH = 20;

    private boolean isExtraTopic = false;

    private ShareTypeEnum shareTypeEnum = ShareTypeEnum.NONE;

    private PopTipsRunnable mPopRunnable;

    private boolean isInAddVideo = false;//是否已经发起直播请求

    private final static boolean TOPIC_CAN_EMPTY = true;//是否允许话题为空

    /**
     * 话题(String)
     */
    public static String EXTRA_TITLE = "extra_title";
    /**
     * 话题ID(int)
     */
    public static String EXTRA_CATE_ID = "extra_cate_id";
    private SurfaceHolder holder;
    private Camera camera;//声明相机
    private App_userinfoActModel app_userinfoActModel;
    private Boolean isLoading = true;
    int is_authentication = 0;//认证

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.act_live_create_room);
        mEditText.addTextChangedListener(this);
        getExtraData(getIntent());
        init();
    }

    /**
     * 获取activity传递的数据
     *
     * @param extraIntent
     */
    private void getExtraData(Intent extraIntent) {
        Bundle bundle = extraIntent.getExtras();
        if (bundle != null) {
            mTopic = bundle.getString(EXTRA_TITLE);
            mCateId = bundle.getInt(EXTRA_CATE_ID);
            isExtraTopic = true;
            if (mTopic.length() > DEFAULT_TOPIC_LENGTH) {
                mLengthTopic = mTopic.length();//扩展最大话题字符数
                mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mLengthTopic)});
            }
            mEditText.setText(mTopic);
            mEditText.setSelection(mEditText.getText().toString().length());
        }
    }

    private void init() {
        request();
        initView();
        setShareShow();
        showLocation();
        mPopRunnable = new PopTipsRunnable();
        mManagerSelect = new SDSelectManager<>();
        mHandler = new PhotoHandler(this);
        mHandler.setListener(new PhotoHandler.PhotoHandlerListener() {
            @Override
            public void onResultFromAlbum(File file) {
                openCropAct(file);
                OpenCamera();
            }

            @Override
            public void onResultFromCamera(File file) {
                openCropAct(file);
            }

            @Override
            public void onFailure(String msg) {
                OpenCamera();
            }
        });
        mManagerSelect.setMode(Mode.SINGLE);
        mManagerSelect.addSelectCallback(mManagerListener);
        mManagerSelect.setItems(new ImageView[]{iv_share_weibo, iv_share_timeline, iv_share_wechat, iv_share_qq, iv_share_qqzone});
        ck_open_pk_room.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    ck_open_pk_room.setTextColor(getResources().getColor(R.color.white));
                    ck_open_pk_room.setText("关闭PK房");
                }else{
                    ck_open_pk_room.setTextColor(getResources().getColor(R.color.color_create_room_gray));
                    ck_open_pk_room.setText("开启PK房");
                }
            }
        });
    }

    private void initView() {
        tv_start_show.setTextColor(SDDrawable.getStateListColor(SDResourcesUtil.getColor(R.color.main_color), SDResourcesUtil.getColor(R.color.white)));
        view_close.setOnClickListener(this);
        ll_position_switch.setOnClickListener(this);
        tv_start_show.setOnClickListener(this);
        ll_add_topic.setOnClickListener(this);
        ll_private_show.setOnClickListener(this);

        mEditText.setOnEditorActionListener(this);
        iv_share_weibo.setOnClickListener(this);
        iv_share_timeline.setOnClickListener(this);
        iv_share_wechat.setOnClickListener(this);
        iv_share_qq.setOnClickListener(this);
        iv_share_qqzone.setOnClickListener(this);
        rl_room_image.setOnClickListener(this);
        mPopTips = new LiveCreateRoomShareTipsPop(this);
    }

    /**
     * 根据后台判断显示对应分享平台
     */
    private void setShareShow() {
        if (isSinaEnable()) {
            SDViewUtil.setVisible(iv_share_weibo);
        } else {
            SDViewUtil.setGone(iv_share_weibo);
        }

        if (isQQEnable()) {
            SDViewUtil.setVisible(iv_share_qq);
            SDViewUtil.setVisible(iv_share_qqzone);
        } else {
            SDViewUtil.setGone(iv_share_qq);
            SDViewUtil.setGone(iv_share_qqzone);
        }

        if (isWeixinEnable()) {
            SDViewUtil.setVisible(iv_share_timeline);
            SDViewUtil.setVisible(iv_share_wechat);
        } else {
            SDViewUtil.setGone(iv_share_timeline);
            SDViewUtil.setGone(iv_share_wechat);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (isFirstTime) {
            isFirstTime = false;
        }

    }

    /**
     * 初始化页面 默认定位，展示地址
     */
    private void showLocation() {
        String city = getCity();
        String province = getProvince();
//        String city = "";
//        String province = "";
        if (TextUtils.isEmpty(city) && TextUtils.isEmpty(province))//假如本地未缓存有效的地址，切换定位图标
        {
            //开启定位
            startLocate();
        } else
            tv_position_text.setText(province + city);
    }

    /**
     * 开启定位
     */
    private void startLocate() {
        tv_position_text.setText("正在定位");
        SDTencentMapManager.getInstance().startLocation(false, this);
    }

    /**
     * 打开图片裁剪页面
     *
     * @param file
     */
    private void openCropAct(File file) {
        if (AppRuntimeWorker.getOpen_sts() == 1) {
            ImageCropManage.startCropActivity(this, file.getAbsolutePath());
        } else {
            Intent intent = new Intent(this, LiveUploadImageActivity.class);
            intent.putExtra(LiveUploadImageActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            request();
        } else {
            mHandler.onActivityResult(requestCode, resultCode, data);
            ImageCropManage.onActivityResult(this, requestCode, resultCode, data);
        }

    }

    /**
     * 接收图片选择回传地址事件
     *
     * @param event
     */
    public void onEventMainThread(EUpLoadImageComplete event) {
        if (!TextUtils.isEmpty(event.server_full_path)) {
            image_path = event.server_path;
            GlideUtil.load(event.server_full_path).into(iv_room_image);
        }
        OpenCamera();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_position_switch:
                setPositionSwitch();
                break;
            case R.id.view_close:
                finish();
                break;
            case R.id.ll_add_topic:
                startTopicActivity();
                break;
            case R.id.ll_private_show:
                setShowPrivate();
                break;
            case R.id.tv_start_show:
                if (!isLoading) {
                    requestCreatetLive();
                }
                break;
            case R.id.iv_share_weibo:
                mManagerSelect.performClick(iv_share_weibo);
                break;
            case R.id.iv_share_timeline:
                mManagerSelect.performClick(iv_share_timeline);
                break;
            case R.id.iv_share_wechat:
                mManagerSelect.performClick(iv_share_wechat);
                break;
            case R.id.iv_share_qq:
                mManagerSelect.performClick(iv_share_qq);
                break;
            case R.id.iv_share_qqzone:
                mManagerSelect.performClick(iv_share_qqzone);
                break;
            case R.id.rl_room_image:
                PhotoBotShowUtils.openBotPhotoView(this, mHandler, PhotoBotShowUtils.DIALOG_ALBUM);
            default:
                break;
        }
    }

    /**
     * 请求创建直播间
     */
    private int isLivePK=0;//是否是pk直播 1为true 0 false
    private void requestCreatetLive() {
        if (isInAddVideo) {
            return;
        }
        if (!TOPIC_CAN_EMPTY) {
            if (TextUtils.isEmpty(getTopic())) {
                SDToast.showToast("请为直播间添加话题");
                return;
            }
        }
        isInAddVideo = true;
        if(ck_open_pk_room.isChecked()){
            isLivePK=1;
        }else{
            isLivePK=0;
        }
        CommonInterface.requestAddVideo(isLivePK,image_path, getTopic(), getCateId(), getCity(), getProvince(), getShareType(), isLocate, isPrivate, new AppRequestCallback<Video_add_videoActModel>() {
            @Override
            protected void onSuccess(SDResponse resp) {
                dismissProgressDialog();
                if (actModel.isOk()) {
                    CreateLiveData data = new CreateLiveData();
                    data.setRoomId(actModel.getRoom_id());
                    data.setSdkType(actModel.getSdk_type());
                    AppRuntimeWorker.createLive(data, LiveCreateRoomActivity.this);
                }else{
                    if(is_authentication!=2){
                        Intent intent = new Intent(getActivity(), UserCenterAuthentActivity.class);
                        startActivityForResult(intent,10);
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                SDToast.showToast("请求房间id失败");
                dismissProgressDialog();
                super.onError(resp);
            }

            @Override
            protected void onFinish(SDResponse resp) {
                isInAddVideo = false;
                super.onFinish(resp);
            }
        });
    }

    /**
     * 获取输入框内的话题
     *
     * @return
     */
    private String getTopic() {
        return mEditText.getText().toString().trim();
    }

    /**
     * 获取话题ID
     *
     * @return
     */
    private int getCateId() {
        if (TextUtils.equals(getTopic(), mTopic)) {
            return mCateId;
        }
        return 0;
    }

    /**
     * 获取城市
     *
     * @return
     */
    private String getCity() {
        if (isLocate == 1) {
            String city = SDTencentMapManager.getInstance().getCity();
            return !TextUtils.isEmpty(city) ? city : "";
        }
        return "";
    }

    /**
     * 获取省份
     *
     * @return
     */
    private String getProvince() {
        if (isLocate == 1) {
            String province = SDTencentMapManager.getInstance().getProvince();
            return !TextUtils.isEmpty(province) ? province : "";
        }
        return "";
    }

    /**
     * 获取分享类型
     *
     * @return
     */
    private String getShareType() {
        if (isPrivate == 0) {
            switch (shareTypeEnum) {
                case WEIBO_ON:
                    return "sina";
                case TIMELINE_ON:
                    return "weixin_circle";
                case WECHAT_ON:
                    return "weixin";
                case QQ_ON:
                    return "qq";
                case QQZONE_ON:
                    return "qzone";
                default:
                    return null;
            }
        }
        return null;
    }

    /**
     * 设置定位开关，并改变相应图片
     */
    private void setPositionSwitch() {
        if (isLocate == 1) {
            isLocate = 0;
            iv_position_icon.setImageResource(R.drawable.create_room_position_close);
            tv_position_text.setText("");
            SDTencentMapManager.getInstance().stopLocation();//停止定位
        } else {
            isLocate = 1;
            iv_position_icon.setImageResource(R.drawable.create_room_position_open);
            showLocation();
        }
    }

    /**
     * 设置私密直播，并改变相应图片
     */
    private void setShowPrivate() {
        if (isPrivate == 0) {
            isPrivate = 1;
            SDViewUtil.setTextViewColorResId(tv_private_state, R.color.white);
            SDViewUtil.setInvisible(ll_share_layout);
            iv_private_lock.setImageResource(R.drawable.create_room_lock_off);
        } else {
            isPrivate = 0;
            SDViewUtil.setTextViewColorResId(tv_private_state, R.color.textview_gray);
            SDViewUtil.setVisible(ll_share_layout);
            mManagerSelect.clearSelected();
            iv_private_lock.setImageResource(R.drawable.create_room_lock_on);
        }
    }

    private SDSelectManager.SelectCallback<ImageView> mManagerListener = new SDSelectManager.SelectCallback<ImageView>() {

        @Override
        public void onNormal(int index, ImageView item) {
            changeShareImage(item);

        }

        @Override
        public void onSelected(int index, ImageView item) {
            changeShareImage(item);
        }
    };

    /**
     * 更换分享状态改变的图片
     *
     * @param view
     */
    private void changeShareImage(ImageView view) {
        shareTypeEnum = ShareTypeEnum.NONE;
        if (mManagerSelect.isSelected(view)) {
            change2PressImage(view);
            showShareTipsPop(view);
            SDHandlerManager.getMainHandler().removeCallbacks(mPopRunnable);
            SDHandlerManager.getMainHandler().postDelayed(mPopRunnable, 1500);

        } else {
            change2NormalImage(view);
            mPopTips.dismiss();
        }
    }

    private void showShareTipsPop(View view) {
        switch (shareTypeEnum) {
            case WEIBO_ON:
                mPopTips.showPopTips("微博分享已开启", view);
                break;
            case TIMELINE_ON:
                mPopTips.showPopTips("朋友圈分享已开启", view);
                break;
            case WECHAT_ON:
                mPopTips.showPopTips("微信分享已开启", view);
                break;
            case QQ_ON:
                mPopTips.showPopTips("QQ分享已开启", view);
                break;
            case QQZONE_ON:
                mPopTips.showPopTips("QQ空间分享已开启", view);
                break;
            default:
                break;
        }
    }

    private void change2PressImage(View view) {
        switch (view.getId()) {
            case R.id.iv_share_weibo:
                shareTypeEnum = ShareTypeEnum.WEIBO_ON;
                iv_share_weibo.setImageResource(R.drawable.create_room_weibo_on);
                break;
            case R.id.iv_share_timeline:
                shareTypeEnum = ShareTypeEnum.TIMELINE_ON;
                iv_share_timeline.setImageResource(R.drawable.create_room_moments_on);
                break;
            case R.id.iv_share_wechat:
                shareTypeEnum = ShareTypeEnum.WECHAT_ON;
                iv_share_wechat.setImageResource(R.drawable.create_room_wechat_on);
                break;
            case R.id.iv_share_qq:
                shareTypeEnum = ShareTypeEnum.QQ_ON;
                iv_share_qq.setImageResource(R.drawable.create_room_qq_on);
                break;
            case R.id.iv_share_qqzone:
                shareTypeEnum = ShareTypeEnum.QQZONE_ON;
                iv_share_qqzone.setImageResource(R.drawable.create_room_qqzone_on);
                break;
            default:
                break;
        }
    }

    private void change2NormalImage(View view) {
        switch (view.getId()) {
            case R.id.iv_share_weibo:
                iv_share_weibo.setImageResource(R.drawable.create_room_weibo_off);
                break;
            case R.id.iv_share_timeline:
                iv_share_timeline.setImageResource(R.drawable.create_room_moments_off);
                break;
            case R.id.iv_share_wechat:
                iv_share_wechat.setImageResource(R.drawable.create_room_wechat_off);
                break;
            case R.id.iv_share_qq:
                iv_share_qq.setImageResource(R.drawable.create_room_qq_off);
                break;
            case R.id.iv_share_qqzone:
                iv_share_qqzone.setImageResource(R.drawable.create_room_qqzone_off);
                break;
            default:
                break;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        OpenCamera();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //   OpenCamera();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }


    private class PopTipsRunnable implements Runnable {
        @Override
        public void run() {
            if (mPopTips.isShowing()) {
                mPopTips.dismiss();
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, s.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        List<MatcherInfo> listMatch = SDPatternUtil.findMatcherInfo("#[^#]+#", s.toString());
        if (!listMatch.isEmpty()) {
            for (MatcherInfo info : listMatch) {
                ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.main_color));
                s.setSpan(span, info.getStart(), info.getEnd(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (isExtraTopic) {
            int et_length = mEditText.getText().length();
            if (et_length > DEFAULT_TOPIC_LENGTH) {
                mLengthTopic = et_length;
            } else {
                mLengthTopic = DEFAULT_TOPIC_LENGTH;
            }
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mLengthTopic)});
        }
        if (s.length() > 0 && before == 0 && count == 1) {
            if (s.subSequence(start, start + 1).toString().equals("#")) {
                // "#"不能直接输入到输入框内，跳转至话题界面
                mEditText.setText(s.subSequence(0, start).toString() + s.subSequence(start + 1, s.length()).toString());
                mEditText.setSelection(start);
                startTopicActivity();
            }
        }
    }

    private void startTopicActivity() {
        Intent intent = new Intent(this, LiveCreateRoomTopicActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getExtraData(intent);
    }

    public void onEventMainThread(ECreateLiveSuccess event) {
        finish();
    }

    /**
     * 分享方式枚举
     */
    private enum ShareTypeEnum {
        WEIBO_ON, TIMELINE_ON, WECHAT_ON, QQ_ON, QQZONE_ON, NONE;
    }


    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
        TencentLocation location = SDTencentMapManager.getInstance().getLocation();
        if (isLocate == 1) {
            if (location != null) {
                tv_position_text.setText(tencentLocation.getProvince() + tencentLocation.getCity());
            } else {
                tv_position_text.setText("定位失败");
//                setPositionSwitch();
            }
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        SDKeyboardUtil.hideKeyboard(v);
        if (event == null) {
            return true;
        }
        return (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);
    }

    @Override
    protected void onDestroy() {

        if (mPopTips != null) {
            SDHandlerManager.getMainHandler().removeCallbacks(mPopRunnable);
            mPopTips = null;
        }
        super.onDestroy();
    }

    public void OpenCamera() {
        if (camera == null) {
            camera = getCamera();
            try {
                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            try {
                camera = getCamera();
                camera.setPreviewDisplay(holder);//通过surfaceview显示取景画面
                camera.startPreview();//开始预览
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void request() {
        CommonInterface.requestMyUserInfo(new AppRequestCallback<App_userinfoActModel>() {

            @Override
            protected void onSuccess(SDResponse resp) {
                isLoading = false;
                if (actModel.getStatus() == 1) {
                    app_userinfoActModel = actModel;
                    UserModelDao.insertOrUpdate(actModel.getUser());
                    bindNormalData(actModel.getUser());
                }
            }
        });
    }

    private void bindNormalData(UserModel user) {
        if (user != null) {
            is_authentication = user.getIs_authentication();
        }
    }

    private Camera getCamera() {
        //调用前置摄像头的代码；
        //如果想后置摄像头，可直接Camera.open();默认就是后置的
        Camera.CameraInfo info = new Camera.CameraInfo();
        int count = Camera.getNumberOfCameras();
        for (int i = 0; i < count; i++) {
            Camera.getCameraInfo(i, info);
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {

                    //打开前置相机
                    camera = Camera.open(i);
                    camera.setDisplayOrientation(90);

                } catch (RuntimeException e) {
// TODO: handle exception
                }
            }

        }

        return camera;
    }

    //横竖屏切换
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //横屏
            camera.setDisplayOrientation(0);
        } else {
            //竖屏
            camera.setDisplayOrientation(90);
        }
    }
}
