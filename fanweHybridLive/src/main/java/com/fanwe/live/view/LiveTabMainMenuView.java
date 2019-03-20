package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fanwe.live.R;

/**
 * Created by Administrator on 2016/7/29.
 */
public class LiveTabMainMenuView extends LinearLayout
{
    public ImageView iv_tab_image;

    public LiveTabMainMenuView(Context context)
    {
        super(context);
        init();
    }

    public LiveTabMainMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public LiveTabMainMenuView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        LayoutInflater.from(getContext()).inflate(R.layout.view_live_tab_main_menu, this, true);

        iv_tab_image = (ImageView) findViewById(R.id.iv_tab_image);
    }
}
