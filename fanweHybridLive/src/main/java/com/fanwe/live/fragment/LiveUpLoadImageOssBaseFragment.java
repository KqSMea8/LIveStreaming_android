package com.fanwe.live.fragment;

import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.fanwe.hybrid.fragment.BaseFragment;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.ImageFileCompresser;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.activity.LiveUpLoadImageOssActivity;
import com.fanwe.live.appview.LiveOssImageView;
import com.fanwe.live.common.AliyunOssManage;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.event.EUpLoadImageComplete;
import com.fanwe.live.model.App_aliyun_stsActModel;
import com.sunday.eventbus.SDEventManager;

import org.xutils.view.annotation.ViewInject;

import java.io.File;

/**
 * Created by Administrator on 2016/10/19.
 */

public class LiveUpLoadImageOssBaseFragment extends BaseFragment
{
    @ViewInject(R.id.oss_imageview)
    private LiveOssImageView oss_imageview;

    private OSS mOss;
    private App_aliyun_stsActModel app_aliyun_stsActModel;
    private String mStrUrl;

    private String fileName;
    private String objectKey;
    private String full_oss_path;

    @Override
    protected int onCreateContentView()
    {
        return R.layout.act_live_up_load_oss_picture;
    }

    @Override
    protected void init()
    {
        super.init();
        getIntentData();
        initCropView();
        requestInitParams();
    }

    private void getIntentData()
    {
        mStrUrl = getActivity().getIntent().getStringExtra(LiveUpLoadImageOssActivity.EXTRA_IMAGE_URL);
        if (isEmpty(mStrUrl))
        {
            SDToast.showToast("图片不存在");
            getActivity().finish();
        }
        File file = new File(mStrUrl);
        if (!file.exists())
        {
            SDToast.showToast("图片不存在");
            getActivity().finish();
        }
    }

    private void initCropView()
    {
        oss_imageview.setImageFileCompresserListener(new ImageFileCompresser.ImageFileCompresserListener()
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
                    LogUtil.e(msg);
                }
                //如果失败则原图上传
                File file = new File(mStrUrl);
                requestUpload(file);
            }

            @Override
            public void onFinish()
            {

            }
        });
        oss_imageview.loadUrl(mStrUrl);
    }

    private void requestUpload(File fileCompressed)
    {
        if (fileCompressed == null)
        {
            return;
        }

        if (!fileCompressed.exists())
        {
            return;
        }

        uploadImage(fileCompressed.getPath());
    }

    private void requestInitParams()
    {
        CommonInterface.requestAliyunSts(new AppRequestCallback<App_aliyun_stsActModel>()
        {
            @Override
            protected void onStart()
            {
                super.onStart();
                showProgressDialog("");
            }

            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                if (actModel.getStatus() == 1)
                {
                    app_aliyun_stsActModel = actModel;
                    mOss = AliyunOssManage.getInstance().init(actModel);
                }
            }

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
                dismissProgressDialog();
            }
        });
    }

    private OSSAsyncTask uploadImage(String uploadFilePath)
    {
        if (mOss == null || app_aliyun_stsActModel == null)
        {
            SDToast.showToast("网络异常");
            getActivity().finish();
            return null;
        }

        showProgressDialog("正在上传图片");

        fileName = System.currentTimeMillis() + ".png";
        objectKey = app_aliyun_stsActModel.getDir() + fileName;
        full_oss_path = app_aliyun_stsActModel.getOss_domain() + objectKey;

        PutObjectRequest put = new PutObjectRequest(app_aliyun_stsActModel.getBucket(), objectKey, uploadFilePath);
        OSSAsyncTask task = mOss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>()
        {
            @Override
            public void onSuccess(PutObjectRequest request, PutObjectResult result)
            {
                dismissProgressDialog();
                String server_path = "./" + objectKey;
                String server_full_path = full_oss_path;
                String local_path = "file://" + mStrUrl;
                onOssSuccess(request, result, server_path, server_full_path, local_path);
                onOssSuccessPath(server_path, server_full_path, local_path);
            }

            @Override
            public void onFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1)
            {
                SDToast.showToast("上传Oss失败");
                dismissProgressDialog();
                onOssFailure(putObjectRequest, e, e1);
            }
        });
        return task;
    }

    protected void onOssSuccess(PutObjectRequest request, PutObjectResult result, String server_path, String server_full_path, String local_path)
    {
        EUpLoadImageComplete event = new EUpLoadImageComplete();
        event.server_path = server_path;
        event.server_full_path = server_full_path;
        event.local_path = local_path;
        SDEventManager.post(event);
        getActivity().finish();
    }

    protected void onOssSuccessPath(String server_path, String server_full_path, String local_path)
    {

    }


    protected void onOssFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1)
    {

    }

    public void upLoadImage()
    {
        oss_imageview.clickUpload();
    }
}
