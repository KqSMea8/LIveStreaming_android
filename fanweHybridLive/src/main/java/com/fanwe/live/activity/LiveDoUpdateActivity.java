package com.fanwe.live.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDSelectManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.handler.PhotoHandler;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.view.SDTabImage;
import com.fanwe.library.view.select.SDSelectViewManager;
import com.fanwe.live.R;
import com.fanwe.live.business.InitBusiness;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.common.ImageCropManage;
import com.fanwe.live.event.EUpLoadImageComplete;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.UserModel;
import com.fanwe.live.utils.GlideUtil;

import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Administrator on 2016/7/6.
 */
public class  LiveDoUpdateActivity extends BaseTitleActivity
{
    public static final String EXTRA_USER_MODEL = "extra_user_model";

    @ViewInject(R.id.tab_male)
    private SDTabImage tabImageMale;
    @ViewInject(R.id.tab_right)
    private SDTabImage tabImageFeMale;

    @ViewInject(R.id.iv_head)
    private ImageView iv_head;
    @ViewInject(R.id.tv_finish)
    private TextView tv_finish;
    @ViewInject(R.id.et_nickname)
    private EditText et_nickname;
    @ViewInject(R.id.tv_text_limit)
    private TextView tv_text_limit;

    private SDSelectViewManager<SDTabImage> mSelectManager = new SDSelectViewManager<SDTabImage>();

    private PhotoHandler mPhotoHandler;

    private UserModel user;

