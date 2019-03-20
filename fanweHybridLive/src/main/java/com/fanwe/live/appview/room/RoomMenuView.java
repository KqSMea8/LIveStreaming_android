package com.fanwe.live.appview.room;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.appview.AMenuView;
import com.fanwe.live.utils.GlideUtil;

/**
 * 直播间菜单
 */
public class RoomMenuView extends AMenuView
{
    private View rl_menu;
    private ImageView iv_image;
    private TextView tv_unread;
    private TextView tv_new;

    public RoomMenuView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init();
    }

    public RoomMenuView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init();
    }

    public RoomMenuView(Context context)
    {
        super(context);
        init();
    }

    @Override
    protected int onCreateContentView()
    {
        return R.layout.view_room_menu;
    }

    protected void init()
    {
        rl_menu = find(R.id.rl_menu);
        iv_image = find(R.id.iv_image);
        tv_unread = find(R.id.tv_unread);
        tv_new = find(R.id.tv_new);
    }

    @Override
    public void setSizeMenu(int width, int height)
    {
        SDViewUtil.setSize(rl_menu, width, height);
    }

    @Override
    public void setSizeImage(int width, int height)
    {
        SDViewUtil.setSize(iv_image, width, height);
    }

    @Override
    public void setTextUnread(String text)
    {
        if (text == null)
        {
            SDViewUtil.setInvisible(tv_unread);
            SDViewUtil.setInvisible(tv_new);
        } else if ("".equals(text))
        {
            SDViewUtil.setInvisible(tv_unread);
            SDViewUtil.setVisible(tv_new);
        } else
        {
            SDViewUtil.setInvisible(tv_new);
            SDViewUtil.setVisible(tv_unread);
            tv_unread.setText(text);
        }
    }

    @Override
    public void setImageUrl(String url)
    {
        GlideUtil.load(url).into(iv_image);
    }

    @Override
    public void setImageResId(int resId)
    {
        iv_image.setImageResource(resId);
    }
}
