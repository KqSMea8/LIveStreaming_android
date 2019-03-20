package com.fanwe.baimei.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    public static boolean isViewInvisible(@NonNull View view)
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

    public static void setViewInvisible(@NonNull View view)
    {
        if (isViewInvisible(view))
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

    public static boolean setViewVisibleOrGone(@NonNull View view, boolean show)
    {
        if (show)
        {
            setViewVisible(view);
        } else
        {
            setViewGone(view);
        }
        return show;
    }

    public static boolean setViewVisibleAndGone(@NonNull View view1, @NonNull View view2, boolean show)
    {
        if (show)
        {
            setViewVisible(view1);
            setViewGone(view2);
        } else
        {
            setViewVisible(view2);
            setViewGone(view1);
        }
        return show;
    }

    public static boolean setViewVisibleOrInvisible(@NonNull View view, boolean show)
    {
        if (show)
        {
            setViewVisible(view);
        } else
        {
            setViewInvisible(view);
        }
        return show;
    }

    public static void setText(@NonNull TextView textView, String text, String defaultText)
    {
        textView.setText(TextUtils.isEmpty(text) ? defaultText : text);
        if (textView instanceof EditText)
        {
            ((EditText) textView).setSelection(textView.getText().length());
        }
    }

    public static void setText(@NonNull TextView textView, String text)
    {
        setText(textView, text, "");
    }



}
