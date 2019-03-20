package com.fanwe.live.activity;

import android.os.Bundle;
import android.view.View;

import com.fanwe.hybrid.activity.BaseTitleActivity;
import com.fanwe.library.model.ImageModel;
import com.fanwe.library.title.SDTitleItem;
import com.fanwe.library.utils.SDToast;
import com.fanwe.live.R;
import com.fanwe.live.appview.SDSelectImageView;
import com.fanwe.live.event.ESelectImage;
import com.sunday.eventbus.SDEventManager;

import java.util.List;

/**
 * Created by Administrator on 2016/9/1.
 */
public class SelectPhotoActivity extends BaseTitleActivity
{
    private SDSelectImageView view_select_image;
    int count;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select_photo);
        count=getIntent().getIntExtra("photo_count",9);
        mTitle.setMiddleTextTop("图片");
        mTitle.initRightItem(1);
        mTitle.getItemRight(0).setTextBot("确定");
        view_select_image = find(R.id.view_select_image);
        view_select_image.callback.set(selectImageCallback);
        view_select_image.setSelectMode(SDSelectImageView.EnumSelectMode.multi);
        view_select_image.loadImage();
    }

    private SDSelectImageView.SelectImageCallback selectImageCallback = new SDSelectImageView.SelectImageCallback()
    {
        @Override
        public boolean onSelect(int position, ImageModel model)
        {
            if (view_select_image.getSelectedImages().size() > count)
            {
                SDToast.showToast("最多只能选择"+count+"张");
                return true;
            } else
            {
                return false;
            }
        }

        @Override
        public void onItemClick(int position, ImageModel item, View view)
        {

        }
    };

    @Override
    public void onCLickRight_SDTitleSimple(SDTitleItem v, int index)
    {
        List<ImageModel> listImage = view_select_image.getSelectedImages();

        if (listImage.isEmpty())
        {
            SDToast.showToast("请选择图片");
        } else
        {
            ESelectImage event = new ESelectImage();
            event.listImage.addAll(listImage);
            SDEventManager.post(event);
            finish();
        }
    }
}
