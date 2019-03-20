package com.fanwe.live.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.view.select.SDSelectViewAuto;
import com.fanwe.live.R;

/**
 * 主播插件基础工具itemview
 */
public class RoomPluginToolView extends SDSelectViewAuto
{
    public RoomPluginToolView(Context context)
    {
        super(context);
        init();
    }

    public RoomPluginToolView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public ImageView iv_image;
    public TextView tv_name;

    protected void init()
    {
        setContentView(R.layout.view_room_plugin_tool);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        tv_name = (TextView) findViewById(R.id.tv_name);

        addAutoView(iv_image);
    }

    public void setTextName(String text)
    {
        tv_name.setText(text);
    }
}
