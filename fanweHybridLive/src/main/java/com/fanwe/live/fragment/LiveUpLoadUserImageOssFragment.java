package com.fanwe.live.fragment;

import android.text.TextUtils;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.dao.UserModelDao;
import com.fanwe.live.event.EUserImageUpLoadComplete;
import com.fanwe.live.model.App_do_updateActModel;
import com.fanwe.live.model.UserModel;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Administrator on 2016/10/19.上传用户头像
 */

public class LiveUpLoadUserImageOssFragment extends LiveUpLoadImageOssBaseFragment
{
    @Override
    protected void onOssSuccess(PutObjectRequest request, PutObjectResult result, String server_path, String server_full_path, String local_path)
    {

    }

    @Override
    protected void onOssFailure(PutObjectRequest putObjectRequest, ClientException e, ServiceException e1)
    {
        super.onOssFailure(putObjectRequest, e, e1);
    }

    @Override
    protected void onOssSuccessPath(String server_path, String server_full_path, String local_path)
    {
        super.onOssSuccessPath(server_path, server_full_path, local_path);
        requestUpLoadUserImage(server_path);
    }

    protected void requestUpLoadUserImage(String server_path)
    {
        UserModel user = UserModelDao.query();
        if (user == null)
        {
            SDToast.showToast("user为空");
            return;
        }
        CommonInterface.requestDoUpdate(user.getUser_id(),server_path, new AppRequestCallback<App_do_updateActModel>()
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
                            getActivity().finish();
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

            @Override
            protected void onFinish(SDResponse resp)
            {
                super.onFinish(resp);
            }
        });
    }

}
