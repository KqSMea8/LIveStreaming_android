package com.fanwe.live.fragment;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;

/**
 * Created by Administrator on 2016/10/19.普通上传图片
 */

public class LiveUpLoadImageOssFragment extends LiveUpLoadImageOssBaseFragment
{
    @Override
    protected void onOssSuccess(PutObjectRequest request, PutObjectResult result, String server_path, String server_full_path, String local_path)
    {
        super.onOssSuccess(request, result, server_path, server_full_path, local_path);
    }

    @Override
    protected void onOssFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1)
    {
        super.onOssFailure(putObjectRequest, e, e1);
    }
}
