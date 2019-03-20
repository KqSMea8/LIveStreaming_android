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
import com.fanwe.live.event.EUpLoadImageComplete;
import com.fanwe.live.model.App_uploadImageActModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Administrator on 2016/7/25.
 */
public class LiveUploadImageActivity extends BaseTitleActivity
{
    public static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";

    @ViewInject(R.id.crop_imageview)
    private LiveCropImageView liveCropView;

    private String mStrUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_upload_image);
        init();
    }

    private void init()
    {
        initTitle();
        getIntentData();
        initCropView();
    }

    private void initTitle()
    {
        mTitle.setMiddleTextTop("上传图片");
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("上传");
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
                showProgressDialog("正在处理图片");
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
                dismissProgressDialog();
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
                    String path = actModel.getPath();
                    if (!TextUtils.isEmpty(path))
                    {
                        EUpLoadImageComplete event = new EUpLoadImageComplete();
                        event.server_full_path=actModel.getServer_full_path();
                        event.server_path = path;
                        event.local_path = mStrUrl;
                        SDEventManager.post(event);
                        finish();
                    } else
                    {
                        SDToast.showToast("图片地址为空");
                    }
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
}
