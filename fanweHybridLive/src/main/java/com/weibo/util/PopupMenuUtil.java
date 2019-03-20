package com.weibo.util;

import android.app.Activity;
import android.support.annotation.MenuRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;

/**
 * Created by Administrator on 2016/9/9.
 */
public class PopupMenuUtil
{
    private static final int DEFAULT_GROUP_ID = 0;
    private static final int DEFAULT_ORDER_ID = 0;


    public static PopupMenu popMenu(@NonNull Activity activity, @MenuRes int menuRes, @NonNull View anchor, @Nullable PopupMenu.OnMenuItemClickListener listener)
    {
        PopupMenu popupMenu = new PopupMenu(activity, anchor);
        popupMenu.inflate(menuRes);
        popupMenu.setGravity(Gravity.RIGHT);
        if (listener != null)
        {
            popupMenu.setOnMenuItemClickListener(listener);
        }
        popupMenu.show();
        return popupMenu;
    }

    public static PopupMenu popMenu(@NonNull Activity activity,@NonNull int[] ids,@NonNull String[] titles,  @NonNull View anchor,  @Nullable PopupMenu.OnMenuItemClickListener listener)
    {
        PopupMenu popupMenu = new PopupMenu(activity, anchor);

        int size = Math.min(titles.length, ids.length);

        Menu menu = popupMenu.getMenu();
        for (int i = 0; i < size; i++)
        {
            menu.add(DEFAULT_GROUP_ID, ids[i], DEFAULT_ORDER_ID,titles[i]);
        }

        popupMenu.setGravity(Gravity.RIGHT);
        if (listener != null)
        {
            popupMenu.setOnMenuItemClickListener(listener);
        }
        popupMenu.show();
        return popupMenu;
    }


}
