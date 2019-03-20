package com.fanwe.live;

import android.app.Activity;
import android.app.Application;
import android.view.View;

import com.fanwe.baimei.activity.BMHomeActivity;
import com.fanwe.hybrid.app.App;
import com.fanwe.hybrid.constant.ApkConstant;
import com.fanwe.library.common.SDActivityManager;
import com.fanwe.library.dialog.SDDialogMenu;
import com.fanwe.library.utils.SDOtherUtil;
import com.fanwe.library.utils.SDPackageUtil;
import com.fanwe.library.utils.SDShakeListener;
import com.fanwe.live.activity.LiveLoginActivity;
import com.fanwe.live.common.HostManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 仅用于fanwe内部开发调试帮助
 */
public class DebugHelper
{
    private static SDShakeListener shakeListener;

    private DebugHelper()
    {
    }

    public static void init(Application app)
    {
        if (!ApkConstant.DEBUG)
        {
            return;
        }

        if (shakeListener == null)
        {
            shakeListener = new SDShakeListener(app);
        }
        shakeListener.setShakeCallback(new SDShakeListener.ShakeCallback()
        {
            @Override
            public void onShake()
            {
                if (!SDPackageUtil.isBackground())
                {
                    DebugHelper.onShake();
                }
            }
        }).start();
    }

    public static void release()
    {
        if (shakeListener != null)
        {
            shakeListener.stop();
            shakeListener.setShakeCallback(null);
            shakeListener = null;
        }
    }

    private static List<Class<?>> getCanShakeClass()
    {
        List<Class<?>> listClass = new ArrayList<>();
        listClass.add(BMHomeActivity.class);
        listClass.add(LiveLoginActivity.class);
        return listClass;
    }

    private static void onShake()
    {
        if (!ApkConstant.DEBUG)
        {
            return;
        }
        Activity activity = SDActivityManager.getInstance().getLastActivity();
        if (activity == null)
        {
            return;
        }
        if (!getCanShakeClass().contains(activity.getClass()))
        {
            return;
        }

        String title = "当前站：" + SDOtherUtil.getHost(HostManager.getInstance().getApiUrl());
        String[] arrItem = new String[]{
                "原始站：" + SDOtherUtil.getHost(ApkConstant.SERVER_URL),
                "演示站：ilvb.fanwe.net",
                "开发站：livet1.fanwe.net",
                "测试站：ilvbt3.fanwe.net"};

        SDDialogMenu dialog = new SDDialogMenu(activity);
        dialog.setTextTitle(title);
        dialog.setItems(arrItem);
        dialog.setmListener(new SDDialogMenu.SDDialogMenuListener()
        {
            @Override
            public void onCancelClick(View v, SDDialogMenu dialog)
            {

            }

            @Override
            public void onDismiss(SDDialogMenu dialog)
            {

            }

            @Override
            public void onItemClick(View v, int index, SDDialogMenu dialog)
            {
                String url = null;
                switch (index)
                {
                    case 0:
                        url = ApkConstant.SERVER_URL_API;
                        break;
                    case 1:
                        url = "http://ilvb.fanwe.net" + ApkConstant.SERVER_URL_PATH_API;
                        break;
                    case 2:
                        url = "http://livet1.fanwe.net" + ApkConstant.SERVER_URL_PATH_API;
                        break;
                    case 3:
                        url = "http://ilvbt3.fanwe.net" + ApkConstant.SERVER_URL_PATH_API;
                        break;
                    default:
                        url = ApkConstant.SERVER_URL_API;
                        break;
                }

                HostManager.getInstance().setApiUrl(url);
                App.getApplication().logout(false);
            }
        });
        dialog.showBottom();
    }
}
