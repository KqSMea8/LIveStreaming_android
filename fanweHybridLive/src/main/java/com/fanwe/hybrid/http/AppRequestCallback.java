package com.fanwe.hybrid.http;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.event.EUnLogin;
import com.fanwe.hybrid.model.BaseActModel;
import com.fanwe.hybrid.model.BaseEncryptModel;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.utils.GsonUtil;
import com.fanwe.hybrid.utils.RetryInitWorker;
import com.fanwe.library.adapter.http.callback.SDModelRequestCallback;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.AESUtil;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDJsonUtil;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.common.CommonInterface;
import com.fanwe.live.utils.LiveUtils;
import com.google.gson.Gson;
import com.sunday.eventbus.SDEventManager;

public abstract class AppRequestCallback<D> extends SDModelRequestCallback<D>
{
    private static final String TAG = "AppRequestCallback";

    public AppRequestParams getAppRequestParams()
    {
        if (getRequestParams() instanceof AppRequestParams)
        {
            return (AppRequestParams) getRequestParams();
        } else
        {
            return null;
        }
    }

    public BaseActModel getBaseActModel()
    {
        if (getActModel() instanceof BaseActModel)
        {
            return (BaseActModel) getActModel();
        } else
        {
            return null;
        }
    }

    /**
     * 处理返回的结果
     *
     * @param resp
     */
    private void dealResponseData(SDResponse resp)
    {
        //先获取已解密的字符串
        String decryptedResult = resp.getDecryptedResult();

        if (TextUtils.isEmpty(decryptedResult))
        {
            //如果已解密字符串为空，则对原始字符串进行解密
            String result = resp.getResult();
            BaseEncryptModel model = SDJsonUtil.json2Object(result, BaseEncryptModel.class);
            decryptedResult = decryptData(model.getOutput());

            //解密给resp对象设置已解密字符串
            resp.setDecryptedResult(decryptedResult);
        }

        LogUtil.i(getActInfo() + "---------->" + decryptedResult);
        if (ApkConstant.DEBUG)
        {
            if (decryptedResult != null && decryptedResult.contains("false"))
            {
//                SDToast.showToast(getActInfo() + " false");
            }
        }
    }

    /**
     * 解密
     *
     * @param data
     * @return
     */
    private String decryptData(String data)
    {
        String decryptedData = AESUtil.decrypt(data, ApkConstant.getAeskey());
        if (TextUtils.isEmpty(decryptedData))
        {
            //如果解密失败，尝试用打包配置的key解密，并清空已保存的key
            decryptedData = AESUtil.decrypt(data, ApkConstant.AES_KEY);
            ApkConstant.setAeskey(null); //very important
        }
        if (TextUtils.isEmpty(decryptedData))
        {
            LiveUtils.updateAeskey(true, null);
            Log.e(TAG, "----------decryptData error");
        }

        return decryptedData;
    }

    @Override
    protected void onSuccessBefore(SDResponse resp)
    {
        dealResponseData(resp);

        // 调用父类方法转实体
        super.onSuccessBefore(resp);

        dealRequestParams();
    }

    private void dealRequestParams()
    { if (getAppRequestParams() == null || getBaseActModel() == null)
        {
            return;
        }

        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            if (getBaseActModel().getInit_version() > initActModel.getInit_version())
            {
                //需要重新初始化
                RetryInitWorker.getInstance().start();
            }
        }

        if (getAppRequestParams().isNeedShowActInfo())
        {
//            SDToast.showToast(getBaseActModel().getError());
        }
        if (getAppRequestParams().isNeedCheckLoginState())
        {
            if (getBaseActModel().getUser_login_status() == 0)
            {
                // 未登录
                if (ApkConstant.DEBUG)
                {
                    Activity activity = SDActivityManager.getInstance().getLastActivity();
                    if (activity == null)
                    {
                        return;
                    }

                    SDDialogConfirm dialogConfirm = new SDDialogConfirm(activity);
                    dialogConfirm.setCanceledOnTouchOutside(false);
                    dialogConfirm.setCancelable(false);
                    dialogConfirm.setTextContent(getActInfo() + "未登录");
                    dialogConfirm.setTextCancel(null).setCallback(new SDDialogCustom.SDDialogCustomCallback()
                    {
                        @Override
                        public void onClickCancel(View v, SDDialogCustom dialog)
                        {
                        }

                        @Override
                        public void onClickConfirm(View v, SDDialogCustom dialog)
                        {
                            dealUnLogin();
                        }
                    }).show();
                } else
                {
                    dealUnLogin();
                }
            }
        }
    }

    /**
     * 处理服务端返回未登录状态
     */
    private void dealUnLogin()
    {
        EUnLogin event = new EUnLogin();
        SDEventManager.post(event);
        if (AppRuntimeWorker.getIsOpenWebviewMain())
        {
            App.getApplication().logout(false, false, true);
        } else
        {
            App.getApplication().logout(true);
        }
    }

    @Override
    protected void onError(SDResponse resp)
    {
        String errorLog = getActInfo() + String.valueOf(resp.getThrowable());

        CommonInterface.reportErrorLog(errorLog);
        Log.e(TAG, "----------onError:" + errorLog);
        if (ApkConstant.DEBUG)
        {
//            SDToast.showToast(errorLog);
        }
    }

    @Override
    protected void onCancel(SDResponse resp)
    {
        LogUtil.i("onCancel:" + getActInfo());
    }

    @Override
    protected void onFinish(SDResponse resp)
    {

    }

    @Override
    protected <T> T parseActModel(String result, Class<T> clazz)
    {
        return SDJsonUtil.json2Object(result, clazz);
    }
}
