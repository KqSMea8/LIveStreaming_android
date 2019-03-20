package com.fanwe.live.dialog;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.fanwe.library.dialog.SDDialogBase;
import com.fanwe.library.utils.SDViewUtil;
import com.fanwe.live.R;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.view.RoomPluginToolView;

/**
 * Created by yhz on 2017/4/7.
 */

public class LiveViewerPluginDialog extends SDDialogBase
{
    private RoomPluginToolView view_star_store;
    private RoomPluginToolView view_shop_store;

    private ClickListener clickListener;

    public void setClickListener(ClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public LiveViewerPluginDialog(Activity activity)
    {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_live_viewer_plugin);
        paddings(0);
        setCancelable(true);
        setCanceledOnTouchOutside(true);

        initView();
    }

    private void initView()
    {
        view_star_store = (RoomPluginToolView) findViewById(R.id.view_star_store);
        view_shop_store = (RoomPluginToolView) findViewById(R.id.view_shop_store);

        view_star_store.setTextName("星店");
        view_star_store.getViewConfig(view_star_store.iv_image)
                .setImageNormalResId(R.drawable.selector_plugin_tool_star_store);
        view_star_store.updateViewState();

        view_shop_store.setTextName("小店");
        view_shop_store.getViewConfig(view_shop_store.iv_image)
                .setImageNormalResId(R.drawable.selector_plugin_tool_shop_store);
        view_shop_store.updateViewState();

        view_star_store.setOnClickListener(this);
        view_shop_store.setOnClickListener(this);

        //开启购物功能
        if (AppRuntimeWorker.getShopping_goods() == 1 || AppRuntimeWorker.getIsOpenWebviewMain())
        {
            SDViewUtil.setVisible(view_star_store);
        } else
        {
            SDViewUtil.setGone(view_star_store);
        }

        //开启我的小店功能
        if (AppRuntimeWorker.getOpen_podcast_goods() == 1)
        {
            SDViewUtil.setVisible(view_shop_store);
        } else
        {
            SDViewUtil.setGone(view_shop_store);
        }
    }

    @Override
    public void onClick(View v)
    {
        super.onClick(v);
        if (clickListener != null)
        {
            if (v == view_star_store)
            {
                clickListener.onClickStarStore(view_star_store);
            } else if (v == view_shop_store)
            {
                clickListener.onClickShopStore(view_shop_store);
            }
        }
    }

    public interface ClickListener
    {
        /**
         * 我的星店
         */
        void onClickStarStore(RoomPluginToolView view);

        /**
         * 我的小店
         */
        void onClickShopStore(RoomPluginToolView view);
    }
}