    private String head_image_path;
    private String user_id;
    private String nick_name;
    private String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_live_do_upadte);
        init();
    }

    private void init()
    {
        getIntentInfo();
        register();
        initTitle();
        initTabImageMale();
        initPhotoHandler();
        bindData();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("手机注册");
    }

    private void getIntentInfo()
    {
        if (getIntent().hasExtra(EXTRA_USER_MODEL))
        {
            user = (UserModel) getIntent().getSerializableExtra(EXTRA_USER_MODEL);
            this.user_id = user.getUser_id();
        }
    }

    private void register()
    {
        iv_head.setOnClickListener(this);
        tv_finish.setOnClickListener(this);
        et_nickname.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

            }

            @Override
            public void afterTextChanged(Editable s)
            {
                String text = et_nickname.getText().toString();
                if (!TextUtils.isEmpty(text))
                {
                    int intLength = text.length();
                    String strLength = Integer.toString(intLength);
                    tv_text_limit.setText(strLength);
                } else
                {
                    tv_text_limit.setText("0");
                }
            }
        });
    }

    private void bindData()
    {
        String user_nick_name = user.getNick_name();
        if (!TextUtils.isEmpty(user_nick_name))
        {
            et_nickname.setText(user_nick_name);
        }

        String user_head_image = user.getHead_image();
        if (!TextUtils.isEmpty(user_head_image))
        {
            GlideUtil.loadHeadImage(user_head_image).into(iv_head);
        }
    }

    private void initTabImageMale()
    {
        tabImageMale.getViewConfig(tabImageMale.mIv_image).setImageNormalResId(R.drawable.ic_male).setImageSelectedResId(R.drawable.ic_selected_male);
        tabImageFeMale.getViewConfig(tabImageFeMale.mIv_image).setImageNormalResId(R.drawable.ic_female).setImageSelectedResId(R.drawable.ic_selected_female);

        mSelectManager.addSelectCallback(new SDSelectManager.SelectCallback<SDTabImage>()
        {

            @Override
            public void onNormal(int index, SDTabImage item)
            {
            }

            @Override
            public void onSelected(int index, SDTabImage item)
            {
                switch (index)
                {
                    case 0:
                        click0();
                        break;
                    case 1:
                        click1();
                        break;
                    default:
                        break;
                }
            }

        });

        mSelectManager.setItems(new SDTabImage[]
                {tabImageMale, tabImageFeMale});

        int sex = user.getSex();
        if (sex == 2)
        {
            mSelectManager.performClick(1);
        } else
        {
            mSelectManager.performClick(0);
        }
    }

    private void initPhotoHandler()
    {
        mPhotoHandler = new PhotoHandler(this);
        mPhotoHandler.setListener(new PhotoHandler.PhotoHandlerListener()
        {

            @Override
            public void onResultFromCamera(File file)
            {
                if (file != null && file.exists())
                {
                    dealImageFile(file);
                }
            }

            @Override
            public void onResultFromAlbum(File file)
            {
                if (file != null && file.exists())
                {
                    dealImageFile(file);
                }
            }

            @Override
            public void onFailure(String msg)
            {
                SDToast.showToast(msg);
            }
        });
    }

    private void dealImageFile(File file)
    {
        if (AppRuntimeWorker.getOpen_sts() == 1)
        {
            ImageCropManage.startCropActivity(this, file.getAbsolutePath());
        } else
        {
            Intent intent = new Intent(LiveDoUpdateActivity.this, LiveUploadImageActivity.class);
            intent.putExtra(LiveUploadImageActivity.EXTRA_IMAGE_URL, file.getAbsolutePath());
            startActivity(intent);
        }
    }

    private void click0()
    {
        sex = "1";
    }

    private void click1()
    {
        sex = "2";
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.iv_head:
                clickIvHead();
                break;
            case R.id.tv_finish:
                clickTvFinish();
                break;
        }
    }

    private void clickIvHead()
    {
        showBotDialog();
    }

    private void showBotDialog()
    {
        String[] arrOption = new String[]
                {"拍照", "相册"};

        SDDialogMenu dialog = new SDDialogMenu(this);

        dialog.setItems(arrOption);
        dialog.setmListener(new SDDialogMenu.SDDialogMenuListener()
        {

            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog)
            {
                switch (index)
                {
                    case 0:
                        mPhotoHandler.getPhotoFromCamera();
                        break;
                    case 1:
                        mPhotoHandler.getPhotoFromAlbum();
                        break;
                    default:
                        break;
                }
                dialog.dismiss();
            }

            @Override
            public void onDismiss(SDDialogMenu dialog)
            {
            }

            @Override
            public void onCancelClick(View v, SDDialogMenu dialog)
            {
            }
        });
        dialog.showBottom();
    }

    private void clickTvFinish()
    {
        requestdoUpdate();
    }

    private void requestdoUpdate()
    {
        nick_name = et_nickname.getText().toString();
        if (TextUtils.isEmpty(nick_name))
        {
            SDToast.showToast("请输入昵称");
            return;
        }

        if (nick_name.trim().length() == 0)
        {
            SDToast.showToast("输入的昵称不能全空格");
            return;
        }

        CommonInterface.requestDoUpdate(user_id, nick_name, sex, head_image_path, new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            public void onStart()
            {
                showProgressDialog("正在提交资料");
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    UserModel user = actModel.getUser_info();
                    if (user != null)
                    {
                        if (UserModel.dealLoginSuccess(user, true))
                        {
                            InitBusiness.startMainActivity(LiveDoUpdateActivity.this);
                            dismissProgressDialog();
                            try {
                                InitBusiness.finishLoginActivity();
                                InitBusiness.finishMobileRegisterActivity();
                            } catch (Exception e) {
                                e.printStackTrace();
                                LogUtil.e("LiveDoUpdateActivity:" + e.getMessage());
                            }
                        } else
                        {
                            dismissProgressDialog();
                            SDToast.showToast("保存用户信息失败");
                        }
                    } else
                    {
                        dismissProgressDialog();
                        SDToast.showToast("user_info字段为空");
                    }
                } else {
                    dismissProgressDialog();
                    if (!TextUtils.isEmpty(actModel.getError())) {
                        SDToast.showToast(actModel.getError());
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp) {
                super.onError(resp);
                dismissProgressDialog();
                LogUtil.e(resp.getResult());
                SDToast.showToast(resp.getResult());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        mPhotoHandler.onActivityResult(requestCode, resultCode, data);
        ImageCropManage.onActivityResult(this, requestCode, resultCode, data);
    }

    public void onEventMainThread(EUpLoadImageComplete event)
    {
        this.head_image_path = event.server_path;
        GlideUtil.loadHeadImage(event.server_full_path).into(iv_head);
    }
}
