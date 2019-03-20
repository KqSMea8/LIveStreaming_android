package com.fanwe.live.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.hardware.Camera;
import android.icu.text.AlphabeticIndex;
import android.media.MediaRecorder;
import android.os.Binder;

import java.lang.reflect.Method;

/**
 * Created by Administrator on 2016/12/20.
 */

public class PermissionUtil
{
    /**
     * 判断 悬浮窗口权限是否打开
     *
     * @param context
     * @return true 允许  false禁止
     */
    public static boolean getAppOps(Context context)
    {
        try
        {
            Object object = context.getSystemService("appops");
            if (object == null)
            {
                return false;
            }
            Class localClass = object.getClass();
            Class[] arrayOfClass = new Class[3];
            arrayOfClass[0] = Integer.TYPE;
            arrayOfClass[1] = Integer.TYPE;
            arrayOfClass[2] = String.class;
            Method method = localClass.getMethod("checkOp", arrayOfClass);
            if (method == null)
            {
                return false;
            }
            Object[] arrayOfObject1 = new Object[3];
            arrayOfObject1[0] = Integer.valueOf(24);
            arrayOfObject1[1] = Integer.valueOf(Binder.getCallingUid());
            arrayOfObject1[2] = context.getPackageName();
            int m = ((Integer) method.invoke(object, arrayOfObject1)).intValue();
            return m == AppOpsManager.MODE_ALLOWED;
        } catch (Exception ex)
        {

        }
        return false;
    }

    /**
     * 测试当前摄像头能否被使用
     *
     * @return
     */
    public static boolean isCameraCanUse()
    {
        boolean canUse = true;
        Camera mCamera = null;
        try
        {
            mCamera = Camera.open(0);
            mCamera.setDisplayOrientation(90);
        } catch (Exception e)
        {
            canUse = false;
        } finally
        {
            if (canUse)
            {
                mCamera.release();
            }
        }
        return canUse;
    }
    /**
     * 测试当前摄像头能否被使用
     *
     * @return
     */
    public static boolean isMideaCanUse()
    {
        boolean canUse = true;
        MediaRecorder mediaRecorder = null;
        try
        {
            mediaRecorder = new MediaRecorder();
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (Exception e)
        {
            canUse = false;
        } finally
        {
            if (canUse)
            {
                mediaRecorder.release();
            }
        }
        return canUse;
    }
}
