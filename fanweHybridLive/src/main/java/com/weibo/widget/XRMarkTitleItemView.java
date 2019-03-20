package com.weibo.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fanwe.live.R;

/**
 * @包名 com.fanwe.xianrou.view
 * @描述 带标记的标题栏菜单项
 * @作者 Su
 * @创建时间 2017/3/16 11:55
 **/
public class XRMarkTitleItemView extends FrameLayout
{
    private ImageView itemIcon;

    private
    @DrawableRes
    int iconRes;

    private XRSimpleCircleView marker;


    public XRMarkTitleItemView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }


    public XRMarkTitleItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        addView(LayoutInflater.from(getContext()).inflate(R.layout.xr_view_mark_title_item, this, false),
                new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        itemIcon = (ImageView) findViewById(R.id.iv_view_xr_view_mark_title_item);
        marker = (XRSimpleCircleView) findViewById(R.id.view_marker_xr_view_mark_title_item);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XRMarkTitleItemView);

        if (typedArray != null)
        {
            iconRes = typedArray.getResourceId(R.styleable.XRMarkTitleItemView_item_icon, -1);
        }

        typedArray.recycle();

        itemIcon.setImageResource(iconRes);
    }

    public void setIconRes(@DrawableRes int iconRes)
    {
        this.iconRes = iconRes;
    }

    public void showMarker()
    {
        if (marker.getVisibility() != VISIBLE)
        {
            marker.setVisibility(VISIBLE);
        }
    }

    public void hideMarker()
    {
        if (marker.getVisibility() == VISIBLE)
        {
            marker.setVisibility(GONE);
        }
    }

}
