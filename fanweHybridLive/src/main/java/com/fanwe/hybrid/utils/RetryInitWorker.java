package com.fanwe.hybrid.utils;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.event.ERetryInitSuccess;
import com.fanwe.hybrid.http.AppRequestCallback;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.library.adapter.http.model.SDResponse;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.model.SDDelayRunnable;
import com.fanwe.library.receiver.SDNetworkReceiver;
import com.fanwe.library.utils.LogUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.live.common.CommonInterface;
import com.sunday.eventbus.SDEventManager;

public class RetryInitWorker implements SDNetworkReceiver.SDNetworkCallback
{

    /**
     * 重试时间间隔
     */
    private static final int RETRY_TIME_SPAN = 1000 * 5;
    /**
     * 重试次数
     */
    private static final int RETRY_MAX_COUNT = 60;

    private static RetryInitWorker sInstance;
    /**
     * 是否正在重试中
     */
    private boolean mIsInRetry = false;
    /**
     * 是否初始化成功
     */
    private boolean mIsInitSuccess = false;
    /**
     * 重试次数
     */
    private int mRetryCount = 0;
    /**
     * 重试失败窗口
     */
    private Dialog mRetryFailDialog;

    private RetryInitWorker()
    {
        SDNetworkReceiver.addCallback(this);
    }

    public static RetryInitWorker getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new RetryInitWorker();
        }
        return sInstance;
    }

    /**
     * 开始重试初始化
     */
    public void start()
    {
        if (mIsInRetry)
        {
            return;
        }
        mIsInRetry = true;
        mIsInitSuccess = false;
        mRetryCount = 0;

        mRetryRunnable.run();
    }

    private SDDelayRunnable mRetryRunnable = new SDDelayRunnable()
    {
        @Override
        public void run()
        {
            LogUtil.i("retry init:" + mRetryCount);

            if (mIsInitSuccess)
            {
                stop();
                return;
            }

            if (mRetryCount >= RETRY_MAX_COUNT && !mIsInitSuccess)
            {
                // 达到最大重试次数，并且没有初始化成功
                stop();
                showRetryFailDialog();
                return;
            }

            if (!SDNetworkReceiver.isNetworkConnected(App.getApplication()))
            {
                // 无网络
                LogUtil.i("stop retry none net");
                stop();
                return;
            }

            requestInit();
        }
    };

    private void requestInit()
    {
        if (mIsInitSuccess)
        {
            return;
        }
        CommonInterface.requestInit(new AppRequestCallback<InitActModel>()
        {
            @Override
            protected void onSuccess(SDResponse sdResponse)
            {
                onRequestInitSuccess(actModel);
                Log.e("bmbmbmbmbm",actModel.toString());
            }

            @Override
            protected void onError(SDResponse resp)
            {
                onRequestInitError();
                super.onError(resp);
            }
        });
        mRetryCount++;
    }

    /**
     * 请求初始化接口成功回调
     *
     * @param actModel
     */
    private void onRequestInitSuccess(InitActModel actModel)
    {
        if (actModel.isOk())
        {
            LogUtil.i("retry init success");

            mIsInitSuccess = true;
            stop();
            ERetryInitSuccess event = new ERetryInitSuccess();
            SDEventManager.post(event);
        } else
        {
            mRetryRunnable.runDelay(RETRY_TIME_SPAN);
        }
    }

    /**
     * 请求初始化接口失败回调
     */
    private void onRequestInitError()
    {
        mRetryRunnable.runDelay(RETRY_TIME_SPAN);
    }

    /**
     * 结束重试
     */
    public void stop()
    {
        mIsInRetry = false;
        LogUtil.i("stop retry");
    }

    protected void showRetryFailDialog()
    {
        if (SDPackageUtil.isBackground())
        {
            return;
        }
        if (mRetryFailDialog != null && mRetryFailDialog.isShowing())
        {
            mRetryFailDialog.dismiss();
        }
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity == null)
        {
            return;
        }

        SDDialogConfirm dialog = new SDDialogConfirm(activity);
        dialog.setCancelable(false);
        dialog.setTextContent("已经尝试初始化" + RETRY_MAX_COUNT + "次失败，是否继续重试？");
        dialog.setTextConfirm("重试").setCallback(new SDDialogCustom.SDDialogCustomCallback()
        {
            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                start();
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                stop();
            }
        });
        dialog.show();
        mRetryFailDialog = dialog;
    }

    @Override
    public void onNetworkChanged(SDNetworkReceiver.NetworkType type)
    {
        if (type != SDNetworkReceiver.NetworkType.None)
        {
            if (!mIsInitSuccess)
            {
                start();
            }
        }
    }
}
