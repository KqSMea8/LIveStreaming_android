package com.fanwe.auction;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.fanwe.auction.event.EBigToSmallScreen;
import com.fanwe.library.dialog.SDDialogConfirm;
import com.fanwe.library.dialog.SDDialogCustom;
import com.fanwe.library.utils.SDResourcesUtil;
import com.fanwe.live.R;
import com.fanwe.live.utils.PermissionUtil;
import com.sunday.eventbus.SDEventManager;

/**
 * Created by Administrator on 2016/12/12.
 */

public class FloatViewPermissionHelp
{
    //SDK 23以上可以判断是否有应用之上的权限
    public static void askForPermissionSdk23(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= 23)
        {
            if (!Settings.canDrawOverlays(activity))
            {
                startAppManage(activity);
            }
        }
    }

    public static final int OVERLAY_PERMISSION_REQ_CODE = 989;

    //跳转App在应用之上显示页面
    public static void startAppManage(Activity activity)
    {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    }

    public static final int APP_SETTINGS_REQ_CODE = 988;

    //跳转App应用信息页面
    public static void startAppSettings(Activity activity)
    {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", activity.getPackageName(), null));
        activity.startActivityForResult(intent, APP_SETTINGS_REQ_CODE);
    }

    public static void checkPermissionAndSwitchSmallScreen(Activity activity)
    {
        boolean is_open_small_screen = SDResourcesUtil.getResources().getBoolean(R.bool.is_open_small_screen);
        if (!is_open_small_screen)
        {
            return;
        }

        if (!PermissionUtil.getAppOps(activity))
        {
            showOpenSettingDialog(activity);
        } else
        {
            EBigToSmallScreen event = new EBigToSmallScreen();
            SDEventManager.post(event);
        }
    }

    public static void showOpenSettingDialog(final Activity activity)
    {
        SDDialogConfirm dialog = new SDDialogConfirm(activity);
        dialog.setTextContent("您未开启悬浮框权限，无法使用小屏功能，请在权限管理中打开？");
        dialog.setTextConfirm("立即前往");
        dialog.setTextCancel("下次在说");
        dialog.setmListener(new SDDialogCustom.SDDialogCustomListener()
        {

            @Override
            public void onDismiss(SDDialogCustom dialog)
            {
            }

            @Override
            public void onClickConfirm(View v, SDDialogCustom dialog)
            {
                FloatViewPermissionHelp.startAppSettings(activity);
            }

            @Override
            public void onClickCancel(View v, SDDialogCustom dialog)
            {
            }
        });
        dialog.show();
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data)
    {
        if (requestCode == APP_SETTINGS_REQ_CODE)
        {
            checkPermissionAndSwitchSmallScreen(activity);
        }
    }
}
