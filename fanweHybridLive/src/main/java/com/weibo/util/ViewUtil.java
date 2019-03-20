package com.weibo.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @包名 com.fanwe.xianrou.util
 * @描述
 * @作者 Su
 * @创建时间 2017/3/17 17:40
 **/
public class ViewUtil
{
    private static long lastClickTime;

    public synchronized static boolean isFastClick()
    {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500)
        {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    public static boolean isViewVisible(@NonNull View view)
    {
        return view.getVisibility() == View.VISIBLE;
    }

    public static boolean isViewInVisible(@NonNull View view)
    {
        return view.getVisibility() == View.INVISIBLE;
    }

    public static boolean isViewGone(@NonNull View view)
    {
        return view.getVisibility() == View.GONE;
    }

    public static void setViewVisible(@NonNull View view)
    {
        if (isViewVisible(view))
        {
            return;
        }
        view.setVisibility(View.VISIBLE);
    }

    public static void setViewInVisible(@NonNull View view)
    {
        if (isViewInVisible(view))
        {
            return;
        }
        view.setVisibility(View.INVISIBLE);
    }

    public static void setViewGone(@NonNull View view)
    {
        if (isViewGone(view))
        {
            return;
        }
        view.setVisibility(View.GONE);
    }

    public static boolean setViewVisibleOrGone(@NonNull View view, boolean showFlag)
    {
        if (showFlag)
        {
            setViewVisible(view);
        } else
        {
            setViewGone(view);
        }
        return showFlag;
    }

    public static boolean setViewVisibleOrInvisible(@NonNull View view, boolean flag)
    {
        if (flag)
        {
            setViewVisible(view);
        } else
        {
            setViewInVisible(view);
        }
        return flag;
    }

    public static void setText(@NonNull TextView editText, String text, String defaultText)
    {
        editText.setText(TextUtils.isEmpty(text) ? defaultText : text);
        if (editText instanceof EditText)
        {
            ((EditText) editText).setSelection(editText.getText().length());
        }
    }

    public static void setText(@NonNull TextView editText, String text)
    {
        setText(editText, text, "");
    }

}
