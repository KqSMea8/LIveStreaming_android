package com.fanwe.live.appview;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 菜单view
 */
public abstract class AMenuView extends BaseAppView
{
    public AMenuView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    public AMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public AMenuView(Context context)
    {
        super(context);
    }

    /**
     * 设置图片
     *
     * @param url 图片url链接
     */
    public abstract void setImageUrl(String url);

    /**
     * 设置图片
     *
     * @param resId 图片资源id
     */
    public abstract void setImageResId(int resId);

    /**
     * 设置未读
     *
     * @param text null-隐藏未读；空字符串-显示小圆点；其他的原样显示
     */
    public abstract void setTextUnread(String text);

    /**
     * 设置菜单大小
     */
    public abstract void setSizeMenu(int width, int height);

    /**
     * 设置图片大小
     */
    public abstract void setSizeImage(int width, int height);

}
