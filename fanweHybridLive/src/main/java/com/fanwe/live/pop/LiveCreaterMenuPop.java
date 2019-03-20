package com.fanwe.live.pop;

import android.app.Activity;
import android.view.View;

import com.fanwe.library.customview.SDGridLinearLayout;
import com.fanwe.library.listener.SDItemClickCallback;
import com.fanwe.library.popupwindow.SDPopupWindow;
import com.fanwe.live.R;
import com.fanwe.live.adapter.LiveCreaterMenuAdapter;
import com.fanwe.live.common.AppRuntimeWorker;
import com.fanwe.live.model.LiveCreaterMenuModel;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class LiveCreaterMenuPop extends SDPopupWindow
{

    @ViewInject(R.id.lv_content)
    private SDGridLinearLayout lv_content;

    private Activity activity;
    private LiveCreaterMenuAdapter adapter;
    private List<LiveCreaterMenuModel> listModel = new ArrayList<LiveCreaterMenuModel>();

    private LiveCreaterMenuModel modelShare;
    private LiveCreaterMenuModel modelflash;
    private LiveCreaterMenuModel modelChangeCamera;
    private LiveCreaterMenuModel modelMic;
    private LiveCreaterMenuModel modelBeauty;

    private SDItemClickCallback<LiveCreaterMenuModel> itemClickCallback;

    public void setItemClickCallback(SDItemClickCallback<LiveCreaterMenuModel> itemClickCallback)
    {
        this.itemClickCallback = itemClickCallback;
    }

    public LiveCreaterMenuPop(Activity activity)
    {
        super();
        this.activity = activity;
        init();
    }

    private void init()
    {
        setContentView(R.layout.pop_creater_menu);
        x.view().inject(this, getContentView());
        adapter = new LiveCreaterMenuAdapter(listModel, activity);
        adapter.setItemClickCallback(new SDItemClickCallback<LiveCreaterMenuModel>()
        {
            @Override
            public void onItemClick(int position, LiveCreaterMenuModel item, View view)
            {
                if (item.isEnable())
                {
                    if (itemClickCallback != null)
                    {
                        itemClickCallback.onItemClick(position, item, view);
                    }
                }
            }
        });
        lv_content.setAdapter(adapter);
        bindData();
    }

    public LiveCreaterMenuAdapter getAdapter()
    {
        return adapter;
    }

    private void bindData()
    {
        // 分享
        modelShare = new LiveCreaterMenuModel();
        modelShare.setTextNormal("分享");
        modelShare.setTextSelected("分享");
        modelShare.setTextColorResIdNormal(R.color.white);
        modelShare.setTextColorResIdSelected(R.color.white);
        modelShare.setImageResIdNormal(R.drawable.ic_creater_menu_share);
        modelShare.setImageResIdSelected(R.drawable.ic_creater_menu_share);

        // 闪光灯
        modelflash = new LiveCreaterMenuModel();
        modelflash.setTextNormal("闪光灯");
        modelflash.setTextSelected("闪光灯");
        modelflash.setTextColorResIdNormal(R.color.white);
        modelflash.setTextColorResIdSelected(R.color.main_color);
        modelflash.setImageResIdNormal(R.drawable.ic_creater_menu_flash_off);
        modelflash.setImageResIdSelected(R.drawable.ic_creater_menu_flash_on);

        // 切换摄像头
        modelChangeCamera = new LiveCreaterMenuModel();
        modelChangeCamera.setTextNormal("翻转");
        modelChangeCamera.setTextSelected("翻转");
        modelChangeCamera.setTextColorResIdNormal(R.color.white);
        modelChangeCamera.setTextColorResIdSelected(R.color.white);
        modelChangeCamera.setImageResIdNormal(R.drawable.ic_creater_menu_change_camera);
        modelChangeCamera.setImageResIdSelected(R.drawable.ic_creater_menu_change_camera);

        // 麦克风
        modelMic = new LiveCreaterMenuModel();
        modelMic.setTextNormal("麦克风");
        modelMic.setTextSelected("麦克风");
        modelMic.setTextColorResIdNormal(R.color.white);
        modelMic.setTextColorResIdSelected(R.color.main_color);
        modelMic.setImageResIdNormal(R.drawable.ic_creater_menu_mic_off);
        modelMic.setImageResIdSelected(R.drawable.ic_creater_menu_mic_on);
        modelMic.setSelected(true);

        // 切换美颜
        boolean isEditMode = AppRuntimeWorker.isBeautyEditMode();

        modelBeauty = new LiveCreaterMenuModel();
        modelBeauty.setTextNormal("美颜");
        modelBeauty.setTextSelected("美颜");
        modelBeauty.setImageResIdNormal(R.drawable.ic_live_camera_beauty_off);
        modelBeauty.setTextColorResIdNormal(R.color.white);
        if (isEditMode)
        {
            modelBeauty.setImageResIdSelected(R.drawable.ic_live_camera_beauty_off);
            modelBeauty.setTextColorResIdSelected(R.color.white);
        } else
        {
            modelBeauty.setImageResIdSelected(R.drawable.ic_live_camera_beauty_on);
            modelBeauty.setTextColorResIdSelected(R.color.main_color);
        }
        modelBeauty.setSelected(true);

        listModel.add(modelShare);
        listModel.add(modelflash);
        listModel.add(modelChangeCamera);
        listModel.add(modelMic);
        listModel.add(modelBeauty);

        adapter.updateData(listModel);
    }

    public void dealModelShare(boolean isPrivate)
    {
        modelShare.setVisible(!isPrivate);
        adapter.notifyDataSetChanged();
    }

    public void dealModelFlash(boolean isBackCamera)
    {
        if (isBackCamera)
        {
        } else
        {
            modelflash.setSelected(false);
            adapter.notifyDataSetChanged();
        }
    }
}
