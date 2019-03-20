package com.fanwe.live.activity;

import android.os.Bundle;
import android.text.TextUtils;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.appview.LiveCropImageView;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EUserImageUpLoadComplete;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.App_uploadImageActModel;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Administrator on 2016/7/14.
 */
public class LiveUploadUserImageActivity extends BaseTitleActivity
{
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    @ViewInject(R.id.crop_imageview)
    private LiveCropImageView liveCropView;

    private String mStrUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_user_head);
        init();
    }

    private void init()
    {
        initTitle();
        getIntentData();
        initCropView();
    }

    private void getIntentData()
    {
        mStrUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        if (isEmpty(mStrUrl))
        {
            SDToast.showToast("图片不存在");
            finish();
        }
        File file = new File(mStrUrl);
        if (!file.exists())
        {
            SDToast.showToast("图片不存在");
            finish();
        }
        mStrUrl = "file://" + mStrUrl;
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("上传头像");
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("上传");
    }

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        liveCropView.clickUpload();
    }

    private void initCropView()
    {
        liveCropView.setImageFileCompresserListener(new ImageFileCompresser.ImageFileCompresserListener()
        {
            @Override
            public void onStart()
            {

            }

            @Override
            public void onSuccess(File fileCompressed)
            {
                requestUpload(fileCompressed);
            }

            @Override
            public void onFailure(String msg)
            {
                if (!TextUtils.isEmpty(msg))
                {
                    SDToast.showToast(msg);
                }
            }

            @Override
            public void onFinish()
            {

            }
        });
        liveCropView.loadUrl(mStrUrl);
    }

    protected void requestUpload(File fileCompressed)
    {
        if (fileCompressed == null)
        {
            return;
        }

        if (!fileCompressed.exists())
        {
            return;
        }

        CommonInterface.requestUploadImage(fileCompressed, new AppRequestCallback<App_uploadImageActModel>()
        {
            @Override
            public void onStart()
            {
                showProgressDialog("正在上传");
            }

            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    String server_path = actModel.getPath();
                    String server_full_path = actModel.getServer_full_path();
                    String local_path = mStrUrl;
                    onSuccessUpLoadImage(server_path, server_full_path, local_path);
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                SDToast.showToast("上传失败");
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    protected void onSuccessUpLoadImage(String server_path, String server_full_path, String local_path)
    {
        if (!TextUtils.isEmpty(server_path))
        {
            requestUpLoadUserImage(server_path);
        } else
        {
            SDToast.showToast("图片地址为空");
        }
    }


    protected void requestUpLoadUserImage(String server_path)
    {
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            SDToast.showToast("user为空");
            return;
        }
        CommonInterface.requestDoUpdateNormal(user.getUser_id(), server_path, new AppRequestCallback<App_do_updateActModel>()
        {
            @Override
            protected void onSuccess(SDResponse resp)
            {
                if (actModel.getStatus() == 1)
                {
                    UserModel user = actModel.getUser_info();
                    if (user != null)
                    {
                        if (!TextUtils.isEmpty(user.getHead_image()))
                        {
                            EUserImageUpLoadComplete event = new EUserImageUpLoadComplete();
                            event.head_image = user.getHead_image();
                            SDEventManager.post(event);
                            finish();
                        } else
                        {
                            SDToast.showToast("图片地址为空");
                        }
                    } else
                    {
                        SDToast.showToast("user为空");
                    }
                }
            }

            @Override
            protected void onError(SDResponse resp)
            {
                super.onError(resp);
                SDToast.showToast("上传失败");
            }
        });
    }
}
