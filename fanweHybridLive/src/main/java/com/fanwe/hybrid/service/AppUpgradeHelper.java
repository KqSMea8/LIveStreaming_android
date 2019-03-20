package com.fanwe.hybrid.service;

import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import com.fanwe.hybrid.dao.InitActModelDao;
import com.fanwe.hybrid.model.InitActModel;
import com.fanwe.hybrid.model.InitUpgradeModel;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDFileUtil;
import com.fanwe.library.utils.SDNotification;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDToast;
import com.fanwe.library.utils.SDTypeParseUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.ReporterUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

/**
 * 检查更新
 */
public class AppUpgradeHelper
{
    public static final int NOTIFICATION_ID = 100;

    private Context context;
    private SDNotification notification;

    /**
     * 0-app启动自动检查，1-手动点击检查更新
     */
    private int checkType;

    /**
     * apk下载链接
     */
    private String apkUrl;
    /**
     * 本地保存的apk名字
     */
    private String apkName;
    /**
     * 本地保存的apk路径
     */
    private String apkPath;
    /**
     * 本地保存apk的文件夹
     */
    private File apkDir;
    /**
     * 服务端日期版本号
     */
    private int serverVersion;
    /**
     * 是否强制升级
     */
    private boolean isForceUpgrade;
    /**
     * 是否正在下载中
     */
    private static boolean isDownloading;

    public AppUpgradeHelper(Context context)
    {
        this.context = context.getApplicationContext();
        init();
    }

    private void init()
    {
        notification = new SDNotification(context);

        apkDir = SDFileUtil.getCacheDir(context, "apk");
    }

    /**
     * 检查更新
     *
     * @param checkType 0-app启动自动检查，1-手动点击检查更新
     */
    public void check(int checkType)
    {
        if (apkDir == null)
        {
            ReporterUtil.reportError("create apk dir fail，can not upgrade app");
            return;
        }

        if (isDownloading)
        {
            SDToast.showToast("正在下载中，请打开通知栏查看");
            return;
        }

        this.checkType = checkType;

        InitActModel initActModel = InitActModelDao.query();
        if (initActModel != null)
        {
            InitUpgradeModel model = initActModel.getVersion();
            if (model != null)
            {
                int localVersion = SDPackageUtil.getCurrentPackageInfo().versionCode;
                serverVersion = SDTypeParseUtil.getInt(model.getServerVersion());

                if (serverVersion > localVersion)
                {
                    // 有新版本
                    apkUrl = model.getFilename();
                    apkName = context.getResources().getString(R.string.app_name) + "_" + serverVersion + ".apk";
                    apkPath = apkDir.getAbsolutePath() + File.separator + apkName;
                    isForceUpgrade = "1".equals(model.getForced_upgrade()) ? true : false;

                    if (new File(apkPath).exists())
                    {
                        dealApkFileExist();
                    } else
                    {
                        if (TextUtils.isEmpty(apkUrl))
                        {
                            SDToast.showToast("发现新版本，但是下载链接为空");
                        } else
                        {
                            showUpgradeDialog(model);
                        }
                    }
                } else
                {
                    if (checkType == 1)
                    {
                        SDToast.showToast("当前已是最新版本");
                    }
                }
            }
        }
    }

    private void dealApkFileExist()
    {
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity == null)
        {
            return;
        }

        SDDialogConfirm dialog = new SDDialogConfirm(activity);
        dialog.setTextContent("新版本App已下载完成").setTextConfirm("安装").setTextCancel("重新下载");
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
                startDownload();
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                SDPackageUtil.installApkPackage(apkPath);
            }

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
            }
        });

        if (isForceUpgrade)
        {
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    /**
     * 显示更新提示窗口
     *
     * @param model
     */
    protected void showUpgradeDialog(final InitUpgradeModel model)
    {
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity == null)
        {
            return;
        }

        SDDialogConfirm dialog = new SDDialogConfirm(activity);
        dialog.setTextContent(model.getAndroid_upgrade()).setTextTitle("更新内容").setTextCancel("取消").setTextConfirm("下载");
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {
            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                startDownload();
            }

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
            }
        });
        if (isForceUpgrade)
        {
            dialog.setTextCancel(null);
            dialog.setCancelable(false);
        }
        dialog.show();
    }

    /**
     * 开始下载
     */
    private void startDownload()
    {
        isDownloading = true;

        RequestParams params = new RequestParams(apkUrl);
        params.setSaveFilePath(apkPath);
        params.setAutoRename(false);
        params.setAutoResume(false);

        x.http().get(params, new Callback.ProgressCallback<File>()
        {

            @Override
            public void onError(Throwable arg0, boolean arg1)
            {
                isDownloading = false;
                SDToast.showToast("下载失败");
            }

            @Override
            public void onCancelled(CancelledException e)
            {
                isDownloading = false;
                notification.cancel(NOTIFICATION_ID);
            }

            @Override
            public void onFinished()
            {
                isDownloading = false;
            }

            @Override
            public void onSuccess(File file)
            {
                isDownloading = false;
                dealDownloadSuccess(file);
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading)
            {
                int progress = (int) ((current * 100) / (total));
                sendDownloadNotification(progress);
            }

            @Override
            public void onWaiting()
            {

            }

            @Override
            public void onStarted()
            {
            }
        });
    }

    /**
     * 下载完成处理
     *
     * @param file
     */
    protected void dealDownloadSuccess(File file)
    {
        if (file != null)
        {
            sendFinishNotification();
            SDPackageUtil.installApkPackage(file.getAbsolutePath());
            SDToast.showToast("下载完成");
        } else
        {
            SDToast.showToast("下载失败");
        }
    }

    /**
     * 发送下载进度通知
     */
    protected void sendDownloadNotification(int progress)
    {
        notification.setSmallIcon(R.drawable.icon)
                .setTicker(apkName + "下载中")
                .setContentTitle("下载中")
                .setContentText(progress + "%" + apkName)
                .notify(NOTIFICATION_ID);
    }

    /**
     * 发送下载完成通知
     */
    protected void sendFinishNotification()
    {
        notification.setSmallIcon(R.drawable.icon)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle("下载中")
                .setContentText(100 + "%" + apkName)
                .notify(NOTIFICATION_ID);

        notification.cancel(NOTIFICATION_ID);
    }
}